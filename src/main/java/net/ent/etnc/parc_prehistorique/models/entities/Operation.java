package net.ent.etnc.parc_prehistorique.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etnc.parc_prehistorique.models.commons.AbstractPersistableWithIdSetter;
import net.ent.etnc.parc_prehistorique.models.enums.EtatIntervention;
import net.ent.etnc.parc_prehistorique.models.enums.TypeIntervention;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "operations"
)
@ToString(of = {"etat", "type", "debut", "fin", "notes"}, callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Operation extends AbstractPersistableWithIdSetter<Long> {

    @Getter
    @Setter
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "etat_intervention", nullable = false)
    private EtatIntervention etatIntervention;

    @Getter
    @Setter
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type_intervention", nullable = false)
    private TypeIntervention typeIntervention;

    @Getter
    @Setter
    @NotNull
    @PastOrPresent
    @Column(name = "debut", nullable = false)
    private LocalDateTime debut;

    @Getter
    @Setter
    @NotNull
    @Column(name = "fin", nullable = false)
    private LocalDateTime fin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = true)
    private Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_depart_id")
    private Zone zoneDepart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_arrivee_id")
    private Zone zoneArrivee;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "operations_personnels",
            joinColumns = @JoinColumn(name = "operation_id"),
            inverseJoinColumns = @JoinColumn(name = "personnel_id")
    )
    private Set<Personnel> personnels = new HashSet<>();

    @Getter
    @Setter
    @Column(name = "notes", nullable = true, length = 100)
    private String notes;

    public Set<Personnel> getPersonnels() {
        return Collections.unmodifiableSet(personnels);
    }

    public void addPersonnel(Personnel personnel) {
        personnels.add(personnel);
    }

    public void removePersonnel(Personnel personnel) {
        personnels.remove(personnel);
    }
}
