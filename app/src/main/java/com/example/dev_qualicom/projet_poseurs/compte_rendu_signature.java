package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;

public class compte_rendu_signature extends AppCompatActivity {

    SignaturePad mSignaturePad1;
    SignaturePad mSignaturePad2;
    SignaturePad mSignaturePad3;
    SignaturePad mSignaturePad4;
    SignaturePad mSignaturePad5;
    String mCurrentSgnaturePath;

    String commentaire;

    ArrayList<SignaturePad> pads = new ArrayList<SignaturePad>();
    ArrayList<QuestionFinChantier> tabq = new ArrayList<QuestionFinChantier>();

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_compte_rendu_signature);

        commentaire = getIntent().getStringExtra("commentaire");

        Bundle extra = getIntent().getBundleExtra("extra");
        tabq = (ArrayList<QuestionFinChantier>) extra.getSerializable("objects");

        ImageView retour = (ImageView) findViewById(R.id.retour_btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(compte_rendu_signature.this, planning.class);
                String id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
                String nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();
                i.putExtra("id_equipe", id_equipe);
                i.putExtra("nom_equipe", nom_equipe);
                startActivity(i);
                finish();
            }
        });


        mSignaturePad1 = (SignaturePad) findViewById(R.id.textureView1);

        next = (Button) findViewById(R.id.suivant);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AbstractViewRenderer page = new AbstractViewRenderer(compte_rendu_signature.this, R.layout.pdf_compte_rendu_pdc) {

                    @Override
                    protected void initView(View view) {

                        RecyclerView recyclerView;
                        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());


                        recyclerView = (RecyclerView) view.findViewById(R.id.list_pdc);
                        QuestionAdapter docsAdapter = new QuestionAdapter();
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(docsAdapter);

                    }

                    class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder> {

                        @Override
                        public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                            View vue = getLayoutInflater().inflate(R.layout.activity_pdf_compte_rendu_pdc_item, null);
                            return new QuestionHolder(vue);
                        }

                        @Override
                        public void onBindViewHolder(QuestionHolder holder, int position) {

                            QuestionFinChantier question = tabq.get(position);
                            holder.bind(question);

                        }

                        @Override
                        public int getItemCount() {
                            return tabq.size();
                        }
                    }

                    class QuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                        private TextView title;
                        private TextView name;

                        public QuestionHolder(View itemView) {
                            super(itemView);

                            title = itemView.findViewById(R.id.pdc_label);
                            name = itemView.findViewById(R.id.pdc_resp);

                        }

                        public void bind(QuestionFinChantier question) {

                            title.setText(question.getQuestion());
                            name.setText(question.getReponse());

                            this.itemView.setId(tabq.indexOf(question));
                            this.itemView.setOnClickListener(this);

                        }

                        @Override
                        public void onClick(View view) {

                        }
                    }
                };

                PdfDocument doc            = new PdfDocument(compte_rendu_signature.this);

// add as many pages as you have
                doc.addPage(page);

                doc.setRenderWidth(210);
                doc.setRenderHeight(297);
                doc.setOrientation(PdfDocument.A4_MODE.PORTRAIT);
                doc.setFileName("Compte_rendu");
                doc.setSaveDirectory(compte_rendu_signature.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()));
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

                doc.createPdf(compte_rendu_signature.this);

// you can reuse the bitmap if you want
                page.setReuseBitmap(true);

//                Intent intent = new Intent(compte_rendu_signature.this, proces_verbal_donner_tab.class);
//                startActivity(intent);
            }
        });

        mSignaturePad1.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                try {
                    String sign = createStringPathFile("1");
                    Bitmap bitmap = mSignaturePad1.getSignatureBitmap();

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

        mSignaturePad2 = (SignaturePad) findViewById(R.id.textureView2);
        mSignaturePad2.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                try {
                    String sign = createStringPathFile("2");
                    Bitmap bitmap = mSignaturePad2.getSignatureBitmap();

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

        mSignaturePad3 = (SignaturePad) findViewById(R.id.textureView3);
        mSignaturePad3.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                try {
                    String sign = createStringPathFile("3");
                    Bitmap bitmap = mSignaturePad3.getSignatureBitmap();

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

        mSignaturePad4 = (SignaturePad) findViewById(R.id.textureView4);
        mSignaturePad4.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                try {
                    String sign = createStringPathFile("4");
                    Bitmap bitmap = mSignaturePad4.getSignatureBitmap();

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

        mSignaturePad5 = (SignaturePad) findViewById(R.id.textureView5);
        mSignaturePad5.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                try {
                    String sign = createStringPathFile("5");
                    Bitmap bitmap = mSignaturePad5.getSignatureBitmap();

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

        pads.add(mSignaturePad1);
        pads.add(mSignaturePad2);
        pads.add(mSignaturePad3);
        pads.add(mSignaturePad4);
        pads.add(mSignaturePad5);

        Log.e("TESTTT", String.valueOf(PoseSingleton.getInstance().getPose().getTechniciens().size()));

        for(int i=0 ; i < PoseSingleton.getInstance().getPose().getTechniciens().size() ; i++){
            pads.get(i).setVisibility(View.VISIBLE);
        }

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
