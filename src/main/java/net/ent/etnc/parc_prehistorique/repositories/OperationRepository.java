package net.ent.etnc.parc_prehistorique.repositories;

import net.ent.etnc.parc_prehistorique.models.entities.Animal;
import net.ent.etnc.parc_prehistorique.models.entities.Operation;
import net.ent.etnc.parc_prehistorique.models.entities.Personnel;
import net.ent.etnc.parc_prehistorique.repositories.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationRepository extends BaseRepository<Operation> {

    boolean existsByAnimauxContaining(Animal animal);

    boolean existsByPersonnelsContainingAndEtatInterventionIn(
            Personnel personnel,
            List<net.ent.etnc.parc_prehistorique.models.enums.EtatIntervention> etats
    );

    @Query("""
            SELECT COUNT(o) > 0
            FROM Operation o
            JOIN o.personnels p
            WHERE p = :personnel
              AND o.etatIntervention IN (
                  net.ent.etnc.parc_prehistorique.models.enums.EtatIntervention.PLANIFIEE,
                  net.ent.etnc.parc_prehistorique.models.enums.EtatIntervention.EN_COURS
              )
              AND (:excludeId IS NULL OR o.id <> :excludeId)
              AND o.debut < :fin
              AND o.fin > :debut
            """)
    boolean existsConflitPersonnel(
            @Param("personnel") Personnel personnel,
            @Param("debut") LocalDateTime debut,
            @Param("fin") LocalDateTime fin,
            @Param("excludeId") Long excludeId
    );

    @Query("""
            SELECT COUNT(o) > 0
            FROM Operation o
            JOIN o.animaux a
            WHERE a = :animal
              AND o.etatIntervention IN (
                  net.ent.etnc.parc_prehistorique.models.enums.EtatIntervention.PLANIFIEE,
                  net.ent.etnc.parc_prehistorique.models.enums.EtatIntervention.EN_COURS
              )
              AND (:excludeId IS NULL OR o.id <> :excludeId)
              AND o.debut < :fin
              AND o.fin > :debut
            """)
    boolean existsConflitAnimal(
            @Param("animal") Animal animal,
            @Param("debut") LocalDateTime debut,
            @Param("fin") LocalDateTime fin,
            @Param("excludeId") Long excludeId
    );
}