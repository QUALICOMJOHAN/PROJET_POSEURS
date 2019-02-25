package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class compte_rendu_q extends AppCompatActivity {

    Button tech1;
    Button tech2;
    Button tech3;
    Button tech4;
    Button suivant;
    ArrayList<Button> techs = new ArrayList<Button>();
    int first = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_compte_rendu_q);

        techs.add(tech1 = (Button) findViewById(R.id.tech1));
        techs.add(tech2 = (Button) findViewById(R.id.tech2));
        techs.add(tech3 = (Button) findViewById(R.id.tech3));
        techs.add(tech4 = (Button) findViewById(R.id.tech4));
        suivant = (Button) findViewById(R.id.suivant);

        for(String id_tech : PoseSingleton.getInstance().getPose().getTechniciens()) {
            for(Techniciens tech : TechsSingleton.getInstance().getTechniciens()) {
                if(tech.equals(id_tech)) {
                    techs.get(first).setText(tech.getNom()+" "+tech.getPrenom());
                    techs.get(first).setVisibility(View.VISIBLE);
                    first ++;
                }
            }
        }

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(compte_rendu_q.this, compte_rendu_signature.class);
                startActivity(next);
            }
        });

        tech1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tech1.setBackgroundColor(Color.parseColor("#42f468"));
                tech2.setBackgroundColor(Color.parseColor("#e17211"));
                tech3.setBackgroundColor(Color.parseColor("#e17211"));
                tech4.setBackgroundColor(Color.parseColor("#e17211"));
                suivant.setVisibility(View.VISIBLE);
            }
        });

        tech2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tech1.setBackgroundColor(Color.parseColor("#c3610c"));
                tech2.setBackgroundColor(Color.parseColor("#42f468"));
                tech3.setBackgroundColor(Color.parseColor("#e17211"));
                tech4.setBackgroundColor(Color.parseColor("#e17211"));
                suivant.setVisibility(View.VISIBLE);
            }
        });

        tech3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tech1.setBackgroundColor(Color.parseColor("#c3610c"));
                tech2.setBackgroundColor(Color.parseColor("#e17211"));
                tech3.setBackgroundColor(Color.parseColor("#42f468"));
                tech4.setBackgroundColor(Color.parseColor("#e17211"));
                suivant.setVisibility(View.VISIBLE);
            }
        });

        tech4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tech1.setBackgroundColor(Color.parseColor("#c3610c"));
                tech2.setBackgroundColor(Color.parseColor("#e17211"));
                tech3.setBackgroundColor(Color.parseColor("#e17211"));
                tech4.setBackgroundColor(Color.parseColor("#42f468"));
                suivant.setVisibility(View.VISIBLE);
            }
        });

    }
}
