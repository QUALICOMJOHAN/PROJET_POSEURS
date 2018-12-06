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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class photo_pre_inter extends AppCompatActivity {

    private static final int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    ImageView mImageView;
    ArrayList<PhotoPre> photos = new ArrayList<PhotoPre>();
    RecyclerView recyclerView;
    LinearLayoutManager llm = new LinearLayoutManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_pre_inter);

        photos = initPhoto();

        Log.e("TAG", String.valueOf(photos.size()));

        recyclerView = (RecyclerView) findViewById(R.id.liste_photos);

        PhotoAdapter docsAdapter = new PhotoAdapter();
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(docsAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {

            if (resultCode == RESULT_OK) {


                File file = new File(mCurrentPhotoPath);
                Bitmap bitmap = MediaStore.Images.Media
                        .getBitmap(getContentResolver(), Uri.fromFile(file));

                setPic();

                if (bitmap != null) {

                }
            }

        } catch (Exception error) {
            Log.i("TAG", "Bite6");
            error.printStackTrace();
        }
    }

    private void dispatchTakePictureIntent() {

        Log.i("TAG", "Bite2");

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

                Log.i("TAG", "Bite3");
            }
        }
    }

    private File createImageFile() throws IOException {

        Log.i("TAG", "Bite4");

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        Log.i("TAG", "Bite5");

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

            File imgFile = new File(photo.getFilePath());

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);

            }

        }

        @Override
        public void onClick(View view) {

            /*Intent i = new Intent(planning.this, demande_intervention.class);
            i.putExtra("id_pose", photos.get(view.getId()).getEquipe());
            startActivity(i);*/

        }
    }

    public ArrayList<PhotoPre> initPhoto(){

        ArrayList<PhotoPre> result = new ArrayList<PhotoPre>();

        PhotoPre photo = new PhotoPre("Photo Panneaux");
        result.add(photo);
        photo = new PhotoPre("Photo Panneaux2");
        result.add(photo);
        photo = new PhotoPre("Photo Maison");
        result.add(photo);
        photo = new PhotoPre("Photo Ballon");
        result.add(photo);
        photo = new PhotoPre("Photo Wendy");
        result.add(photo);
        photo = new PhotoPre("Photo Coucou");
        result.add(photo);
        photo = new PhotoPre("Photo Coca");
        result.add(photo);

        return result;
    }

}
