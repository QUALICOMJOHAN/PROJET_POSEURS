package com.example.dev_qualicom.projet_poseurs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;

public class enquete_preliminaire extends AppCompatActivity {

    private SignaturePad mSignaturePad;
    private MyFTPClientFunctions ftpclient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquete_preliminaire);

        ftpclient = new MyFTPClientFunctions();

        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);

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
        });
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
