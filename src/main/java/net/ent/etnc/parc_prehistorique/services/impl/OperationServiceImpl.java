package net.ent.etnc.parc_prehistorique.services.impl;

import net.ent.etnc.parc_prehistorique.models.entities.Animal;
import net.ent.etnc.parc_prehistorique.models.entities.Espece;
import net.ent.etnc.parc_prehistorique.models.entities.Operation;
import net.ent.etnc.parc_prehistorique.models.entities.Personnel;
import net.ent.etnc.parc_prehistorique.models.enums.EtatIntervention;
import net.ent.etnc.parc_prehistorique.models.enums.Sante;
import net.ent.etnc.parc_prehistorique.repositories.OperationRepository;
import net.ent.etnc.parc_prehistorique.services.OperationService;
import net.ent.etnc.parc_prehistorique.services.commons.AbstractService;
import net.ent.etnc.parc_prehistorique.services.commons.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl extends AbstractService<Operation, OperationRepository> implements OperationService {

    @Autowired
    public OperationServiceImpl(OperationRepository repository) {
        super(repository);
    }

    @Override
    public Operation create(Operation operation) {
        valider(operation);
        return super.create(operation);
    }

    @Override
    public Operation update(Operation operation) {
        valider(operation);
        return super.update(operation);
    }

    private void valider(Operation operation) {
        validerModification(operation);
        validerAnimaux(operation);
        validerHabilitations(operation);
        validerConflitPlanning(operation);
    }

    private void validerModification(Operation operation) {
        if (operation.getId() == null) {
            return;
        }

        repository.findById(operation.getId()).ifPresent(existing -> {
            if (existing.getEtatIntervention() == EtatIntervention.TERMINEE
                    || existing.getEtatIntervention() == EtatIntervention.ANNULEE) {
                throw new ServiceException("Cette opération ne peut plus être modifiée");
            }
        });
    }

    private void validerAnimaux(Operation operation) {
        for (Animal animal : operation.getAnimaux()) {
            if (animal.getSante() == Sante.DECEDE) {
                throw new ServiceException("Un animal décédé ne peut pas participer à une opération");
            }
        }
    }

    private void validerHabilitations(Operation operation) {
        for (Personnel personnel : operation.getPersonnels()) {
            for (Animal animal : operation.getAnimaux()) {
                Espece espece = animal.getEspece();

                if (!personnel.getEspeces().contains(espece)) {
                    throw new ServiceException("Le soigneur " + personnel.getNom() + " n'est pas habilité sur l'espèce " + espece.getNom());
                }

                if (personnel.getHabilitation().compareTo(
                        espece.getHabilitationMinimale()
                ) < 0) {
                    throw new ServiceException("Habilitation du soigneur " + personnel.getNom() + " insuffisante pour l'espèce " + espece.getNom());
                }
            }
        }
    }

    private void validerConflitPlanning(Operation operation) {

        for (Personnel personnel : operation.getPersonnels()) {
            boolean conflit = repository.existsConflitPersonnel(
                    personnel,
                    operation.getDebut(),
                    operation.getFin(),
                    operation.getId()
            );

            if (conflit) {
                throw new ServiceException(
                        "Le soigneur " + personnel.getNom()
                                + " est déjà affecté sur ce créneau"
                );
            }
        }

        for (Animal animal : operation.getAnimaux()) {
            boolean conflit = repository.existsConflitAnimal(
                    animal,
                    operation.getDebut(),
                    operation.getFin(),
                    operation.getId()
            );

            if (conflit) {
                throw new ServiceException(
                        "L'animal " + animal.getNom()
                                + " a déjà une opération sur ce créneau"
                );
            }
        }
    }
}
