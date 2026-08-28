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
                .nom(zone.getNom())
                .enclo(zone.getEnclo())
                .capaciteMax(zone.getCapaciteMax())
                .etat(zone.getEtat())
                .description(zone.getDescription())
                .build();
    }

    public Zone toEntity(ZoneDto zoneDto) {
        Zone zone = new Zone();
        zone.setId(zoneDto.getId());
        zone.setNom(zoneDto.getNom());
        zone.setEnclo(zoneDto.getEnclo());
        zone.setCapaciteMax(zoneDto.getCapaciteMax());
        zone.setEtat(zoneDto.getEtat());
        zone.setDescription(zoneDto.getDescription());
        return zone;
    }

    public Collection<ZoneDto> toDtos(List<Zone> content) {
        return content.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
