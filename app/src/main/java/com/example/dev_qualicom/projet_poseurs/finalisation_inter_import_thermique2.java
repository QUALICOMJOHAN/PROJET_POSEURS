package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class finalisation_inter_import_thermique2 extends AppCompatActivity {

    Button next;
    int nb;
    TextView nb_photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_finalisation_inter_import_thermique2);

        ImageView retour = (ImageView) findViewById(R.id.retour_btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(finalisation_inter_import_thermique2.this, planning.class);
                String id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
                String nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();
                i.putExtra("id_equipe", id_equipe);
                i.putExtra("nom_equipe", nom_equipe);
                startActivity(i);
                finish();
            }
        });


        Intent intent = getIntent();
        nb = intent.getExtras().getInt("nb");

        nb_photos = (TextView)findViewById(R.id.nb_photos);
        nb_photos.setText("Nombre de photos importées :"+ String.valueOf(nb));

        next = (Button) findViewById(R.id.suivant);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(finalisation_inter_import_thermique2.this, finalisation_rappel_envoi_docs.class);
                startActivity(intent);
            }
        });

    }
}
