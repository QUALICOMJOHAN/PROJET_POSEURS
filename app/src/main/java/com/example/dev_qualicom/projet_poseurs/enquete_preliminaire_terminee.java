package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class enquete_preliminaire_terminee extends AppCompatActivity {

    Button suivant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquete_preliminaire_terminee);

        suivant = (Button) findViewById(R.id.suivant);
        //id_pose = getIntent().getStringExtra("id_pose");

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(enquete_preliminaire_terminee.this, validation_install.class);
                startActivity(i);
                finish();

            }
        });

    }
}
