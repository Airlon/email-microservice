package br.com.repassa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendGridEmailDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7923347310849687622L;

    private String recipient;
    private String subject;
    private String templateType;
    private String bagId;
    private String username;
    private String content;
    private Map<String, String> attributesFields;
}
