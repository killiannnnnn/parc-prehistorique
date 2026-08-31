package net.ent.etnc.parc_prehistorique.dtos.assemblers;

import net.ent.etnc.parc_prehistorique.dtos.OperationDto;
import net.ent.etnc.parc_prehistorique.models.entities.Animal;
import net.ent.etnc.parc_prehistorique.models.entities.Operation;
import net.ent.etnc.parc_prehistorique.models.entities.Personnel;
import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OperationAssembler {

    public OperationDto toDto(Operation operation) {
        Set<Long> animauxIds = operation.getAnimaux().stream()
                .map(Animal::getId)
                .collect(Collectors.toSet());

        Set<Long> personnelsIds = operation.getPersonnels().stream()
                .map(Personnel::getId)
                .collect(Collectors.toSet());

        return OperationDto.builder()
                .id(operation.getId())
                .matricule(operation.getMatricule())
                .etatIntervention(operation.getEtatIntervention())
                .typeIntervention(operation.getTypeIntervention())
                .debut(operation.getDebut())
                .fin(operation.getFin())
                .zoneInitialeId(operation.getZoneInitiale() != null ? operation.getZoneInitiale().getId() : null)
                .zoneArriveeId(operation.getZoneArrivee() != null ? operation.getZoneArrivee().getId() : null)
                .animauxIds(animauxIds)
                .personnelsIds(personnelsIds)
                .notes(operation.getNotes())
                .build();
    }

    public Operation toEntity(OperationDto operationDto) {
        Operation operation = new Operation();
        operation.setId(operationDto.getId());
        operation.setMatricule(operationDto.getMatricule());
        operation.setEtatIntervention(operationDto.getEtatIntervention());
        operation.setTypeIntervention(operationDto.getTypeIntervention());
        operation.setDebut(operationDto.getDebut());
        operation.setFin(operationDto.getFin());
        operation.setNotes(operationDto.getNotes());

        if (operationDto.getZoneInitialeId() != null) {
            Zone zoneInitiale = new Zone();
            zoneInitiale.setId(operationDto.getZoneInitialeId());
            operation.setZoneInitiale(zoneInitiale);
        }

        if (operationDto.getZoneArriveeId() != null) {
            Zone zoneArrivee = new Zone();
            zoneArrivee.setId(operationDto.getZoneArriveeId());
            operation.setZoneArrivee(zoneArrivee);
        }

        if (operationDto.getAnimauxIds() != null) {
            operationDto.getAnimauxIds().forEach(animalId -> {
                Animal animal = new Animal();
                animal.setId(animalId);
                operation.addAnimal(animal);
            });
        }

        if (operationDto.getPersonnelsIds() != null) {
            operationDto.getPersonnelsIds().forEach(personnelId -> {
                Personnel personnel = new Personnel();
                personnel.setId(personnelId);
                operation.addPersonnel(personnel);
            });
        }

        return operation;
    }

    public Collection<OperationDto> toDtos(List<Operation> content) {
        return content.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
