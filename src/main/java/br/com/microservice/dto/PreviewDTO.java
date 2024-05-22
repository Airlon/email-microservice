package br.com.repassa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreviewDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4572683997025181341L;

    private String templateId;
    private String templateName;
    private String preview;
}
