package com.example.zuba.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import com.example.zuba.R;
import com.example.zuba.model.OrderCreateModel;

public class DialogPagesServices {
    private OrderCreateModel orderCreateModel;
    private Context context;
    private ImageView profileCredit;
    private Switch switchAgreement;
    private EditText etPaid, etTerm, etIP;

    public DialogPagesServices(Context context) {
        this.context = context;
    }

    public void showFormDialog(OrderDialogCallback callback) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_form, null);

        etPaid = view.findViewById(R.id.etPaid);
        etTerm = view.findViewById(R.id.etTerm);
        etIP = view.findViewById(R.id.etIP);
        switchAgreement = view.findViewById(R.id.switchAgreement);
        profileCredit = view.findViewById(R.id.imageView5);

        profileCredit.setOnClickListener(v -> {
            openCamera();
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view)
                .setTitle("Кредит")
                .setPositiveButton("Отправить", (dialog, which) -> {
                    int paid = Integer.parseInt(etPaid.getText().toString());
                    int term = Integer.parseInt(etTerm.getText().toString());
                    int ip = Integer.parseInt(etIP.getText().toString());
                    boolean agreement = switchAgreement.isChecked();
                    orderCreateModel = new OrderCreateModel(0, paid, term, ip, agreement, null, null);
                    callback.onOrderCreated(orderCreateModel);
                })
                .setNegativeButton("Cancel", ((dialog, which) -> callback.onCancel()))
                .create()
                .show();
    }

    public void confirmDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Хорошо", (dialog, id) -> {
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ((Activity) context).startActivityForResult(cameraIntent, 1);
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }
}
