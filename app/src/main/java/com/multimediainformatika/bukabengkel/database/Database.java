package com.multimediainformatika.bukabengkel.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.multimediainformatika.bukabengkel.adapter.Antrian;
import com.multimediainformatika.bukabengkel.adapter.Bengkel;
import com.multimediainformatika.bukabengkel.adapter.Montir;
import com.multimediainformatika.bukabengkel.adapter.Produk;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by eksmud on 17/05/2017.
 */

public class Database extends SQLiteAssetHelper {
    public static final String DATABASE_NAME = "bukabengkel.db";
    public static final int DATABASE_VERSION = 9;
    public static final String ID = "_id";

    //PRODUK
    public static final String NAMA = "nama";
    public static final String DESKRIPSI = "deskripsi";
    public static final String PRICE = "price";
    public static final String IMAGE = "image";
    public static final String ALAMAT = "alamat";
    public static final String JARAK = "jarak";
    public static final String JENIS = "jenis";
    public static final String TANGGAL = "tanggal";
    public static final String NO_ANTRIAN = "no_antrian";
    public static final String NOPLAT = "noplat";
    public static final String STATUS = "status";
    public static final String ID_BENGKEL = "id_bengkel";
    public static final String ID_ANTRIAN = "id_antrian";
    public static final String ID_PRODUK = "id_produk";
    public static final String FK_IDUSERS = "fk_idusers";
    public static final String FK_IDBENGKEL = "fk_idbengkel";

    public static final String ID_MONTIR = "id_montir";
    public static final String LAT = "lat";
    public static final String LONGI = "longi";
    public static final String PESAN = "pesan";


    public static final String TABLE_MONTIR = "montir";
    public static final String TABLE_PRODUK = "produk";
    public static final String TABLE_BENGKEL = "bengkel";
    public static final String TABLE_ANTRIAN = "antrian";


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
        Cursor mCursor = db.query(true, TABLE_PRODUK, new String[]{ID,ID_PRODUK, NAMA,
                        DESKRIPSI, PRICE,IMAGE}, null, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getBengkelMobil() throws SQLException {
        Cursor mCursor = db.query(true, TABLE_BENGKEL, new String[]{ID,ID_BENGKEL, NAMA,
                        ALAMAT, JARAK,JENIS}, JENIS+"=?", new String[]{"2"},
                null, null, JARAK+" ASC", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getBengkelMotor() throws SQLException {
        Cursor mCursor = db.query(true, TABLE_BENGKEL, new String[]{ID,ID_BENGKEL, NAMA,
                        ALAMAT, JARAK,JENIS}, JENIS+"=?", new String[]{"1"},
                null, null, JARAK+" ASC", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getAntrian() throws SQLException {
        Cursor mCursor = db.query(true, TABLE_ANTRIAN, new String[]{ID,ID_ANTRIAN, TANGGAL,
                        NO_ANTRIAN,NOPLAT,FK_IDBENGKEL, FK_IDUSERS,STATUS}, null, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getMontir() throws SQLException {
        Cursor mCursor = db.query(true, TABLE_MONTIR, new String[]{ID,ID_MONTIR, LAT,
                        LONGI,TANGGAL,PESAN, FK_IDUSERS}, null, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertAntrian(Antrian antrian) {
        SQLiteDatabase databaseInsert = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_ANTRIAN, antrian.id_antrian);
        values.put(TANGGAL, antrian.tanggal);
        values.put(NO_ANTRIAN, antrian.no_antrian);
        values.put(NOPLAT, antrian.noplat);
        values.put(FK_IDBENGKEL, antrian.fk_idbengkel);
        values.put(FK_IDUSERS, antrian.fk_idusers);
        values.put(STATUS, antrian.status);
        databaseInsert.insert(TABLE_ANTRIAN, null, values);
    }

    public void insertBengkel(Bengkel bengkel) {
        SQLiteDatabase databaseInsert = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_BENGKEL, bengkel.id_bengkel);
        values.put(NAMA, bengkel.nama);
        values.put(ALAMAT, bengkel.alamat);
        values.put(JARAK, bengkel.jarak);
        values.put(JENIS, bengkel.jenis);
        databaseInsert.insert(TABLE_BENGKEL, null, values);
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

    public void insertMontir(Montir montir) {
        SQLiteDatabase databaseInsert = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_MONTIR, montir.id_montir);
        values.put(LAT, montir.lat);
        values.put(LONGI, montir.longi);
        values.put(TANGGAL, montir.tanggal);
        values.put(PESAN, montir.pesan);
        values.put(FK_IDUSERS, montir.fk_idusers);
        databaseInsert.insert(TABLE_MONTIR, null, values);
    }

    public void deleteProduk() {
        SQLiteDatabase databaseDelete = this.getWritableDatabase();
        databaseDelete.delete(TABLE_PRODUK, null, null);
    }

    public void deleteBengkel() {
        SQLiteDatabase databaseDelete = this.getWritableDatabase();
        databaseDelete.delete(TABLE_BENGKEL, null, null);
    }

    public void deleteAntrian() {
        SQLiteDatabase databaseDelete = this.getWritableDatabase();
        databaseDelete.delete(TABLE_ANTRIAN, null, null);
    }

    public void deleteMontir() {
        SQLiteDatabase databaseDelete = this.getWritableDatabase();
        databaseDelete.delete(TABLE_MONTIR, null, null);
    }
}
