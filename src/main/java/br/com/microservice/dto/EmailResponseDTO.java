package br.com.repassa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1463895865665075836L;

    private Long id;
    private String recipient;
    private String subject;
    private String templateVersion;
    private String bagId;
    private String username;
    private LocalDateTime processingDate;
    private String status;
    private String stage;
}
