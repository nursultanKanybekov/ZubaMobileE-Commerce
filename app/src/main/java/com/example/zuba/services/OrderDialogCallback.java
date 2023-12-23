package com.example.zuba.services;

import com.example.zuba.model.OrderCreateModel;

public interface OrderDialogCallback {
    void onOrderCreated(OrderCreateModel orderCreateModel);

    void onCancel();
}
