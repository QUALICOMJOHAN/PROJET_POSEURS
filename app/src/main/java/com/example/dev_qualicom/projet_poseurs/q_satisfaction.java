package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class q_satisfaction extends AppCompatActivity {

    Button ts;
    Button s;
    Button ps;
    Button m;
    ArrayList<String[]> tabq = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_q_satisfaction);

        ImageView retour = (ImageView) findViewById(R.id.retour_btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(q_satisfaction.this, planning.class);
                String id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
                String nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();
                i.putExtra("id_equipe", id_equipe);
                i.putExtra("nom_equipe", nom_equipe);
                startActivity(i);
                finish();
            }
        });


        ts = (Button) findViewById(R.id.ts);
        s = (Button) findViewById(R.id.s);
        ps = (Button) findViewById(R.id.ps);
        m = (Button) findViewById(R.id.m);

        ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction.this, q_satisfaction2.class);
                tabq.add(new String[]{"Etes-vous satisfait de l'installation effectuée par nos équipes ?", "ts"});
                Bundle extra = new Bundle();
                extra.putSerializable("objects", tabq);
                intent.putExtra("extra", extra);
                startActivity(intent);
            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction.this, q_satisfaction2.class);
                tabq.add(new String[]{"Etes-vous satisfait de l'installation effectuée par nos équipes ?", "s"});
                Bundle extra = new Bundle();
                extra.putSerializable("objects", tabq);
                intent.putExtra("extra", extra);
                startActivity(intent);
            }
        });

        ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction.this, q_satisfaction2.class);
                tabq.add(new String[]{"Etes-vous satisfait de l'installation effectuée par nos équipes ?", "ps"});
                Bundle extra = new Bundle();
                extra.putSerializable("objects", tabq);
                intent.putExtra("extra", extra);
                startActivity(intent);
            }
        });

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction.this, q_satisfaction2.class);
                tabq.add(new String[]{"Etes-vous satisfait de l'installation effectuée par nos équipes ?", "m"});
                Bundle extra = new Bundle();
                extra.putSerializable("objects", tabq);
                intent.putExtra("extra", extra);
                startActivity(intent);
            }
        });
    }
}
