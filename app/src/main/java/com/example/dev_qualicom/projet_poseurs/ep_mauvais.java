package com.example.dev_qualicom.projet_poseurs;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ep_mauvais extends AppCompatActivity {

    Button suivant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_mauvais);

        suivant = (Button) findViewById(R.id.suivant);
        //id_pose = getIntent().getStringExtra("id_pose");

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder a_builder = new AlertDialog.Builder(ep_mauvais.this);
                a_builder.setMessage("Toutes les questions ont-elles bien été résolues ?")
                        .setCancelable(false)
                        .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(ep_mauvais.this, validation_install.class);
                                //i.putExtra("id_pose", id_pose);
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
