package net.ent.etnc.parc_prehistorique.models.entities;

import net.ent.etnc.parc_prehistorique.models.enums.Dangerosite;
import net.ent.etnc.parc_prehistorique.models.enums.Habilitation;
import net.ent.etnc.parc_prehistorique.models.enums.TypeEspece;

import java.util.List;

public class Espece {
    private String nom;
    private TypeEspece type;
    private Dangerosite dangerosite;
    private String description;
    private boolean zoneRenforcee;
    private Habilitation habilitation;
    private List<Zone> zonesPossibles;
    private List<Espece> especesIncompatibles;
}
