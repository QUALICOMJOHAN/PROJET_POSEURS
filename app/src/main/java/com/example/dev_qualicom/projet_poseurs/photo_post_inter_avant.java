package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class photo_post_inter_avant extends AppCompatActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_post_inter_avant);

        next = (Button) findViewById(R.id.commencer_post);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(photo_post_inter_avant.this, photo_post_inter.class);
                startActivity(intent);
            }
        });

    }
}
