package com.example.zuba.services;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zuba.config.MyDBHelper;
import com.example.zuba.model.ImagesModel;
import com.example.zuba.model.ProductsModel;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseOperationsServices {

    private SQLiteDatabase database;
    private MyDBHelper dbHelper;

    public MyDatabaseOperationsServices(Context context) {
        dbHelper = new MyDBHelper(context);
    }
    // Open the database
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    // Close the database
    public void close() {
        dbHelper.close();
    }

    // Insert data into the main table
    public long insertProduct(ProductsModel product, String type) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("stock", product.getStock());
        values.put("available", product.isAvailable() ? 1 : 0);
        values.put("created", product.getCreated());
        values.put("updated", product.getUpdated());
        values.put("category", product.getCategory());
        values.put("shop", product.getShop());

        if (type.equals("like"))
            return database.insert("mytable", null, values);
        else return database.insert("purches", null, values);
    }

    // Insert data into the images table
    public long insertImage(ImagesModel image) {
        ContentValues values = new ContentValues();
        values.put("image", image.getImage());
        values.put("product_id", image.getProductId());

        return database.insert("images_table", null, values);
    }

    // Retrieve all products from the main table
    @SuppressLint("Range")
    public List<ProductsModel> getAllProducts(String type) {
        List<ProductsModel> productList = new ArrayList<>();
        Cursor cursor;
        if (type.equals("like"))
            cursor = database.rawQuery("SELECT * FROM mytable", null);
        else
            cursor = database.rawQuery("SELECT * FROM purches", null);

        if (cursor.moveToFirst()) {
            do {
                ProductsModel product = new ProductsModel();
                product.setId(cursor.getInt(cursor.getColumnIndex("id")));
                product.setName(cursor.getString(cursor.getColumnIndex("name")));
                product.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                product.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                product.setStock(cursor.getInt(cursor.getColumnIndex("stock")));
                product.setAvailable(cursor.getInt(cursor.getColumnIndex("available")) == 1);
                product.setCreated(cursor.getString(cursor.getColumnIndex("created")));
                product.setUpdated(cursor.getString(cursor.getColumnIndex("updated")));
                product.setCategory(cursor.getInt(cursor.getColumnIndex("category")));
                product.setShop(cursor.getInt(cursor.getColumnIndex("shop")));

                // Add the product to the list
                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return productList;
    }

    // Retrieve images for a specific product
    @SuppressLint("Range")
    public List<ImagesModel> getImagesForProduct(int productId) {
        List<ImagesModel> imageList = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM images_table WHERE product_id = ?", new String[]{String.valueOf(productId)});

        if (cursor.moveToFirst()) {
            do {
                ImagesModel image = new ImagesModel();
                image.setId(cursor.getInt(cursor.getColumnIndex("id")));
                image.setImage(cursor.getString(cursor.getColumnIndex("image")));
                image.setProductId(cursor.getInt(cursor.getColumnIndex("product_id")));

                // Add the image to the list
                imageList.add(image);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return imageList;
    }

    public void deleteProductById(int productId, String type) {
        // Assuming "mytable" is the table where your products are stored
        if (type.equals("like"))
            database.delete("mytable", "id=?", new String[]{String.valueOf(productId)});
        else
            database.delete("purches", "id=?", new String[]{String.valueOf(productId)});
    }

    public void upgradeDatabase() {
        dbHelper.onUpgrade(database, dbHelper.getWritableDatabase().getVersion(), dbHelper.getWritableDatabase().getVersion() + 1);
    }

    public boolean doesProductExist(int productId, String type) {
        Cursor cursor;
        if (type.equals("like"))
            cursor = database.rawQuery("SELECT id FROM mytable WHERE id = ?", new String[]{String.valueOf(productId)});
        else
            cursor = database.rawQuery("SELECT id FROM purches WHERE id = ?", new String[]{String.valueOf(productId)});
        boolean productExists = cursor.getCount() > 0;
        cursor.close();
        return productExists;
    }


}
