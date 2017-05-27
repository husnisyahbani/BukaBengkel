package com.multimediainformatika.bukabengkel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.multimediainformatika.bukabengkel.Belanja;
import com.multimediainformatika.bukabengkel.Login;
import com.multimediainformatika.bukabengkel.PanggilMontir;
import com.multimediainformatika.bukabengkel.PanggilMontirNew;
import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.ServiceMobil;
import com.multimediainformatika.bukabengkel.ServiceMotor;
import com.multimediainformatika.bukabengkel.adapter.CustomGrid;
import com.multimediainformatika.bukabengkel.adapter.MenuPilihan;
import com.multimediainformatika.bukabengkel.database.Session;
import com.multimediainformatika.bukabengkel.database.SessionData;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

/**
 * Created by eksmud on 23/02/2017.
 */

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener{
    private GridView grid;
    private int[] sampleImages = {R.drawable.gambar2, R.drawable.gambar1,R.drawable.gambar3};
    private String berita[] = {"PELAYANAN CEPAT DAN RAMAH","BERDEDIKASI TINGGI","LAYANAN YANG PRIMA"};
    private MenuPilihan data[];

    public static HomeFragment newInstance() {
        HomeFragment r = new HomeFragment();
        return r;
    }

    public HomeFragment() {
        // Required empty public constructor
        data = new MenuPilihan[4];
        MenuPilihan pil1 = new MenuPilihan();
        pil1.icon = R.drawable.ic_mobilereapir128;
        pil1.kategori = "SERVICE\nMOBIL";

        MenuPilihan pil2 = new MenuPilihan();
        pil2.icon = R.drawable.ic_motorreapir128;
        pil2.kategori = "SERVICE\nMOTOR";

        MenuPilihan pil3 = new MenuPilihan();
        pil3.icon = R.drawable.ic_montirreapir128;
        pil3.kategori = "PANGGIL\nMONTIR";

        MenuPilihan pil4 = new MenuPilihan();
        pil4.icon = R.drawable.ic_cart128;
        pil4.kategori = "BELANJA\nSPAREPART";

        data[0] = pil1;
        data[1] = pil2;
        data[2] = pil3;
        data[3] = pil4;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.content_main, container, false);
        final TextView  judulBerita= (TextView)v.findViewById(R.id.judulBerita);
        CarouselView carouselView = (CarouselView) v.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        judulBerita.setText(berita[0]);
        carouselView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                judulBerita.setText(berita[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);

            }
        });

        judulBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(), Informasi.class));
            }
        });

        grid = (GridView)v.findViewById(R.id.gridView1);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CustomGrid gridAdapter = new CustomGrid(getActivity(),R.layout.tombol,data);
        grid.setAdapter(gridAdapter);
        grid.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == 0)
        {
            startActivity(new Intent(getActivity(), ServiceMobil.class));
        }else if(i == 1)
        {
            startActivity(new Intent(getActivity(), ServiceMotor.class));
        }else if(i == 2)
        {
            startActivity(new Intent(getActivity(), PanggilMontirNew.class));
        }else if(i == 3)
        {
            SessionData data = Session.getSession(getActivity());
            if(data.token.equals("")){
                startActivity(new Intent(getActivity(), Login.class));
            }else{
                startActivity(new Intent(getActivity(), Belanja.class));
            }
        }
    }
}
