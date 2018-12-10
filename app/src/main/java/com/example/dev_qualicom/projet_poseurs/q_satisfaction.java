package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class q_satisfaction extends AppCompatActivity {

    Button ts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_satisfaction);

        ts = (Button) findViewById(R.id.ts);

        ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction.this, q_satisfaction2.class);
                startActivity(intent);
            }
        });
    }
}
