package com.multimediainformatika.bukabengkel.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.multimediainformatika.bukabengkel.AppMain;
import com.multimediainformatika.bukabengkel.adapter.Produk;
import com.multimediainformatika.bukabengkel.adapter.ProdukAdapter;
import com.multimediainformatika.bukabengkel.database.Database;
import com.multimediainformatika.bukabengkel.utils.ConstNetwork;
import com.multimediainformatika.bukabengkel.utils.OnButtonPressedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eksmud on 17/05/2017.
 */

public class BelanjaFragment extends ListFragment implements OnButtonPressedListener{
    private Database database;
    private AppMain main;
    private Cursor data;
    private ProdukAdapter adapter;
    private AQuery aq;
    private boolean first;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq = new AQuery(getActivity());
        Log.e("HUSNI","onCreate");
    }

    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        main = (AppMain) getActivity().getApplication();
        database = main.getDatabase();
        Log.e("HUSNI","onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        data = database.getProduk();
        adapter = new ProdukAdapter(getActivity(), data);
        adapter.setOnButtonPressedListener(BelanjaFragment.this);
        setListAdapter(adapter);
        if(!first){
            updateToServer();

        }
        Log.e("HUSNI","onActivityCreated");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && first) {
            Log.e("HUSNI","setUserVisibleHint");
            updateToServer();
        }
    }

    private void updateToServer()
    {
        aq.ajax(ConstNetwork.URLPRODUK, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                if (json != null) {
                    database.deleteProduk();
                    try {
                        String statusOut = json.getString("status");
                        if(statusOut.equals("OK")) {
                            JSONArray result = json.getJSONArray("products");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject dataRow = result.getJSONObject(i);
                                Produk tmp = new Produk();
                                tmp.name = dataRow.getString("name");
                                tmp.price = dataRow.getString("price");
                                tmp.desc = dataRow.getString("desc");
                                JSONArray img = dataRow.getJSONArray("images");
                                tmp.image = img.getString(0);
                                database.insertProduk(tmp);
                                data = database.getProduk();
                                adapter.changeCursor(data);
                                adapter.refresh();
                                first = true;
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

    @Override
    public void onButtonPressedListener(View v) {

    }
}
