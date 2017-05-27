package com.multimediainformatika.bukabengkel.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.CursorAdapter;

import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.database.Database;
import com.multimediainformatika.bukabengkel.utils.OnButtonPressedListener;

import java.util.List;

/**
 * Created by eksmud on 30/03/2017.
 */

public class BookingAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;
    private Handler mHandler;
    private OnButtonPressedListener listener;

    public BookingAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
        mHandler = new Handler();
        cursorInflater = LayoutInflater.from(context);
    }

    public void setOnButtonPressedListener(OnButtonPressedListener listener) {
        this.listener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View itemView = cursorInflater.inflate(R.layout.booking_item_layout,
                viewGroup, false);
        ViewHolder holder = new ViewHolder();
        holder.textBooking = (TextView) itemView.findViewById(R.id.textBooking);
        holder.textPlat = (TextView) itemView.findViewById(R.id.textPlat);
        holder.textAntrian = (TextView) itemView.findViewById(R.id.textAntrian);
        holder.textStatus = (TextView) itemView.findViewById(R.id.textStatus);
        holder.buttonCancel = (Button) itemView.findViewById(R.id.buttonCancel);
        itemView.setTag(holder);
        return itemView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        BookingAdapter.ViewHolder holder = (BookingAdapter.ViewHolder) view.getTag();

        final String textBooking = cursor.getString(cursor
                .getColumnIndex(Database.TANGGAL));
        final String textPlat = cursor.getString(cursor
                .getColumnIndex(Database.NOPLAT));
        final String textAntrian = cursor.getString(cursor
                .getColumnIndex(Database.NO_ANTRIAN));
        final String textStatus = cursor.getString(cursor
                .getColumnIndex(Database.STATUS));

        holder.textBooking.setText(textBooking);
        holder.textPlat.setText(textPlat);
        holder.textAntrian.setText(textAntrian);
        holder.textStatus.setText(textStatus);

        final TextView textView = holder.textStatus;
        if (listener != null) {
            holder.buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag(textView);
                    listener.onButtonPressedListener(v);
                }
            });
        }
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
        private TextView textBooking;
        private TextView textPlat;
        private TextView textAntrian;
        private TextView textStatus;
        private Button buttonCancel;
    }

//    @Override
//    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item_layout, parent, false);
//        BookingViewHolder bookingViewHolder = new BookingViewHolder(v);
//        return bookingViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(final BookingViewHolder holder, int position) {
//        holder.textBooking.setText(bookings.get(position).tgl_booking);
//        holder.textNama.setText(bookings.get(position).nama);
//        holder.textPaspor.setText(bookings.get(position).no_paspor);
//        holder.textAntrian.setText(bookings.get(position).no_antrian);
//        if (listener != null) {
//            holder.buttonCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    v.setTag(holder.textStatus);
//                    listener.onButtonPressedListener(v);
//                }
//            });
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return bookings.size();
//    }
//
//    public static class BookingViewHolder extends RecyclerView.ViewHolder{
//        TextView textBooking;
//        TextView textPaspor;
//        TextView textNama;
//        TextView textAntrian;
//        TextView textStatus;
//        Button buttonCancel;
//
//        public BookingViewHolder(View itemView) {
//            super(itemView);
//            textBooking = (TextView)itemView.findViewById(R.id.textBooking);
//            textPaspor = (TextView)itemView.findViewById(R.id.textPaspor);
//            textNama = (TextView) itemView.findViewById(R.id.textNama);
//            textAntrian = (TextView) itemView.findViewById(R.id.textAntrian);
//            textStatus = (TextView) itemView.findViewById(R.id.textStatus);
//            buttonCancel = (Button) itemView.findViewById(R.id.buttonCancel);
//        }
//    }
}
