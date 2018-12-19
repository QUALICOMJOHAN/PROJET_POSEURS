package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class enquete_preliminaire_terminee extends AppCompatActivity {

    Button suivant;
    ArrayList<Question> objects = new ArrayList<Question>();
    TextView code_view;
    String code = md5(SingletonPose.getInstance().pose.getTitle()).substring(0,5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquete_preliminaire_terminee);

        suivant = (Button) findViewById(R.id.suivant);
        code_view = (TextView) findViewById(R.id.code_tech);

        Log.e("TEST", code);

        Bundle extra = getIntent().getBundleExtra("extra");
        objects = (ArrayList<Question>) extra.getSerializable("objects");

        suivant.setVisibility(View.INVISIBLE);

        code_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("TEST", "TC");
                if(code.equals(s.toString())){
                    suivant.setVisibility(View.VISIBLE);
                }else{
                    suivant.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(enquete_preliminaire_terminee.this, enquete_preliminaire_apres_num.class);
                Bundle extra = new Bundle();
                extra.putSerializable("objects", objects);
                i.putExtra("extra", extra);
                startActivity(i);
                finish();

            }
        });

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
}
