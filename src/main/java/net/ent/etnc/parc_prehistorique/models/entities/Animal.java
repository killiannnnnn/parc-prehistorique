package net.ent.etnc.parc_prehistorique.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etnc.parc_prehistorique.models.commons.AbstractPersistableWithIdSetter;
import net.ent.etnc.parc_prehistorique.models.enums.RegimeAlimentaire;
import net.ent.etnc.parc_prehistorique.models.enums.Sante;
import net.ent.etnc.parc_prehistorique.models.enums.Sexe;

import java.time.LocalDate;

@Entity
@Table(
        name = "animaux"
)
@ToString(of = {
        "nom",
        "sante",
        "sexe",
        "regimeAlimentaire",
        "dateNaissance",
        "dateDeces"
}, callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Animal extends AbstractPersistableWithIdSetter<Long> {

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
    @JoinColumn(name = "zone_id", nullable = true, foreignKey = @ForeignKey(name = "fk__animal__zone"))
    private Zone zone; // VERIFIE COMPATIBILITE AVEC LA ZONE

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
    private LocalDate dateNaissance;

    @Getter
    @Setter
    @PastOrPresent
    @Column(name = "date_deces", nullable = true)
    private LocalDate dateDeces; // VERIFIER SI IL EST VIVANT POUR FAIRE DES OPERATIONS ETC

}
