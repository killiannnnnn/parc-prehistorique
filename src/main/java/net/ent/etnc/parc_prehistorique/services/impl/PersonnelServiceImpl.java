package net.ent.etnc.parc_prehistorique.services.impl;

import net.ent.etnc.parc_prehistorique.models.entities.Personnel;
import net.ent.etnc.parc_prehistorique.repositories.PersonnelRepository;
import net.ent.etnc.parc_prehistorique.services.PersonnelService;
import net.ent.etnc.parc_prehistorique.services.commons.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonnelServiceImpl extends AbstractService<Personnel, PersonnelRepository> implements PersonnelService {

    @Autowired
    public PersonnelServiceImpl(PersonnelRepository repository) {
        super(repository);
    }

    @Override
    public Personnel findByNom(String nom) {
        return repository.findByNom(nom);
    }
}