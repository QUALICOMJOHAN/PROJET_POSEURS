package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class proces_verbal extends AppCompatActivity {

    Button next;
    TextView labeldate, labelnom, labeladresse, labelcp, labelville, labeltel1, labeltel2;
    TableLayout table;
    ArrayList<String> liste_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_proces_verbal);

        ImageView retour = (ImageView) findViewById(R.id.retour_btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(proces_verbal.this, planning.class);
                String id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
                String nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();
                i.putExtra("id_equipe", id_equipe);
                i.putExtra("nom_equipe", nom_equipe);
                startActivity(i);
                finish();
            }
        });


        labeldate = (TextView) findViewById(R.id.date);
        labelnom = (TextView) findViewById(R.id.nom);
        labeladresse = (TextView) findViewById(R.id.adresse);
        labelcp = (TextView) findViewById(R.id.CP);
        labelville = (TextView) findViewById(R.id.ville);
        labeltel1 = (TextView) findViewById(R.id.tel1);
        labeltel2 = (TextView) findViewById(R.id.tel2);

        table = (TableLayout) findViewById(R.id.table);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        liste_action = PoseSingleton.getInstance().getPose().getListe();

        for(String l : liste_action){
            TableRow row = new TableRow(this);
            if(Character.isDigit(l.charAt(0))){
                TextView t1 = new TextView(this);
                TextView t2 = new TextView(this);
                String[] separated = l.split(" ", 2);
                t1.setText(separated[0]);
                t2.setText(separated[1]);

                row.addView(t1);
                row.addView(t2);

            }else{
                TextView t1 = new TextView(this);
                t1.setText(l);
                row.addView(t1);
            }

            table.addView(row);
        }

        labeldate.setText(df.format(PoseSingleton.getInstance().getPose().x_date_pose.toDate()));
        labelnom.setText(PoseSingleton.getInstance().getPose().getName());
        labeladresse.setText(PoseSingleton.getInstance().getPose().getStreet());
        labelcp.setText(PoseSingleton.getInstance().getPose().getZip());
        labelville.setText(PoseSingleton.getInstance().getPose().getCity());
        labeltel1.setText(PoseSingleton.getInstance().getPose().getPhone());
        labeltel2.setText(PoseSingleton.getInstance().getPose().getMobile());


        next = (Button) findViewById(R.id.suivant);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(proces_verbal.this, proces_verbal_signature.class);
                startActivity(intent);
            }
        });
    }
}
