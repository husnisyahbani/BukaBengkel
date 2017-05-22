package com.multimediainformatika.bukabengkel.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by eksmud on 15/05/2017.
 */

public class Session {
    private static final String USER_ID = "email";
    private static final String TOKEN = "token";

    public static void saveSession(Context context, SessionData data) {
        SharedPreferences appPrefs = context.getSharedPreferences("SESSION",
                Context.MODE_PRIVATE); // create database private
        SharedPreferences.Editor prefsEditor = appPrefs.edit(); // mode edit
        prefsEditor.putString(USER_ID, data.user_id);
        prefsEditor.putString(TOKEN, data.token);

        prefsEditor.commit();
    }

    public static SessionData getSession(Context context) {

        SharedPreferences appPrefs = context.getSharedPreferences("SESSION",
                Context.MODE_PRIVATE);
        SessionData data = new SessionData();
        data.user_id = appPrefs.getString(USER_ID,"");
        data.token = appPrefs.getString(TOKEN,"");
        return data;
    }
}
