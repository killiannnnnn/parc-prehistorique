package net.ent.etnc.parc_prehistorique.dtos.assemblers;

import net.ent.etnc.parc_prehistorique.dtos.ZoneDto;
import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ZoneAssembler {
    public ZoneDto toDto(Zone zone) {
        return null;
    }

    public Zone toEntity(ZoneDto zoneDto) {
        return null;
    }

    public Collection<ZoneDto> toDtos(List<Zone> content) {
        return null;
    }
}
