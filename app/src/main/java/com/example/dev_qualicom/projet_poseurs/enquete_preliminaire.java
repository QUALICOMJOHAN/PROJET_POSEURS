package com.example.dev_qualicom.projet_poseurs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class enquete_preliminaire extends AppCompatActivity {

    private SignaturePad mSignaturePad;
    private MyFTPClientFunctions ftpclient = null;
    private ArrayList<Question> tabq = new ArrayList<Question>();
    private int currentq = 1;
    private Button oui;
    private Button non;
    private TextView question;
    private TextView numq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquete_preliminaire);

        init_tab_question();

        ftpclient = new MyFTPClientFunctions();

        question = (TextView) findViewById(R.id.label_photo_souvenir);
        numq = (TextView) findViewById(R.id.numq);

        oui = (Button) findViewById(R.id.oui);
        non = (Button) findViewById(R.id.non);

        oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                saveImage(mSignaturePad.getSignatureBitmap(), "TEST");

                new Thread(new Runnable() {
                    public void run() {
                        boolean status = false;
                        // host – your FTP address
                        // username & password – for your secured login
                        // 21 default gateway for FTP
                        status = ftpclient.ftpConnect("ftp.cluster017.ovh.net", "qualicomwa", "3UhvXgXmvTP8", 21);

                        ftpclient.ftpUpload(Environment.getExternalStorageDirectory().toString() +"/TEST.JPG", "RES.JPG", "", enquete_preliminaire.this);


                        if (status == true) {
                            Log.d("TEST", "Connection Success");
                        } else {
                            Log.d("TEST", "Connection failed");
                        }
                    }
                }).start();

            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });*/
    }

    private void q_suivante(boolean reponse){
        tabq.get(currentq).setReponse(reponse);
        currentq ++;

        question.setText(tabq.get(currentq).getQuestion());
        numq.setText(tabq.get(currentq).getNum()+"/10");

    }

    private void init_tab_question(){

        tabq.add(new Question(1, "Enquête préliminaire Etes-vous satisfait de notre technicien M.TECHNICIEN ?"));
        tabq.add(new Question(2, "Avez-vous parfaitement compris ses explications ?"));
        tabq.add(new Question(3, "Avez-vous compris l’ensemble de votre bon de commande ?"));
        tabq.add(new Question(4, "Avez-vous des questions sur votre offre de financement ?"));
        tabq.add(new Question(5, "Avez-vous en votre possession votre double du bon de commande ?"));
        tabq.add(new Question(6, "Avez-vous en votre possession le double de votre dossier de financement ?"));
        tabq.add(new Question(7, "Avez-vous en votre possession votre extension de garantie ?"));
        tabq.add(new Question(8, "Avez-vous en votre possession votre contrat d’entretien avec le montant annuel inscrit  ?"));
        tabq.add(new Question(9, "Avez-vous d’autres questions ?"));
        tabq.add(new Question(10, "Pouvons-nous commencer la prestation technique et l’installation de votre matériel conformément à votre bon de commande ?"));

    }

    private void saveImage(Bitmap finalBitmap, String image_name) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "/" + image_name+ ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
