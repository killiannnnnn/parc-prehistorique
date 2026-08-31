package net.ent.etnc.parc_prehistorique.controllers;

import net.ent.etnc.parc_prehistorique.dtos.EspeceDto;
import net.ent.etnc.parc_prehistorique.dtos.assemblers.EspeceAssembler;
import net.ent.etnc.parc_prehistorique.models.entities.Espece;
import net.ent.etnc.parc_prehistorique.services.EspeceService;
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
@RequestMapping("/api/v1/especes")
public class EspeceController {

    private final EspeceService especeService;
    private final EspeceAssembler especeAssembler;

    @Autowired
    public EspeceController(EspeceService especeService, EspeceAssembler especeAssembler) {
        this.especeService = especeService;
        this.especeAssembler = especeAssembler;
    }

    @GetMapping("/")
    public ResponseEntity<Page<EspeceDto>> findAll(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sort
    ) {
        try {
            Pageable pageRequest = ControllerUtils.getPageable(page, size, sort);
            return ResponseEntity.ok(especeService.findAll(pageRequest).map(especeAssembler::toDto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/")
    public ResponseEntity<EspeceDto> findById(@PathVariable Long id) {
        try {
            Optional<Espece> optionalEspece = especeService.findById(id);
            if (optionalEspece.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return optionalEspece
                    .map(especeAssembler::toDto)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<EspeceDto> create(@RequestBody EspeceDto especeDto) {
        try {
            if (especeDto == null || especeDto.getId() != null) {
                return ResponseEntity.badRequest().build();
            }
            Espece espece = especeService.create(especeAssembler.toEntity(especeDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(especeAssembler.toDto(espece));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<EspeceDto> update(@PathVariable Long id, @RequestBody EspeceDto especeDto) {
        try {
            if (Objects.isNull(especeDto) || !id.equals(especeDto.getId())) {
                return ResponseEntity.badRequest().build();
            }
            if (!especeService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Espece espece = especeService.update(especeAssembler.toEntity(especeDto));
            return ResponseEntity.ok(especeAssembler.toDto(espece));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<EspeceDto> delete(@PathVariable Long id) {
        try {
            if (Objects.isNull(id)) {
                return ResponseEntity.badRequest().build();
            }
            Optional<Espece> especeOptional = especeService.findById(id);
            if (especeOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Espece espece = especeOptional.get();
            especeService.delete(espece);
            return ResponseEntity.ok(especeAssembler.toDto(espece));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
