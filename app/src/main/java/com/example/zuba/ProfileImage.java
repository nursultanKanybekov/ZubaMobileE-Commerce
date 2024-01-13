package com.example.zuba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.Manifest;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zuba.model.OrderCreateModel;
import com.example.zuba.services.BitmapUtilsServices;
import com.example.zuba.services.DialogPagesServices;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ProfileImage extends AppCompatActivity {
    private static final int pic_id = 123;
    private static final int MY_CAMERA_PERMISSION_CODE = 124;

    private Button camera_open_id, button2;
    private ImageView click_image_id;
    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);

        camera_open_id = findViewById(R.id.button);
        click_image_id = findViewById(R.id.imageView6);
        button2 = findViewById(R.id.button2);

        OrderCreateModel orderCreateModel;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        orderCreateModel = (OrderCreateModel) bundle.getSerializable("modelKey");

        camera_open_id.setOnClickListener(v -> {
            // Check if the camera permission is granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // If not granted, request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                // Permission already granted, open the camera
                openCamera();
            }
        });

        button2.setOnClickListener(v -> {
            if (photo != null) {
//                Intent broadcastIntent = new Intent("profile_photo_broadcast");
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                broadcastIntent.putExtra("profilePhoto", byteArray);
//                sendBroadcast(broadcastIntent);
//
//                finish();

                File savedFile = BitmapUtilsServices.saveBitmapToFile(this, photo, "image.png");
                if (savedFile != null) {
                    orderCreateModel.setImage(savedFile);
                } else {
                    Toast.makeText(this, "Фото не может быть пустым", Toast.LENGTH_SHORT).show();
                }

                // Finish the activity
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                new DialogPagesServices(this, null).confirmDialog("Сначала сфоткайтесь пожайлуста!");
            }
        });
    }

    // Handle the result of the camera permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to open the camera
    private void openCamera() {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, pic_id);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            click_image_id.setImageBitmap(photo);
        }
    }
}