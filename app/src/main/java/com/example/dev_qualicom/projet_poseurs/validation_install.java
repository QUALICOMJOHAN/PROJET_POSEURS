package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class validation_install extends AppCompatActivity {

    Button commencer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_install);

        commencer = (Button) findViewById(R.id.commencer_inter);

        commencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(validation_install.this, photo_pre_inter.class);
                //i.putExtra("id_pose", id_pose);
                startActivity(i);
            }
        });


    }
}
