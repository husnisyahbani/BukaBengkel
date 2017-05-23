package com.multimediainformatika.bukabengkel.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.auth.BasicHandle;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.multimediainformatika.bukabengkel.AppMain;
import com.multimediainformatika.bukabengkel.Booking;
import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.adapter.Bengkel;
import com.multimediainformatika.bukabengkel.adapter.BengkelAdapter;
import com.multimediainformatika.bukabengkel.adapter.Produk;
import com.multimediainformatika.bukabengkel.database.Database;
import com.multimediainformatika.bukabengkel.utils.ConstNetwork;
import com.multimediainformatika.bukabengkel.utils.OnButtonPressedListener;
import com.multimediainformatika.bukabengkel.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eksmud on 30/04/2017.
 */

public class ListBengkelMobilFragment extends ListFragment {
    private Database database;
    private AppMain main;
    private Cursor data;
    private BengkelAdapter adapter;

    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        main = (AppMain) getActivity().getApplication();
        database = main.getDatabase();
    }

    public static ListBengkelMobilFragment newInstance() {
        ListBengkelMobilFragment r = new ListBengkelMobilFragment();
        return r;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.listbengkelmobilfragment, container, false);
        data = database.getBengkel();
        adapter = new BengkelAdapter(getActivity(),data);
        setListAdapter(adapter);
        updateToServer("1","-2.9560534","104.7462008");
        return v;
    }

    private void updateToServer(String jenis, final String lat, final String longi)
    {
        database.deleteBengkel();
        final AQuery aq = new AQuery(getActivity());
        BasicHandle handle = new BasicHandle(ConstNetwork.USERNAME,ConstNetwork.PASSWORD);

        aq.auth(handle).ajax(ConstNetwork.URLBENGKEL+"?jenis="+jenis+"&lat="+lat+"&longi="+longi, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
               // Toast.makeText(getActivity(), json.toString(), Toast.LENGTH_LONG).show();
                if (json != null) {
                    try {
                        boolean statusOut = json.getBoolean("status");
                        if(statusOut) {
                            JSONArray result = json.getJSONArray("result");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject dataRow = result.getJSONObject(i);
                                Bengkel tmp = new Bengkel();
                                tmp.nama = dataRow.getString("nama");
                                tmp.alamat = dataRow.getString("alamat");
                                String latA = dataRow.getString("lat");
                                String longiA = dataRow.getString("longi");

                                String jarak = Utils.getDistance(latA,longiA,lat,longi);
                                tmp.jarak = jarak;
                                tmp.jenis = 1;

                                database.insertBengkel(tmp);
                                data = database.getBengkel();
                                adapter.changeCursor(data);
                                adapter.refresh();
                            }

                        }else{
                            Toast.makeText(getActivity(), "Gagal", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "JSONException", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


}
