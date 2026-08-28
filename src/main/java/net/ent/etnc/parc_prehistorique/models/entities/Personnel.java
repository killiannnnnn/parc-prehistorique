package net.ent.etnc.parc_prehistorique.models.entities;

import net.ent.etnc.parc_prehistorique.models.enums.Habilitation;

import java.util.List;

public class Personnel {
    private String prenom;
    private String nom;
    private Habilitation habilitation;
    private List<Espece> especes;
}