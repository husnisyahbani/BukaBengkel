package com.multimediainformatika.bukabengkel.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by eksmud on 17/05/2017.
 */

public class BiodataDatabase {
    private static final String NAMA = "nama";
    private static final String ALAMAT = "alamat";
    private static final String NO_TELP = "no_telp";
    private static final String EMAIL = "email";
    private static final String TOKEN = "token";
    private static final String ID_USERS = "id_users";

    public static void saveBiodata(Context context, BiodataData data) {
        SharedPreferences appPrefs = context.getSharedPreferences("BIODATA",
                Context.MODE_PRIVATE); // create database private
        SharedPreferences.Editor prefsEditor = appPrefs.edit(); // mode edit
        prefsEditor.putString(NAMA, data.nama);
        prefsEditor.putString(ALAMAT, data.alamat);
        prefsEditor.putString(NO_TELP, data.no_telp);
        prefsEditor.putString(EMAIL, data.email);
        prefsEditor.putString(ID_USERS, data.id_users);

        prefsEditor.commit();
    }

    public static void saveToken(Context context, String data) {
        SharedPreferences appPrefs = context.getSharedPreferences("BIODATA",
                Context.MODE_PRIVATE); // create database private
        SharedPreferences.Editor prefsEditor = appPrefs.edit(); // mode edit
        prefsEditor.putString(TOKEN, data);
        prefsEditor.commit();
    }

    public static BiodataData getBiodata(Context context) {

        SharedPreferences appPrefs = context.getSharedPreferences("BIODATA",
                Context.MODE_PRIVATE);
        BiodataData data = new BiodataData();
        data.nama = appPrefs.getString(NAMA,"");
        data.alamat = appPrefs.getString(ALAMAT,"");
        data.no_telp = appPrefs.getString(NO_TELP,"");
        data.email = appPrefs.getString(EMAIL,"");
        data.token = appPrefs.getString(TOKEN,"");
        data.id_users = appPrefs.getString(ID_USERS,"");
        return data;
    }
}
