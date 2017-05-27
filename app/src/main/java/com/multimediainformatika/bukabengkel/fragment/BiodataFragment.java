package com.multimediainformatika.bukabengkel.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.auth.BasicHandle;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.multimediainformatika.bukabengkel.AppMain;
import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.adapter.Antrian;
import com.multimediainformatika.bukabengkel.database.BiodataData;
import com.multimediainformatika.bukabengkel.database.BiodataDatabase;
import com.multimediainformatika.bukabengkel.database.Database;
import com.multimediainformatika.bukabengkel.utils.ConstNetwork;
import com.multimediainformatika.bukabengkel.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eksmud on 23/02/2017.
 */

public class BiodataFragment extends Fragment{
    private Database database;
    private AppMain main;

    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        main = (AppMain) getActivity().getApplication();
        database = main.getDatabase();
    }

    public BiodataFragment() {
        // Required empty public constructor
    }

    public static BiodataFragment newInstance() {
        BiodataFragment r = new BiodataFragment();
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
        View v = inflater.inflate(R.layout.biodata_fragment, container, false);
        final EditText nama = (EditText)v.findViewById(R.id.input_namalengkap);
        final EditText alamat = (EditText)v.findViewById(R.id.input_alamat);
        final EditText no_telp = (EditText)v.findViewById(R.id.input_notelp);
        final EditText email = (EditText)v.findViewById(R.id.input_email);

        BiodataData data = BiodataDatabase.getBiodata(getActivity());
        if( !data.nama.equals("")&& !data.alamat.equals("")&& !data.no_telp.equals("")&& !data.email.equals("")){
            nama.setText(data.nama);
            alamat.setText(data.alamat);
            no_telp.setText(data.no_telp);
            email.setText(data.email);
        }

        Button buttonSubmit = (Button) v.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(nama,alamat,no_telp,email);
            }
        });

        return v;
    }

    private void save(EditText nama,EditText alamat,EditText no_telp,EditText email){
        if( !isEmpty(nama) && !isEmpty(alamat) && !isEmpty(no_telp) && !isEmpty(email)){
            BiodataData data = new BiodataData();
            data.nama = nama.getText().toString();
            data.alamat = alamat.getText().toString();
            data.no_telp = no_telp.getText().toString();
            data.email = email.getText().toString();

            BiodataData tmp = BiodataDatabase.getBiodata(getActivity());
            updateToServer(data.email,data.no_telp,data.nama,data.alamat,tmp.id_users,tmp.token);

        }else{
            MyAlertDialogFragment frg = MyAlertDialogFragment.newInstance("PERHATIAN","Mohon Lengkapi Data Anda");
            frg.show(getFragmentManager(),"ALERT");
        }
    }

    private boolean isEmpty(EditText edit1) {
        if (edit1 == null || edit1.getText().toString().equals("") ) {
            return true;
        } else {
            return false;
        }
    }


    private void updateToServer(final String email, final String telp, final String nama, final String alamat, final String id_users,final String token) {
        final AQuery aq = new AQuery(getActivity());
        BasicHandle handle = new BasicHandle(ConstNetwork.USERNAME, ConstNetwork.PASSWORD);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);
        params.put("telp", telp);
        params.put("nama", nama);
        params.put("alamat", alamat);
        params.put("token", token);

        if(id_users !=null && !id_users.equals("")){
            params.put("id_users", id_users);
        }

        aq.auth(handle).progress(Utils.makeProgressDialog(getActivity())).ajax(ConstNetwork.URLPELANGGAN, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json != null) {

                    try {
                        boolean statusOut = json.getBoolean("status");

                        if (statusOut) {
                            String fk_idusers = json.getString("id_users");
                            BiodataData data = new BiodataData();
                            data.nama = nama;
                            data.alamat = alamat;
                            data.email = email;
                            data.no_telp = telp;
                            data.id_users = fk_idusers;

                            BiodataDatabase.saveBiodata(getActivity(),data);

                            MyAlertDialogFragment frg = MyAlertDialogFragment.newInstance("PERHATIAN","Biodata Berhasil Tersimpan");
                            frg.show(getFragmentManager(),"ALERT");

                        } else {
                            String error = json.getString("msg");
                            MyAlertDialogFragment frg = MyAlertDialogFragment.newInstance("PERHATIAN", error);
                            frg.show(getFragmentManager(), "ALERT");
                        }
                    } catch (JSONException e) {
                        MyAlertDialogFragment frg = MyAlertDialogFragment.newInstance("PERHATIAN", "JSONException");
                        frg.show(getFragmentManager(), "ALERT");
                    }
                } else {
                    MyAlertDialogFragment frg = MyAlertDialogFragment.newInstance("PERHATIAN", "Kosong");
                    frg.show(getFragmentManager(), "ALERT");
                }
            }
        });
    }
}
