package net.ent.etnc.firstback.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PiloteDto {
    // request + response DTO
    private Long id;
    private String nid;
    private String nom;
    private String prenom;
    private String grade;
    private LocalDate dateNaissance;
}
