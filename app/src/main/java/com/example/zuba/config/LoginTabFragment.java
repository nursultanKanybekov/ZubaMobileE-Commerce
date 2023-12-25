package com.example.zuba.config;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zuba.PurchaseActivity;
import com.example.zuba.R;
import com.example.zuba.model.LoginModel;
import com.example.zuba.services.MyDatabaseOperationsServices;
import com.example.zuba.services.ProductApiClientService;

public class LoginTabFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);
        Button button = view.findViewById(R.id.login_button);
        EditText login = view.findViewById(R.id.login_email);
        EditText password = view.findViewById(R.id.login_password);

        new ProductApiClientService(null, getContext()).ifLogedIn();

//        MyDatabaseOperationsServices myDatabaseOperationsServices = new MyDatabaseOperationsServices(getContext());
//        myDatabaseOperationsServices.open();
//        myDatabaseOperationsServices.upgradeDatabase();
//        myDatabaseOperationsServices.close();
        button.setOnClickListener(v -> {
            if (!login.getText().toString().equals("") && !password.toString().equals(""))
                new ProductApiClientService(getContext()).loginPage(new LoginModel(login.getText().toString(), password.getText().toString()));
            else
                Toast.makeText(getContext(), "Пароль или телефон номер не должна быть пустым", Toast.LENGTH_LONG).show();
        });
        return view;
    }

}