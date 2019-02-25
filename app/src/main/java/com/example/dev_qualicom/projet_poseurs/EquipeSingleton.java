package com.example.dev_qualicom.projet_poseurs;

public class EquipeSingleton {

    private static EquipeSingleton singleton = null;
    private Equipe equipe;
    private EquipeSingleton() {

    }

    public synchronized static EquipeSingleton getInstance() {
        if(singleton == null) singleton = new EquipeSingleton();
        return singleton;
    }

    public void init(Equipe equipe){

        this.equipe = equipe;

    }

    public Equipe getEquipe() {
        return equipe;
    }

}
