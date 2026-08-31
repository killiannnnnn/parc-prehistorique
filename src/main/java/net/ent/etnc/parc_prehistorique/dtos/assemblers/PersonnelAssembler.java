package net.ent.etnc.parc_prehistorique.dtos.assemblers;

import net.ent.etnc.parc_prehistorique.dtos.PersonnelDto;
import net.ent.etnc.parc_prehistorique.models.entities.Espece;
import net.ent.etnc.parc_prehistorique.models.entities.Personnel;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PersonnelAssembler {

    public PersonnelDto toDto(Personnel personnel) {
        Set<Long> especesIds = personnel.getEspeces().stream()
                .map(Espece::getId)
                .collect(Collectors.toSet());

        return PersonnelDto.builder()
                .id(personnel.getId())
                .prenom(personnel.getPrenom())
                .nom(personnel.getNom())
                .habilitation(personnel.getHabilitation())
                .especesIds(especesIds)
                .build();
    }

    public Personnel toEntity(PersonnelDto personnelDto) {
        Personnel personnel = new Personnel();
        personnel.setId(personnelDto.getId());
        personnel.setPrenom(personnelDto.getPrenom());
        personnel.setNom(personnelDto.getNom());
        personnel.setHabilitation(personnelDto.getHabilitation());

        if (personnelDto.getEspecesIds() != null) {
            personnelDto.getEspecesIds().forEach(especeId -> {
                Espece espece = new Espece();
                espece.setId(especeId);
                personnel.addEspece(espece);
            });
        }

        return personnel;
    }

    public Collection<PersonnelDto> toDtos(List<Personnel> content) {
        return content.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
