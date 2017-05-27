package com.multimediainformatika.bukabengkel.fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.multimediainformatika.bukabengkel.AppMain;
import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.adapter.BengkelAdapter;
import com.multimediainformatika.bukabengkel.adapter.BookingAdapter;
import com.multimediainformatika.bukabengkel.adapter.BookingItem;
import com.multimediainformatika.bukabengkel.database.Database;
import com.multimediainformatika.bukabengkel.utils.OnButtonPressedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eksmud on 23/02/2017.
 */

public class RekapBookingFragment extends ListFragment implements OnButtonPressedListener {
    private Database database;
    private AppMain main;
    private Cursor data;
    private BookingAdapter adapter;

    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        main = (AppMain) getActivity().getApplication();
        database = main.getDatabase();
    }

    public RekapBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            data = database.getAntrian();
            adapter = new BookingAdapter(getActivity(),data);
            adapter.setOnButtonPressedListener(this);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onButtonPressedListener(View v) {
        TextView textStatus = (TextView)v.getTag();
        textStatus.setTextColor(Color.RED);
        textStatus.setText("CANCEL");
        textStatus.setVisibility(View.VISIBLE);
        v.setVisibility(View.GONE);
    }
}
