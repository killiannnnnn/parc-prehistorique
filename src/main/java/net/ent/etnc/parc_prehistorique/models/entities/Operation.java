package net.ent.etnc.parc_prehistorique.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
        name = "operations",
        uniqueConstraints = @UniqueConstraint(
                name = "operations__matricule__uk",
                columnNames = {"matricule"}
        )
)
@ToString(of = {"etat", "type", "debut", "fin", "notes"}, callSuper = true)
@EqualsAndHashCode(callSuper = false, of = {"matricule"})
public class Operation extends AbstractPersistableWithIdSetter<Long> {

    @Getter
    @Setter
    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    @Column(name = "matricule", nullable = false, length = 10)
    private String matricule;

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
    @Column(name = "debut", nullable = false)
    private LocalDateTime debut;

    @Getter
    @Setter
    @NotNull
    @Column(name = "fin", nullable = false)
    private LocalDateTime fin;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_initiale_id")
    private Zone zoneInitiale;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_arrivee_id")
    private Zone zoneArrivee;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "operations_animaux",
            joinColumns = @JoinColumn(name = "operation_id"),
            inverseJoinColumns = @JoinColumn(name = "animaux_id")
    )
    private Set<Animal> animaux = new HashSet<>();

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

    public Set<Animal> getAnimaux() {
        return Collections.unmodifiableSet(animaux);
    }

    public void addAnimal(Animal animal) {
        animaux.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animaux.remove(animal);
    }

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
