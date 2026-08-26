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

        Arrays.asList(avion1, avion2, avion3).forEach(avionService::create);

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

        Arrays.asList(pilote1, pilote2, pilote3).forEach(piloteService::create);
    }
}
