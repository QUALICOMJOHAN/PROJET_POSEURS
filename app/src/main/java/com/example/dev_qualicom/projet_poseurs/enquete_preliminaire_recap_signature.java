package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

public class enquete_preliminaire_recap_signature extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SignaturePad mSignaturePad;
    Button next;
    CheckBox oui1, oui2, oui3, oui4, oui5, oui6, oui7, oui8, oui9, oui10;
    CheckBox non1, non2, non3, non4, non5, non6, non7, non8, non9, non10;
    ArrayList<CheckBox> oui = new ArrayList<CheckBox>();
    ArrayList<CheckBox> non = new ArrayList<CheckBox>();
    String mCurrentSgnaturePath;
    ArrayList<Question> objects = new ArrayList<Question>();
    TextView nom, equipe, date, q1;
    String nom_equipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_enquete_preliminaire_recap_signature);

        mSignaturePad = (SignaturePad) findViewById(R.id.signature);

        oui.add(oui1 = (CheckBox)findViewById(R.id.oui));
        oui.add(oui2 = (CheckBox)findViewById(R.id.oui2));
        oui.add(oui3 = (CheckBox)findViewById(R.id.oui3));
        oui.add(oui4 = (CheckBox)findViewById(R.id.oui4));
        oui.add(oui5 = (CheckBox)findViewById(R.id.oui5));
        oui.add(oui6 = (CheckBox)findViewById(R.id.oui6));
        oui.add(oui7 = (CheckBox)findViewById(R.id.oui7));
        oui.add(oui8 = (CheckBox)findViewById(R.id.oui8));
        oui.add(oui9 = (CheckBox)findViewById(R.id.oui9));
        oui.add(oui10 = (CheckBox)findViewById(R.id.oui10));

        non.add(non1 = (CheckBox)findViewById(R.id.non));
        non.add(non2 = (CheckBox)findViewById(R.id.non2));
        non.add(non3 = (CheckBox)findViewById(R.id.non3));
        non.add(non4 = (CheckBox)findViewById(R.id.non4));
        non.add(non5 = (CheckBox)findViewById(R.id.non5));
        non.add(non6 = (CheckBox)findViewById(R.id.non6));
        non.add(non7 = (CheckBox)findViewById(R.id.non7));
        non.add(non8 = (CheckBox)findViewById(R.id.non8));
        non.add(non9 = (CheckBox)findViewById(R.id.non9));
        non.add(non10 = (CheckBox)findViewById(R.id.non10));

        nom = (TextView)findViewById(R.id.nom);
        equipe = (TextView)findViewById(R.id.nom_equipe);
        date = (TextView)findViewById(R.id.date);
        q1 = (TextView)findViewById(R.id.q1);


        q1.setText("Etes-vous satisfait de notre technicien conseil Mr "+ PoseSingleton.getInstance().getPose().getVendeur()+" ?");

        DocumentReference docRef = db.collection("Equipes").document(PoseSingleton.getInstance().getPose().getEquipe());
         docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 if (task.isSuccessful()) {
                     final DocumentSnapshot document = task.getResult();
                     if (document.exists()) {
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 nom_equipe = document.getData().get("nom").toString();
                                 equipe.setText(document.getData().get("nom").toString());
                             }
                         });
                     }
                 }
             }
         });

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        nom.setText(PoseSingleton.getInstance().getPose().getName());
        date.setText(df.format(PoseSingleton.getInstance().getPose().x_date_pose.toDate()));


        Bundle extra = getIntent().getBundleExtra("extra");
        objects = (ArrayList<Question>) extra.getSerializable("objects");

        for(Question q: objects){
            if(q.isAttendu()){
                oui.get(q.getNum()-1).setChecked(true);
            }else{
                non.get(q.getNum()-1).setChecked(true);
            }
        }

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                try {
                    String sign = createStringPathFile("recap");
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

        next = (Button)findViewById(R.id.suivant);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AbstractViewRenderer page = new AbstractViewRenderer(enquete_preliminaire_recap_signature.this, R.layout.pdf_livret_pose) {
                    @Override
                    protected void initView(View view) {

                        CheckBox ou1, ou2, ou3, ou4, ou5, ou6, ou7, ou8, ou9, ou10;
                        CheckBox no1, no2, no3, no4, no5, no6, no7, no8, no9, no10;
                        ArrayList<CheckBox> ou = new ArrayList<CheckBox>();
                        ArrayList<CheckBox> no = new ArrayList<CheckBox>();
                        TextView nompdf = (TextView)view.findViewById(R.id.nom);
                        TextView equipepdf = (TextView)view.findViewById(R.id.nom_equipe);
                        TextView datepdf = (TextView)view.findViewById(R.id.date);
                        TextView q1pdf = (TextView)view.findViewById(R.id.q1);

                        ou.add(ou1 = (CheckBox)view.findViewById(R.id.oui));
                        ou.add(ou2 = (CheckBox)view.findViewById(R.id.oui2));
                        ou.add(ou3 = (CheckBox)view.findViewById(R.id.oui3));
                        ou.add(ou4 = (CheckBox)view.findViewById(R.id.oui4));
                        ou.add(ou5 = (CheckBox)view.findViewById(R.id.oui5));
                        ou.add(ou6 = (CheckBox)view.findViewById(R.id.oui6));
                        ou.add(ou7 = (CheckBox)view.findViewById(R.id.oui7));
                        ou.add(ou8 = (CheckBox)view.findViewById(R.id.oui8));
                        ou.add(ou9 = (CheckBox)view.findViewById(R.id.oui9));
                        ou.add(ou10 = (CheckBox)view.findViewById(R.id.oui10));

                        no.add(no1 = (CheckBox)view.findViewById(R.id.non));
                        no.add(no2 = (CheckBox)view.findViewById(R.id.non2));
                        no.add(no3 = (CheckBox)view.findViewById(R.id.non3));
                        no.add(no4 = (CheckBox)view.findViewById(R.id.non4));
                        no.add(no5 = (CheckBox)view.findViewById(R.id.non5));
                        no.add(no6 = (CheckBox)view.findViewById(R.id.non6));
                        no.add(no7 = (CheckBox)view.findViewById(R.id.non7));
                        no.add(no8 = (CheckBox)view.findViewById(R.id.non8));
                        no.add(no9 = (CheckBox)view.findViewById(R.id.non9));
                        no.add(no10 = (CheckBox)view.findViewById(R.id.non10));

                        for(Question q: objects){
                            if(q.isAttendu()){
                                ou.get(q.getNum()-1).setChecked(true);
                            }else{
                                no.get(q.getNum()-1).setChecked(true);
                            }
                        }

                        ImageView signature = (ImageView)view.findViewById(R.id.imageView34);

                        File file = new File(mCurrentSgnaturePath);

                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
                        bitmap = Bitmap.createScaledBitmap(bitmap,642,151,true);

                        signature.setImageBitmap(bitmap);

                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        nompdf.setText(PoseSingleton.getInstance().getPose().getName());
                        datepdf.setText(df.format(PoseSingleton.getInstance().getPose().x_date_pose.toDate()));
                        q1pdf.setText("Etes-vous satisfait de notre technicien conseil Mr "+ PoseSingleton.getInstance().getPose().getVendeur()+" ?");
                        equipepdf.setText(enquete_preliminaire_recap_signature.this.nom_equipe);


                    }
                };

                PdfDocument doc            = new PdfDocument(enquete_preliminaire_recap_signature.this);

// add as many pages as you have
                doc.addPage(page);

                doc.setRenderWidth(210);
                doc.setRenderHeight(297);
                doc.setOrientation(PdfDocument.A4_MODE.PORTRAIT);
                doc.setFileName("Livret_installation_3");
                doc.setSaveDirectory(enquete_preliminaire_recap_signature.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()));
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

                doc.createPdf(enquete_preliminaire_recap_signature.this);

// you can reuse the bitmap if you want
                page.setReuseBitmap(true);

                Intent intent = new Intent(enquete_preliminaire_recap_signature.this, ep_bon.class);
                startActivity(intent);
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
