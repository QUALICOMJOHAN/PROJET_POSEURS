package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class proces_verbal_signature extends AppCompatActivity {

    SignaturePad mSignaturePad;
    String mCurrentSgnaturePath;
    Button next;
    TextView obs_tab;
    TextView nom, adresse, cp, ville, date_reception, date_commande, fait_a_ville, fait_a_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_proces_verbal_signature);

        mSignaturePad = (SignaturePad) findViewById(R.id.signature);

        obs_tab = (TextView) findViewById(R.id.input_obs);

        next = (Button) findViewById(R.id.suivant);

        nom = (TextView) findViewById(R.id.nom);
        adresse = (TextView) findViewById(R.id.adresse);
        cp = (TextView) findViewById(R.id.CP);
        ville = (TextView) findViewById(R.id.ville);
        date_reception = (TextView) findViewById(R.id.date_reception);
        date_commande = (TextView) findViewById(R.id.date_commande);
        fait_a_ville = (TextView) findViewById(R.id.fait_a_ville);
        fait_a_date = (TextView) findViewById(R.id.fait_a_date);

        nom.setText(PoseSingleton.getInstance().getPose().getName());
        adresse.setText(PoseSingleton.getInstance().getPose().getStreet());
        cp.setText(PoseSingleton.getInstance().getPose().getZip());
        ville.setText(PoseSingleton.getInstance().getPose().getCity());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        date_reception.setText(df.format(Calendar.getInstance().getTime()));

        date_commande.setText(df.format(PoseSingleton.getInstance().getPose().getX_date_vente().toDate()));
        fait_a_ville.setText(PoseSingleton.getInstance().getPose().getCity());
        fait_a_date.setText(df.format(Calendar.getInstance().getTime()));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(proces_verbal_signature.this, q_satisfaction_debut.class);
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
                    String sign = createStringPathFile("vp1");
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
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()+"/signature");

        mCurrentSgnaturePath = storageDir+"/"+imageFileName+".png";

        return mCurrentSgnaturePath;
    }
}
