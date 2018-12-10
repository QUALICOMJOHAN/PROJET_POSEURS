package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class compte_rendu_q extends AppCompatActivity {

    Button resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_rendu_q);

        resp = (Button) findViewById(R.id.resp);

        resp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(compte_rendu_q.this, compte_rendu_signature.class);
                startActivity(next);
            }
        });

    }
}
