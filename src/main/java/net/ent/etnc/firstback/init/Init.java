package net.ent.etnc.firstback.init;

import net.ent.etnc.firstback.models.Avion;
import net.ent.etnc.firstback.models.Pilote;
import net.ent.etnc.firstback.services.AvionService;
import net.ent.etnc.firstback.services.PiloteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class Init implements CommandLineRunner {

    private final AvionService avionService;
    private final PiloteService piloteService;

    public Init(AvionService avionService, PiloteService piloteService) {
        this.avionService = avionService;
        this.piloteService = piloteService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.chargePilotes();
        this.chargeAvions();
    }


    private void chargeAvions() {
        if (avionService.count() != 0) return;

        List<Pilote> pilotes = piloteService.findAll(Pageable.unpaged()).getContent();

        Avion avion1 = new Avion();
        avion1.setPilotes(Collections.singletonList(pilotes.getFirst()));
        avion1.setImmat("AA-333");
        avion1.setEnvergure(17);
        avion1.setPuissance(200);
        avion1.setActif(true);
        avion1.setMisEnService(LocalDate.now().minusMonths(3));

        Avion avion2 = new Avion();
        avion2.setPilotes(Collections.singletonList(pilotes.get(1)));
        avion2.setImmat("BB-666");
        avion2.setEnvergure(25);
        avion2.setPuissance(1200);
        avion2.setActif(true);
        avion2.setMisEnService(LocalDate.now().minusMonths(5));

        Avion avion3 = new Avion();
        avion3.setPilotes(Collections.singletonList(pilotes.get(2)));
        avion3.setImmat("CC-999");
        avion3.setEnvergure(10);
        avion3.setPuissance(10_000);
        avion3.setActif(true);
        avion3.setMisEnService(LocalDate.now().minusMonths(10));

        Avion avion4 = new Avion();
        avion4.setPilotes(Collections.singletonList(pilotes.get(3)));
        avion4.setImmat("DD-333");
        avion4.setEnvergure(25);
        avion4.setPuissance(1200);
        avion4.setActif(true);
        avion4.setMisEnService(LocalDate.now().minusMonths(5));

        Avion avion5 = new Avion();
        avion5.setPilotes(Collections.singletonList(pilotes.get(4)));
        avion5.setImmat("EE-666");
        avion5.setEnvergure(10);
        avion5.setPuissance(10_000);
        avion5.setActif(true);
        avion5.setMisEnService(LocalDate.now().minusMonths(5));

        Avion avion6 = new Avion();
        avion6.setPilotes(Collections.singletonList(pilotes.get(5)));
        avion6.setImmat("FF-333");
        avion6.setEnvergure(25);
        avion6.setPuissance(1200);
        avion6.setActif(true);
        avion6.setMisEnService(LocalDate.now().minusMonths(5));

        Arrays.asList(avion1, avion2, avion3, avion4, avion5, avion6).forEach(avionService::create);

    }

    private void chargePilotes() {
        if (piloteService.count() != 0) return;

        Pilote pilote1 = new Pilote();
        pilote1.setNid("0123456789");
        pilote1.setNom("Nom1");
        pilote1.setPrenom("Prenom1");
        pilote1.setGrade("Grade1");
        pilote1.setDateNaissance(LocalDate.now().minusYears(10));
        Pilote pilote2 = new Pilote();
        pilote2.setNid("1234567890");
        pilote2.setNom("Nom2");
        pilote2.setPrenom("Prenom2");
        pilote2.setGrade("Grade2");
        pilote2.setDateNaissance(LocalDate.now().minusYears(15));
        Pilote pilote3 = new Pilote();
        pilote3.setNid("2345678901");
        pilote3.setNom("Nom3");
        pilote3.setPrenom("Prenom3");
        pilote3.setGrade("Grade3");
        pilote3.setDateNaissance(LocalDate.now().minusYears(20));
        Pilote pilote4 = new Pilote();
        pilote4.setNid("9999999999");
        pilote4.setNom("Nom4");
        pilote4.setPrenom("Prenom4");
        pilote4.setGrade("Grade4");
        pilote4.setDateNaissance(LocalDate.now().minusYears(30));
        Pilote pilote5 = new Pilote();
        pilote5.setNid("1111111111");
        pilote5.setNom("Nom5");
        pilote5.setPrenom("Prenom5");
        pilote5.setGrade("Grade5");
        pilote5.setDateNaissance(LocalDate.now().minusYears(30));
        Pilote pilote6 = new Pilote();
        pilote6.setNid("2222222222");
        pilote6.setNom("Nom6");
        pilote6.setPrenom("Prenom6");
        pilote6.setGrade("Grade6");
        pilote6.setDateNaissance(LocalDate.now().minusYears(30));

        Arrays.asList(pilote1, pilote2, pilote3, pilote4, pilote5, pilote6).forEach(piloteService::create);
    }
}
