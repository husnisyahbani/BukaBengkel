package com.multimediainformatika.bukabengkel.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.utils.OnButtonPressedListener;


public class CustomGrid extends ArrayAdapter<MenuPilihan> {
    private LayoutInflater cursorInflater;
    private Handler mHandler;
    private OnButtonPressedListener listener;
    MenuPilihan data[] = null;
    private int layoutResourceId;
    Context context;


    public CustomGrid(Context context, int layoutResourceId, MenuPilihan data[]) {
        super(context, layoutResourceId, data);
        mHandler = new Handler();
        cursorInflater = LayoutInflater.from(context);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public void setOnButtonPressedListener(OnButtonPressedListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MenuHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new MenuHolder();
            holder.imgIcon = (ImageView) row.findViewById(R.id.imageMenu);
            holder.txtTitle = (TextView) row.findViewById(R.id.textMenu);

            row.setTag(holder);
        } else {
            holder = (MenuHolder) row.getTag();
        }

        MenuPilihan menu = data[position];
        holder.txtTitle.setText(menu.kategori);
        holder.imgIcon.setImageResource(menu.icon);

        return row;
    }

    static class MenuHolder {
        ImageView imgIcon;
        TextView txtTitle;
    }
}




