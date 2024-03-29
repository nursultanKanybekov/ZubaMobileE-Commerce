package com.example.zuba.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "likedProducts.db";
    private static final int DATABASE_VERSION = 1;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       createAllTables(db);
    }

    private void createAllTables(SQLiteDatabase db) {
        String createTableQueryPurches = "CREATE TABLE IF NOT EXISTS purches (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, description TEXT, price INTEGER, stock INTEGER, available INTEGER, " +
                "created TEXT, updated TEXT, category INTEGER, shop INTEGER)";
        db.execSQL(createTableQueryPurches);

        String createTableQuery = "CREATE TABLE IF NOT EXISTS mytable (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, description TEXT, price INTEGER, stock INTEGER, available INTEGER, " +
                "created TEXT, updated TEXT, category INTEGER, shop INTEGER)";
        db.execSQL(createTableQuery);

        String createImagesTableQuery = "CREATE TABLE IF NOT EXISTS images_table (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "image TEXT, product_id INTEGER, FOREIGN KEY(product_id) REFERENCES mytable(id))";
        db.execSQL(createImagesTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        createTables(db);
    }

    private void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS purches");
    }

    private void createTables(SQLiteDatabase db) {
        String createTableQueryPurches = "CREATE TABLE IF NOT EXISTS purches (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, description TEXT, price INTEGER, stock INTEGER, available INTEGER, " +
                "created TEXT, updated TEXT, category INTEGER, shop INTEGER)";
        db.execSQL(createTableQueryPurches);
    }

}

