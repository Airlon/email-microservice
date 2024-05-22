package br.com.repassa.resource;

import br.com.backoffice_repassa_utils_lib.error.exception.RepassaException;
import br.com.repassa.dto.EmailDTO;
import br.com.repassa.service.EmailService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;

@Tag(name = "Envio de e-mail", description = "Responsável por receber as requisições de envio de e-mail e enviar à " +
        "fila de envio de e-mails")
@Path("/v1/email")
public class EmailResource {
    private static final Logger LOG = LoggerFactory.getLogger(EmailResource.class);
    @Inject
    EmailService emailService;

    @POST
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Envia mensagem à fila de e-mail", description = "Recebe a mensagem a ser enviada e coloca na" +
            "fila de envio de e-mails ")
    public Response sendEmail(@Valid @RequestBody EmailDTO emailDTO) throws RepassaException {
        return emailService.sendEmail(emailDTO);
    }

    @GET
    @RolesAllowed({"admin", "TRANSACIONAIS.VISUALIZAR_PROCESSAMENTO_SDB"})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listagem de email", description = "Lista os emails enviado")
    public Response getList(@DefaultValue("40") @QueryParam("limit") Integer limit, @DefaultValue("0") @QueryParam("page") Integer page,
                            @QueryParam("search") String search, @QueryParam("stage") String stage,
                            @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate) {

        LOG.debug("Buscando Lista ");
        return emailService.getEmails(limit, page, search, stage, startDate, endDate);
    }

    @GET
    @RolesAllowed({"admin", "TRANSACIONAIS.VISUALIZAR_PROCESSAMENTO_SDB"})
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get email", description = "Buscar o email enviado")
    public Response getEmail(@PathParam("id") Long id) {
        LOG.debug("Buscando Template: " + id);
        return emailService.getTemplateForId(id);
    }


    @GET
    @RolesAllowed({"admin", "TRANSACIONAIS.VISUALIZAR_PROCESSAMENTO_SDB"})
    @Path("/template/{templateVersion}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get template", description = "Buscar o template do email enviado")
    public Response getTemplate(@PathParam("templateVersion") String templateVersion) {
        LOG.debug("Buscando Template: " + templateVersion);
        return emailService.getTemplateEmail(templateVersion);
    }
    @DELETE
    @RolesAllowed({"admin", "user"})
    @Path("/job")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Apagar Emails de 90 dias", description = "Apagar email de 90 dias atrás")
    public Response deleteEmails() {

        LOG.debug("Deletando deleteEmails");
        return emailService.deleteEmails();
    }
}
