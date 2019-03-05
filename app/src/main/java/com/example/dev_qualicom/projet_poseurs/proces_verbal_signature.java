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
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.hendrix.pdfmyxml.PdfDocument;
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer;

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

        ImageView retour = (ImageView) findViewById(R.id.retour_btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(proces_verbal_signature.this, planning.class);
                String id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
                String nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();
                i.putExtra("id_equipe", id_equipe);
                i.putExtra("nom_equipe", nom_equipe);
                startActivity(i);
                finish();
            }
        });


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

                AbstractViewRenderer page = new AbstractViewRenderer(proces_verbal_signature.this, R.layout.pdf_proces_verbal) {
                    @Override
                    protected void initView(View view) {

                        TextView date_d, nom_prenom, adresse, ville1, cp, portable, fixe, nom, adresse2, CP, ville, date_reception, date_commande, fait_a_ville, fait_a_date;


                        int iter = 1;
                        date_d = view.findViewById(R.id.nom_equipe);
                        nom_prenom = view.findViewById(R.id.nom_prenom);
                        adresse = view.findViewById(R.id.adresse1);
                        ville1 = view.findViewById(R.id.ville1);
                        cp = view.findViewById(R.id.cp);
                        portable = view.findViewById(R.id.portable);
                        fixe = view.findViewById(R.id.fixe);
                        adresse2 = view.findViewById(R.id.adresse);
                        CP = view.findViewById(R.id.CP);
                        ville = view.findViewById(R.id.ville);
                        date_reception = view.findViewById(R.id.date_reception);
                        date_commande = view.findViewById(R.id.date_commande);
                        fait_a_ville = view.findViewById(R.id.fait_a_ville);
                        fait_a_date = view.findViewById(R.id.fait_a_date);
                        ImageView signature = (ImageView)view.findViewById(R.id.signature);

                        for (String l : PoseSingleton.getInstance().getPose().getListe()) {

                            if(Character.isDigit(l.charAt(0))){

                                int did = getResources().getIdentifier("designation_value"+iter, "id", getPackageName());
                                int qid = getResources().getIdentifier("quantite_value"+iter, "id", getPackageName());

                                TextView d = (TextView) view.findViewById(did);
                                TextView q = (TextView) view.findViewById(qid);
                                String[] separated = l.split(" ", 2);
                                d.setText(separated[1]);
                                q.setText(separated[0]);

                                iter ++;
                            }

                        }

                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                        date_d.setText(df.format(PoseSingleton.getInstance().getPose().getX_date_pose().toDate()));
                        nom_prenom.setText(PoseSingleton.getInstance().getPose().getName());
                        adresse.setText(PoseSingleton.getInstance().getPose().getStreet());
                        ville1.setText(PoseSingleton.getInstance().getPose().getCity());
                        cp.setText(PoseSingleton.getInstance().getPose().getZip());
                        portable.setText(PoseSingleton.getInstance().getPose().getMobile());
                        fixe.setText(PoseSingleton.getInstance().getPose().getPhone());
                        adresse2.setText(PoseSingleton.getInstance().getPose().getStreet());
                        CP.setText(PoseSingleton.getInstance().getPose().getZip());
                        ville.setText(PoseSingleton.getInstance().getPose().getCity());
                        date_reception.setText(df.format(PoseSingleton.getInstance().getPose().getX_date_pose().toDate()));
                        date_commande.setText(df.format(PoseSingleton.getInstance().getPose().getX_date_vente().toDate()));
                        fait_a_ville.setText(PoseSingleton.getInstance().getPose().getCity());
                        fait_a_date.setText(df.format(PoseSingleton.getInstance().getPose().getX_date_pose().toDate()));

                    }
                };

                PdfDocument doc            = new PdfDocument(proces_verbal_signature.this);

// add as many pages as you have
                doc.addPage(page);

                doc.setRenderWidth(210);
                doc.setRenderHeight(297);
                doc.setOrientation(PdfDocument.A4_MODE.PORTRAIT);
                doc.setFileName("Proces_verbal");
                doc.setSaveDirectory(proces_verbal_signature.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()));
                doc.setInflateOnMainThread(false);
                doc.setListener(new PdfDocument.Callback() {
                    @Override
                    public void onComplete(File file) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Complete");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Error");
                    }
                });

                doc.createPdf(proces_verbal_signature.this);

// you can reuse the bitmap if you want
                page.setReuseBitmap(true);

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
