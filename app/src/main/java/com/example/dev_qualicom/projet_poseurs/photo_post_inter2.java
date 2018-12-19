package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class photo_post_inter2 extends AppCompatActivity {

    String photopath;
    int id;
    ImageView img;
    Button retake;
    Button annule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_post_inter2);

        Intent i = getIntent();
        id = i.getExtras().getInt("id");
        photopath = i.getExtras().getString("photo");

        img = findViewById(R.id.camera_preview);
        retake = findViewById(R.id.non);
        annule = findViewById(R.id.oui);

        File file = new File(photopath);

        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        img.setImageBitmap(myBitmap);

        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(photo_post_inter2.this, photo_post_inter.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });

        annule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(photo_post_inter2.this, photo_post_inter.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
