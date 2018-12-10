package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class final_final extends AppCompatActivity {

    Button terminer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_final);

        terminer = (Button) findViewById(R.id.terminer);
        terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(final_final.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
