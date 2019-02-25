package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class final_final extends AppCompatActivity {

    Button terminer;
    String id_equipe;
    String nom_equipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_final_final);

        id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
        nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();

        terminer = (Button) findViewById(R.id.terminer);
        terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(final_final.this, planning.class);
                intent.putExtra("id_equipe", id_equipe);
                intent.putExtra("nom_equipe", nom_equipe);
                startActivity(intent);
            }
        });

    }
}
