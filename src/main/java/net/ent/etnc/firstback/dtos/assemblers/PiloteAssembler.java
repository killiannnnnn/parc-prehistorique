package net.ent.etnc.firstback.dtos.assemblers;

import net.ent.etnc.firstback.dtos.PiloteDto;
import net.ent.etnc.firstback.models.Pilote;
import net.ent.etnc.firstback.services.PiloteService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class PiloteAssembler {

    private final PiloteService piloteService;

    public PiloteAssembler(PiloteService piloteService) {
        this.piloteService = piloteService;
    }

    public PiloteDto toDto(Pilote pilote) {
        return PiloteDto.builder()
                .id(pilote.getId())
                .nom(pilote.getNom())
                .prenom(pilote.getPrenom())
                .nid(pilote.getNid())
                .grade(pilote.getGrade())
                .build();
    }

    public Pilote toEntity(PiloteDto piloteDto) {
        Pilote pilote = new Pilote();
        pilote.setId(piloteDto.getId());
        pilote.setNom(piloteDto.getNom());
        pilote.setPrenom(piloteDto.getPrenom());
        pilote.setNid(piloteDto.getNid());
        pilote.setGrade(piloteDto.getGrade());

        return pilote;
    }

    public Collection<PiloteDto> toDtos(List<Pilote> content) {
        Collection<PiloteDto> piloteDtos = new ArrayList<>();
        for (Pilote pilote : content) {
            piloteDtos.add(toDto(pilote));
        }
        return piloteDtos;
    }
}
