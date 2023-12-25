package com.example.zuba.config;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zuba.R;
import com.example.zuba.controller.CountryModel;
import com.example.zuba.model.AddressSerialzerModel;
import com.example.zuba.model.ProfileModel;
import com.example.zuba.model.RegionModel;
import com.example.zuba.model.RegisterModel;
import com.example.zuba.model.VillageModel;
import com.example.zuba.services.CountryCallback;
import com.example.zuba.services.ProductApiClientService;
import com.example.zuba.services.RegionCallback;
import com.example.zuba.services.VillageCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SignupTabFragment extends Fragment {
    private EditText signup_email, signup_password, signup_confirm, name, surname, exact_address;
    private Spinner country, region, village, gender;
    private Button signup_button, set_image_button;
    private String base64String;
    private String countryS, regionS, villageS, genderS;
    private ProductApiClientService productApiClientService;
    private List<CountryModel> countryModels;
    private List<RegionModel> regionModels;
    private List<VillageModel> villageModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);

        signup_email = view.findViewById(R.id.signup_email);
        signup_password = view.findViewById(R.id.signup_password);
        signup_confirm = view.findViewById(R.id.signup_confirm);
        name = view.findViewById(R.id.name);
        surname = view.findViewById(R.id.surname);
        gender = view.findViewById(R.id.gender);
        exact_address = view.findViewById(R.id.exact_address);
        country = view.findViewById(R.id.initial_payment);
        region = view.findViewById(R.id.paid);
        village = view.findViewById(R.id.reminder);


        set_image_button = view.findViewById(R.id.set_image_button);

        productApiClientService = new ProductApiClientService(getContext());
        set_image_button.setOnClickListener(v -> openGallery());

        signup_button = view.findViewById(R.id.signup_button);
        setSpinner();
        signup_button.setOnClickListener(v -> {
            if (signup_email.getText().toString().equals("") && signup_password.getText().toString().equals("")
                    && signup_confirm.getText().toString().equals("") && name.getText().toString().equals("") &&
                    surname.getText().toString().equals("") && genderS.equals("") &&
                    exact_address.getText().toString().equals("") && countryS.equals("") &&
                    regionS.equals("") && villageS.equals("")) {
                Toast.makeText(getContext(), "Эти поля не может быть пустым", Toast.LENGTH_LONG).show();
            } else {
                if (signup_password.getText().toString().contains(signup_confirm.getText().toString()))
                    productApiClientService.register(new RegisterModel(signup_email.getText().toString(), signup_password.getText().toString(),
                            new ProfileModel(name.getText().toString(), surname.getText().toString(), Integer.parseInt(genderS),
                                    new AddressSerialzerModel(exact_address.getText().toString(), countryS,
                                            regionS, villageS), base64String)));
                else
                    Toast.makeText(getContext(), "Пароли не похожи", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void setSpinner() {
        String[] genderType = {"Мужчина", "Женщина"};
        productApiClientService.getCountry(new CountryCallback() {
            @Override
            public void onSuccess(List<CountryModel> products) {
                SignupTabFragment.this.countryModels = products;
                setupSpinners();
            }

            @Override
            public void onFailure(String errorMessage) {
                SignupTabFragment.this.countryModels = null;
            }
        });

        productApiClientService.getRegion(new RegionCallback() {
            @Override
            public void onSuccess(List<RegionModel> products) {
                SignupTabFragment.this.regionModels = products;
                setupSpinners();
            }

            @Override
            public void onFailure(String errorMessage) {
                SignupTabFragment.this.regionModels = null;
            }
        });

        productApiClientService.getVillage(new VillageCallback() {
            @Override
            public void onSuccess(List<VillageModel> products) {
                SignupTabFragment.this.villageModels = products;
                setupSpinners();
            }

            @Override
            public void onFailure(String errorMessage) {
                SignupTabFragment.this.villageModels = null;
            }
        });

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                genderType
        );
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (parentView.getItemAtPosition(position).toString().equals("Мужчина"))
                    genderS = "0";
                else genderS = "1";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                genderS = "0";
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
            village.setAdapter(villageAdapter);

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

            village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK && data != null && data.getData() != null) {
            try {
                Uri selectedImageUri = data.getData();
                Bitmap selectedBitmap = resizeBitmap(selectedImageUri);

                if (selectedBitmap != null) {
                    base64String = convertBitmapToBase64(selectedBitmap);
                    requireActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
                } else {
                    Toast.makeText(requireContext(), "Error resizing the image", Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Error loading the selected image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap resizeBitmap(Uri uri) throws IOException {
        final int MAX_WIDTH = 1024;
        final int MAX_HEIGHT = 1024;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = requireActivity().getContentResolver().openInputStream(uri);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        options.inJustDecodeBounds = false;
        imageStream = requireActivity().getContentResolver().openInputStream(uri);
        Bitmap resizedBitmap = BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        return resizedBitmap;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    private String convertBitmapToBase64(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } else {
            return null;
        }
    }

}