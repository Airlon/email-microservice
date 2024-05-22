package br.com.repassa.service;

import br.com.backoffice_repassa_utils_lib.error.exception.RepassaException;
import br.com.repassa.client.TemplateRestClient;
import br.com.repassa.dto.*;
import br.com.repassa.entity.Email;
import br.com.repassa.exception.EmailError;
import br.com.repassa.mapper.SendGridEmailMapper;
import br.com.repassa.repository.EmailRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    @Channel("qu.sendemail")
    Emitter<String> emitter;

    @Inject
    EmailRepository emailRepository;

    private static final String DELIVERED = "DELIVERED";

    @Inject
    @RestClient
    TemplateRestClient templateRestClient;

    @Transactional
    public Response sendEmail(EmailDTO emailDTO) throws RepassaException {
        try {
            LOG.info("Iniciando o envio de e-mail. Parâmetros informados: {}", emailDTO);

            SendGridDTO dto = templateRestClient.searchTemplate(emailDTO.getTemplateType());
            for (VersionDTO versionDTO : dto.getVersions()) {
                StringBuilder buffer = new StringBuilder();
                if(Boolean.TRUE.equals(versionDTO.getActive())) {
                    buffer.append(versionDTO.getHtml_content().replace("\n", ""));
                    SendGridEmailDTO dtoEmail = SendGridEmailMapper.INSTANCE.toDtoEmail(emailDTO, buffer.toString());
                    dtoEmail.setContent(replaceContent(dtoEmail.getContent(), dtoEmail.getAttributesFields()));
                    String templateId = versionDTO.getTemplate_id();
                    String templateName = versionDTO.getName();
                    ObjectMapper objectMapper = new ObjectMapper();
                    emitter.send(objectMapper.writeValueAsString(dtoEmail));

                    salvarEmail(emailDTO, templateId, templateName, dtoEmail.getContent());
                    break;
                }
            }
        } catch (IllegalArgumentException | JsonProcessingException e) {
            LOG.error("Erro ao converter valores do objeto para Map. Erro: {}. Objeto: {}", e, emailDTO);
            throw new RepassaException(EmailError.ERROR_CONVERTER_MAP);
        }

        return Response.ok().build();
    }

    private String replaceContent(String content, Map<String, String> attributesFields) {
        if(attributesFields != null && attributesFields.size() > 0) {
            for (String field : attributesFields.keySet()) {
                content = content.replaceAll(field, attributesFields.get(field));
            }
        }

        return content;
    }

    private void salvarEmail(EmailDTO emailDTO, String templateId, String templateName, String content) {
        Email emailEntity = Email.builder()
                .recipient(emailDTO.getRecipient())
                .subject(emailDTO.getSubject())
                .templateId(templateId)
                .bagId(emailDTO.getBagId())
                .username(emailDTO.getUsername())
                .processingDate(LocalDateTime.now())
                .status(DELIVERED)
                .preview(content)
                .stage(emailDTO.getStage())
                .templateName(templateName)
                .build();
        emailRepository.persist(emailEntity);
    }

    public Response getEmails(Integer limit, Integer page, String search, String stage, Date startDate, Date endDate) {
        if(limit == null) {
            limit = 40;
        }
        if(page == null) {
            limit = 0;
        }
        StringBuilder builder = new StringBuilder();
        Map<String, Object> maps = new HashedMap<>();
        if(StringUtils.isNotEmpty(search)) {
            builder.append(" (recipient = :search or bagId = :search) ");
            maps.put("search", search);
        }

        if(StringUtils.isNotEmpty(stage)) {
            if(!builder.isEmpty()) {
                builder.append(" and ");
            }
            builder.append(" stage = :stage ");
            maps.put("stage", stage);
        }
        if(startDate != null && endDate != null && StringUtils.isNotEmpty(startDate.toString()) && StringUtils.isNotEmpty(endDate.toString()) &&
                (startDate.before(endDate) || startDate.equals(endDate))) {
            if(!builder.isEmpty()) {
                builder.append(" and ");
            }

            builder.append(" processingDate  between '")
                   .append(startDate.toString().concat(" 00:00:00"))
                   .append("' and '")
                   .append(endDate.toString().concat(" 23:59:59"))
                   .append("' ");
        }

        List<Email> emailList =  emailRepository.find(builder.toString(), Sort.descending("processingDate"), maps)
                .page(Page.of(page, limit)).stream().toList();

        return Response.ok(emailList.stream()
                        .map(SendGridEmailMapper.INSTANCE::toEmailResponse)
                .toList()).build();
    }

    public Response getTemplateForId(Long id) {
        Email email = emailRepository.findById(id);

        if(email != null && email.getPreview() != null) {
            PreviewDTO dto = PreviewDTO.builder()
                    .templateId(email.getTemplateId())
                    .templateName(email.getTemplateName())
                    .preview(email.getPreview()).build();
            return Response.ok(dto).build();
        }
        return Response.noContent().build();
    }

    public Response getTemplateEmail(String id) {

        SendGridDTO dto = templateRestClient.getTemplateVersion(id);
        if(dto != null) {
            for (VersionDTO versionDTO : dto.getVersions()) {
                StringBuilder buffer = new StringBuilder();
                if (Boolean.TRUE.equals(versionDTO.getActive())) {
                    buffer.append(versionDTO.getHtml_content().replace("\n", ""));
                    return Response.ok(PreviewDTO.builder().preview(buffer.toString()).build()).build();
                }
            }
        }
        return Response.noContent().build();
    }

    @Transactional
    public Response deleteEmails() {
        LOG.debug("Deletando emails");
        LocalDateTime now = LocalDateTime.now().minusDays(90L);

        emailRepository.delete("processingDate <= '" + now.toLocalDate().toString() + "'");
        return Response.ok().build();
    }
}
