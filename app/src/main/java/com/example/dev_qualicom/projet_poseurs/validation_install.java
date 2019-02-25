package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class validation_install extends AppCompatActivity {

    Button commencer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        File file  = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()), "Livret_installation.pdf");

        if(file.exists()){
            Intent intent = new Intent(validation_install.this, finalisation_inter.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_validation_install);

        commencer = (Button) findViewById(R.id.commencer_inter);

        commencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(validation_install.this, photo_pre_inter_avant.class);
                startActivity(i);
            }
        });


    }
}
