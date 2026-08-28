package net.ent.etnc.parc_prehistorique.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etnc.parc_prehistorique.models.commons.AbstractPersistableWithIdSetter;
import net.ent.etnc.parc_prehistorique.models.enums.EtatEnclo;
import net.ent.etnc.parc_prehistorique.models.enums.TypeEnclo;

@Entity
@Table(
        name = "zones"
)
@ToString(of = {"nom", "enclo", "capaciteMax", "etat", "description"}, callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Zone extends AbstractPersistableWithIdSetter<Long> {

    @Getter
    @Setter
    @NotBlank
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Getter
    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "enclo", nullable = false)
    private TypeEnclo enclo;

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
    @Column(name = "etat", nullable = false)
    private EtatEnclo etat;

    @Getter
    @Setter
    @NotBlank
    @Column(name = "description", nullable = false, length = 50)
    private String description;

}
