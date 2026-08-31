package net.ent.etnc.parc_prehistorique.repositories;

import net.ent.etnc.parc_prehistorique.models.entities.Animal;
import net.ent.etnc.parc_prehistorique.models.entities.Espece;
import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import net.ent.etnc.parc_prehistorique.repositories.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends BaseRepository<Animal> {

    @Query("SELECT a FROM Animal a WHERE LOWER(a.nom) = LOWER(:nom)")
    Animal findByNom(String nom);

    boolean existsByZone(Zone zone);

    boolean existsByEspece(Espece espece);

    long countByZone(Zone zone);

    List<Animal> findByZone(Zone zone);
}