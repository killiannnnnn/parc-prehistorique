package net.ent.etnc.parc_prehistorique.dtos.assemblers;

import net.ent.etnc.parc_prehistorique.dtos.AnimalDto;
import net.ent.etnc.parc_prehistorique.models.entities.Animal;
import net.ent.etnc.parc_prehistorique.models.entities.Espece;
import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnimalAssembler {

    public AnimalDto toDto(Animal animal) {
        return AnimalDto.builder()
                .id(animal.getId())
                .nom(animal.getNom())
                .especeId(animal.getEspece() != null ? animal.getEspece().getId() : null)
                .zoneId(animal.getZone() != null ? animal.getZone().getId() : null)
                .sante(animal.getSante())
                .sexe(animal.getSexe())
                .regimeAlimentaire(animal.getRegimeAlimentaire())
                .dateNaissance(animal.getDateNaissance())
                .dateDeces(animal.getDateDeces())
                .build();
    }

    public Animal toEntity(AnimalDto animalDto) {
        Animal animal = new Animal();
        animal.setId(animalDto.getId());
        animal.setNom(animalDto.getNom());

        if (animalDto.getEspeceId() != null) {
            Espece espece = new Espece();
            espece.setId(animalDto.getEspeceId());
            animal.setEspece(espece);
        }

        if (animalDto.getZoneId() != null) {
            Zone zone = new Zone();
            zone.setId(animalDto.getZoneId());
            animal.setZone(zone);
        }

        animal.setSante(animalDto.getSante());
        animal.setSexe(animalDto.getSexe());
        animal.setRegimeAlimentaire(animalDto.getRegimeAlimentaire());
        animal.setDateNaissance(animalDto.getDateNaissance());
        animal.setDateDeces(animalDto.getDateDeces());
        return animal;
    }

    public Collection<AnimalDto> toDtos(List<Animal> content) {
        return content.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
