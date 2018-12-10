package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class proces_verbal_donner_tab extends AppCompatActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proces_verbal_donner_tab);

        next = (Button) findViewById(R.id.btn_commencer_inter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(proces_verbal_donner_tab.this, proces_verbal.class);
                startActivity(intent);
            }
        });
    }
}
