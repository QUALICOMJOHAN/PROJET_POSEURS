package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class q_satisfaction_validation extends AppCompatActivity {

    Button valider;
    String obs;

    ArrayList<String[]> objects = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_q_satisfaction_validation);

        valider = (Button) findViewById(R.id.modalites);

        Bundle extra = getIntent().getBundleExtra("extra");
        objects = (ArrayList<String[]>) extra.getSerializable("objects");
        obs = getIntent().getExtras().getString("obs");

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction_validation.this, autor_donnees.class);
                Bundle extra = new Bundle();
                extra.putSerializable("objects", objects);
                intent.putExtra("extra", extra);
                intent.putExtra("obs", obs);
                startActivity(intent);
                finish();
            }
        });

    }
}
