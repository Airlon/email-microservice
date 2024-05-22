package br.com.repassa.client;

import br.com.repassa.dto.SendGridDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/templates")
@RegisterRestClient(configKey = "template-resource")
public interface TemplateRestClient {

    @GET
    @Path("/{templateType}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    SendGridDTO searchTemplate(@PathParam("templateType") String templateType);

    @GET
    @Path("/template/{templateVersion}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    SendGridDTO getTemplateVersion(@PathParam("templateVersion") String id);
}