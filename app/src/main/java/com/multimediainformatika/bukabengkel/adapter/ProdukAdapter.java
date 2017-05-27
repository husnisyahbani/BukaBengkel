package com.multimediainformatika.bukabengkel.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.database.Database;
import com.multimediainformatika.bukabengkel.utils.OnButtonPressedListener;

/**
 * Created by eksmud on 17/05/2017.
 */

public class ProdukAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;
    private Handler mHandler;
    private OnButtonPressedListener listener;


    public ProdukAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
        mHandler = new Handler();
        cursorInflater = LayoutInflater.from(context);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View rowView = cursorInflater.inflate(R.layout.belanja_item,
                viewGroup, false);
        ViewHolder holder = new ViewHolder();
        holder.textName = (TextView) rowView.findViewById(R.id.textNama);
        //holder.textDesc = (TextView) rowView.findViewById(R.id.textDeskripsi);
        holder.textPrice = (TextView) rowView.findViewById(R.id.textharga);
        holder.buttonBeli = (Button) rowView.findViewById(R.id.buttonBeli);
        holder.imageProduk = (ImageView) rowView.findViewById(R.id.imageProduk);
        rowView.setTag(holder);
        return rowView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        final String textNama = cursor.getString(cursor
                .getColumnIndex(Database.NAMA));
//        final String textDesc = cursor.getString(cursor
//                .getColumnIndex(Database.DESKRIPSI));
        final String textPrice = cursor.getString(cursor
                .getColumnIndex(Database.PRICE));
        final String textImage = cursor.getString(cursor
                .getColumnIndex(Database.IMAGE));

        Produk produk = new Produk();
        produk.name = textNama;
       // produk.desc = textDesc;
        produk.price = textPrice;
        produk.image = textImage;

        AQuery aq = new AQuery(view);
        holder.buttonBeli.setTag(produk);
        holder.textName.setText(textNama);
       // holder.textDesc.setText(Html.fromHtml(textDesc));
        holder.textPrice.setText(textPrice);
        aq.id(holder.imageProduk).image(textImage);

        if (listener != null) {
            holder.buttonBeli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onButtonPressedListener(v);
                }
            });
        }
    }

    public void setOnButtonPressedListener(OnButtonPressedListener listener) {
        this.listener = listener;
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
       // private TextView textDesc;
        private TextView textPrice;
        private Button buttonBeli;
        private ImageView imageProduk;
    }
}
