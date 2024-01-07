package com.example.zuba.controller;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zuba.R;
import com.example.zuba.config.SignupTabFragment;
import com.example.zuba.model.AddressSerialzerModel;
import com.example.zuba.model.OrderCreateModel;
import com.example.zuba.model.OrderItem;
import com.example.zuba.model.ProductsModel;
import com.example.zuba.model.RegionModel;
import com.example.zuba.model.VillageModel;
import com.example.zuba.services.CountryCallback;
import com.example.zuba.services.OrderDialogCallback;
import com.example.zuba.services.DialogPagesServices;
import com.example.zuba.services.MyDatabaseOperationsServices;
import com.example.zuba.services.ProductApiClientService;
import com.example.zuba.services.RegionCallback;
import com.example.zuba.services.VillageCallback;
import com.example.zuba.view.BasketAdapter;

import java.util.ArrayList;
import java.util.List;

public class BasketFragment extends Fragment {
    private MyDatabaseOperationsServices myDatabaseOperationsServices;
    private Button order;
    private EditText exact_address;
    private Spinner country, region, villige;
    private ImageView credit;
    private DialogPagesServices dialogPagesServices;
    private OrderCreateModel orderCreateModel;
    private RecyclerView recyclerView;
    private BasketAdapter basketAdapter;
    private AddressSerialzerModel addressSerialzerModel;
    private String countryS, regionS, villageS;
    private ProductApiClientService productApiClientService;
    private List<CountryModel> countryModels;
    private List<RegionModel> regionModels;
    private List<VillageModel> villageModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        order = view.findViewById(R.id.order);
        exact_address = view.findViewById(R.id.editTextText3);
        country = view.findViewById(R.id.editTextPhone);
        region = view.findViewById(R.id.editTextTextEmailAddress);
        villige = view.findViewById(R.id.editTextTextPostalAddress);
        credit = view.findViewById(R.id.imageView4);

        productApiClientService = new ProductApiClientService(getContext());
        myDatabaseOperationsServices = new MyDatabaseOperationsServices(getContext());
        setSpinner();
        myDatabaseOperationsServices.open();
        List<ProductsModel> productList = myDatabaseOperationsServices.getAllProducts("purhces");
        basketAdapter = new BasketAdapter(view.getContext(), productList, this);

        if (!productList.isEmpty()) {
            recyclerView = view.findViewById(R.id.basketList);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(basketAdapter);
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            orderItems.add(new OrderItem(productList.get(i).getPrice(), productList.get(i).getStock(), productList.get(i).getId()));
        }
        dialogPagesServices = new DialogPagesServices(getContext(), new OrderDialogCallback() {
            @Override
            public void onOrderCreated(OrderCreateModel orderCreateModel) {
                BasketFragment.this.orderCreateModel = orderCreateModel;
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Отменено кредит", Toast.LENGTH_LONG).show();
            }
        });

        credit.setOnClickListener(v -> {
            dialogPagesServices.showFormDialog();
        });

        order.setOnClickListener(v -> {

            if (!exact_address.getText().toString().equals("")) {
                addressSerialzerModel = new AddressSerialzerModel(exact_address.getText().toString(), countryS,
                        regionS, villageS);
                if (orderCreateModel != null) {
                    orderCreateModel.setAddress(addressSerialzerModel);
                    orderCreateModel.setOrder_item(orderItems);
                    productApiClientService.purches(orderCreateModel, basketAdapter);
                } else productApiClientService.purches(new OrderCreateModel(1, 0, 0, 0, true,
                        addressSerialzerModel, orderItems), basketAdapter);

            } else
                Toast.makeText(getContext(), "Адрес не может быть пустым", Toast.LENGTH_SHORT).show();
        });
        myDatabaseOperationsServices.close();
        return view;
    }

    private void setSpinner() {
        productApiClientService.getCountry(new CountryCallback() {
            @Override
            public void onSuccess(List<CountryModel> products) {
                BasketFragment.this.countryModels = products;
                setupSpinners();
            }

            @Override
            public void onFailure(String errorMessage) {
                BasketFragment.this.countryModels = null;
            }
        });

        productApiClientService.getRegion(new RegionCallback() {
            @Override
            public void onSuccess(List<RegionModel> products) {
                BasketFragment.this.regionModels = products;
                setupSpinners();
            }

            @Override
            public void onFailure(String errorMessage) {
                BasketFragment.this.regionModels = null;
            }
        });

        productApiClientService.getVillage(new VillageCallback() {
            @Override
            public void onSuccess(List<VillageModel> products) {
                BasketFragment.this.villageModels = products;
                setupSpinners();
            }

            @Override
            public void onFailure(String errorMessage) {
                BasketFragment.this.villageModels = null;
            }
        });
    }

    private void setupSpinners() {
        // Check if all data is available
        if (countryModels != null && regionModels != null && villageModels != null) {
            // Now you can set up your spinners
            String countryArray[] = new String[countryModels.size()];
            String regionArray[] = new String[regionModels.size()];
            String villageArray[] = new String[villageModels.size()];
            for (int i = 0; i < countryModels.size(); i++)
                countryArray[i] = countryModels.get(i).getName();
            for (int i = 0; i < regionModels.size(); i++)
                regionArray[i] = regionModels.get(i).getName();
            for (int i = 0; i < villageModels.size(); i++)
                villageArray[i] = villageModels.get(i).getName();

            ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    countryArray
            );
            countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            country.setAdapter(countryAdapter);

            ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    regionArray
            );
            regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            region.setAdapter(regionAdapter);

            ArrayAdapter<String> villageAdapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    villageArray
            );
            villageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            villige.setAdapter(villageAdapter);

            country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    countryS = parentView.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    countryS = "";
                }
            });

            region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    regionS = parentView.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    regionS = "";
                }
            });

            villige.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    villageS = parentView.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    villageS = "";
                }
            });
        }
    }
}