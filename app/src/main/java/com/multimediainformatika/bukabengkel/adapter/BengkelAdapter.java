package com.multimediainformatika.bukabengkel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.utils.OnButtonPressedListener;

import java.util.List;

/**
 * Created by eksmud on 30/03/2017.
 */

public class BengkelAdapter extends RecyclerView.Adapter<BengkelAdapter.BookingViewHolder>{
    List<Bengkel> bengkel;
    private static OnButtonPressedListener listener;

    public BengkelAdapter(List<Bengkel> bengkel){
        this.bengkel = bengkel;
    }

    public void setOnButtonPressedListener(OnButtonPressedListener listener) {
        this.listener = listener;
    }

    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistbengkel, parent, false);
        BookingViewHolder bookingViewHolder = new BookingViewHolder(v);
        return bookingViewHolder;
    }

    @Override
    public void onBindViewHolder(final BookingViewHolder holder, int position) {
        holder.textAlamat.setText(bengkel.get(position).alamat);
        holder.textNama.setText(bengkel.get(position).nama);
        holder.textJarak.setText(bengkel.get(position).jarak);

    }

    @Override
    public int getItemCount() {
        return bengkel.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder{
        TextView textNama;
        TextView textAlamat;
        TextView textJarak;

        public BookingViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onButtonPressedListener(view);
                }
            });
            textNama = (TextView) itemView.findViewById(R.id.textNama);
            textAlamat = (TextView) itemView.findViewById(R.id.textAlamat);
            textJarak = (TextView) itemView.findViewById(R.id.textJarak);
        }
    }
}