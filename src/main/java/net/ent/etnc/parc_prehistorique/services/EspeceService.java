package net.ent.etnc.parc_prehistorique.services;


import net.ent.etnc.parc_prehistorique.models.entities.Espece;
import net.ent.etnc.parc_prehistorique.services.commons.Service;

public interface EspeceService extends Service<Espece, Long> {
    Espece findByNom(String tyrannosaurusRex);
}

