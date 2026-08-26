package net.ent.etnc.firstback.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etnc.firstback.models.commons.AbstractPersistableWithIdSetter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Entity
@Table(
        name = "pilotes",
        uniqueConstraints = @UniqueConstraint(
                name = "pilotes__nid__uk",
                columnNames = {"nid"}
        )
)
@ToString(callSuper = true, of = {"nid", "nom", "prenom", "grade", "dateNaissance"})
@EqualsAndHashCode(callSuper = false, of = {"nid"})
public class Pilote extends AbstractPersistableWithIdSetter<Long> {

    @Getter
    @Setter
    @NotBlank(message = "Le nid est obligatoire")
    @Pattern(regexp = "^[0-9]{10}$")
    @Column(name = "nid", nullable = false, length = 10)
    private String nid;

    @Getter
    @Setter
    @NotBlank(message = "le nom est obligatoire")
    @Length(max = 50)
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Getter
    @Setter
    @NotBlank(message = "le prenom est obligatoire")
    @Length(max = 50)
    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;

    @Getter
    @Setter
    @NotBlank(message = "le grade est obligatoire")
    @Length(max = 50)
    @Column(name = "grade", nullable = false, length = 50)
    private String grade;

    @Getter
    @Setter
    @NotNull(message = "la date de naissance est obligatoire")
    @Past(message = "la date de naissance doit être dans le passé")
    @Column(name = "dateNaissance", nullable = false)
    private LocalDate dateNaissance;

}
