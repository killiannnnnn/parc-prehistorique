package net.ent.etnc.parc_prehistorique.services.impl;

import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import net.ent.etnc.parc_prehistorique.repositories.AnimalRepository;
import net.ent.etnc.parc_prehistorique.repositories.ZoneRepository;
import net.ent.etnc.parc_prehistorique.services.ZoneService;
import net.ent.etnc.parc_prehistorique.services.commons.AbstractService;
import net.ent.etnc.parc_prehistorique.services.commons.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoneServiceImpl extends AbstractService<Zone, ZoneRepository> implements ZoneService {

    private final AnimalRepository animalRepository;

    @Autowired
    public ZoneServiceImpl(ZoneRepository repository, AnimalRepository animalRepository) {
        super(repository);
        this.animalRepository = animalRepository;
    }

    @Override
    public Zone findByNom(String nom) {
        return repository.findByNom(nom);
    }

    @Override
    public void delete(Zone zone) {
        if (animalRepository.existsByZone(zone)) {
            throw new ServiceException("Impossible de supprimer une zone contenant des animaux");
        }

        super.delete(zone);
    }
}