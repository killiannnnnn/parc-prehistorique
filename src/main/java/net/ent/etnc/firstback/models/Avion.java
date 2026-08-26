package net.ent.etnc.firstback.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etnc.firstback.models.commons.AbstractPersistableWithIdSetter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "avions",
        uniqueConstraints = @UniqueConstraint(
                name = "avions__immat__uk",
                columnNames = {"immat"}
        )
)
@ToString(of = {"immat", "envergure", "puissance", "actif", "misEnService"}, callSuper = true)
@EqualsAndHashCode(of = "immat", callSuper = false)
public class Avion extends AbstractPersistableWithIdSetter<Long> {

    @Getter
    @Setter
    @NotBlank(message = "l'immat est obligatoire")
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$")
    @Column(name = "immat", nullable = false, length = 6)
    private String immat;

    @Getter
    @Setter
    @NotNull
    @Positive
    @Max(200)
    @Column(name = "envergure", nullable = false)
    private Integer envergure;

    @Getter
    @Setter
    @NotNull
    @Positive
    @Max(200_000)
    @Column(name = "puissance", nullable = false)
    private Integer puissance;

    @Getter
    @Setter
    @NotNull
    @Column(name = "actif", nullable = false)
    private Boolean actif;

    @Getter
    @Setter
    @NotNull
    @PastOrPresent
    @Column(name = "mis_en_service", nullable = false)
    private LocalDate misEnService;

    
    @Getter
    @Setter
    @Valid
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "avions_id", foreignKey = @ForeignKey(name = "avions_pilotes__avions_id__fk"))
    private List<Pilote> pilotes = new ArrayList<>();
}
