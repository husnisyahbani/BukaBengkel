package com.multimediainformatika.bukabengkel.adapter;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CursorAdapter;
import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.database.Database;

/**
 * Created by eksmud on 30/03/2017.
 */

public class BengkelAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;
    private Handler mHandler;

    public BengkelAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
        mHandler = new Handler();
        cursorInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View rowView = cursorInflater.inflate(R.layout.itemlistbengkel,
                viewGroup, false);
        BengkelAdapter.ViewHolder holder = new BengkelAdapter.ViewHolder();
        holder.textName = (TextView) rowView.findViewById(R.id.textNama);
        holder.textAlamat = (TextView) rowView.findViewById(R.id.textAlamat);
        holder.textJarak = (TextView) rowView.findViewById(R.id.textJarak);
        rowView.setTag(holder);
        return rowView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        BengkelAdapter.ViewHolder holder = (BengkelAdapter.ViewHolder) view.getTag();

        final String textNama = cursor.getString(cursor
                .getColumnIndex(Database.NAMA));
        final String textAlamat = cursor.getString(cursor
                .getColumnIndex(Database.ALAMAT));
        final String textJarak = cursor.getString(cursor
                .getColumnIndex(Database.JARAK));

        holder.textName.setText(textNama);
        holder.textAlamat.setText(textAlamat);
        holder.textJarak.setText(textJarak);
    }



    public void refresh() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            notifyDataSetChanged();
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

    class ViewHolder {
        private TextView textName;
        private TextView textAlamat;
        private TextView textJarak;
    }


//    private String getDistance(String latA, String longiA, String latB,
//                               String longiB) {
//        Location locationA = new Location("point A");
//        Location locationB = new Location("point B");
//        locationA.setLatitude(Double.parseDouble(latA));
//        locationA.setLongitude(Double.parseDouble(longiA));
//        locationB.setLatitude(Double.parseDouble(latB));
//        locationB.setLongitude(Double.parseDouble(longiB));
//
//        double distance = locationA.distanceTo(locationB);
//        distance = distance / 1000.0;
//
//        if(distance <= 10) {
//            String hasil = Double.toString(distance);
//            int index = hasil.indexOf('.');
//
//            if (index > 0) {
//                String koma = hasil.substring(index + 1);
//                if (koma.length() > 2) {
//                    return hasil.substring(0, index) + "." + koma.substring(0, 2);
//                } else {
//                    return hasil.substring(0, index) + "." + koma;
//                }
//
//            } else {
//                return hasil;
//            }
//        }else{
//            return "";
//        }
//    }
}