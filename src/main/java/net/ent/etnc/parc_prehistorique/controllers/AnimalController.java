package net.ent.etnc.parc_prehistorique.controllers;

import net.ent.etnc.parc_prehistorique.dtos.AnimalDto;
import net.ent.etnc.parc_prehistorique.dtos.assemblers.AnimalAssembler;
import net.ent.etnc.parc_prehistorique.models.entities.Animal;
import net.ent.etnc.parc_prehistorique.services.AnimalService;
import net.ent.etnc.parc_prehistorique.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/animaux")
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalAssembler animalAssembler;

    @Autowired
    public AnimalController(AnimalService animalService, AnimalAssembler animalAssembler) {
        this.animalService = animalService;
        this.animalAssembler = animalAssembler;
    }

    @GetMapping("/")
    public ResponseEntity<Page<AnimalDto>> findAll(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sort
    ) {
        try {
            Pageable pageRequest = ControllerUtils.getPageable(page, size, sort);
            return ResponseEntity.ok(animalService.findAll(pageRequest).map(animalAssembler::toDto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/")
    public ResponseEntity<AnimalDto> findById(@PathVariable Long id) {
        try {
            Optional<Animal> optionalAnimal = animalService.findById(id);
            if (optionalAnimal.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return optionalAnimal
                    .map(animalAssembler::toDto)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<AnimalDto> create(@RequestBody AnimalDto animalDto) {
        try {
            if (animalDto == null || animalDto.getId() != null) {
                return ResponseEntity.badRequest().build();
            }
            Animal animal = animalService.create(animalAssembler.toEntity(animalDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(animalAssembler.toDto(animal));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<AnimalDto> update(@PathVariable Long id, @RequestBody AnimalDto animalDto) {
        try {
            if (Objects.isNull(animalDto) || !id.equals(animalDto.getId())) {
                return ResponseEntity.badRequest().build();
            }
            if (!animalService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Animal animal = animalService.update(animalAssembler.toEntity(animalDto));
            return ResponseEntity.ok(animalAssembler.toDto(animal));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<AnimalDto> delete(@PathVariable Long id) {
        try {
            if (Objects.isNull(id)) {
                return ResponseEntity.badRequest().build();
            }
            Optional<Animal> animalOptional = animalService.findById(id);
            if (animalOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Animal animal = animalOptional.get();
            animalService.delete(animal);
            return ResponseEntity.ok(animalAssembler.toDto(animal));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
