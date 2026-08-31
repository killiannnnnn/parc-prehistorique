package net.ent.etnc.parc_prehistorique.controllers;

import net.ent.etnc.parc_prehistorique.dtos.PersonnelDto;
import net.ent.etnc.parc_prehistorique.dtos.assemblers.PersonnelAssembler;
import net.ent.etnc.parc_prehistorique.models.entities.Personnel;
import net.ent.etnc.parc_prehistorique.services.PersonnelService;
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
@RequestMapping("/api/v1/personnels")
public class PersonnelController {

    private final PersonnelService personnelService;
    private final PersonnelAssembler personnelAssembler;

    @Autowired
    public PersonnelController(PersonnelService personnelService, PersonnelAssembler personnelAssembler) {
        this.personnelService = personnelService;
        this.personnelAssembler = personnelAssembler;
    }

    @GetMapping("/")
    public ResponseEntity<Page<PersonnelDto>> findAll(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sort
    ) {
        try {
            Pageable pageRequest = ControllerUtils.getPageable(page, size, sort);
            return ResponseEntity.ok(personnelService.findAll(pageRequest).map(personnelAssembler::toDto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/")
    public ResponseEntity<PersonnelDto> findById(@PathVariable Long id) {
        try {
            Optional<Personnel> optionalPersonnel = personnelService.findById(id);
            if (optionalPersonnel.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return optionalPersonnel
                    .map(personnelAssembler::toDto)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<PersonnelDto> create(@RequestBody PersonnelDto personnelDto) {
        try {
            if (personnelDto == null || personnelDto.getId() != null) {
                return ResponseEntity.badRequest().build();
            }
            Personnel personnel = personnelService.create(personnelAssembler.toEntity(personnelDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(personnelAssembler.toDto(personnel));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<PersonnelDto> update(@PathVariable Long id, @RequestBody PersonnelDto personnelDto) {
        try {
            if (Objects.isNull(personnelDto) || !id.equals(personnelDto.getId())) {
                return ResponseEntity.badRequest().build();
            }
            if (!personnelService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Personnel personnel = personnelService.update(personnelAssembler.toEntity(personnelDto));
            return ResponseEntity.ok(personnelAssembler.toDto(personnel));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<PersonnelDto> delete(@PathVariable Long id) {
        try {
            if (Objects.isNull(id)) {
                return ResponseEntity.badRequest().build();
            }
            Optional<Personnel> personnelOptional = personnelService.findById(id);
            if (personnelOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Personnel personnel = personnelOptional.get();
            personnelService.delete(personnel);
            return ResponseEntity.ok(personnelAssembler.toDto(personnel));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
