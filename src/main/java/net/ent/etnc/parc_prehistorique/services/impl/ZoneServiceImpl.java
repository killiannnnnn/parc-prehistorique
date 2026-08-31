package net.ent.etnc.parc_prehistorique.services.impl;

import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import net.ent.etnc.parc_prehistorique.repositories.ZoneRepository;
import net.ent.etnc.parc_prehistorique.services.ZoneService;
import net.ent.etnc.parc_prehistorique.services.commons.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoneServiceImpl extends AbstractService<Zone, ZoneRepository> implements ZoneService {

    @Autowired
    public ZoneServiceImpl(ZoneRepository repository) {
        super(repository);
    }

    @Override
    public Zone findByNom(String nom) {
        return repository.findByNom(nom);
    }
}