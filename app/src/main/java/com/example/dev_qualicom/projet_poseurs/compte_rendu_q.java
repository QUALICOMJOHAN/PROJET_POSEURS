package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class compte_rendu_q extends AppCompatActivity {

    Button tech1;
    Button tech2;
    Button tech3;
    Button tech4;
    Button suivant;
    private int currentq = 1;
    private ArrayList<QuestionFinChantier> tabq = new ArrayList<QuestionFinChantier>();
    ArrayList<Button> techs = new ArrayList<Button>();
    int first = 0;
    TextView question;
    TextView numq;
    String reponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_compte_rendu_q);

        init_tab_question();

        question = (TextView) findViewById(R.id.label_photo_souvenir);
        numq = (TextView) findViewById(R.id.numq);

        ImageView retour = (ImageView) findViewById(R.id.retour_btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(compte_rendu_q.this, planning.class);
                String id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
                String nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();
                i.putExtra("id_equipe", id_equipe);
                i.putExtra("nom_equipe", nom_equipe);
                startActivity(i);
                finish();
            }
        });


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

                q_suivante(reponse);

//                Intent next = new Intent(compte_rendu_q.this, compte_rendu_signature.class);
//                startActivity(next);
            }
        });

        tech1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tech1.setBackgroundColor(Color.parseColor("#42f468"));
                tech2.setBackgroundColor(Color.parseColor("#e17211"));
                tech3.setBackgroundColor(Color.parseColor("#e17211"));
                tech4.setBackgroundColor(Color.parseColor("#e17211"));
                reponse = tech1.getText().toString();
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
                reponse = tech2.getText().toString();
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
                reponse = tech3.getText().toString();
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
                reponse = tech4.getText().toString();
                suivant.setVisibility(View.VISIBLE);
            }
        });

    }

    private void q_suivante(String reponse){
        tabq.get(currentq-1).setReponse(reponse);
        currentq ++;
        question.setText(tabq.get(currentq-1).getQuestion());
        numq.setText(tabq.get(currentq-1).getNum()+"/"+tabq.size());
    }

    private void init_tab_question(){

        tabq.add(new QuestionFinChantier(1, "Chauffe eau installé,  sortie hydraulique vers les attentes de la  pièces"));
        tabq.add(new QuestionFinChantier(2, "Horizontalité du ballon correcte"));
        tabq.add(new QuestionFinChantier(3, "Fixation du chauffe eau au sol"));
        tabq.add(new QuestionFinChantier(4, "Raccordement de la sortie eau chaude  sur le réseau de la maison"));
        tabq.add(new QuestionFinChantier(5, "Mise en place du raccord diélectrique"));
        tabq.add(new QuestionFinChantier(6, "Pose d’un limiteur thermostatique / réglage de la T° de sortie du ballon"));
        tabq.add(new QuestionFinChantier(7, "Installation du groupe de sécurité sur l’arrivée eau froide du ballon"));
        tabq.add(new QuestionFinChantier(8, "Raccorder le trop-plein au réseau d’évacuation"));
        tabq.add(new QuestionFinChantier(9, "Pression de distribution inférieure à 7 bars"));
        tabq.add(new QuestionFinChantier(10, "Connexion de l’évacuation des condensats"));
        tabq.add(new QuestionFinChantier(11, "Ballon raccordé sur boite  de sortie de câble"));
        tabq.add(new QuestionFinChantier(12, "Ballon rempli avant la mise sous tension"));
        tabq.add(new QuestionFinChantier(13, "Dans la pièces : volume de la pièces supérieur à 20 m³ Vers l’extérieur : Conduit inférieur à 7 mètres"));
        tabq.add(new QuestionFinChantier(14, "Conduits rigides PVC ou PEHD, isolés"));
        tabq.add(new QuestionFinChantier(15, "Respect des  distance mini et maxi entre conduit"));
        tabq.add(new QuestionFinChantier(16, "Respect des diamètres des tuyaux"));

    }
}
