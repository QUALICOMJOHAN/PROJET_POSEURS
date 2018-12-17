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

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.hendrix.pdfmyxml.PdfDocument;
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class autor_donnees extends AppCompatActivity {

    SignaturePad mSignaturePad;
    Button next;
    String mCurrentSgnaturePath;
    ArrayList<Question> objects = new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_donnees);

        mSignaturePad = (SignaturePad) findViewById(R.id.signature);

        Bundle extra = getIntent().getBundleExtra("extra");
        objects = (ArrayList<Question>) extra.getSerializable("objects");

        next = (Button) findViewById(R.id.suivant2);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AbstractViewRenderer page = new AbstractViewRenderer(autor_donnees.this, R.layout.pdf_livret_pose2) {
                    @Override
                    protected void initView(View view) {

                        CheckBox ou1, ou2, ou3, ou4;
                        CheckBox no1, no2, no3, no4;
                        ArrayList<CheckBox> ou = new ArrayList<CheckBox>();
                        ArrayList<CheckBox> no = new ArrayList<CheckBox>();

                        ou.add(ou1 = (CheckBox)view.findViewById(R.id.oui));
                        ou.add(ou2 = (CheckBox)view.findViewById(R.id.oui2));
                        ou.add(ou3 = (CheckBox)view.findViewById(R.id.oui3));
                        ou.add(ou4 = (CheckBox)view.findViewById(R.id.oui4));

                        no.add(no1 = (CheckBox)view.findViewById(R.id.non));
                        no.add(no2 = (CheckBox)view.findViewById(R.id.non2));
                        no.add(no3 = (CheckBox)view.findViewById(R.id.non3));
                        no.add(no4 = (CheckBox)view.findViewById(R.id.non4));

                        for(Question q: objects){
                            if(q.isReponse()){
                                ou.get(q.getNum()-3).setChecked(true);
                            }else{
                                no.get(q.getNum()-3).setChecked(true);
                            }
                        }

                        ImageView signature = (ImageView)view.findViewById(R.id.signature2);

                        File file = new File(mCurrentSgnaturePath);

                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
                        bitmap = Bitmap.createScaledBitmap(bitmap,642,151,true);

                        signature.setImageBitmap(bitmap);

                    }
                };

                PdfDocument doc            = new PdfDocument(autor_donnees.this);

// add as many pages as you have
                doc.addPage(page);

                doc.setRenderWidth(210);
                doc.setRenderHeight(297);
                doc.setOrientation(PdfDocument.A4_MODE.PORTRAIT);
                doc.setFileName("Livret_installation_page2");
                doc.setSaveDirectory(autor_donnees.this.getExternalFilesDir(null));
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
