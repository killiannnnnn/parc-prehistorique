package net.ent.etnc.parc_prehistorique.controllers;

import net.ent.etnc.parc_prehistorique.dtos.OperationDto;
import net.ent.etnc.parc_prehistorique.dtos.assemblers.OperationAssembler;
import net.ent.etnc.parc_prehistorique.models.entities.Operation;
import net.ent.etnc.parc_prehistorique.services.OperationService;
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
@RequestMapping("/api/v1/operations")
public class OperationController {

    private final OperationService operationService;
    private final OperationAssembler operationAssembler;

    @Autowired
    public OperationController(OperationService operationService, OperationAssembler operationAssembler) {
        this.operationService = operationService;
        this.operationAssembler = operationAssembler;
    }

    @GetMapping("/")
    public ResponseEntity<Page<OperationDto>> findAll(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sort
    ) {
        try {
            Pageable pageRequest = ControllerUtils.getPageable(page, size, sort);
            return ResponseEntity.ok(operationService.findAll(pageRequest).map(operationAssembler::toDto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/")
    public ResponseEntity<OperationDto> findById(@PathVariable Long id) {
        try {
            Optional<Operation> optionalOperation = operationService.findById(id);
            if (optionalOperation.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return optionalOperation
                    .map(operationAssembler::toDto)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<OperationDto> create(@RequestBody OperationDto operationDto) {
        try {
            if (operationDto == null || operationDto.getId() != null) {
                return ResponseEntity.badRequest().build();
            }
            Operation operation = operationService.create(operationAssembler.toEntity(operationDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(operationAssembler.toDto(operation));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<OperationDto> update(@PathVariable Long id, @RequestBody OperationDto operationDto) {
        try {
            if (Objects.isNull(operationDto) || !id.equals(operationDto.getId())) {
                return ResponseEntity.badRequest().build();
            }
            if (!operationService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Operation operation = operationService.update(operationAssembler.toEntity(operationDto));
            return ResponseEntity.ok(operationAssembler.toDto(operation));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<OperationDto> delete(@PathVariable Long id) {
        try {
            if (Objects.isNull(id)) {
                return ResponseEntity.badRequest().build();
            }
            Optional<Operation> operationOptional = operationService.findById(id);
            if (operationOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Operation operation = operationOptional.get();
            operationService.delete(operation);
            return ResponseEntity.ok(operationAssembler.toDto(operation));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
