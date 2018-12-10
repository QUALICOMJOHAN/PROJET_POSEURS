package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class q_satisfaction_photo extends AppCompatActivity {

    Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_satisfaction_photo);

        valider = (Button) findViewById(R.id.suivant2);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction_photo.this, q_satisfaction_validation.class);
                startActivity(intent);
            }
        });

    }
}
