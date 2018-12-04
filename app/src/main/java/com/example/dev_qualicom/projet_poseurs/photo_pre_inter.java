package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class photo_pre_inter extends AppCompatActivity {

    Button commencer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_pre_inter);

        commencer = (Button) findViewById(R.id.commencer_photos);

        commencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(photo_pre_inter.this, photo_pre_inter_2.class);
                //i.putExtra("id_pose", id_pose);
                startActivity(i);
            }
        });

    }
}
