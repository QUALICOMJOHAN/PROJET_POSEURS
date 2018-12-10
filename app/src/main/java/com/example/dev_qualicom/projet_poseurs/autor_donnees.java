package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class autor_donnees extends AppCompatActivity {

    SignaturePad mSignaturePad;
    Button next;
    String mCurrentSgnaturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_donnees);

        mSignaturePad = (SignaturePad) findViewById(R.id.signature);

        next = (Button) findViewById(R.id.suivant2);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(autor_donnees.this, finalisation_inter.class);
                startActivity(intent);
            }
        });

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                try {
                    String sign = createStringPathFile("auto");
                    Bitmap bitmap = mSignaturePad.getSignatureBitmap();

                    saveBitmap(bitmap, sign);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });
    }

    private void saveBitmap(Bitmap bitmap, String sign){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(sign, false);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createStringPathFile(String name) throws IOException {

        String imageFileName = "signature_"+name+"_diff";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/test");

        mCurrentSgnaturePath = storageDir+"/"+imageFileName+".png";

        return mCurrentSgnaturePath;
    }
}
