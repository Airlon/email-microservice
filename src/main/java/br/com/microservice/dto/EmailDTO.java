package br.com.repassa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3965939545199506119L;

    @NotEmpty(message = "Campo recipient é obrigatório")
    private String recipient;
    @NotEmpty(message = "Campo subject é obrigatório")
    private String subject;
    @NotEmpty(message = "Campo templateType é obrigatório")
    private String templateType;
    @NotEmpty(message = "Campo bagId é obrigatório")
    private String bagId;
    @NotEmpty(message = "Campo username é obrigatório")
    private String username;
    private String stage;
    private Map<String, String> attributesFields;

}
