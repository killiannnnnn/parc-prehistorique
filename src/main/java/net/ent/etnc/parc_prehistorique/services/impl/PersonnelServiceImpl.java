package net.ent.etnc.parc_prehistorique.services.impl;

import net.ent.etnc.parc_prehistorique.models.entities.Personnel;
import net.ent.etnc.parc_prehistorique.models.enums.EtatIntervention;
import net.ent.etnc.parc_prehistorique.repositories.OperationRepository;
import net.ent.etnc.parc_prehistorique.repositories.PersonnelRepository;
import net.ent.etnc.parc_prehistorique.services.PersonnelService;
import net.ent.etnc.parc_prehistorique.services.commons.AbstractService;
import net.ent.etnc.parc_prehistorique.services.commons.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonnelServiceImpl extends AbstractService<Personnel, PersonnelRepository> implements PersonnelService {

    private final OperationRepository operationRepository;

    @Autowired
    public PersonnelServiceImpl(PersonnelRepository repository, OperationRepository operationRepository) {
        super(repository);
        this.operationRepository = operationRepository;
    }

    @Override
    public Personnel findByNom(String nom) {
        return repository.findByNom(nom);
    }

    @Override
    public void delete(Personnel personnel) {
        boolean affecte = operationRepository.existsByPersonnelsContainingAndEtatInterventionIn(personnel, List.of(EtatIntervention.PLANIFIEE, EtatIntervention.EN_COURS));
        if (affecte) {
            throw new ServiceException("Impossible de supprimer un soigneur affecté à une opération active");
        }

        super.delete(personnel);
    }
}