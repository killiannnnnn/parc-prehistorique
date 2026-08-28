package net.ent.etnc.parc_prehistorique.models.entities;

import net.ent.etnc.parc_prehistorique.models.enums.EtatIntervention;
import net.ent.etnc.parc_prehistorique.models.enums.TypeIntervention;

import java.time.LocalDate;
import java.util.List;

public class Operation {
    private EtatIntervention etat;
    private TypeIntervention type;
    private LocalDate debut;
    private LocalDate fin;
    private Animal animal; // VERIFIER SI UNE OPERATION EXISTE DEJA
    private Zone zoneDepart;
    private Zone zoneArrivee;
    private List<Personnel> personnels; // VERIFIER QU'UN SOIGNEUR N'EST PAS DEJA AFFECTE
    private String notes;
    private boolean terminee;

}
