package br.com.repassa.mapper;

import br.com.repassa.dto.EmailDTO;
import br.com.repassa.dto.EmailResponseDTO;
import br.com.repassa.dto.SendGridEmailDTO;
import br.com.repassa.entity.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SendGridEmailMapper {

    SendGridEmailMapper INSTANCE = Mappers.getMapper(SendGridEmailMapper.class);

    @Mapping(target = "content", source="content")
    @Mapping(target = "recipient", source="emailDTO.recipient")
    @Mapping(target = "subject", source="emailDTO.subject")
    @Mapping(target = "templateType", source="emailDTO.templateType")
    @Mapping(target = "bagId", source="emailDTO.bagId")
    @Mapping(target = "username", source="emailDTO.username")
    @Mapping(target = "attributesFields", source="emailDTO.attributesFields")
    SendGridEmailDTO toDtoEmail(EmailDTO emailDTO, String content);

    EmailResponseDTO toEmailResponse(Email email);
}
