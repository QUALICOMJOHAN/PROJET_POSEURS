package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class q_satisfaction_photo extends AppCompatActivity {

    Button valider;

    ArrayList<Question> objects = new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_satisfaction_photo);

        valider = (Button) findViewById(R.id.suivant2);

        Bundle extra = getIntent().getBundleExtra("extra");
        objects = (ArrayList<Question>) extra.getSerializable("objects");

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(q_satisfaction_photo.this, q_satisfaction_validation.class);
                Bundle extra = new Bundle();
                extra.putSerializable("objects", objects);
                intent.putExtra("extra", extra);
                startActivity(intent);
                finish();
            }
        });

    }
}
