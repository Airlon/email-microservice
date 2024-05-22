package br.com.repassa.service;

import br.com.backoffice_repassa_utils_lib.error.exception.RepassaException;
import br.com.repassa.client.TemplateRestClient;
import br.com.repassa.dto.EmailDTO;
import br.com.repassa.dto.SendGridDTO;
import br.com.repassa.dto.VersionDTO;
import br.com.repassa.entity.Email;
import br.com.repassa.repository.EmailRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.*;

class EmailServiceTest {
    @Mock
    Logger LOG;
    @Mock
    Emitter<String> emitter;
    @Mock
    EmailRepository emailRepository;
    @Mock
    TemplateRestClient templateRestClient;
    @InjectMocks
    EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenSendEmail_returnSuccessSent() throws RepassaException {
        when(templateRestClient.searchTemplate(anyString())).thenReturn(new SendGridDTO("id", "name", "generation", "updated_at", Collections.singletonList(VersionDTO.builder()
                .active(true)
                .editor("editor")
                .id("1234")
                .name("name")
                .html_content("html")
                .build())));

        Response result = emailService.sendEmail(new EmailDTO("recipient", "subject", "templateType", "bagId", "username", "stage", Map.of("String", "String")));
        Assertions.assertNotNull(result);
    }

    @Test
    void givenGetEmails_returnSuccessSentEmails() {
        PanacheQuery query = Mockito.mock(PanacheQuery.class);
        when(query.page(Mockito.any())).thenReturn(query);
        when(emailRepository.find(anyString(), any(), anyMap())).thenReturn(query);
        Response result = emailService.getEmails(Integer.valueOf(50), Integer.valueOf(0), "", "stage", null, null);
        Assertions.assertNotNull(result);
    }

    @Test
    void givenGetEmail_returnSuccess(){
        when(emailRepository.findById(any())).thenReturn(Email.builder().build());
        Response response = emailService.getTemplateForId(1L);
        Assertions.assertNotNull(response);
    }
}