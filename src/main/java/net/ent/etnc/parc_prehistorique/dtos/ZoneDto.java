package net.ent.etnc.parc_prehistorique.dtos;

import lombok.*;
import net.ent.etnc.parc_prehistorique.models.enums.Enclo;
import net.ent.etnc.parc_prehistorique.models.enums.EtatEnclo;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDto {
    // request + response DTO
    private Long id;
    private String nom;
    private Enclo enclo;
    private int capaciteMax;
    private EtatEnclo etat;
    private String description;

}
