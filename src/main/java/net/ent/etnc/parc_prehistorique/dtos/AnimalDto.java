package net.ent.etnc.parc_prehistorique.dtos;

import lombok.*;
import net.ent.etnc.parc_prehistorique.models.enums.RegimeAlimentaire;
import net.ent.etnc.parc_prehistorique.models.enums.Sante;
import net.ent.etnc.parc_prehistorique.models.enums.Sexe;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDto {
    // request + response DTO
    private Long id;
    private String nom;
    private Long especeId;
    private Long zoneId;
    private Sante sante;
    private Sexe sexe;
    private RegimeAlimentaire regimeAlimentaire;
    private LocalDateTime dateNaissance;
    private LocalDateTime dateDeces;
}
