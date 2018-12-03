package com.example.dev_qualicom.projet_poseurs;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Pose {

    public Timestamp start;
    ArrayList<String> techniciens = new ArrayList<String>();
    String title;
    String equipe;

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public ArrayList<String> getTechniciens() {
        return techniciens;
    }

    public void setTechniciens(ArrayList<String> techniciens) {
        this.techniciens = techniciens;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
