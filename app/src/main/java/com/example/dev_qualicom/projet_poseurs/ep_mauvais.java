package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ep_mauvais extends AppCompatActivity {

    Button suivant;
    ArrayList<Question> objects = new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_mauvais);

        suivant = (Button) findViewById(R.id.suivant);
        Bundle extra = getIntent().getBundleExtra("extra");
        objects = (ArrayList<Question>) extra.getSerializable("objects");

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ep_mauvais.this, enquete_preliminaire_terminee.class);
                Bundle extra = new Bundle();
                extra.putSerializable("objects", objects);
                i.putExtra("extra", extra);
                startActivity(i);
                finish();
            }
        });

    }
}
