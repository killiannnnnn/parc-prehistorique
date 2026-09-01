package net.ent.etnc.parc_prehistorique.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etnc.parc_prehistorique.models.commons.AbstractPersistableWithIdSetter;
import net.ent.etnc.parc_prehistorique.models.enums.RegimeAlimentaire;
import net.ent.etnc.parc_prehistorique.models.enums.Sante;
import net.ent.etnc.parc_prehistorique.models.enums.Sexe;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "animaux",
        uniqueConstraints = @UniqueConstraint(
                name = "animaux__matricule__uk",
                columnNames = {"matricule"}
        )
)
@ToString(of = {
        "nom",
        "sante",
        "sexe",
        "regimeAlimentaire",
        "dateNaissance",
        "dateDeces"
}, callSuper = true)
@EqualsAndHashCode(callSuper = false, of = {"matricule"})
public class Animal extends AbstractPersistableWithIdSetter<Long> {

    @Getter
    @Setter
    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    @Column(name = "matricule", nullable = false, length = 10)
    private String matricule;

    @Getter
    @Setter
    @NotBlank(message = "Le nom est obligatoire")
    @Column(name = "nom", nullable = false, length = 20)
    private String nom;

    @Getter
    @Setter
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "espece_id", nullable = false, foreignKey = @ForeignKey(name = "fk__animal__espece"))
    private Espece espece;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = false, foreignKey = @ForeignKey(name = "fk__animal__zone"))
    private Zone zone;

    @Getter
    @Setter
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "sante", nullable = false, length = 20)
    private Sante sante;

    @Getter
    @Setter
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "sexe", nullable = false, length = 20)
    private Sexe sexe;

    @Getter
    @Setter
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "regime_alimentaire", nullable = false, length = 20)
    private RegimeAlimentaire regimeAlimentaire;

    @Getter
    @Setter
    @NotNull
    @PastOrPresent
    @Column(name = "date_naissance", nullable = false)
    private LocalDateTime dateNaissance;

    @Getter
    @Setter
    @PastOrPresent
    @Column(name = "date_deces", nullable = true)
    private LocalDateTime dateDeces; // VERIFIER SI IL EST VIVANT POUR FAIRE DES OPERATIONS ETC

}
