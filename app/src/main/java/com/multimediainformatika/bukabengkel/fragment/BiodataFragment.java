package com.multimediainformatika.bukabengkel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.database.BiodataData;
import com.multimediainformatika.bukabengkel.database.BiodataDatabase;

/**
 * Created by eksmud on 23/02/2017.
 */

public class BiodataFragment extends Fragment{
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

            BiodataDatabase.saveBiodata(getActivity(),data);

           // startActivity(new Intent(getActivity(),MainActivity.class));
        }else{
            Toast.makeText(getActivity(),"Mohon Lengkapi Data Anda",Toast.LENGTH_LONG).show();
        }
    }

    private boolean isEmpty(EditText edit1) {
        if (edit1 == null || edit1.getText().toString().equals("") ) {
            return true;
        } else {
            return false;
        }
    }
}
