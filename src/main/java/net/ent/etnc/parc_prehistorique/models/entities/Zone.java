package net.ent.etnc.parc_prehistorique.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etnc.parc_prehistorique.models.commons.AbstractPersistableWithIdSetter;
import net.ent.etnc.parc_prehistorique.models.enums.EncloSecurite;
import net.ent.etnc.parc_prehistorique.models.enums.EtatEnclo;

@Entity
@Table(
        name = "zones",
        uniqueConstraints = @UniqueConstraint(
                name = "zones__matricule__uk",
                columnNames = {"matricule"}
        )
)
@ToString(of = {"nom", "encloSecurite", "capaciteMax", "etatEnclo", "description"}, callSuper = true)
@EqualsAndHashCode(callSuper = false, of = {"matricule"})
public class Zone extends AbstractPersistableWithIdSetter<Long> {

    @Getter
    @Setter
    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    @Column(name = "matricule", nullable = false, length = 10)
    private String matricule;

    @Getter
    @Setter
    @NotBlank
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Getter
    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "enclo_securite", nullable = false)
    private EncloSecurite encloSecurite;

    @Getter
    @Setter
    @NotNull
    @Positive
    @Max(20)
    @Column(name = "capacite_max", nullable = false)
    private Integer capaciteMax;

    @Getter
    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat_enclo", nullable = false)
    private EtatEnclo etatEnclo;

    @Getter
    @Setter
    @NotBlank
    @Column(name = "description", nullable = false, length = 50)
    private String description;

}
