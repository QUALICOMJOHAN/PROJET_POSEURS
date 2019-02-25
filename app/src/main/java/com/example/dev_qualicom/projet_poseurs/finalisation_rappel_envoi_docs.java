package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

public class finalisation_rappel_envoi_docs extends AppCompatActivity {

    Button next;
    LinearLayout galerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_finalisation_rappel_envoi_docs);

        next = (Button) findViewById(R.id.suivant);

        galerie = (LinearLayout) findViewById(R.id.galerie);

        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()+"/documents").toString());
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            ImageView iv = new ImageView(this);
            iv.setPadding(10,0,0,0);
            Bitmap myBitmap = BitmapFactory.decodeFile(files[i].getAbsolutePath());
            iv.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 240, 240, false));
            galerie.addView(iv);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(finalisation_rappel_envoi_docs.this, final_final.class);
                startActivity(intent);
            }
        });

    }
}
