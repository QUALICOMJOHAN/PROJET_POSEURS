package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class q_satisfaction2 extends AppCompatActivity {

    Button ts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_satisfaction2);

        ts = (Button) findViewById(R.id.ouip);

        ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction2.this, q_satisfaction3.class);
                startActivity(intent);
            }
        });
    }
}
