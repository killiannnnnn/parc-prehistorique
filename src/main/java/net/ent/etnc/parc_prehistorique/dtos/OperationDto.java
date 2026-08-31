package net.ent.etnc.parc_prehistorique.dtos;

import lombok.*;
import net.ent.etnc.parc_prehistorique.models.enums.EtatIntervention;
import net.ent.etnc.parc_prehistorique.models.enums.TypeIntervention;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {
    // request + response DTO
    private Long id;
    private String matricule;
    private EtatIntervention etatIntervention;
    private TypeIntervention typeIntervention;
    private LocalDateTime debut;
    private LocalDateTime fin;
    private Long zoneInitialeId;
    private Long zoneArriveeId;
    private Set<Long> animauxIds;
    private Set<Long> personnelsIds;
    private String notes;
}
