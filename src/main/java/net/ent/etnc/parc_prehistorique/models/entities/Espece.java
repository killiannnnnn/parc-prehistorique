package net.ent.etnc.parc_prehistorique.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etnc.parc_prehistorique.models.commons.AbstractPersistableWithIdSetter;
import net.ent.etnc.parc_prehistorique.models.enums.Dangerosite;
import net.ent.etnc.parc_prehistorique.models.enums.Habilitation;
import net.ent.etnc.parc_prehistorique.models.enums.TypeEspece;

import java.util.*;


@Entity
@Table(
        name = "especes"
)
@ToString(of = {"nom", "typeEspece", "dangerosite", "description", "habilitationMinimale"}, callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Espece extends AbstractPersistableWithIdSetter<Long> {

    @Getter
    @Setter
    @NotBlank
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Getter
    @Setter
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type_espece", nullable = false)
    private TypeEspece typeEspece;

    @Getter
    @Setter
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "dangerosite", nullable = false)
    private Dangerosite dangerosite;

    @Getter
    @Setter
    @NotBlank
    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Getter
    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "habilitation_minimale", nullable = false)
    private Habilitation habilitationMinimale;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "espece_zone",
            joinColumns = @JoinColumn(name = "espece_id"),
            inverseJoinColumns = @JoinColumn(name = "zone_id")
    )
    private List<Zone> zonesPossibles = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "especes_incompatibles",
            joinColumns = @JoinColumn(name = "espece_id"),
            inverseJoinColumns = @JoinColumn(name = "incompatible_id")
    )
    private Set<Espece> especesIncompatibles = new HashSet<>();

    public List<Zone> getZonesPossibles() {
        return Collections.unmodifiableList(zonesPossibles);
    }

    public void addZone(Zone zone) {
        zonesPossibles.add(zone);
    }

    public void removeZone(Zone zone) {
        zonesPossibles.remove(zone);
    }

    public Set<Espece> getEspecesIncompatibles() {
        return Collections.unmodifiableSet(especesIncompatibles);
    }

    public void addEspece(Espece espece) {
        especesIncompatibles.add(espece);
    }

    public void removeEspece(Espece espece) {
        especesIncompatibles.remove(espece);
    }
}
