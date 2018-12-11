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

public class proces_verbal_signature extends AppCompatActivity {

    SignaturePad mSignaturePad;
    String mCurrentSgnaturePath;
    Button next;
    TextView obs_tab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proces_verbal_signature);

        mSignaturePad = (SignaturePad) findViewById(R.id.signature);

        obs_tab = (TextView) findViewById(R.id.input_obs);

        next = (Button) findViewById(R.id.suivant);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AbstractViewRenderer page = new AbstractViewRenderer(proces_verbal_signature.this, R.layout.pdf_livret_pose1) {
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

                        obs.setText(obs_tab.getText());
                        lieu.setText("Fontaine les dijon");
                        date.setText("11/12/2018");
                        adresse.setText("5 rue de la grande fin");
                        nom.setText("JOULKVA");
                    }
                };

                PdfDocument doc            = new PdfDocument(proces_verbal_signature.this);

// add as many pages as you have
                doc.addPage(page);

                doc.setRenderWidth(210);
                doc.setRenderHeight(297);
                doc.setOrientation(PdfDocument.A4_MODE.PORTRAIT);
                doc.setFileName("Livret_installation");
                doc.setSaveDirectory(proces_verbal_signature.this.getExternalFilesDir(null));
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
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/test");

        mCurrentSgnaturePath = storageDir+"/"+imageFileName+".png";

        return mCurrentSgnaturePath;
    }
}
