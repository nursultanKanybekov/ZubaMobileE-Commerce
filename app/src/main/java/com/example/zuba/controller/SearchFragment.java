package com.example.zuba.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.zuba.R;
import com.example.zuba.model.ProductsModel;
import com.example.zuba.services.ProductApiClientService;
import com.example.zuba.view.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private EditText searchProduct;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchProduct = view.findViewById(R.id.searchProduct);
        recyclerView = view.findViewById(R.id.searchedRecycler);

        new ProductApiClientService(recyclerView, getContext()).searchValues(searchProduct);

        return view;
    }
}