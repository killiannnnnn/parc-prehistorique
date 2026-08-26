package net.ent.etnc.firstback.services;

import net.ent.etnc.firstback.models.Pilote;
import net.ent.etnc.firstback.services.commons.Service;

import java.util.List;

public interface PiloteService extends Service<Pilote, Long> {

    List<Pilote> findByAvionId(long id);

}
