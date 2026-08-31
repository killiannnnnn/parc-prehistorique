package net.ent.etnc.parc_prehistorique.repositories;

import net.ent.etnc.parc_prehistorique.models.entities.Animal;
import net.ent.etnc.parc_prehistorique.repositories.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends BaseRepository<Animal> {

    @Query("SELECT a FROM Animal a WHERE LOWER(a.nom) = LOWER(:nom)")
    Animal findByNom(String nom);
}

