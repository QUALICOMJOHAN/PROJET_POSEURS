package com.example.dev_qualicom.projet_poseurs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class demande_intervention extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button start_intervention;
    TextView labeldate, labelnom, labeladresse, labelcp, labelville, labeltel1, labeltel2;
    TextView input_q;
    ArrayList<String> liste_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        File file  = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()), "Livret_installation_3.pdf");

        if(file.exists()){
            Intent intent = new Intent(demande_intervention.this, validation_install.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_demande_intervention);

        labeldate = (TextView) findViewById(R.id.date);
        labelnom = (TextView) findViewById(R.id.nom);
        labeladresse = (TextView) findViewById(R.id.adresse);
        labelcp = (TextView) findViewById(R.id.CP);
        labelville = (TextView) findViewById(R.id.ville);
        labeltel1 = (TextView) findViewById(R.id.tel1);
        labeltel2 = (TextView) findViewById(R.id.tel2);
        input_q = (TextView) findViewById(R.id.input_q);

        start_intervention = (Button) findViewById(R.id.commencer_inter);

        liste_action = PoseSingleton.getInstance().getPose().getListe();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for(String l : liste_action){
            input_q.setText(input_q.getText()+"\n"+l);
        }

        labeldate.setText(df.format(PoseSingleton.getInstance().getPose().x_date_pose.toDate()));
        labelnom.setText(PoseSingleton.getInstance().getPose().getName());
        labeladresse.setText(PoseSingleton.getInstance().getPose().getStreet());
        labelcp.setText(PoseSingleton.getInstance().getPose().getZip());
        labelville.setText(PoseSingleton.getInstance().getPose().getCity());
        labeltel1.setText(PoseSingleton.getInstance().getPose().getPhone());
        labeltel2.setText(PoseSingleton.getInstance().getPose().getMobile());


        start_intervention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder a_builder = new AlertDialog.Builder(demande_intervention.this);
                a_builder.setMessage("Etes-vous bien présent sur le lieu de l'intervention ?")
                        .setCancelable(false)
                        .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DocumentReference pose = db.collection("Poses").document(PoseSingleton.getInstance().getPose().getId().toString());

                                Calendar cal = Calendar.getInstance();

                                if(PoseSingleton.getInstance().getPose().getStart_pose() == null) {
                                    pose.update("start_pose", cal.getTime());
                                }

                                Intent i = new Intent(demande_intervention.this, donner_tab_client.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("NON", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = a_builder.create();
                alert.show();

            }
        });
    }
}
