package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class q_satisfaction3 extends AppCompatActivity {

    Button ts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_satisfaction3);

        ts = (Button) findViewById(R.id.oui);

        ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction3.this, q_satisfaction_temoignage.class);
                startActivity(intent);
            }
        });
    }
}
