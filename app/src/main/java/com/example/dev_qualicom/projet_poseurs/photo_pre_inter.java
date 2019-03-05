package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class photo_pre_inter extends AppCompatActivity {

    private static final int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    ImageView mImageView;
    ArrayList<PhotoPre> photos = new ArrayList<PhotoPre>();
    RecyclerView recyclerView;
    LinearLayoutManager llm = new LinearLayoutManager(this);
    int currenteSelected;
    Button suivant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PoseSingleton.getInstance().getPose().getSociete().equals("EASY-WATT")) {
            setTheme(R.style.AppTheme_Ew);
        }

        setContentView(R.layout.activity_photo_pre_inter);

        ImageView retour = (ImageView) findViewById(R.id.retour_btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(photo_pre_inter.this, planning.class);
                String id_equipe = EquipeSingleton.getInstance().getEquipe().getId();
                String nom_equipe = EquipeSingleton.getInstance().getEquipe().getNom();
                i.putExtra("id_equipe", id_equipe);
                i.putExtra("nom_equipe", nom_equipe);
                startActivity(i);
                finish();
            }
        });


        photos = initPhoto();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id = extras.getInt("id");
            currenteSelected = id;
            dispatchTakePictureIntent();
        }
        suivant = findViewById(R.id.suivant);

        recyclerView = (RecyclerView) findViewById(R.id.liste_photos);

        PhotoAdapter docsAdapter = new PhotoAdapter();
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(docsAdapter);

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(photo_pre_inter.this, guide_inter.class);
                startActivity(next);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {

            if (resultCode == RESULT_OK) {


                File file = new File(mCurrentPhotoPath);
                Bitmap bitmap = MediaStore.Images.Media
                        .getBitmap(getContentResolver(), Uri.fromFile(file));

                photos.get(currenteSelected).setFilePath(mCurrentPhotoPath);

                View view = findViewById(currenteSelected);
                mImageView = view.findViewById(R.id.item_photo);
                setPic();

                if (bitmap != null) {

                }
            }

        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.dev_qualicom.projet_poseurs.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    private File createImageFile() throws IOException {

        // Create an image file name
        File image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()+"/pre")+"/"+ photos.get(currenteSelected).getPrefix_photo()+"_"+PoseSingleton.getInstance().getPose().getId()+".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        Log.e("TESTTT", mCurrentPhotoPath);

        return image;
    }

    private void setPic() {

        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Bitmap orientedBitmap = ExifUtil.rotateBitmap(mCurrentPhotoPath, bitmap);
        mImageView.setImageBitmap(orientedBitmap);

    }

    class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View vue = getLayoutInflater().inflate(R.layout.activity_photo_pre_inter_item, null);
            return new PhotoHolder(vue);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {

            PhotoPre photo = photos.get(position);
            holder.bind(photo);

        }

        @Override
        public int getItemCount() {
            return photos.size();
        }
    }

    class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView title;

        public PhotoHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_text);
            imageView = itemView.findViewById(R.id.item_photo);

        }

        public void bind(PhotoPre photo) {

            title.setText(photo.getTitre());

            File imgFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()+"/pre")+"/"+ photo.getPrefix_photo()+"_"+PoseSingleton.getInstance().getPose().getId()+".jpg");

            if(imgFile.exists()){

                photo.setFilePath(getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+PoseSingleton.getInstance().getPose().getId()+"/pre")+"/"+ photo.getPrefix_photo()+"_"+PoseSingleton.getInstance().getPose().getId()+".jpg");
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 120, 120, false));

            }

            this.itemView.setId(photos.indexOf(photo));
            this.itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if(!photos.get(view.getId()).getFilePath().equals("")){
                Intent intent = new Intent(photo_pre_inter.this, photo_pre_inter_2.class);
                intent.putExtra("id", view.getId());
                intent.putExtra("photo", photos.get(view.getId()).getFilePath());
                startActivity(intent);
                finish();
            }else{
                currenteSelected = view.getId();
                dispatchTakePictureIntent();
            }

        }
    }

    public ArrayList<PhotoPre> initPhoto(){

        ArrayList<PhotoPre> result = new ArrayList<PhotoPre>();

        PhotoPre photo = new PhotoPre("Photo Panneaux", "Panneaux");
        result.add(photo);
        photo = new PhotoPre("Photo Panneaux2", "Panneaux2");
        result.add(photo);
        photo = new PhotoPre("Photo Maison", "Maison");
        result.add(photo);
        photo = new PhotoPre("Photo Ballon", "Ballon");
        result.add(photo);
        photo = new PhotoPre("Photo Ballon2", "Ballon2");
        result.add(photo);
        photo = new PhotoPre("Photo Maison2", "Maison2");
        result.add(photo);
        photo = new PhotoPre("Photo Panneaux3", "Panneaux3");
        result.add(photo);

        return result;
    }

}
