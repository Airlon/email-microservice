package br.com.repassa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VersionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8527014944322363017L;

    private String id;
    private Integer user_id;
    private String template_id;
    private Boolean active;
    private String name;
    private String html_content;
    private String plain_content;
    private Boolean generate_plain_content;
    private String subject;
    private String updated_at;
    private String editor;
    private String test_data;
    private String thumbnail_url;
}
