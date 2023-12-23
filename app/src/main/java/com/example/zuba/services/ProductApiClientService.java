package com.example.zuba.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zuba.PurchaseActivity;
import com.example.zuba.config.MyDBHelper;
import com.example.zuba.controller.CountryModel;
import com.example.zuba.model.ContactModel;
import com.example.zuba.model.GetUserDetailSerizlizerModel;
import com.example.zuba.model.LoginModel;
import com.example.zuba.model.ObligationSerialiszerModel;
import com.example.zuba.model.OrderCreateModel;
import com.example.zuba.model.ProductsModel;
import com.example.zuba.model.RegionModel;
import com.example.zuba.model.RegisterModel;
import com.example.zuba.model.VillageModel;
import com.example.zuba.repo.AutorRepo;
import com.example.zuba.repo.CountryApiRepo;
import com.example.zuba.repo.LoginRepo;
import com.example.zuba.repo.OrderCreateRepo;
import com.example.zuba.repo.ProductRepo;
import com.example.zuba.repo.RegionApiRepo;
import com.example.zuba.repo.RegisterRepo;
import com.example.zuba.repo.SendContactRepo;
import com.example.zuba.repo.VillageApiRepo;
import com.example.zuba.view.BasketAdapter;
import com.example.zuba.view.ObligationAdapter;
import com.example.zuba.view.ProductAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductApiClientService {

    private final String BASE_URL = "http://45.63.124.225/";
    private final Retrofit retrofit;
    private ProductRepo productService;
    private LoginRepo loginRepo;
    private RegisterRepo registerRepo;
    private SendContactRepo sendContactRepo;
    private OrderCreateRepo orderCreateRepo;
    private DialogPagesServices dialogPagesServices;

    private AutorRepo autorRepo;
    private int[] categoriesId = {1, 3, 4, 5, 7, 9, 10, 11, 13, 14, 17, 21, 22, 23, 25};
    private Context context;
    private RecyclerView recyclerView;
    private List<RecyclerView> recyclerViewList;
    private ProductAdapter productAdapter;
    private CountryApiRepo countryApiRepo;
    private RegionApiRepo regionApiRepo;
    private VillageApiRepo villageApiRepo;

    public ProductApiClientService(Context context, RecyclerView recyclerView, List<RecyclerView> recyclerViewList) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.productService = retrofit.create(ProductRepo.class);
        this.context = context;
        this.recyclerView = recyclerView;
        this.recyclerViewList = recyclerViewList;
        this.dialogPagesServices = new DialogPagesServices(context);
    }

    public ProductApiClientService(RecyclerView recyclerView, Context context) {
        this.recyclerView = recyclerView;
        this.context = context;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productService = retrofit.create(ProductRepo.class);
        autorRepo = retrofit.create(AutorRepo.class);
        dialogPagesServices = new DialogPagesServices(context);
    }

    public ProductApiClientService(Context context) {
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        registerRepo = retrofit.create(RegisterRepo.class);
        sendContactRepo = retrofit.create(SendContactRepo.class);
        orderCreateRepo = retrofit.create(OrderCreateRepo.class);
        loginRepo = retrofit.create(LoginRepo.class);
        countryApiRepo = retrofit.create(CountryApiRepo.class);
        regionApiRepo = retrofit.create(RegionApiRepo.class);
        villageApiRepo = retrofit.create(VillageApiRepo.class);

        dialogPagesServices = new DialogPagesServices(context);
    }

    public void getProducts() {
        Call<List<ProductsModel>> call = productService.getProducts();

        call.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                if (response.isSuccessful()) {
                    List<ProductsModel> products = response.body();
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(new ProductAdapter(context, products));
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable t) {
                dialogPagesServices.confirmDialog("Проблема с интернетом");
            }
        });
    }

    public void loginPage(LoginModel loginModel) {
        Call<ResponseBody> call = loginRepo.addProduct(loginModel);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        if (jsonResponse.has("access")) {
                            String accessToken = jsonResponse.getString("access");
                            saveToken(context, accessToken);

                            context.startActivity(new Intent(context, PurchaseActivity.class));
                        } else {
                            Toast.makeText(context, "Access token not found in the response", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error parsing JSON response", Toast.LENGTH_LONG).show();
                    }
                } else {
                    dialogPagesServices.confirmDialog("Вы еще не зарегистрировались");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialogPagesServices.confirmDialog("Проблема с интернетом");
            }
        });
    }


    public void showByCategories() {
        Call<List<ProductsModel>> call = productService.getProducts();

        call.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                if (response.isSuccessful()) {
                    List<ProductsModel> products = response.body();
                    showSortedValues(products);
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable t) {
                dialogPagesServices.confirmDialog("Проблема с интернетом");
            }
        });
    }

    private void showSortedValues(List<ProductsModel> products) {
        int idRecycler = 0;
        for (int k : categoriesId) {
            List<ProductsModel> showByCategories = new ArrayList<>();
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getCategory() == k) {
                    showByCategories.add(new ProductsModel(products.get(i).getId(), products.get(i).getName(),
                            products.get(i).getDescription(), products.get(i).getPrice(), products.get(i).getStock(),
                            products.get(i).isAvailable(), products.get(i).getCreated(), products.get(i).getUpdated(),
                            products.get(i).getCategory(), products.get(i).getShop(), products.get(i).getImages()));
                }
            }
            recyclerViewList.get(idRecycler).setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewList.get(idRecycler).setAdapter(new ProductAdapter(context, showByCategories));
            idRecycler++;
        }
    }

    public void searchValues(EditText searchProduct) {
        Call<List<ProductsModel>> call = productService.getProducts();

        call.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                if (response.isSuccessful()) {
                    List<ProductsModel> products = response.body();

                    productAdapter = new ProductAdapter(context, products);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(productAdapter);

                    searchProduct.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            filter(s.toString(), products);
                        }
                    });
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable t) {
                dialogPagesServices.confirmDialog("Проблема с интернетом");
            }
        });
    }

    private void filter(String toString, List<ProductsModel> productsModels) {
        List<ProductsModel> productsModelFilteredList = new ArrayList<>();
        for (ProductsModel p : productsModels) {
            if (p.getName().toLowerCase().contains(toString.toLowerCase())) {
                productsModelFilteredList.add(p);
            }
        }
        productAdapter.filterList(productsModelFilteredList);
    }

    public void register(RegisterModel registerModel) {
        Call<ResponseBody> call = registerRepo.addProduct(registerModel);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    context.startActivity(new Intent(context, PurchaseActivity.class));
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
//                    RequestBody requestBody = call.request().body();
//                    if (requestBody != null) {
//                        try {
//                            Buffer buffer = new Buffer();
//                            requestBody.writeTo(buffer);
//                            String requestBodyString = buffer.readUtf8();
//                            System.out.println("Request Body: " + requestBodyString);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialogPagesServices.confirmDialog("Проблема с интернетом");
            }
        });
    }

    public void getProfileInfo(TextView name, TextView surname, TextView gender, ImageView imageView) {
        String token = retrieveToken(context);
        if (token != null && !token.isEmpty()) {
            String authorizationHeader = "Bearer " + token;
            Call<GetUserDetailSerizlizerModel> call = autorRepo.getProfile(authorizationHeader);

            call.enqueue(new Callback<GetUserDetailSerizlizerModel>() {
                @Override
                public void onResponse(Call<GetUserDetailSerizlizerModel> call, Response<GetUserDetailSerizlizerModel> response) {
                    if (response.isSuccessful()) {
                        GetUserDetailSerizlizerModel getUserDetailSerizlizerModels = response.body();
                        name.setText("Имя: " + getUserDetailSerizlizerModels.getProfile().getFirst_name());
                        surname.setText("Фамилия: " + getUserDetailSerizlizerModels.getProfile().getLast_name());
                        gender.setText("Пол: " + getUserDetailSerizlizerModels.getProfile().getGender());
                        new LoadImageTask(imageView).execute(getUserDetailSerizlizerModels.getProfile().getAvatar());
                        if (getUserDetailSerizlizerModels.getObligations() != null) {
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(new ObligationAdapter(context, getUserDetailSerizlizerModels.getObligations(), null));
                        }

                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<GetUserDetailSerizlizerModel> call, Throwable t) {
                    dialogPagesServices.confirmDialog(t.toString());
                }
            });
        } else {
            Toast.makeText(context, "Token not available", Toast.LENGTH_LONG).show();
        }
    }


    private void saveToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    private String retrieveToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }

    public void sendContact(List<ContactModel> allContacts) {
        Call<ResponseBody> call = sendContactRepo.addProducts(allContacts);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                RequestBody requestBody = call.request().body();
//                if (requestBody != null) {
//                    try {
//                        Buffer buffer = new Buffer();
//                        requestBody.writeTo(buffer);
//                        String requestBodyString = buffer.readUtf8();
//                        System.out.println("Request Body: " + requestBodyString);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Send contacts", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialogPagesServices.confirmDialog("Проблема с интернетом");
            }
        });
    }

    public void purches(OrderCreateModel orderCreateModel) {
        String token = retrieveToken(context);
        if (token != null && !token.isEmpty()) {
            String authorizationHeader = "Bearer " + token;
            Call<ResponseBody> call = orderCreateRepo.addProduct(orderCreateModel, authorizationHeader);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        RequestBody requestBody = call.request().body();
                        if (requestBody != null) {
                            try {
                                Buffer buffer = new Buffer();
                                requestBody.writeTo(buffer);
                                String requestBodyString = buffer.readUtf8();
                                System.out.println("Request Body: " + requestBodyString);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        MyDBHelper myDBHelper = new MyDBHelper(context);
                        myDBHelper.clearTable();
                        dialogPagesServices.confirmDialog("Поздравляю, скоро свяжется");
                    } else {
                        dialogPagesServices.confirmDialog("Извините за неудобство, были проблемы \nможете заново заполнить поля и отправить");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dialogPagesServices.confirmDialog("Проблема с интернетом");
                }
            });
        }
    }

    public void getVillage(VillageCallback callback) {
        Call<List<VillageModel>> call = villageApiRepo.getVillageList();

        call.enqueue(new Callback<List<VillageModel>>() {
            @Override
            public void onResponse(Call<List<VillageModel>> call, Response<List<VillageModel>> response) {
                if (response.isSuccessful()) {
                    List<VillageModel> products = response.body();
                    callback.onSuccess(products);
                } else {
                    callback.onFailure("Error");
                }
            }

            @Override
            public void onFailure(Call<List<VillageModel>> call, Throwable t) {
                callback.onFailure("Проблема с интернетом");
            }
        });
    }

    public void getCountry(CountryCallback callback) {
        Call<List<CountryModel>> call = countryApiRepo.getCountryList();

        call.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                if (response.isSuccessful()) {
                    List<CountryModel> products = response.body();
                    callback.onSuccess(products);
                } else {
                    callback.onFailure("Error");
                }
            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {
                callback.onFailure("Проблема с интернетом");
            }
        });
    }

    public void getRegion(RegionCallback callback) {
        Call<List<RegionModel>> call = regionApiRepo.getRegionList();

        call.enqueue(new Callback<List<RegionModel>>() {
            @Override
            public void onResponse(Call<List<RegionModel>> call, Response<List<RegionModel>> response) {
                if (response.isSuccessful()) {
                    List<RegionModel> products = response.body();
                    callback.onSuccess(products);
                } else {
                    callback.onFailure("Error");
                }
            }

            @Override
            public void onFailure(Call<List<RegionModel>> call, Throwable t) {
                callback.onFailure("Проблема с интернетом");
            }
        });
    }
}