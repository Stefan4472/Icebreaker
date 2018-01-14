package com.andycjstefan.icebreaker_android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Utility methods for converting images to base64, creating+retrieving thumbnails.
*/
public class ImageUtil {

    // convert bitmap to base64
    public static String bmpToBase64(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    // decode base64 string defining image\
    public static Bitmap base64ToBmp(String encodedImg) {
        byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
