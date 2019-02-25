package com.example.dev_qualicom.projet_poseurs;

public class PoseSingleton {

    private static PoseSingleton singleton = null;
    private Pose pose;
    private PoseSingleton() {

    }

    public synchronized static PoseSingleton getInstance() {
        if(singleton == null) singleton = new PoseSingleton();
        return singleton;
    }

    public void init(Pose pose){

        this.pose = pose;

    }

    public Pose getPose() {
        return pose;
    }

}
