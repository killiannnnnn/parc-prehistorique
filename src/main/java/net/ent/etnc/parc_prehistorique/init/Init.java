package net.ent.etnc.parc_prehistorique.init;

import com.github.javafaker.Faker;
import net.ent.etnc.parc_prehistorique.models.entities.*;
import net.ent.etnc.parc_prehistorique.models.enums.*;
import net.ent.etnc.parc_prehistorique.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class Init implements CommandLineRunner {

    // list for javafaker
    private final Faker faker = new Faker();
//  private final List<String> nomEspeces = List.of("Tyrannosaurus Rex", "Velociraptor", "Triceratops", "Brachiosaurus")

    private final ZoneService zoneService;
    private final EspeceService especeService;
    private final AnimalService animalService;
    private final PersonnelService personnelService;
    private final OperationService operationService;

    public Init(ZoneService zoneService,
                EspeceService especeService,
                AnimalService animalService,
                PersonnelService personnelService,
                OperationService operationService) {
        this.zoneService = zoneService;
        this.especeService = especeService;
        this.animalService = animalService;
        this.personnelService = personnelService;
        this.operationService = operationService;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        this.chargeZones();
        this.chargeEspeces();
        this.chargePersonnels();
        this.chargeAnimaux();
        this.chargeOperations();
    }

    private void chargeZones() {
        if (zoneService.count() != 0) return;

        Zone zone1 = new Zone();
        zone1.setNom("Sector Alpha");
        zone1.setCapaciteMax(500);
        zone1.setEncloSecurite(EncloSecurite.MAXIMUM);
        zone1.setEtatEnclo(EtatEnclo.ACTIF);
        zone1.setDescription("Enclos haute sécurité carnivores");

        Zone zone2 = new Zone();
        zone2.setNom("Sector Beta");
        zone2.setCapaciteMax(10);
        zone2.setEncloSecurite(EncloSecurite.STANDARD);
        zone2.setEtatEnclo(EtatEnclo.ACTIF);
        zone2.setDescription("Zone ouverte pour herbivores");

        Zone zone3 = new Zone();
        zone3.setNom("Sector Gamma");
        zone3.setCapaciteMax(8);
        zone3.setEncloSecurite(EncloSecurite.STANDARD);
        zone3.setEtatEnclo(EtatEnclo.MAINTENANCE);
        zone3.setDescription("Zone aquatique en cours de rénovation");

        Zone zone4 = new Zone();
        zone4.setNom("Sector Delta");
        zone4.setCapaciteMax(3);
        zone4.setEncloSecurite(EncloSecurite.MAXIMUM);
        zone4.setEtatEnclo(EtatEnclo.ACTIF);
        zone4.setDescription("Enclos isolé pour espèces dangereuses");

        zone1.setMatricule(generateMatricule());
        zone2.setMatricule(generateMatricule());
        zone3.setMatricule(generateMatricule());
        zone4.setMatricule(generateMatricule());

        List.of(zone1, zone2, zone3, zone4).forEach(zoneService::create);
    }

    private void chargeEspeces() {
        if (especeService.count() != 0) return;

        Zone zoneAlpha = zoneService.findByNom("Sector Alpha");
        Zone zoneBeta = zoneService.findByNom("Sector Beta");
        Zone zoneDelta = zoneService.findByNom("Sector Delta");

        Espece tRex = new Espece();
        tRex.setNom("Tyrannosaurus Rex");
        tRex.setTypeEspece(TypeEspece.TERRESTRE);
        tRex.setDangerosite(Dangerosite.CRITIQUE);
        tRex.setDescription("Prédateur apex du Crétacé supérieur");
        tRex.setHabilitationMinimale(Habilitation.EXPERT);
        tRex.addZone(zoneAlpha);
        tRex.addZone(zoneDelta);

        Espece velociraptor = new Espece();
        velociraptor.setNom("Velociraptor");
        velociraptor.setTypeEspece(TypeEspece.TERRESTRE);
        velociraptor.setDangerosite(Dangerosite.CRITIQUE);
        velociraptor.setDescription("Carnivore agile et chasseur en meute");
        velociraptor.setHabilitationMinimale(Habilitation.EXPERT);
        velociraptor.addZone(zoneAlpha);
        velociraptor.addZone(zoneDelta);

        Espece triceratops = new Espece();
        triceratops.setNom("Triceratops");
        triceratops.setTypeEspece(TypeEspece.TERRESTRE);
        triceratops.setDangerosite(Dangerosite.MODERE);
        triceratops.setDescription("Herbivore à trois cornes");
        triceratops.setHabilitationMinimale(Habilitation.CONFIRME);
        triceratops.addZone(zoneBeta);

        Espece brachiosaurus = new Espece();
        brachiosaurus.setNom("Brachiosaurus");
        brachiosaurus.setTypeEspece(TypeEspece.TERRESTRE);
        brachiosaurus.setDangerosite(Dangerosite.FAIBLE);
        brachiosaurus.setDescription("Grand herbivore au long cou");
        brachiosaurus.setHabilitationMinimale(Habilitation.JUNIOR);
        brachiosaurus.addZone(zoneBeta);

        tRex.setMatricule(generateMatricule());
        velociraptor.setMatricule(generateMatricule());
        triceratops.setMatricule(generateMatricule());
        brachiosaurus.setMatricule(generateMatricule());

        // 1. Sauvegarder TOUTES les espèces sans incompatibilités
        List.of(tRex, velociraptor, triceratops, brachiosaurus).forEach(especeService::create);

        // 2. Ajouter les incompatibilités APRÈS que tout est persisté
        tRex.addEspece(triceratops);
        tRex.addEspece(brachiosaurus);
        tRex.addEspece(velociraptor);

        velociraptor.addEspece(triceratops);
        velociraptor.addEspece(brachiosaurus);
        velociraptor.addEspece(tRex);


        List.of(tRex, velociraptor).forEach(especeService::update);
    }

    private void chargePersonnels() {
        if (personnelService.count() != 0) return;

        Espece tRex = especeService.findByNom("Tyrannosaurus Rex");
        Espece velociraptor = especeService.findByNom("Velociraptor");
        Espece triceratops = especeService.findByNom("Triceratops");
        Espece brachiosaurus = especeService.findByNom("Brachiosaurus");

        // Soigneur expert — habilité sur tous les carnivores
        Personnel alan = new Personnel();
        alan.setPrenom("Alan");
        alan.setNom("Grant");
        alan.setHabilitation(Habilitation.ELITE);
        alan.addEspece(tRex);
        alan.addEspece(velociraptor);

        // Soigneuse intermédiaire — herbivores uniquement
        Personnel ellie = new Personnel();
        ellie.setPrenom("Ellie");
        ellie.setNom("Sattler");
        ellie.setHabilitation(Habilitation.EXPERT);
        ellie.addEspece(triceratops);
        ellie.addEspece(brachiosaurus);

        // Vétérinaire — toutes espèces
        Personnel henry = new Personnel();
        henry.setPrenom("Henry");
        henry.setNom("Wu");
        henry.setHabilitation(Habilitation.CONFIRME);
        henry.addEspece(tRex);
        henry.addEspece(velociraptor);
        henry.addEspece(triceratops);
        henry.addEspece(brachiosaurus);

        // Soigneur débutant — herbivores non dangereux
        Personnel tim = new Personnel();
        tim.setPrenom("Tim");
        tim.setNom("Murphy");
        tim.setHabilitation(Habilitation.JUNIOR);
        tim.addEspece(brachiosaurus);

        alan.setMatricule(generateMatricule());
        ellie.setMatricule(generateMatricule());
        henry.setMatricule(generateMatricule());
        tim.setMatricule(generateMatricule());

        List.of(alan, ellie, henry, tim).forEach(personnelService::create);
    }

    private void chargeAnimaux() {
        if (animalService.count() != 0) return;

        Espece tRex = especeService.findByNom("Tyrannosaurus Rex");
        Espece velociraptor = especeService.findByNom("Velociraptor");
        Espece triceratops = especeService.findByNom("Triceratops");
        Espece brachiosaurus = especeService.findByNom("Brachiosaurus");

        Zone zoneAlpha = zoneService.findByNom("Sector Alpha");
        Zone zoneBeta = zoneService.findByNom("Sector Beta");
        Zone zoneDelta = zoneService.findByNom("Sector Delta");

        // T-Rex
        Animal rex = new Animal();
        rex.setNom("Rexy");
        rex.setEspece(tRex);
        rex.setZone(zoneAlpha);
        rex.setSante(Sante.EN_BONNE_SANTE);
        rex.setSexe(Sexe.FEMELLE);
        rex.setRegimeAlimentaire(RegimeAlimentaire.CARNIVORE);
        rex.setDateNaissance(LocalDateTime.of(2018, 3, 14, 8, 0));

        for (int i = 0; i < 50; i++){
            Animal animal = new Animal();
            animal.setNom(faker.name().lastName());
            animal.setMatricule(generateMatricule());
            animal.setEspece(tRex);
            animal.setZone(zoneAlpha);
            animal.setSante(Sante.EN_BONNE_SANTE);
            animal.setSexe(faker.random().nextBoolean() ? Sexe.MALE : Sexe.FEMELLE);
            animal.setRegimeAlimentaire(RegimeAlimentaire.CARNIVORE);
            animal.setDateNaissance(faker.date().birthday(1,30).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            this.animalService.create(animal);
        }

        // Velociraptors (meute de 3)
        Animal raptor1 = new Animal();
        raptor1.setNom("Blue");
        raptor1.setEspece(velociraptor);
        raptor1.setZone(zoneDelta);
        raptor1.setSante(Sante.EN_BONNE_SANTE);
        raptor1.setSexe(Sexe.FEMELLE);
        raptor1.setRegimeAlimentaire(RegimeAlimentaire.CARNIVORE);
        raptor1.setDateNaissance(LocalDateTime.of(2020, 6, 1, 6, 30));

        Animal raptor2 = new Animal();
        raptor2.setNom("Delta");
        raptor2.setEspece(velociraptor);
        raptor2.setZone(zoneDelta);
        raptor2.setSante(Sante.MALADE);
        raptor2.setSexe(Sexe.FEMELLE);
        raptor2.setRegimeAlimentaire(RegimeAlimentaire.CARNIVORE);
        raptor2.setDateNaissance(LocalDateTime.of(2020, 6, 1, 7, 0));

        Animal raptor3 = new Animal();
        raptor3.setNom("Echo");
        raptor3.setEspece(velociraptor);
        raptor3.setZone(zoneDelta);
        raptor3.setSante(Sante.EN_BONNE_SANTE);
        raptor3.setSexe(Sexe.MALE);
        raptor3.setRegimeAlimentaire(RegimeAlimentaire.CARNIVORE);
        raptor3.setDateNaissance(LocalDateTime.of(2020, 6, 2, 9, 15));

        // Triceratops
        Animal trike = new Animal();
        trike.setNom("Trike");
        trike.setEspece(triceratops);
        trike.setZone(zoneBeta);
        trike.setSante(Sante.EN_BONNE_SANTE);
        trike.setSexe(Sexe.MALE);
        trike.setRegimeAlimentaire(RegimeAlimentaire.HERBIVORE);
        trike.setDateNaissance(LocalDateTime.of(2019, 9, 22, 10, 0));

        // Brachiosaurus
        Animal brachi = new Animal();
        brachi.setNom("Brachie");
        brachi.setEspece(brachiosaurus);
        brachi.setZone(zoneBeta);
        brachi.setSante(Sante.EN_BONNE_SANTE);
        brachi.setSexe(Sexe.FEMELLE);
        brachi.setRegimeAlimentaire(RegimeAlimentaire.HERBIVORE);
        brachi.setDateNaissance(LocalDateTime.of(2017, 1, 5, 12, 0));

        rex.setMatricule(generateMatricule());
        raptor1.setMatricule(generateMatricule());
        raptor2.setMatricule(generateMatricule());
        raptor3.setMatricule(generateMatricule());
        trike.setMatricule(generateMatricule());
        brachi.setMatricule(generateMatricule());

        List.of(rex, raptor1, raptor2, raptor3, trike, brachi).forEach(animalService::create);
    }

    private void chargeOperations() {
        if (operationService.count() != 0) return;

        Zone zoneAlpha = zoneService.findByNom("Sector Alpha");
        Zone zoneDelta = zoneService.findByNom("Sector Delta");
        Zone zoneBeta = zoneService.findByNom("Sector Beta");

        Animal rex = animalService.findByNom("Rexy");
        Animal raptor2 = animalService.findByNom("Delta"); // la malade
        Animal trike = animalService.findByNom("Trike");

        Personnel alan = personnelService.findByNom("Grant");
        Personnel ellie = personnelService.findByNom("Sattler");
        Personnel henry = personnelService.findByNom("Wu");

        // Opération 1 : Soins vétérinaires sur le raptor malade
        Operation soinsRaptor = new Operation();
        soinsRaptor.setEtatIntervention(EtatIntervention.TERMINEE);
        soinsRaptor.setTypeIntervention(TypeIntervention.SOIN_MEDICAL);
        soinsRaptor.setDebut(LocalDateTime.of(2024, 11, 10, 9, 0));
        soinsRaptor.setFin(LocalDateTime.of(2024, 11, 10, 11, 30));
        soinsRaptor.setZoneInitiale(zoneDelta);
        soinsRaptor.setNotes("Traitement antibiotique pour infection respiratoire de Delta");
        soinsRaptor.addAnimal(raptor2);
        soinsRaptor.addPersonnel(alan);

        // Opération 2 : Transfert de Rex depuis Alpha vers Delta
        Operation transfertRex = new Operation();
        transfertRex.setEtatIntervention(EtatIntervention.PLANIFIEE);
        transfertRex.setTypeIntervention(TypeIntervention.DEPLACEMENT);
        transfertRex.setDebut(LocalDateTime.of(2025, 1, 15, 6, 0));
        transfertRex.setFin(LocalDateTime.of(2025, 1, 15, 8, 0));
        transfertRex.setZoneInitiale(zoneAlpha);
        transfertRex.setZoneArrivee(zoneDelta);
        transfertRex.setNotes("Transfert pour travaux de renforcement de l'enclos Alpha");
        transfertRex.addAnimal(rex);
        transfertRex.addPersonnel(alan);

        // Opération 3 : Nourrissage herbivores
        Operation nourrissageBeta = new Operation();
        nourrissageBeta.setEtatIntervention(EtatIntervention.EN_COURS);
        nourrissageBeta.setTypeIntervention(TypeIntervention.NOURRISSAGE);
        nourrissageBeta.setDebut(LocalDateTime.now().minusHours(1));
        nourrissageBeta.setFin(LocalDateTime.now().plusHours(1));
        nourrissageBeta.setZoneInitiale(zoneBeta);
        nourrissageBeta.setNotes("Nourrissage quotidien zone Beta — ration d'automne");
        nourrissageBeta.addAnimal(trike);
        nourrissageBeta.addPersonnel(henry);

        soinsRaptor.setMatricule(generateMatricule());
        transfertRex.setMatricule(generateMatricule());
        nourrissageBeta.setMatricule(generateMatricule());

        List.of(soinsRaptor, transfertRex, nourrissageBeta).forEach(operationService::create);
    }

    private String generateMatricule() {
        return faker.number().digits(10);
    }
}