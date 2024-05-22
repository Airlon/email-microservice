package br.com.repassa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendGridDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -599447087167213200L;

    private String id;
    private String name;
    private String generation;
    private String updated_at;
    private List<VersionDTO> versions;
}
