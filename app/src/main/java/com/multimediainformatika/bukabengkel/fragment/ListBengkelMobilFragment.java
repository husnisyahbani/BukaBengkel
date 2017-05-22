package com.multimediainformatika.bukabengkel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.multimediainformatika.bukabengkel.Booking;
import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.adapter.Bengkel;
import com.multimediainformatika.bukabengkel.adapter.BengkelAdapter;
import com.multimediainformatika.bukabengkel.utils.OnButtonPressedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eksmud on 30/04/2017.
 */

public class ListBengkelMobilFragment extends Fragment implements OnButtonPressedListener{

    public static ListBengkelMobilFragment newInstance() {
        ListBengkelMobilFragment r = new ListBengkelMobilFragment();
        return r;
    }

    List<Bengkel> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<>();
        data.add(new Bengkel("AHASS MERDEKA","JL. Merdeka 1 No.36 Palembang","1 km"));
        data.add(new Bengkel("AHASS SUDIRMAN","JL. Sudirman 1 No.36 Palembang","5 km"));
        data.add(new Bengkel("AHASS YAYASAN","JL. Yayasan 1 No.36 Palembang","6 km"));
        data.add(new Bengkel("AHASS ATMO","JL. Kol. Atmo 1 No.36 Palembang","7 km"));
        data.add(new Bengkel("AHASS VETERAN","JL. Veteran 1 No.36 Palembang","8 km"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.listbengkelmobilfragment, container, false);
        RecyclerView rv = (RecyclerView)v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        BengkelAdapter adapter = new BengkelAdapter(data);
        adapter.setOnButtonPressedListener(this);
        rv.setAdapter(adapter);


        return v;
    }

    @Override
    public void onButtonPressedListener(View v) {
        startActivity(new Intent(getActivity(), Booking.class));
    }
}
