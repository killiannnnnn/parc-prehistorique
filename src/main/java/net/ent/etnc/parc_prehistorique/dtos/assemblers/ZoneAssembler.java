package net.ent.etnc.parc_prehistorique.dtos.assemblers;

import net.ent.etnc.parc_prehistorique.dtos.ZoneDto;
import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ZoneAssembler {
    public ZoneDto toDto(Zone zone) {
        return ZoneDto.builder()
                .id(zone.getId())
                .matricule(zone.getMatricule())
                .nom(zone.getNom())
                .typeEnclo(zone.getTypeEnclo())
                .capaciteMax(zone.getCapaciteMax())
                .etatEnclo(zone.getEtatEnclo())
                .description(zone.getDescription())
                .build();
    }

    public Zone toEntity(ZoneDto zoneDto) {
        Zone zone = new Zone();
        zone.setId(zoneDto.getId());
        zone.setMatricule(zoneDto.getMatricule());
        zone.setNom(zoneDto.getNom());
        zone.setTypeEnclo(zoneDto.getTypeEnclo());
        zone.setCapaciteMax(zoneDto.getCapaciteMax());
        zone.setEtatEnclo(zoneDto.getEtatEnclo());
        zone.setDescription(zoneDto.getDescription());
        return zone;
    }

    public Collection<ZoneDto> toDtos(List<Zone> content) {
        return content.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
