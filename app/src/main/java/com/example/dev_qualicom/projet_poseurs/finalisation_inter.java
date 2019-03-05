package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class finalisation_inter extends AppCompatActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        File file  = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId())+"/documents", "SEPA.jpg");

        if(file.exists() || !PoseSingleton.getInstance().getPose().isX_dossier_financement()){
            Intent intent = new Intent(finalisation_inter.this, demande_financement.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_finalisation_inter);

        ImageView retour = (ImageView) findViewById(R.id.retour_btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(finalisation_inter.this, planning.class);
                String id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
                String nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();
                i.putExtra("id_equipe", id_equipe);
                i.putExtra("nom_equipe", nom_equipe);
                startActivity(i);
                finish();
            }
        });


        next = (Button) findViewById(R.id.suivant);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(finalisation_inter.this, finalisation_inter_certif.class);
                startActivity(intent);
            }
        });

    }
}
