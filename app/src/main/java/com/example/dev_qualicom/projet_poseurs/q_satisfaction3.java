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

public class q_satisfaction3 extends AppCompatActivity {

    private Button oui;
    private Button non;
    private int currentq = 1;
    private TextView numq, question;
    private ArrayList<Question> tabq = new ArrayList<Question>();
    ArrayList<String[]> tab = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_q_satisfaction3);

        ImageView retour = (ImageView) findViewById(R.id.retour_btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(q_satisfaction3.this, planning.class);
                String id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
                String nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();
                i.putExtra("id_equipe", id_equipe);
                i.putExtra("nom_equipe", nom_equipe);
                startActivity(i);
                finish();
            }
        });


        init_tab_question();

        oui = (Button) findViewById(R.id.oui);
        non = (Button) findViewById(R.id.non);

        Bundle extra = getIntent().getBundleExtra("extra");
        tab = (ArrayList<String[]>) extra.getSerializable("objects");

        numq = (TextView)findViewById(R.id.numq);
        question = (TextView)findViewById(R.id.label_valid_horaires);

        oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab.add(new String[]{tabq.get(currentq-1).getQuestion(), "oui"});
                q_suivante(true);
            }
        });
        non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab.add(new String[]{tabq.get(currentq-1).getQuestion(), "non"});
                q_suivante(false);
            }
        });
    }

    private void q_suivante(boolean reponse){
        tabq.get(currentq-1).setReponse(reponse);
        currentq ++;

        if(currentq > tabq.size()){


            Intent intent = new Intent(q_satisfaction3.this, q_satisfaction_temoignage.class);
            Bundle extra = new Bundle();
            extra.putSerializable("objects", tab);
            intent.putExtra("extra", extra);
            startActivity(intent);
            finish();


        }else{
            if(tabq.get(currentq-1).isAttendu()){
                oui.setBackgroundColor(Color.parseColor("#669900"));
                non.setBackgroundColor(Color.parseColor("#cc0000"));
            }else{
                oui.setBackgroundColor(Color.parseColor("#cc0000"));
                non.setBackgroundColor(Color.parseColor("#669900"));
            }
            question.setText(tabq.get(currentq-1).getQuestion());
            numq.setText(tabq.get(currentq-1).getNum()+"/6");
        }
    }

    private void init_tab_question(){

        tabq.add(new Question(3, "Les techniciens sont-ils arrivés à l’heure ?",true));
        tabq.add(new Question(4, "Les techniciens ont-ils répondu à vos questions ?",true));
        tabq.add(new Question(5, "Les techniciens vous ont-ils expliqué le fonctionnement du matériel ?",true));
        tabq.add(new Question(6, "Les techniciens ont-ils nettoyé le chantier après avoir terminé leur intervention ?",true));

    }

}
