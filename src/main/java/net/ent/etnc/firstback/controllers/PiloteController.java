package net.ent.etnc.firstback.controllers;

import net.ent.etnc.firstback.dtos.PiloteDto;
import net.ent.etnc.firstback.dtos.assemblers.PiloteAssembler;
import net.ent.etnc.firstback.models.Pilote;
import net.ent.etnc.firstback.services.PiloteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pilotes")
public class PiloteController {

    private final PiloteService piloteService;
    private final PiloteAssembler piloteAssembler;

    @Autowired
    public PiloteController(PiloteService piloteService, PiloteAssembler piloteAssembler) {
        this.piloteService = piloteService;
        this.piloteAssembler = piloteAssembler;
    }

    @GetMapping("/")
    public ResponseEntity<Page<PiloteDto>> findAll(
            @RequestParam(required = false) Long aviondId) {
        try {
            if (aviondId == null) {
                Pageable pageable = PageRequest.of(0, 10);
                return ResponseEntity.ok(piloteService.findAll(pageable).map(piloteAssembler::toDto));
            }

            List<Pilote> pilotes = piloteService.findByAvionId(aviondId);
            if (pilotes.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }
            return ResponseEntity.ok(this.piloteAssembler.toDtos(pilotes));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/")
    public ResponseEntity<PiloteDto> findById(@PathVariable Long id) {
        try {
            Optional<Pilote> optionalPilote = piloteService.findById(id);
            if (optionalPilote.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(this.piloteAssembler.toDto(optionalPilote.get()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<PiloteDto> create(@RequestBody PiloteDto piloteDto) {
        try {
            if (piloteDto == null || piloteDto.getId() != null) {
                return ResponseEntity.badRequest().build();
            }
            Pilote pilote = piloteService.create(this.piloteAssembler.toEntity(piloteDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(this.piloteAssembler.toDto(pilote));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<Pilote> update(@PathVariable Long id, @RequestBody Pilote pilote) {
        try {
            if (!id.equals(pilote.getId()) || Objects.isNull(pilote)) {
                return ResponseEntity.badRequest().build();
            }
            if (!piloteService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            pilote = piloteService.update(pilote);
            return ResponseEntity.ok(pilote);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Pilote> delete(@PathVariable Long id) {
        try {
            if (Objects.isNull(id)) {
                return ResponseEntity.badRequest().build();
            }
            Optional<Pilote> piloteOptional = piloteService.findById(id);
            if (piloteOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Pilote pilote = piloteOptional.get();
            piloteService.delete(pilote);
            return ResponseEntity.ok(pilote);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
