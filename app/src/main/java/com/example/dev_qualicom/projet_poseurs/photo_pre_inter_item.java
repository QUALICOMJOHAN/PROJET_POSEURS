package com.example.dev_qualicom.projet_poseurs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class photo_pre_inter_item extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_photo_pre_inter_item);
    }
}
