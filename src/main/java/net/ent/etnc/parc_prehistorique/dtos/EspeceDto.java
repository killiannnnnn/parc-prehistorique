package net.ent.etnc.parc_prehistorique.dtos;

import lombok.*;
import net.ent.etnc.parc_prehistorique.models.enums.Dangerosite;
import net.ent.etnc.parc_prehistorique.models.enums.Habilitation;
import net.ent.etnc.parc_prehistorique.models.enums.TypeEspece;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspeceDto {
    // request + response DTO
    private Long id;
    private String nom;
    private TypeEspece typeEspece;
    private Dangerosite dangerosite;
    private String description;
    private Habilitation habilitationMinimale;
    private List<Long> zonesPossiblesIds;
    private Set<Long> especesIncompatiblesIds;
}
