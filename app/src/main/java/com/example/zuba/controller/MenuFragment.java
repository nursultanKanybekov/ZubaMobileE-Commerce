package com.example.zuba.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zuba.R;
import com.example.zuba.services.ProductApiClientService;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {
    private List<RecyclerView> recyclerViewList = new ArrayList<>();
    private String[] categories = {"Ноутбук", "Наушники", "Аир подсы", "Зарядники для айфона", "Повер банк", "Проводные наушники", "Повер банк",
            "Проводные  наушник", "Полу проводные наушники", "Полу проводные наушники", "Накладные (большие) наушники", "Зарядники микро, таепси",
            "Зарядники (микро, таепси)", "Подставки", "Потставки"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        showLoopRecycler(view);
        new ProductApiClientService(getContext(),null, recyclerViewList).showByCategories();
        return view;
    }

    private void showLoopRecycler(View context) {
        LinearLayout linearLayout = context.findViewById(R.id.linearLayoutContainer);

        for (int i = 0; i < categories.length; i++) { // Change the loop condition based on the number of pairs you need
            // Create a new LinearLayout for each pair
            LinearLayout pairLayout = new LinearLayout(context.getContext());
            pairLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            pairLayout.setOrientation(LinearLayout.VERTICAL);
            pairLayout.setPadding(0, 15, 0, 0); // Adjust padding as needed

            // Create and add TextView
            TextView textView = new TextView(context.getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText(categories[i]);
            textView.setPadding(15, 0, 0, 0); // Adjust padding as needed

            pairLayout.addView(textView);

            // Create and add RecyclerView
            RecyclerView recyclerView = new RecyclerView(context.getContext());
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            recyclerView.setPadding(15, 10, 15, 0); // Adjust padding as needed

            pairLayout.addView(recyclerView);
            recyclerViewList.add(recyclerView);

            // Add the pairLayout to the main LinearLayout
            linearLayout.addView(pairLayout);
        }

    }
}