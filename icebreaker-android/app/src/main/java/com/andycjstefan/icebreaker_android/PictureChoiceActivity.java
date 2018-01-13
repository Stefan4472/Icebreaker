package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.provider.MediaStore;


public class PictureChoiceActivity extends Activity {

    private Button takePictureButton, uploadPictureButton, donePictureButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_choice_activity);

        takePictureButton = (Button) findViewById(R.id.take_picture_button);
        uploadPictureButton = (Button) findViewById(R.id.upload_picture_button);
        donePictureButton = (Button) findViewById(R.id.picture_done_button);
    }

    public void onTakePicture(View view) {
        // send user to take a picture
        dispatchTakePictureIntent();
        startActivity(new Intent(this, PictureChoiceActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView = 
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void onUploadPicture(View view) {
        // send user to uplooad a picture (Gallery)
        startActivity(new Intent(this, PictureChoiceActivity.class));
    }

    // called when user clicks button to submit Name
    public void onDoneWithPicture(View view) {
        // send user to dashboard
        startActivity(new Intent(this, DashboardActivity.class));
    }

}
