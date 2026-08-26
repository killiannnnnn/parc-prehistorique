package net.ent.etnc.firstback.services.impl;

import net.ent.etnc.firstback.models.Avion;
import net.ent.etnc.firstback.repositories.AvionRepository;
import net.ent.etnc.firstback.services.AvionService;
import net.ent.etnc.firstback.services.commons.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvionServiceImpl extends AbstractService<Avion, AvionRepository> implements AvionService {

    @Autowired
    public AvionServiceImpl(AvionRepository repository) {
        super(repository);
    }
}
