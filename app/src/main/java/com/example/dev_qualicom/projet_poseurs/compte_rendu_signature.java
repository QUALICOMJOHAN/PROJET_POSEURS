package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class compte_rendu_signature extends AppCompatActivity {

    SignaturePad mSignaturePad1;
    SignaturePad mSignaturePad2;
    SignaturePad mSignaturePad3;
    SignaturePad mSignaturePad4;
    SignaturePad mSignaturePad5;
    String mCurrentSgnaturePath;

    ArrayList<SignaturePad> pads = new ArrayList<SignaturePad>();

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_compte_rendu_signature);

        ImageView retour = (ImageView) findViewById(R.id.retour_btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(compte_rendu_signature.this, planning.class);
                String id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
                String nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();
                i.putExtra("id_equipe", id_equipe);
                i.putExtra("nom_equipe", nom_equipe);
                startActivity(i);
                finish();
            }
        });


        mSignaturePad1 = (SignaturePad) findViewById(R.id.textureView1);

        next = (Button) findViewById(R.id.suivant);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(compte_rendu_signature.this, proces_verbal_donner_tab.class);
                startActivity(intent);
            }
        });

        mSignaturePad1.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                try {
                    String sign = createStringPathFile("1");
                    Bitmap bitmap = mSignaturePad1.getSignatureBitmap();

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

        mSignaturePad2 = (SignaturePad) findViewById(R.id.textureView2);
        mSignaturePad2.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                try {
                    String sign = createStringPathFile("2");
                    Bitmap bitmap = mSignaturePad2.getSignatureBitmap();

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

        mSignaturePad3 = (SignaturePad) findViewById(R.id.textureView3);
        mSignaturePad3.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                try {
                    String sign = createStringPathFile("3");
                    Bitmap bitmap = mSignaturePad3.getSignatureBitmap();

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

        mSignaturePad4 = (SignaturePad) findViewById(R.id.textureView4);
        mSignaturePad4.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                try {
                    String sign = createStringPathFile("4");
                    Bitmap bitmap = mSignaturePad4.getSignatureBitmap();

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

        mSignaturePad5 = (SignaturePad) findViewById(R.id.textureView5);
        mSignaturePad5.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                try {
                    String sign = createStringPathFile("5");
                    Bitmap bitmap = mSignaturePad5.getSignatureBitmap();

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

        pads.add(mSignaturePad1);
        pads.add(mSignaturePad2);
        pads.add(mSignaturePad3);
        pads.add(mSignaturePad4);
        pads.add(mSignaturePad5);

        Log.e("TESTTT", String.valueOf(PoseSingleton.getInstance().getPose().getTechniciens().size()));

        for(int i=0 ; i < PoseSingleton.getInstance().getPose().getTechniciens().size() ; i++){
            pads.get(i).setVisibility(View.VISIBLE);
        }

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
