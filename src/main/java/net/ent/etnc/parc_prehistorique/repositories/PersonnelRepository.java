package net.ent.etnc.parc_prehistorique.repositories;

import net.ent.etnc.parc_prehistorique.models.entities.Personnel;
import net.ent.etnc.parc_prehistorique.repositories.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonnelRepository extends BaseRepository<Personnel> {

    @Query("SELECT p FROM Personnel p WHERE LOWER(p.nom) = LOWER(:nom)")
    Personnel findByNom(String nom);
}

