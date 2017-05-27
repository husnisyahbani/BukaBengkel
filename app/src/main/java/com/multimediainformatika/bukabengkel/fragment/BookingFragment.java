package com.multimediainformatika.bukabengkel.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by eksmud on 12/03/2017.
 */

public class BookingFragment extends Fragment {
    private Database database;
    private AppMain main;

    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        main = (AppMain) getActivity().getApplication();
        database = main.getDatabase();
    }

    public static BookingFragment newInstance(String id_bengkel, String nama, String alamat) {
        BookingFragment f = new BookingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("NAMA", nama);
        bundle.putString("ALAMAT", alamat);
        bundle.putString("ID_BENGKEL", id_bengkel);

        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle data = getArguments();
        View v = inflater.inflate(R.layout.bookingfragment, container, false);
        TextView textNama = (TextView) v.findViewById(R.id.textNama);
        TextView textAlamat = (TextView) v.findViewById(R.id.textAlamat);

        final EditText input_plat = (EditText) v.findViewById(R.id.input_plat);

        final RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);

        final RadioButton radioButton1 = (RadioButton) v.findViewById(R.id.radioButton1);
        radioButton1.setText(Utils.getTime(0));
        final RadioButton radioButton2 = (RadioButton) v.findViewById(R.id.radioButton2);
        radioButton2.setText(Utils.getTime(1));
        final RadioButton radioButton3 = (RadioButton) v.findViewById(R.id.radioButton3);
        radioButton3.setText(Utils.getTime(2));
        final RadioButton radioButton4 = (RadioButton) v.findViewById(R.id.radioButton4);
        radioButton4.setText(Utils.getTime(3));
        final RadioButton radioButton5 = (RadioButton) v.findViewById(R.id.radioButton5);
        radioButton5.setText(Utils.getTime(4));


        final String nama = data.getString("NAMA");
        final String alamat = data.getString("ALAMAT");
        final String id_bengkel = data.getString("ID_BENGKEL");
        textNama.setText(nama);
        textAlamat.setText(alamat);


        Button buttonBooking = (Button) v.findViewById(R.id.buttonBooking);
        buttonBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = radioGroup.getCheckedRadioButtonId();
                if (id != -1 && !input_plat.getText().toString().equals("")) {
                    String tanggalpi = "";
                    if (id == R.id.radioButton1) {
                        tanggalpi = radioButton1.getText().toString();
                    } else if (id == R.id.radioButton2) {
                        tanggalpi = radioButton2.getText().toString();
                    } else if (id == R.id.radioButton3) {
                        tanggalpi = radioButton3.getText().toString();
                    } else if (id == R.id.radioButton4) {
                        tanggalpi = radioButton4.getText().toString();
                    } else if (id == R.id.radioButton5) {
                        tanggalpi = radioButton5.getText().toString();
                    }

                    BiodataData biodataData = BiodataDatabase.getBiodata(getActivity());
                    if (!biodataData.id_users.equals("")) {
                        String noplat = input_plat.getText().toString();
                        updateToServer(biodataData.id_users, noplat, id_bengkel, tanggalpi);
                    } else {
                        MyAlertDialogFragment frg = MyAlertDialogFragment.newInstance("PERHATIAN", "Mohon Isi Biodata Anda");
                        frg.show(getFragmentManager(), "ALERT");
                    }

                } else {
                    MyAlertDialogFragment frg = MyAlertDialogFragment.newInstance("PERHATIAN", "Data harus diisi");
                    frg.show(getFragmentManager(), "ALERT");
                }

            }
        });
        return v;
    }


    private void updateToServer(String id_users, String noplat, String id_bengkel, String tanggal) {
        final AQuery aq = new AQuery(getActivity());
        BasicHandle handle = new BasicHandle(ConstNetwork.USERNAME, ConstNetwork.PASSWORD);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id_users", id_users);
        params.put("noplat", noplat);
        params.put("id_bengkel", id_bengkel);
        params.put("tanggal", tanggal);

        aq.auth(handle).progress(Utils.makeProgressDialog(getActivity())).ajax(ConstNetwork.URLANTRIAN, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json != null) {

                    try {
                        boolean statusOut = json.getBoolean("status");

                        if (statusOut) {

                            JSONArray result = json.getJSONArray("result");
                            JSONObject dataRow = result.getJSONObject(0);
                            String id_antrian = dataRow.getString("id_antrian");
                            String noantrian = dataRow.getString("noantrian");
                            String tanggal = dataRow.getString("tanggal");
                            String fk_idbengkel = dataRow.getString("fk_idbengkel");
                            String fk_idusers = dataRow.getString("fk_idusers");
                            String noplat = dataRow.getString("noplat");
                            String statusku = dataRow.getString("status");


                            Antrian data = new Antrian();
                            data.id_antrian = id_antrian;
                            data.no_antrian = noantrian;
                            data.tanggal = tanggal;
                            data.fk_idbengkel = fk_idbengkel;
                            data.fk_idusers = fk_idusers;
                            data.noplat = noplat;
                            data.status = statusku;

                            database.insertAntrian(data);

                            MyAlertDialogFragment frg = MyAlertDialogFragment.newInstance("PERHATIAN", "Ambil Antrian Berhasil, silahkan akses menu REKAP");
                            frg.show(getFragmentManager(), "ALERT");

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
