package net.ent.etnc.parc_prehistorique.dtos;

import lombok.*;
import net.ent.etnc.parc_prehistorique.models.enums.Habilitation;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonnelDto {
    // request + response DTO
    private Long id;
    private String matricule;
    private String prenom;
    private String nom;
    private Habilitation habilitation;
    private Set<Long> especesIds;
}
