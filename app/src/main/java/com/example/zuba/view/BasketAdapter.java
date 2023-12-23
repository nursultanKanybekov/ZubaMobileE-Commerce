package com.example.zuba.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zuba.R;
import com.example.zuba.controller.BasketFragment;
import com.example.zuba.model.ContactModel;
import com.example.zuba.model.ImagesModel;
import com.example.zuba.model.ProductsModel;
import com.example.zuba.services.GetContactService;
import com.example.zuba.services.MyDatabaseOperationsServices;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder> {
    private List<ProductsModel> productsModelList;
    private Context context;
    private MyDatabaseOperationsServices databaseOperations;
    private Fragment fragment;

    public BasketAdapter(Context context, List<ProductsModel> productsModelList, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.productsModelList = productsModelList;
        databaseOperations = new MyDatabaseOperationsServices(context);
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BasketViewHolder(LayoutInflater.from(context).inflate(R.layout.basket_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, int position) {
        holder.stokBasket.setText(String.valueOf(productsModelList.get(position).getStock()));
        holder.priceBasket.setText(String.valueOf(productsModelList.get(position).getPrice()));
        holder.nameBasket.setText(productsModelList.get(position).getName());
        holder.descriptionBasket.setText(productsModelList.get(position).getDescription());

        holder.shareBasket.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);

            StringBuilder shareableText = new StringBuilder();
            for (ProductsModel product : productsModelList) {
                shareableText.append(product.getName()).append("\n");
                shareableText.append(product.getPrice()).append("\n");
            }
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareableText.toString());
            sendIntent.setType("text/plain");
            context.startActivity(Intent.createChooser(sendIntent, "Share products"));
        });

        holder.likeBasket.setOnClickListener(v -> {
            databaseOperations.open();
            boolean productExists = databaseOperations.doesProductExist(productsModelList.get(position).getId(), "like");

            if (!productExists) {
                databaseOperations.insertProduct(productsModelList.get(position), "like");
                Toast.makeText(context, "Saved to likes product", Toast.LENGTH_LONG).show();
            } else {
                int selectedProductId = productsModelList.get(position).getId();
                databaseOperations.deleteProductById(selectedProductId, "like");
                Toast.makeText(context, "Removed from likes product", Toast.LENGTH_LONG).show();
            }
            databaseOperations.close();
        });

        holder.deleteBasket.setOnClickListener(v -> {
            databaseOperations.open();
            boolean productExists = databaseOperations.doesProductExist(productsModelList.get(position).getId(), "purches");

            if (!productExists) {
                databaseOperations.insertProduct(productsModelList.get(position), "purches");
                Toast.makeText(context, "Added to basket", Toast.LENGTH_LONG).show();
            } else {
                int selectedProductId = productsModelList.get(position).getId();
                databaseOperations.deleteProductById(selectedProductId, "purches");
                Toast.makeText(context, "Removed from basket", Toast.LENGTH_LONG).show();
            }
            databaseOperations.close();
            refreshFragment();
        });
    }

    @Override
    public int getItemCount() {
        return productsModelList.size();
    }

    public void refreshFragment() {
        androidx.fragment.app.FragmentTransaction ft = fragment.requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, new BasketFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    class BasketViewHolder extends RecyclerView.ViewHolder {
        private ImageView deleteBasket, creditBasket, likeBasket, shareBasket, imageBasket;
        private TextView stokBasket, priceBasket, nameBasket, credit, seeMore, descriptionBasket;

        public BasketViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteBasket = itemView.findViewById(R.id.deleteBasket);
            creditBasket = itemView.findViewById(R.id.creditBasket);
            likeBasket = itemView.findViewById(R.id.likeBasket);
            shareBasket = itemView.findViewById(R.id.shareBasket);
            imageBasket = itemView.findViewById(R.id.imageBasket);

            stokBasket = itemView.findViewById(R.id.stokBasket);
            priceBasket = itemView.findViewById(R.id.priceBasket);
            nameBasket = itemView.findViewById(R.id.nameBasket);
            credit = itemView.findViewById(R.id.credit);
            seeMore = itemView.findViewById(R.id.seeMore);
            descriptionBasket = itemView.findViewById(R.id.descriptionBasket);
        }
    }
}
