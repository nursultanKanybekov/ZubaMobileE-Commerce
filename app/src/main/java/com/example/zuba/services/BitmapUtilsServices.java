package com.example.zuba.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BitmapUtilsServices {
    public static File saveBitmapToFile(Context context, Bitmap bitmap, String fileName) {
        File imageFile = new File(context.getFilesDir(), fileName);

        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if there's an error saving the file
        }
//        File directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File file = new File(directory, fileName);
//
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//            fos.close();
//
//            return file;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}
