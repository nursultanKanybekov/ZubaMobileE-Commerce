package com.example.zuba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zuba.services.DialogPagesServices;

import java.io.ByteArrayOutputStream;

public class ProfileImage extends AppCompatActivity {
    private static final int pic_id = 123;
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

        camera_open_id.setOnClickListener(v -> {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id);
        });
        button2.setOnClickListener(v -> {
            if (photo != null) {
                Intent broadcastIntent = new Intent("profile_photo_broadcast");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                broadcastIntent.putExtra("profilePhoto", byteArray);
                sendBroadcast(broadcastIntent);

                finish();
            } else
                new DialogPagesServices(this, null).confirmDialog("Сначала сфоткайтесь пожайлуста!");
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id) {
            photo = (Bitmap) data.getExtras().get("data");
            click_image_id.setImageBitmap(photo);
        }
    }
}