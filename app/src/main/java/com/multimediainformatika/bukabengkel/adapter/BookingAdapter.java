package com.multimediainformatika.bukabengkel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.utils.OnButtonPressedListener;

import java.util.List;

/**
 * Created by eksmud on 30/03/2017.
 */

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder>{
    List<BookingItem> bookings;
    private OnButtonPressedListener listener;

    public BookingAdapter(List<BookingItem> bookings){
        this.bookings = bookings;
    }

    public void setOnButtonPressedListener(OnButtonPressedListener listener) {
        this.listener = listener;
    }

    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item_layout, parent, false);
        BookingViewHolder bookingViewHolder = new BookingViewHolder(v);
        return bookingViewHolder;
    }

    @Override
    public void onBindViewHolder(final BookingViewHolder holder, int position) {
        holder.textBooking.setText(bookings.get(position).tgl_booking);
        holder.textNama.setText(bookings.get(position).nama);
        holder.textPaspor.setText(bookings.get(position).no_paspor);
        holder.textAntrian.setText(bookings.get(position).no_antrian);
        if (listener != null) {
            holder.buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag(holder.textStatus);
                    listener.onButtonPressedListener(v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder{
        TextView textBooking;
        TextView textPaspor;
        TextView textNama;
        TextView textAntrian;
        TextView textStatus;
        Button buttonCancel;

        public BookingViewHolder(View itemView) {
            super(itemView);
            textBooking = (TextView)itemView.findViewById(R.id.textBooking);
            textPaspor = (TextView)itemView.findViewById(R.id.textPaspor);
            textNama = (TextView) itemView.findViewById(R.id.textNama);
            textAntrian = (TextView) itemView.findViewById(R.id.textAntrian);
            textStatus = (TextView) itemView.findViewById(R.id.textStatus);
            buttonCancel = (Button) itemView.findViewById(R.id.buttonCancel);
        }
    }
}
