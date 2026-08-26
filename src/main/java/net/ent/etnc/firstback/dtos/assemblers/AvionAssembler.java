package net.ent.etnc.firstback.dtos.assemblers;

import net.ent.etnc.firstback.dtos.AvionDto;
import net.ent.etnc.firstback.models.Avion;
import net.ent.etnc.firstback.models.Pilote;
import net.ent.etnc.firstback.services.PiloteService;
import net.ent.etnc.firstback.services.commons.ServiceException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class AvionAssembler {

    private final PiloteService piloteService;

    public AvionAssembler(PiloteService piloteService) {
        this.piloteService = piloteService;
    }

    public AvionDto toDto(Avion avion) {
        return AvionDto.builder()
                .id(avion.getId())
                .immat(avion.getImmat())
                .envergure(avion.getEnvergure())
                .puissance(avion.getPuissance())
                .actif(avion.getActif())
                .miseEnService(avion.getMisEnService())
                .build();
    }

    public Avion toEntity(AvionDto avionDto) {
        Avion avion = new Avion();
        avion.setId(avionDto.getId());
        avion.setImmat(avionDto.getImmat());
        avion.setEnvergure(avionDto.getEnvergure());
        avion.setPuissance(avionDto.getPuissance());
        avion.setActif(avionDto.getActif());
        avion.setMisEnService(avionDto.getMiseEnService());

        if (avionDto.getPilotesIds() != null && !avionDto.getPilotesIds().isEmpty()) {
            for (Long id : avionDto.getPilotesIds()) {
                Optional<Pilote> piloteOptional = piloteService.findById(id);
                if (piloteOptional.isEmpty()) {
                    throw new ServiceException("Pilote with id " + id + " not found");
                }
                avion.getPilotes().add(piloteOptional.get());
            }
        }

        return avion;
    }

    public Collection<AvionDto> toDtos(List<Avion> content) {
        Collection<AvionDto> avionDtos = new ArrayList<>();
        for (Avion avion : content) {
            avionDtos.add(toDto(avion));
        }
        return avionDtos;
    }
}
