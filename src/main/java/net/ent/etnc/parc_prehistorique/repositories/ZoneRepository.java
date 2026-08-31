package net.ent.etnc.parc_prehistorique.repositories;

import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import net.ent.etnc.parc_prehistorique.repositories.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends BaseRepository<Zone> {

    @Query("SELECT z FROM Zone z WHERE LOWER(z.nom) = LOWER(:nom)")
    Zone findByNom(String nom);
}

