package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;


public class PictureChoiceActivity extends Activity {

    private Button takePictureButton, uploadPictureButton, donePictureButton;
    private ImageView profilePicture;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static int GET_FROM_GALLERY = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_choice_activity);

        profilePicture = (ImageView) findViewById(R.id.profile_picture) ;

        takePictureButton = (Button) findViewById(R.id.take_picture_button);
        uploadPictureButton = (Button) findViewById(R.id.upload_picture_button);
        donePictureButton = (Button) findViewById(R.id.picture_done_button);
    }

    public void onTakePicture(View view) {
        //take a picture
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profilePicture.setImageBitmap(imageBitmap);
        }

        else if(requestCode==GET_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                //bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                profilePicture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



    public void onUploadPicture(View view) {
        // send user to uplooad a picture (Gallery)
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    // called when user clicks button to submit Name
    public void onDoneWithPicture(View view) {
        // save user id TODO: REQUEST SERVER FOR NEW USER ID
        SharedPreferences prefs = getSharedPreferences(NewUserActivity.PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
        prefs.edit().putInt(NewUserActivity.USER_ID_KEY, 1).apply();
        // send user to dashboard
        startActivity(new Intent(this, DashboardActivity.class));
    }

}
