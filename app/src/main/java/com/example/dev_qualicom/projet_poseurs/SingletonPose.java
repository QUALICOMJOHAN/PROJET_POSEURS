package com.example.dev_qualicom.projet_poseurs;

public class SingletonPose {

    private static SingletonPose mInstance= null;

    public Pose pose;

    protected SingletonPose(){}

    public static synchronized SingletonPose getInstance() {
        if(null == mInstance){
            mInstance = new SingletonPose();
        }
        return mInstance;
    }
}

