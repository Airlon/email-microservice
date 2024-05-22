package br.com.repassa.resource;

import br.com.backoffice_repassa_utils_lib.error.exception.RepassaException;
import br.com.repassa.dto.EmailDTO;
import br.com.repassa.service.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.util.Map;

import static org.mockito.Mockito.*;

class EmailResourceTest {
    @Mock
    EmailService emailService;
    @InjectMocks
    EmailResource emailResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenSendEmail_returnSuccess() throws RepassaException {
        when(emailService.sendEmail(any())).thenReturn(null);

        Response result = emailResource.sendEmail(new EmailDTO("recipient", "subject", "templateType", "bagId", "username", "stage", Map.of("String", "String")));
        Assertions.assertEquals(null, result);
    }

    @Test
    void givenGetEmails_returnSuccess() {
        when(emailService.getEmails(anyInt(), anyInt(), anyString(), anyString(), any(), any())).thenReturn(null);

        Response result = emailResource.getList(Integer.valueOf(0), Integer.valueOf(0), "search", "stage", null, null);
        Assertions.assertEquals(null, result);
    }

    @Test
    void givenGetEmailById_returnSuccess() {
        when(emailService.getTemplateForId(any())).thenReturn(null);

        Response result = emailResource.getEmail(1L);
        Assertions.assertEquals(null, result);
    }
}