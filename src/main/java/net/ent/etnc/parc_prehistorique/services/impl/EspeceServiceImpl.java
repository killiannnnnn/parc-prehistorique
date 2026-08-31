package net.ent.etnc.parc_prehistorique.services.impl;

import net.ent.etnc.parc_prehistorique.models.entities.Espece;
import net.ent.etnc.parc_prehistorique.repositories.AnimalRepository;
import net.ent.etnc.parc_prehistorique.repositories.EspeceRepository;
import net.ent.etnc.parc_prehistorique.services.EspeceService;
import net.ent.etnc.parc_prehistorique.services.commons.AbstractService;
import net.ent.etnc.parc_prehistorique.services.commons.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspeceServiceImpl extends AbstractService<Espece, EspeceRepository> implements EspeceService {

    private final AnimalRepository animalRepository;

    @Autowired
    public EspeceServiceImpl(EspeceRepository repository, AnimalRepository animalRepository) {
        super(repository);
        this.animalRepository = animalRepository;
    }

    @Override
    public Espece findByNom(String nom) {
        return repository.findByNom(nom);
    }

    @Override
    public void delete(Espece espece) {
        if (animalRepository.existsByEspece(espece)) {
            throw new ServiceException("Impossible de supprimer une espèce ayant des animaux");
        }

        super.delete(espece);
    }
}