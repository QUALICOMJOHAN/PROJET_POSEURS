package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class q_satisfaction2 extends AppCompatActivity {

    Button ouip, partiellement, pasdutout;
    ArrayList<String[]> tabq = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_q_satisfaction2);

        ImageView retour = (ImageView) findViewById(R.id.retour_btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(q_satisfaction2.this, planning.class);
                String id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
                String nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();
                i.putExtra("id_equipe", id_equipe);
                i.putExtra("nom_equipe", nom_equipe);
                startActivity(i);
                finish();
            }
        });


        Bundle extra = getIntent().getBundleExtra("extra");
        tabq = (ArrayList<String[]>) extra.getSerializable("objects");

        ouip = (Button) findViewById(R.id.ouip);
        partiellement = (Button) findViewById(R.id.partiellement);
        pasdutout = (Button) findViewById(R.id.pasdutout);

        ouip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction2.this, q_satisfaction3.class);
                tabq.add(new String[]{"Le matériel est-il conforme à votre bon de commande ?", "ouip"});
                Bundle extra = new Bundle();
                extra.putSerializable("objects", tabq);
                intent.putExtra("extra", extra);
                startActivity(intent);
            }
        });

        partiellement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction2.this, q_satisfaction3.class);
                tabq.add(new String[]{"Le matériel est-il conforme à votre bon de commande ?", "partiellement"});
                Bundle extra = new Bundle();
                extra.putSerializable("objects", tabq);
                intent.putExtra("extra", extra);
                startActivity(intent);
            }
        });

        pasdutout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction2.this, q_satisfaction3.class);
                tabq.add(new String[]{"Le matériel est-il conforme à votre bon de commande ?", "pasdutout"});
                Bundle extra = new Bundle();
                extra.putSerializable("objects", tabq);
                intent.putExtra("extra", extra);
                startActivity(intent);
            }
        });
    }
}
