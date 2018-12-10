package com.example.dev_qualicom.projet_poseurs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class demande_intervention extends AppCompatActivity {

    Button start_intervention;
    String id_pose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande_intervention);

        start_intervention = (Button) findViewById(R.id.commencer_inter);
        id_pose = getIntent().getStringExtra("id_pose");

        start_intervention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder a_builder = new AlertDialog.Builder(demande_intervention.this);
                a_builder.setMessage("Etes-vous bien présent sur le lieu de l'intervention ?")
                        .setCancelable(false)
                        .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(demande_intervention.this, donner_tab_client.class);
                                i.putExtra("id_pose", id_pose);
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
