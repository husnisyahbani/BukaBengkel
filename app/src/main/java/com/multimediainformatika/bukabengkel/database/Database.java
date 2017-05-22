package com.multimediainformatika.bukabengkel.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.multimediainformatika.bukabengkel.adapter.Produk;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by eksmud on 17/05/2017.
 */

public class Database extends SQLiteAssetHelper {
    public static final String DATABASE_NAME = "bukabengkel.db";
    public static final int DATABASE_VERSION = 1;
    public static final String ID = "_id";

    //PRODUK
    public static final String NAMA = "nama";
    public static final String DESKRIPSI = "deskripsi";
    public static final String PRICE = "price";
    public static final String IMAGE = "image";

    public static final String TABLE_PRODUK = "produk";


    final Context context;
    SQLiteDatabase db;

    public Database(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
        this.context = ctx;
    }

    public void open() throws SQLException {
        db = getReadableDatabase();
    }

    public Cursor getProduk() throws SQLException {
        Cursor mCursor = db.query(true, TABLE_PRODUK, new String[]{ID, NAMA,
                        DESKRIPSI, PRICE,IMAGE}, null, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertProduk(Produk produk) {
        SQLiteDatabase databaseInsert = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAMA, produk.name);
        values.put(DESKRIPSI, produk.desc);
        values.put(PRICE, produk.price);
        values.put(IMAGE, produk.image);
        databaseInsert.insert(TABLE_PRODUK, null, values);
    }

    public void deleteProduk() {
        SQLiteDatabase databaseDelete = this.getWritableDatabase();
        databaseDelete.delete(TABLE_PRODUK, null, null);
    }
}
