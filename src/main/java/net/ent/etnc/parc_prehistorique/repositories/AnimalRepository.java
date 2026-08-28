package net.ent.etnc.parc_prehistorique.repositories;

import net.ent.etnc.parc_prehistorique.models.entities.Animal;
import net.ent.etnc.parc_prehistorique.repositories.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends BaseRepository<Animal> {
}

