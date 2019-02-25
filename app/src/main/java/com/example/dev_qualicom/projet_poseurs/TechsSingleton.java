package com.example.dev_qualicom.projet_poseurs;

import java.util.ArrayList;

public class TechsSingleton {

    private static TechsSingleton singleton = null;
    private ArrayList<Techniciens> tab;
    private TechsSingleton() {

    }

    public synchronized static TechsSingleton getInstance() {
        if(singleton == null) singleton = new TechsSingleton();
        return singleton;
    }

    public void init(ArrayList<Techniciens> techniciens){

        this.tab = techniciens;

    }

    public ArrayList<Techniciens> getTechniciens() {
        return tab;
    }
}
