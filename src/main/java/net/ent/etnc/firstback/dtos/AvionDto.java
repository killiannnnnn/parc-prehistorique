package net.ent.etnc.firstback.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvionDto {
    // request + response DTO
    private Long id;
    private String immat;
    private Integer envergure;
    private Integer puissance;
    private Boolean actif;
    private LocalDate miseEnService;
    // request only DTO
    private Set<Long> pilotesIds;
}
