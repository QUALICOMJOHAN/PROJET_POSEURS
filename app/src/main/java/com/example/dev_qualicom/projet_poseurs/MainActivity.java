package com.example.dev_qualicom.projet_poseurs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hendrix.pdfmyxml.PdfDocument;
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.reflect.Reflection.initialize;


public class MainActivity extends AppCompatActivity {

    Button connection;
    TextView login;
    TextView pass;
    String id_equipe;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * permissions request code
     */
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    /**
     * Permissions that need to be explicitly requested from end user.
     */
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent i = new Intent(MainActivity.this, finalisation_inter_import_thermique.class);
        //startActivity(i);

//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("file/*");
//        startActivityForResult(intent, 2);

        checkPermissions();

        AbstractViewRenderer page = new AbstractViewRenderer(this, R.layout.pdf_livret_install_1) {
            @Override
            protected void initView(View view) {
                TextView tv_hello = (TextView)view.findViewById(R.id.soussigne);
                tv_hello.setText("Je suis content e suis content");
            }
        };

        PdfDocument doc            = new PdfDocument(this);

// add as many pages as you have
        doc.addPage(page);

        doc.setRenderWidth(210);
        doc.setRenderHeight(297);
        doc.setOrientation(PdfDocument.A4_MODE.PORTRAIT);
        doc.setFileName("test");
        doc.setSaveDirectory(this.getExternalFilesDir(null));
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

        doc.createPdf(this);

// you can reuse the bitmap if you want
        page.setReuseBitmap(true);


        login = (TextView) findViewById(R.id.identifiant);
        pass = (TextView) findViewById(R.id.mdp);

        connection = (Button) findViewById(R.id.login_btn);

        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("Equipes").whereEqualTo("login", login.getText().toString()).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        if(document.get("pass").toString().equals(md5(pass.getText().toString()))){

                                            id_equipe = document.getId();

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent i = new Intent(MainActivity.this, planning.class);
                                                    i.putExtra("id_equipe", id_equipe);
                                                    startActivity(i);
                                                }
                                            });
                                        }
                                    }
                                } else {
                                    Log.d("TEST", "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
    }

    /**
     * Checks the dynamically-controlled permissions and requests missing permissions from end user.
     */
    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                // all permissions were granted
                initialize();
                break;
        }
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {

            Log.i("TAG", intent.getData().toString());

        } catch (Exception error) {
            Log.i("TAG", "Bite6");
            error.printStackTrace();
        }
    }

}

