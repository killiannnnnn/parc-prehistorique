package net.ent.etnc.parc_prehistorique.dtos.assemblers;

import net.ent.etnc.parc_prehistorique.dtos.EspeceDto;
import net.ent.etnc.parc_prehistorique.models.entities.Espece;
import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EspeceAssembler {

    public EspeceDto toDto(Espece espece) {
        List<Long> zonesPossiblesIds = espece.getZonesPossibles().stream()
                .map(Zone::getId)
                .collect(Collectors.toList());

        Set<Long> especesIncompatiblesIds = espece.getEspecesIncompatibles().stream()
                .map(Espece::getId)
                .collect(Collectors.toSet());

        return EspeceDto.builder()
                .id(espece.getId())
                .nom(espece.getNom())
                .typeEspece(espece.getTypeEspece())
                .dangerosite(espece.getDangerosite())
                .description(espece.getDescription())
                .habilitationMinimale(espece.getHabilitationMinimale())
                .zonesPossiblesIds(zonesPossiblesIds)
                .especesIncompatiblesIds(especesIncompatiblesIds)
                .build();
    }

    public Espece toEntity(EspeceDto especeDto) {
        Espece espece = new Espece();
        espece.setId(especeDto.getId());
        espece.setNom(especeDto.getNom());
        espece.setTypeEspece(especeDto.getTypeEspece());
        espece.setDangerosite(especeDto.getDangerosite());
        espece.setDescription(especeDto.getDescription());
        espece.setHabilitationMinimale(especeDto.getHabilitationMinimale());

        if (especeDto.getZonesPossiblesIds() != null) {
            especeDto.getZonesPossiblesIds().forEach(zoneId -> {
                Zone zone = new Zone();
                zone.setId(zoneId);
                espece.addZone(zone);
            });
        }

        if (especeDto.getEspecesIncompatiblesIds() != null) {
            especeDto.getEspecesIncompatiblesIds().forEach(incompatibleId -> {
                Espece incompatible = new Espece();
                incompatible.setId(incompatibleId);
                espece.addEspece(incompatible);
            });
        }

        return espece;
    }

    public Collection<EspeceDto> toDtos(List<Espece> content) {
        return content.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
