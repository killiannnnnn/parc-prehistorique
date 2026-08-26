package net.ent.etnc.firstback.controllers;

import net.ent.etnc.firstback.dtos.AvionDto;
import net.ent.etnc.firstback.dtos.assemblers.AvionAssembler;
import net.ent.etnc.firstback.models.Avion;
import net.ent.etnc.firstback.services.AvionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/avions")
public class AvionController {

    private final AvionService avionService;
    private final AvionAssembler avionAssembler;

    @Autowired
    public AvionController(AvionService avionService, AvionAssembler avionAssembler) {
        this.avionService = avionService;
        this.avionAssembler = avionAssembler;
    }

    @GetMapping("/")
    public ResponseEntity<Collection<AvionDto>> findAll() {
        try {
            return ResponseEntity.ok(this.avionAssembler.toDtos(avionService.findAll(Pageable.unpaged()).getContent()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/")
    public ResponseEntity<AvionDto> findById(@PathVariable Long id) {
        try {
            Optional<Avion> optionalAvion = avionService.findById(id);
            if (optionalAvion.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return optionalAvion
                    .map(avionAssembler::toDto)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<AvionDto> create(@RequestBody AvionDto avionDto) {
        try {
            if (avionDto == null || avionDto.getId() != null) {
                return ResponseEntity.badRequest().build();
            }
            Avion avion = avionService.create(this.avionAssembler.toEntity(avionDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(this.avionAssembler.toDto(avion));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<Avion> update(@PathVariable Long id, @RequestBody Avion avion) {
        try {
            if (!id.equals(avion.getId()) || Objects.isNull(avion)) {
                return ResponseEntity.badRequest().build();
            }
            if (!avionService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            avion = avionService.update(avion);
            return ResponseEntity.ok(avion);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Avion> delete(@PathVariable Long id) {
        try {
            if (Objects.isNull(id)) {
                return ResponseEntity.badRequest().build();
            }
            Optional<Avion> avionOptional = avionService.findById(id);
            if (avionOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Avion avion = avionOptional.get();
            avionService.delete(avion);
            return ResponseEntity.ok(avion);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
