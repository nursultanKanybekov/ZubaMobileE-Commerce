package com.example.zuba.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zuba.R;
import com.example.zuba.model.ImagesModel;
import com.example.zuba.model.ProductsModel;
import com.example.zuba.services.LoadImageTask;
import com.example.zuba.services.MyDatabaseOperationsServices;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductsModel> productsModels;
    private MyDatabaseOperationsServices databaseOperations;
    private int increment = 1;

    public ProductAdapter(Context context, List<ProductsModel> productsModels) {
        this.context = context;
        this.productsModels = productsModels;
        databaseOperations = new MyDatabaseOperationsServices(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.prodcut_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        databaseOperations.open();
        boolean productExists = databaseOperations.doesProductExist(productsModels.get(position).getId(), "like");
        boolean purchesExists = databaseOperations.doesProductExist(productsModels.get(position).getId(), "purches");
        if (productExists) {
            holder.like.setColorFilter(Color.RED);
        } else {
            holder.like.setColorFilter(ContextCompat.getColor(context, R.color.lavender));
        }

        if (purchesExists) {
            holder.basket.setColorFilter(Color.RED);
        } else {
            holder.basket.setColorFilter(ContextCompat.getColor(context, R.color.lavender));
        }

        holder.name.setText(productsModels.get(position).getName());
        holder.description.setText(productsModels.get(position).getDescription());
        holder.price.setText(String.valueOf(productsModels.get(position).getPrice()));
        holder.amount.setText(String.valueOf(increment));
        holder.stock.setText(String.valueOf(productsModels.get(position).getStock()));
        holder.like.setOnClickListener(v -> {
            if (!productExists) {
                long productId = databaseOperations.insertProduct(productsModels.get(position), "like");
                ImagesModel image1 = new ImagesModel();
                image1.setProductId((int) productId);
                databaseOperations.insertImage(image1);
                Toast.makeText(context, "Saved to likes product", Toast.LENGTH_LONG).show();
            } else {
                int selectedProductId = productsModels.get(position).getId();
                databaseOperations.deleteProductById(selectedProductId, "like");
                Toast.makeText(context, "Removed from likes product", Toast.LENGTH_LONG).show();
            }

        });

        holder.basket.setOnClickListener(v -> {
            if (!purchesExists) {
                databaseOperations.insertProduct(productsModels.get(position), "purches");
                Toast.makeText(context, "Added to basket", Toast.LENGTH_LONG).show();
            } else {
                int selectedProductId = productsModels.get(position).getId();
                databaseOperations.deleteProductById(selectedProductId, "purches");
                Toast.makeText(context, "Removed from basket", Toast.LENGTH_LONG).show();
            }

        });

        if (productsModels.get(position).getImages() != null && !productsModels.get(position).getImages().isEmpty()) {
            String imageUrl = productsModels.get(position).getImages().get(0).getImage();
            new LoadImageTask(holder.productImage).execute(imageUrl);
        }
    }

    @Override
    public int getItemCount() {
        return productsModels.size();
    }

    public void filterList(List<ProductsModel> productsModelFilteredList) {
        productsModels = productsModelFilteredList;
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, description, price, amount, stock;
        private ImageView basket, like, minus, plus, productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameProduct);
            description = itemView.findViewById(R.id.productDescription);
            price = itemView.findViewById(R.id.productPrice);
            amount = itemView.findViewById(R.id.amount);
            stock = itemView.findViewById(R.id.stok);
            basket = itemView.findViewById(R.id.productPurches);
            like = itemView.findViewById(R.id.productLike);
            minus = itemView.findViewById(R.id.productMinus);
            plus = itemView.findViewById(R.id.productPlus);
            productImage = itemView.findViewById(R.id.productImage);

            minus.setOnClickListener(this);
            plus.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                // Access the ProductsModel object for the clicked item

                // Perform actions based on the clicked view
                if (v.getId() == R.id.productMinus) {
                    if (increment > 0)
                        --increment;
                    else
                        Toast.makeText(itemView.getContext(), "У вас только один товар", Toast.LENGTH_LONG).show();
                } else if (v.getId() == R.id.productPlus) {
                    ++increment;
                }
                notifyItemChanged(position);
            }
        }
    }
}
