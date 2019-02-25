package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hendrix.pdfmyxml.PdfDocument;
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class autor_donnees extends AppCompatActivity {

    SignaturePad mSignaturePad;
    Button next;
    String mCurrentSgnaturePath;
    ArrayList<String[]> objects = new ArrayList<String[]>();
    TextView nom, adresse, cp, ville, date_reception;
    LinearLayout galerie;
    String obs;
    Double value_reponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_autor_donnees);

        mSignaturePad = (SignaturePad) findViewById(R.id.signature);

        value_reponse = 0.0;

        Bundle extra = getIntent().getBundleExtra("extra");
        objects = (ArrayList<String[]>) extra.getSerializable("objects");

        nom = (TextView) findViewById(R.id.nom);
        adresse = (TextView) findViewById(R.id.adresse);
        cp = (TextView) findViewById(R.id.CP);
        ville = (TextView) findViewById(R.id.ville);
        date_reception = (TextView) findViewById(R.id.date_reception);
        galerie = (LinearLayout) findViewById(R.id.galerie);

        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()+"/pre").toString());
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            ImageView iv = new ImageView(this);
            iv.setPadding(10,0,0,0);
            Bitmap myBitmap = BitmapFactory.decodeFile(files[i].getAbsolutePath());
            iv.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 120, 120, false));
            galerie.addView(iv);
        }

        directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()+"/post").toString());
        files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            ImageView iv = new ImageView(this);
            iv.setPadding(10,0,0,0);
            Bitmap myBitmap = BitmapFactory.decodeFile(files[i].getAbsolutePath());
            iv.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 120, 120, false));
            galerie.addView(iv);
        }

        nom.setText(PoseSingleton.getInstance().getPose().getName());
        adresse.setText(PoseSingleton.getInstance().getPose().getStreet());
        cp.setText(PoseSingleton.getInstance().getPose().getZip());
        ville.setText(PoseSingleton.getInstance().getPose().getCity());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        date_reception.setText(df.format(Calendar.getInstance().getTime()));

        next = (Button) findViewById(R.id.suivant2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AbstractViewRenderer page = new AbstractViewRenderer(autor_donnees.this, R.layout.pdf_livret_pose1) {
                    @Override
                    protected void initView(View view) {
                        TextView obs = (TextView)view.findViewById(R.id.editText);
                        TextView lieu = (TextView)view.findViewById(R.id.lieu_sign);
                        TextView date = (TextView)view.findViewById(R.id.date_sign);
                        TextView adresse = (TextView)view.findViewById(R.id.demeurant);
                        TextView nom = (TextView)view.findViewById(R.id.soussigne);
                        ImageView signature = (ImageView)view.findViewById(R.id.signature_img);

                        File file = new File(mCurrentSgnaturePath);

                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
                        bitmap = Bitmap.createScaledBitmap(bitmap,642,151,true);

                        signature.setImageBitmap(bitmap);

                        lieu.setText(PoseSingleton.getInstance().getPose().getCity());

                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                        date.setText(df.format(Calendar.getInstance().getTime()));
                        adresse.setText(PoseSingleton.getInstance().getPose().getStreet());
                        nom.setText(PoseSingleton.getInstance().getPose().getName());
                    }
                };

                PdfDocument doc            = new PdfDocument(autor_donnees.this);

// add as many pages as you have
                doc.addPage(page);

                doc.setRenderWidth(210);
                doc.setRenderHeight(297);
                doc.setOrientation(PdfDocument.A4_MODE.PORTRAIT);
                doc.setFileName("Livret_installation");
                doc.setSaveDirectory(autor_donnees.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()));
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

                doc.createPdf(autor_donnees.this);

