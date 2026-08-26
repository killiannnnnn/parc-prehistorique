package net.ent.etnc.firstback.services.impl;

import net.ent.etnc.firstback.models.Pilote;
import net.ent.etnc.firstback.repositories.PiloteRepository;
import net.ent.etnc.firstback.services.PiloteService;
import net.ent.etnc.firstback.services.commons.AbstractService;
import net.ent.etnc.firstback.services.commons.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PiloteServiceImpl extends AbstractService<Pilote, PiloteRepository> implements PiloteService {

    @Autowired
    public PiloteServiceImpl(PiloteRepository repository) {
        super(repository);
    }

    @Override
    public List<Pilote> findByAvionId(long id) {
        try {
            return repository.findByAvionId(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
