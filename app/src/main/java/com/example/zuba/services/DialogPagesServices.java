package com.example.zuba.services;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.widget.Toast;

import com.example.zuba.ProfileImage;
import com.example.zuba.R;
import com.example.zuba.model.OrderCreateModel;

import java.io.File;
import java.io.IOException;

public class DialogPagesServices {
    private Context context;
    private ImageView profileCredit;
    private Switch switchAgreement;
    private EditText etTerm, etIP;
    private OrderDialogCallback callback;
    private BroadcastReceiver receiver;
    private int term;
    private boolean agreement;
    private Bitmap bitmapSelfie;
    private OrderCreateModel orderCreateModel;

    public DialogPagesServices(Context context, OrderDialogCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void showFormDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_form, null);
        etTerm = view.findViewById(R.id.etTerm);
        etIP = view.findViewById(R.id.etIP);
        switchAgreement = view.findViewById(R.id.switchAgreement);
        profileCredit = view.findViewById(R.id.imageView5);
        orderCreateModel = new OrderCreateModel();

        profileCredit.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProfileImage.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("modelKey", orderCreateModel);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view)
                .setTitle("Кредит")
                .setPositiveButton("Отправить", null)
                .setNegativeButton("Cancel", ((dialog, which) -> callback.onCancel()));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> {
            if (!switchAgreement.isChecked()) {
                confirmDialog("Пожалуйста, согласитесь на условию!");
            } else {
                term = Integer.parseInt(etTerm.getText().toString());
                agreement = switchAgreement.isChecked();
                orderCreateModel.setTerm(term);
                orderCreateModel.setAgreement(agreement);

                callback.onOrderCreated(orderCreateModel);

                alertDialog.dismiss();
            }
        });
    }

    private void initializeReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("profile_photo_broadcast".equals(intent.getAction())) {
                    byte[] byteArray = intent.getByteArrayExtra("profilePhoto");
                    bitmapSelfie = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                    File savedFile = BitmapUtilsServices.saveBitmapToFile(context, bitmapSelfie, "image.png");
                    if (savedFile != null) {
                        orderCreateModel.setImage(savedFile);
                    } else {
                        Toast.makeText(context, "Фото не может быть пустым", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        context.registerReceiver(receiver, new IntentFilter("profile_photo_broadcast"));
    }

    public void confirmDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Хорошо", (dialog, id) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
