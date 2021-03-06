package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private int error = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_enquete_preliminaire);

        ImageView retour = (ImageView) findViewById(R.id.retour_btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(enquete_preliminaire.this, planning.class);
                String id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
                String nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();
                i.putExtra("id_equipe", id_equipe);
                i.putExtra("nom_equipe", nom_equipe);
                startActivity(i);
                finish();
            }
        });


        init_tab_question();

        ftpclient = new MyFTPClientFunctions();

        question = (TextView) findViewById(R.id.label_photo_souvenir);
        numq = (TextView) findViewById(R.id.numq);

        question.setText(tabq.get(currentq-1).getQuestion());
        numq.setText(tabq.get(currentq-1).getNum()+"/10");

        oui = (Button) findViewById(R.id.oui);
        non = (Button) findViewById(R.id.non);

        oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q_suivante(true);
            }
        });

        non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q_suivante(false);
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
        tabq.get(currentq-1).setReponse(reponse);
        currentq ++;

        if(currentq > tabq.size()){

            for(Question q : tabq){
                if(!q.isEqual()){
                    error += 1;
                }
            }

            if(error == 0){
                Intent intent = new Intent(enquete_preliminaire.this, enquete_preliminaire_recap_signature.class);
                Bundle extra = new Bundle();
                extra.putSerializable("objects", tabq);
                intent.putExtra("extra", extra);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(enquete_preliminaire.this, ep_mauvais.class);
                Bundle extra = new Bundle();
                extra.putSerializable("objects", tabq);
                intent.putExtra("extra", extra);
                startActivity(intent);
                finish();
            }

        }else{
            if(tabq.get(currentq-1).isAttendu()){
                oui.setBackgroundColor(Color.parseColor("#669900"));
                non.setBackgroundColor(Color.parseColor("#cc0000"));
            }else{
                oui.setBackgroundColor(Color.parseColor("#cc0000"));
                non.setBackgroundColor(Color.parseColor("#669900"));
            }
            question.setText(tabq.get(currentq-1).getQuestion());
            numq.setText(tabq.get(currentq-1).getNum()+"/10");
        }
    }

    private void init_tab_question(){

        tabq.add(new Question(1, "Etes-vous satisfait de notre technicien M."+ PoseSingleton.getInstance().getPose().getVendeur() +" ?",true));
        tabq.add(new Question(2, "Avez-vous parfaitement compris ses explications ?",true));
        tabq.add(new Question(3, "Avez-vous compris l’ensemble de votre bon de commande ?",true));
        if(PoseSingleton.getInstance().getPose().isX_dossier_financement()) {
            tabq.add(new Question(4, "Avez-vous des questions sur votre offre de financement ?", false));
            tabq.add(new Question(5, "Avez-vous en votre possession votre double du bon de commande ?", true));
            tabq.add(new Question(6, "Avez-vous en votre possession le double de votre dossier de financement ?", true));
            tabq.add(new Question(7, "Avez-vous en votre possession votre extension de garantie ?", true));
            tabq.add(new Question(8, "Avez-vous en votre possession votre contrat d’entretien avec le montant annuel inscrit  ?", true));
            tabq.add(new Question(9, "Avez-vous d’autres questions ?", false));
            tabq.add(new Question(10, "Pouvons-nous commencer la prestation technique et l’installation de votre matériel conformément à votre bon de commande ?", true));
        }else{
            tabq.add(new Question(5, "Avez-vous en votre possession votre double du bon de commande ?", true));
            tabq.add(new Question(7, "Avez-vous en votre possession votre extension de garantie ?", true));
            tabq.add(new Question(8, "Avez-vous en votre possession votre contrat d’entretien avec le montant annuel inscrit  ?", true));
            tabq.add(new Question(9, "Avez-vous d’autres questions ?", false));
            tabq.add(new Question(10, "Pouvons-nous commencer la prestation technique et l’installation de votre matériel conformément à votre bon de commande ?", true));
        }
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
