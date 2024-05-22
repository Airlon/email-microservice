package br.com.repassa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email")
public class Email extends PanacheEntityBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 205840486457337573L;

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String recipient;

    @NotNull
    private String subject;

    @Column(name = "template_id")
    @NotNull
    private String templateId;

    @Column(name = "bag_id")
    @NotNull
    private String bagId;

    @NotNull
    private String username;

    @Column(name = "processing_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @NotNull
    private LocalDateTime processingDate;

    @Column(name = "status")
    private String status;

    @Column(name = "preview")
    private String preview;

    @Column(name = "stage")
    private String stage;

    @Column(name = "template_name")
    private String templateName;

}
