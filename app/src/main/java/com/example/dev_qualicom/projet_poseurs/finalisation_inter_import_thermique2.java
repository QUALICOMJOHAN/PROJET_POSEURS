package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class finalisation_inter_import_thermique2 extends AppCompatActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalisation_inter_import_thermique2);

        next = (Button) findViewById(R.id.suivant);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(finalisation_inter_import_thermique2.this, finalisation_rappel_envoi_docs.class);
                startActivity(intent);
            }
        });

    }
}
