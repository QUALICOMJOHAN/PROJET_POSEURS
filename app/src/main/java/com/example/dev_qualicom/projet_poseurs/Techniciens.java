package com.example.dev_qualicom.projet_poseurs;

public class Techniciens {

    String id;
    String nom;
    String prenom;

    public Techniciens(String id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean equals(String id) {
        if (this.id.equals(id)) {
            return true;
        }else{
            return false;
        }
    }
}
