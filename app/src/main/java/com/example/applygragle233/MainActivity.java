package com.example.applygragle233;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    // Request code identifying camera events
    private static final int CAMERA_REQUEST_CODE = 1889;

    // Identifier for the image returned by the camera
    private static final String EXTRA_RESULT = "data";

    private ImageView mImageView;
    File imageFile = null;
    String currentImagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            imageFile = getImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (imageFile != null) {
            // Uri imageUri = FileProvider.getUriForFile(this, "com.example.recognize_online.MqProvider", imageFile);
            Uri imageUri = Uri.fromFile(imageFile);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            Bitmap photo = data.getExtras().getParcelable(EXTRA_RESULT);
            mImageView.setImageBitmap(photo);
        }
    }

    private File getImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File imageFile = File.createTempFile(imageName, ".jpg", storageDir);
        return imageFile;
    }
}
