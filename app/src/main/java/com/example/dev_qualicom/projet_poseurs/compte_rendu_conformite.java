package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class compte_rendu_conformite extends AppCompatActivity {


    ArrayList<QuestionFinChantier> tabq = new ArrayList<QuestionFinChantier>();
    TextView commentaire;
    CheckBox oui;
    CheckBox non;
    Button suivant;
    LinearLayout nonconformite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_rendu_commentaire_conformite);

        commentaire = findViewById(R.id.pdc_commentaire);
        oui = findViewById(R.id.ouiconforme);
        non = findViewById(R.id.nonconforme);
        suivant = findViewById(R.id.suivant);
        nonconformite = findViewById(R.id.fiche_conformite_ctn);


        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(compte_rendu_conformite.this, compte_rendu_signature.class);
                Bundle extra = new Bundle();
                extra.putSerializable("objects", tabq);
                next.putExtra("extra", extra);
                next.putExtra("commentaire", commentaire.getText().toString());
                startActivity(next);
                finish();
            }
        });

        oui.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    suivant.setVisibility(View.VISIBLE);
                    non.setChecked(false);
                }else{
                    suivant.setVisibility(View.INVISIBLE);
                }
            }

        });

        non.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    nonconformite.setVisibility(View.VISIBLE);
                    oui.setChecked(false);
                    suivant.setVisibility(View.INVISIBLE);
                }else{
                    nonconformite.setVisibility(View.INVISIBLE);
                }
            }

        });

        Bundle extra = getIntent().getBundleExtra("extra");
        tabq = (ArrayList<QuestionFinChantier>) extra.getSerializable("objects");

    }
}
