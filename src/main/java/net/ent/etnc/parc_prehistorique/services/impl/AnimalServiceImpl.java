package net.ent.etnc.parc_prehistorique.services.impl;

import net.ent.etnc.parc_prehistorique.models.entities.Animal;
import net.ent.etnc.parc_prehistorique.repositories.AnimalRepository;
import net.ent.etnc.parc_prehistorique.services.AnimalService;
import net.ent.etnc.parc_prehistorique.services.commons.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalServiceImpl extends AbstractService<Animal, AnimalRepository> implements AnimalService {

    @Autowired
    public AnimalServiceImpl(AnimalRepository repository) {
        super(repository);
    }
}