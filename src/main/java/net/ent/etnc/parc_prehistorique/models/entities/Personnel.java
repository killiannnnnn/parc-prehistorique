package net.ent.etnc.parc_prehistorique.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etnc.parc_prehistorique.models.commons.AbstractPersistableWithIdSetter;
import net.ent.etnc.parc_prehistorique.models.enums.Habilitation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "personnels"
)
@ToString(of = {"prenom", "nom", "habilitation"}, callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Personnel extends AbstractPersistableWithIdSetter<Long> {

    @Getter
    @Setter
    @NotBlank
    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;

    @Getter
    @Setter
    @NotBlank
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Getter
    @Setter
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "habilitation", nullable = false)
    private Habilitation habilitation;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "personnels_especes",
            joinColumns = @JoinColumn(name = "personnel_id"),
            inverseJoinColumns = @JoinColumn(name = "espece_id")
    )
    private Set<Espece> especes = new HashSet<>();

    public Set<Espece> getEspeces() {
        return Collections.unmodifiableSet(especes);
    }

    public void addEspece(Espece espece) {
        this.especes.add(espece);
    }

    public void removeEspece(Espece espece) {
        this.especes.remove(espece);
    }

    public void supprimerEspeces() {
        this.especes.clear();
    }
}