// you can reuse the bitmap if you want
                page.setReuseBitmap(true);


                AbstractViewRenderer page2 = new AbstractViewRenderer(autor_donnees.this, R.layout.pdf_livret_pose2) {
                    @Override
                    protected void initView(View view) {

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference pose = db.collection("Poses").document(PoseSingleton.getInstance().getPose().getId().toString());

                        CheckBox ou1, ou2, ou3, ou4;
                        CheckBox no1, no2, no3, no4;
                        CheckBox ts, s, ps, m, ts2, m2, ps2;
                        ArrayList<CheckBox> ou = new ArrayList<CheckBox>();
                        ArrayList<CheckBox> no = new ArrayList<CheckBox>();
                        TextView fait_a_ville;
                        TextView fait_a_date;

                        ou.add(ou1 = (CheckBox)view.findViewById(R.id.oui));
                        ou.add(ou2 = (CheckBox)view.findViewById(R.id.oui2));
                        ou.add(ou3 = (CheckBox)view.findViewById(R.id.oui3));
                        ou.add(ou4 = (CheckBox)view.findViewById(R.id.oui4));

                        no.add(no1 = (CheckBox)view.findViewById(R.id.non));
                        no.add(no2 = (CheckBox)view.findViewById(R.id.non2));
                        no.add(no3 = (CheckBox)view.findViewById(R.id.non3));
                        no.add(no4 = (CheckBox)view.findViewById(R.id.non4));

                        fait_a_ville = (TextView)view.findViewById(R.id.fait_a_ville);
                        fait_a_date = (TextView)view.findViewById(R.id.fait_a_date);

                        fait_a_ville.setText(PoseSingleton.getInstance().getPose().getCity());

                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                        fait_a_date.setText(df.format(Calendar.getInstance().getTime()));

                        ts = (CheckBox)view.findViewById(R.id.check_ts);
                        s = (CheckBox)view.findViewById(R.id.check_s);
                        ps = (CheckBox)view.findViewById(R.id.check_ps);
                        m = (CheckBox)view.findViewById(R.id.check_m);
                        ts2 = (CheckBox)view.findViewById(R.id.check_ts2);
                        m2 = (CheckBox)view.findViewById(R.id.check_m2);
                        ps2 = (CheckBox)view.findViewById(R.id.check_ps2);

                        switch (objects.get(0)[1]){
                            case "ts": ts.setChecked(true); value_reponse += 1;
                                break;
                            case "s": s.setChecked(true); value_reponse += 0.7;
                                break;
                            case "ps": ps.setChecked(true); value_reponse += 0.3;
                                break;
                            case "m": m.setChecked(true); value_reponse += 0;
                                break;
                        }
                        switch (objects.get(1)[1]){
                            case "ouip": ts2.setChecked(true); value_reponse += 1;
                                break;
                            case "partiellement": m2.setChecked(true); value_reponse += 0.5;
                                break;
                            case "pasdutout": ps2.setChecked(true); value_reponse += 0;
                                break;
                        }

                        switch (objects.get(2)[1]){
                            case "oui": ou1.setChecked(true); value_reponse += 1;
                                break;
                            case "non": no1.setChecked(true); value_reponse += 0;
                                break;
                        }
                        switch (objects.get(3)[1]){
                            case "oui": ou2.setChecked(true); value_reponse += 1;
                                break;
                            case "non": no2.setChecked(true); value_reponse += 0;
                                break;
                        }
                        switch (objects.get(4)[1]){
                            case "oui": ou3.setChecked(true); value_reponse += 1;
                                break;
                            case "non": no3.setChecked(true); value_reponse += 0;
                                break;
                        }
                        switch (objects.get(5)[1]){
                            case "oui": ou4.setChecked(true); value_reponse += 1;
                                break;
                            case "non": no4.setChecked(true); value_reponse += 0;
                                break;
                        }

                        value_reponse = value_reponse / 6;

                        pose.update("satisfaction", value_reponse);

                        ImageView signature = (ImageView)view.findViewById(R.id.signature2);

                        File file = new File(mCurrentSgnaturePath);

                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
                        bitmap = Bitmap.createScaledBitmap(bitmap,642,151,true);

                        signature.setImageBitmap(bitmap);

                    }
                };

                PdfDocument doc2            = new PdfDocument(autor_donnees.this);

// add as many pages as you have
                doc2.addPage(page2);

                doc2.setRenderWidth(210);
                doc2.setRenderHeight(297);
                doc2.setOrientation(PdfDocument.A4_MODE.PORTRAIT);
                doc2.setFileName("Livret_installation_page2");
                doc2.setSaveDirectory(autor_donnees.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()));
                doc2.setInflateOnMainThread(false);
                doc2.setListener(new PdfDocument.Callback() {
                    @Override
                    public void onComplete(File file) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Complete");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Error");
                    }
                });

                doc2.createPdf(autor_donnees.this);

// you can reuse the bitmap if you want
                page2.setReuseBitmap(true);

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
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()+"/signature");

        mCurrentSgnaturePath = storageDir+"/"+imageFileName+".png";

        return mCurrentSgnaturePath;
    }
}
