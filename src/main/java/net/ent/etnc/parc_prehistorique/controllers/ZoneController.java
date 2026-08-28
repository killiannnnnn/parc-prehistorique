package net.ent.etnc.parc_prehistorique.controllers;

import net.ent.etnc.parc_prehistorique.dtos.ZoneDto;
import net.ent.etnc.parc_prehistorique.dtos.assemblers.ZoneAssembler;
import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import net.ent.etnc.parc_prehistorique.services.ZoneService;
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
@RequestMapping("/api/v1/zones")
public class ZoneController {

    private final ZoneService zoneService;
    private final ZoneAssembler zoneAssembler;

    @Autowired
    public ZoneController(ZoneService zoneService, ZoneAssembler zoneAssembler) {
        this.zoneService = zoneService;
        this.zoneAssembler = zoneAssembler;
    }

    @GetMapping("/")
    public ResponseEntity<Page<ZoneDto>> findAll(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sort
    ) {
        try {
            Pageable pageRequest = ControllerUtils.getPageable(page, size, sort);
            return ResponseEntity.ok(zoneService.findAll(pageRequest).map(zoneAssembler::toDto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/")
    public ResponseEntity<ZoneDto> findById(@PathVariable Long id) {
        try {
            Optional<Zone> optionalZone = zoneService.findById(id);
            if (optionalZone.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return optionalZone
                    .map(zoneAssembler::toDto)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<ZoneDto> create(@RequestBody ZoneDto ZoneDto) {
        try {
            if (ZoneDto == null || ZoneDto.getId() != null) {
                return ResponseEntity.badRequest().build();
            }
            Zone Zone = zoneService.create(zoneAssembler.toEntity(ZoneDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(zoneAssembler.toDto(Zone));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<Zone> update(@PathVariable Long id, @RequestBody Zone Zone) {
        try {
            if (!id.equals(Zone.getId()) || Objects.isNull(Zone)) {
                return ResponseEntity.badRequest().build();
            }
            if (!zoneService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(zoneService.update(Zone));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Zone> delete(@PathVariable Long id) {
        try {
            if (Objects.isNull(id)) {
                return ResponseEntity.badRequest().build();
            }
            Optional<Zone> ZoneOptional = zoneService.findById(id);
            if (ZoneOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Zone Zone = ZoneOptional.get();
            zoneService.delete(Zone);
            return ResponseEntity.ok(Zone);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
