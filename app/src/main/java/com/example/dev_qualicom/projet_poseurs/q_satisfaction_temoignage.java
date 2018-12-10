package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class q_satisfaction_temoignage extends AppCompatActivity {

    Button valider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_satisfaction_temoignage);

        valider = (Button) findViewById(R.id.oui);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction_temoignage.this, q_satisfaction_photo.class);
                startActivity(intent);
            }
        });

    }
}
