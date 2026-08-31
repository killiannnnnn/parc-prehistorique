package net.ent.etnc.parc_prehistorique.repositories;

import net.ent.etnc.parc_prehistorique.models.entities.Espece;
import net.ent.etnc.parc_prehistorique.repositories.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EspeceRepository extends BaseRepository<Espece> {

    @Query("SELECT e FROM Espece e WHERE LOWER(e.nom) = LOWER(:nom)")
    Espece findByNom(String nom);
}

