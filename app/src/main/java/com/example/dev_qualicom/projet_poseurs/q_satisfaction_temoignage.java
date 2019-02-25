package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class q_satisfaction_temoignage extends AppCompatActivity {

    Button valider;
    TextView input_temoignage;

    ArrayList<String[]> objects = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_q_satisfaction_temoignage);

        valider = (Button) findViewById(R.id.oui);
        input_temoignage = (TextView) findViewById(R.id.input_temoignage);

        Bundle extra = getIntent().getBundleExtra("extra");
        objects = (ArrayList<String[]>) extra.getSerializable("objects");


        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction_temoignage.this, q_satisfaction_photo.class);
                Bundle extra = new Bundle();
                extra.putSerializable("objects", objects);
                intent.putExtra("extra", extra);
                intent.putExtra("obs", input_temoignage.getText());
                startActivity(intent);
                finish();
            }
        });

    }
}
