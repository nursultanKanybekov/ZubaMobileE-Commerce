package com.example.zuba.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.zuba.R;
import com.example.zuba.model.ImagesModel;
import com.example.zuba.model.ProductsModel;
import com.example.zuba.services.MyDatabaseOperationsServices;
import com.example.zuba.services.ProductApiClientService;
import com.example.zuba.view.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class AutorFragment extends Fragment {
    private Switch likedProducts;
    private TextView name, surname, gender;
    private ImageView imageView;
    private MyDatabaseOperationsServices myDatabaseOperationsServices;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_autor, container, false);

        name = view.findViewById(R.id.name);
        surname = view.findViewById(R.id.surname);
        gender = view.findViewById(R.id.gender);
        imageView = view.findViewById(R.id.imageView);

        likedProducts = view.findViewById(R.id.likedSwitch);
        recyclerView = view.findViewById(R.id.obligation);
        new ProductApiClientService(recyclerView, getContext()).getProfileInfo(name, surname, gender, imageView);


        likedProducts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                myDatabaseOperationsServices = new MyDatabaseOperationsServices(getContext());
                myDatabaseOperationsServices.open();
                List<ProductsModel> productList = myDatabaseOperationsServices.getAllProducts("like");
                if (!productList.isEmpty()) {
                    int firstProductId = productList.get(0).getId();
                    List<ImagesModel> imageList = myDatabaseOperationsServices.getImagesForProduct(firstProductId);
                    recyclerView = view.findViewById(R.id.likedRecyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    recyclerView.setAdapter(new ProductAdapter(view.getContext(), productList));
                }
                myDatabaseOperationsServices.close();
            } else {
                recyclerView = view.findViewById(R.id.likedRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(new ProductAdapter(view.getContext(), new ArrayList<>()));
            }
        });
        return view;
    }
}