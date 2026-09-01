package net.ent.etnc.parc_prehistorique.services.impl;

import net.ent.etnc.parc_prehistorique.models.entities.Animal;
import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import net.ent.etnc.parc_prehistorique.models.enums.Dangerosite;
import net.ent.etnc.parc_prehistorique.models.enums.EncloSecurite;
import net.ent.etnc.parc_prehistorique.models.enums.EtatEnclo;
import net.ent.etnc.parc_prehistorique.models.enums.Sante;
import net.ent.etnc.parc_prehistorique.repositories.AnimalRepository;
import net.ent.etnc.parc_prehistorique.repositories.OperationRepository;
import net.ent.etnc.parc_prehistorique.services.AnimalService;
import net.ent.etnc.parc_prehistorique.services.commons.AbstractService;
import net.ent.etnc.parc_prehistorique.services.commons.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl extends AbstractService<Animal, AnimalRepository> implements AnimalService {

    private final OperationRepository operationRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository repository, OperationRepository operationRepository) {
        super(repository);
        this.operationRepository = operationRepository;
    }

    @Override
    public Animal findByNom(String nom) {
        return repository.findByNom(nom);
    }

    @Override
    public void delete(Animal animal) {
        if (operationRepository.existsByAnimauxContaining(animal)) {
            throw new ServiceException("Impossible de supprimer un animal référencé dans une opération");
        }

        super.delete(animal);
    }

    @Override
    public Animal create(Animal animal) {
        validerAffectationZone(animal);
        return super.create(animal);
    }

    @Override
    public Animal update(Animal animal) {
        validerAffectationZone(animal);
        return super.update(animal);
    }

    private void validerAffectationZone(Animal animal) {
        Zone zone = animal.getZone();
        if (zone == null) {
            return;
        }

        if (animal.getSante() == Sante.DECEDE) {
            throw new ServiceException("Un animal décédé ne peut pas être affecté à une zone");
        }

        if (zone.getEtatEnclo() != EtatEnclo.ACTIF) {
            throw new ServiceException("La zone n'est pas active");
        }

        if (!animal.getEspece().getZonesPossibles().contains(zone)) {
            throw new ServiceException("Cette espèce ne peut pas être placée dans cette zone");
        }

        validerSecurite(animal, zone);
        long occupants = repository.countByZone(zone);

        if (animal.getId() != null) {
            Animal ancien = repository.findById(animal.getId()).orElse(null);

            if (ancien != null && zone.equals(ancien.getZone())) {
                occupants--;
            }
        }

        if (occupants >= zone.getCapaciteMax()) {
            throw new ServiceException("La zone a atteint sa capacité maximale");
        }

        List<Animal> animauxZone = repository.findByZone(zone);
        boolean conflit = animauxZone.stream()
                .filter(a -> !a.equals(animal))
                .anyMatch(a ->
                        animal.getEspece()
                                .getEspecesIncompatibles()
                                .contains(a.getEspece())
                                || a.getEspece()
                                .getEspecesIncompatibles()
                                .contains(animal.getEspece())
                );

        if (conflit) {
            throw new ServiceException("Espèce incompatible déjà présente dans la zone");
        }
    }

    private void validerSecurite(Animal animal, Zone zone) {
        Dangerosite dangerosite = animal.getEspece().getDangerosite();

        EncloSecurite securiteMinimale = switch (dangerosite) {
            case FAIBLE, MODERE -> EncloSecurite.STANDARD;
            case ELEVE -> EncloSecurite.RENFORCE;
            case CRITIQUE -> EncloSecurite.MAXIMUM;
        };

        if (zone.getEncloSecurite().ordinal() < securiteMinimale.ordinal()) {
            throw new ServiceException("Le niveau de sécurité de la zone est insuffisant pour cette espèce");
        }
    }
}