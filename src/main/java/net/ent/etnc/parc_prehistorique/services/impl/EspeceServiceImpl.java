package net.ent.etnc.parc_prehistorique.services.impl;

import net.ent.etnc.parc_prehistorique.models.entities.Espece;
import net.ent.etnc.parc_prehistorique.repositories.EspeceRepository;
import net.ent.etnc.parc_prehistorique.services.EspeceService;
import net.ent.etnc.parc_prehistorique.services.commons.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspeceServiceImpl extends AbstractService<Espece, EspeceRepository> implements EspeceService {

    @Autowired
    public EspeceServiceImpl(EspeceRepository repository) {
        super(repository);
    }

    @Override
    public Espece findByNom(String nom) {
        return repository.findByNom(nom);
    }
}