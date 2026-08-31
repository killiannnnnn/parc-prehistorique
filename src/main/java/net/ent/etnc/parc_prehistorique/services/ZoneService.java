package net.ent.etnc.parc_prehistorique.services;


import net.ent.etnc.parc_prehistorique.models.entities.Zone;
import net.ent.etnc.parc_prehistorique.services.commons.Service;

public interface ZoneService extends Service<Zone, Long> {
    Zone findByNom(String nom);
}

