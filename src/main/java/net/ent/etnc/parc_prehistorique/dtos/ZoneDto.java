package net.ent.etnc.parc_prehistorique.dtos;

import lombok.*;
import net.ent.etnc.parc_prehistorique.models.enums.EncloSecurite;
import net.ent.etnc.parc_prehistorique.models.enums.EtatEnclo;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDto {
    // request + response DTO
    private Long id;
    private String matricule;
    private String nom;
    private EncloSecurite encloSecurite;
    private int capaciteMax;
    private EtatEnclo etatEnclo;
    private String description;

}
