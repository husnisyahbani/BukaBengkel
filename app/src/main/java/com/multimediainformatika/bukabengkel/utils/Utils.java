package com.multimediainformatika.bukabengkel.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.multimediainformatika.bukabengkel.MainActivity;
import com.multimediainformatika.bukabengkel.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by eksmud on 18/06/2016.
 */
public class Utils {

    public static void open(Context context, String url)
    {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        context.startActivity(launchBrowser);
    }

    public static void pindah(MainActivity act, Fragment fragment)
    {
        FragmentManager fragmentManager = act
                .getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.addToBackStack(null)
                .replace(R.id.frame_container, fragment).commit();
    }

    public static void pindahClear(MainActivity act, Fragment fragment)
    {
        clearBackStack(act);
        FragmentManager fragmentManager = act
                .getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.frame_container, fragment).commit();
    }

    private static void clearBackStack(MainActivity act) {
        final FragmentManager fragmentManager = act.getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    public static final ProgressDialog makeProgressDialog(
            Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setInverseBackgroundForced(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setMessage("Downloading...");
        return dialog;
    }

    public static String rot13(String value) {

        char[] values = value.toCharArray();
        for (int i = 0; i < values.length; i++) {
            char letter = values[i];

            if (letter >= 'a' && letter <= 'z') {
                // Rotate lowercase letters.

                if (letter > 'm') {
                    letter -= 13;
                } else {
                    letter += 13;
                }
            } else if (letter >= 'A' && letter <= 'Z') {
                // Rotate uppercase letters.

                if (letter > 'M') {
                    letter -= 13;
                } else {
                    letter += 13;
                }
            }
            values[i] = letter;
        }
        // Convert array to a new String.
        return new String(values);
    }

    public static String getTimeNow()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public static String getTime(int i)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.DATE,i);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        String currentDateandTime = sdf.format(cal.getTime());
        return currentDateandTime;
    }

    public static Calendar getCalendarTimeNow()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal;
    }

    public static Calendar getCalenderTime(String input)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            cal.setTime(sdf.parse(input));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static long timeDifferent(Calendar input1, Calendar input2)
    {
        Date d1 = input1.getTime();
        Date d2 = input2.getTime();
        return (d2.getTime()-d1.getTime())/1000;
    }

    public static String getDistance(String latA, String longiA, String latB,
                               String longiB) {
        Location locationA = new Location("point A");
        Location locationB = new Location("point B");
        locationA.setLatitude(Double.parseDouble(latA));
        locationA.setLongitude(Double.parseDouble(longiA));
        locationB.setLatitude(Double.parseDouble(latB));
        locationB.setLongitude(Double.parseDouble(longiB));

        double distance = locationA.distanceTo(locationB);
        distance = distance / 1000.0;

        String hasil = Double.toString(distance);
        int index = hasil.indexOf('.');

        if (index > 0) {
            String koma = hasil.substring(index + 1);
            if (koma.length() > 2) {
                return hasil.substring(0, index) + "." + koma.substring(0, 2);
            } else {
                return hasil.substring(0, index) + "." + koma;
            }

        } else {
            return hasil;
        }
    }

}
