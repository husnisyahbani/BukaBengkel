package com.multimediainformatika.bukabengkel.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.adapter.BookingAdapter;
import com.multimediainformatika.bukabengkel.adapter.BookingItem;
import com.multimediainformatika.bukabengkel.utils.OnButtonPressedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eksmud on 23/02/2017.
 */

public class RekapBookingFragment extends Fragment implements OnButtonPressedListener {
    List<BookingItem> data;
    public RekapBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<>();
        data.add(new BookingItem("30/03/2017","13204199","M HUSNI SYAHBANI","120"));
        data.add(new BookingItem("30/03/2017","13204199","M HUSNI SYAHBANI","120"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.rekapbookingfragment, container, false);
        RecyclerView rv = (RecyclerView)v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        BookingAdapter adapter = new BookingAdapter(data);
        adapter.setOnButtonPressedListener(this);
        rv.setAdapter(adapter);

        return v;

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
