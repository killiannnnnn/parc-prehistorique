package net.ent.etnc.parc_prehistorique.init;

import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import net.ent.etnc.parc_prehistorique.models.enums.EtatEnclo;
import net.ent.etnc.parc_prehistorique.models.enums.TypeEnclo;
import net.ent.etnc.parc_prehistorique.services.ZoneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Init implements CommandLineRunner {

    private final ZoneService zoneService;

    public Init(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.chargeZones();
        // TODO : Ajouter toutes les entitées
    }


    private void chargeZones() {
        if (zoneService.count() != 0) return;

        // TODO : Merci de faire au moins 5 entitées à chaque fois

        Zone zone1 = new Zone();
        zone1.setNom("Zone 1");
        zone1.setCapaciteMax(5);
        zone1.setTypeEnclo(TypeEnclo.MAXIMUM);
        zone1.setEtatEnclo(EtatEnclo.ACTIF);
        zone1.setDescription("Description de test pour la zone 1");

        List.of(zone1).forEach(zoneService::create);

    }
}
