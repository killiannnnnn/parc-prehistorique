package net.ent.etnc.firstback.repositories;

import net.ent.etnc.firstback.models.Pilote;
import net.ent.etnc.firstback.repositories.commons.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PiloteRepository extends BaseRepository<Pilote> {


    @Query("SELECT p FROM Avion a JOIN a.pilotes p WHERE a.id = :avionId")
    List<Pilote> findByAvionId(Long avionId);

}
