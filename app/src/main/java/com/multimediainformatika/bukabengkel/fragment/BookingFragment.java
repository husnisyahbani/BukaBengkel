package com.multimediainformatika.bukabengkel.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.multimediainformatika.bukabengkel.R;


/**
 * Created by eksmud on 12/03/2017.
 */

public class BookingFragment extends Fragment {
    public static BookingFragment newInstance() {
        BookingFragment f = new BookingFragment();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = makeProgressDialog(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bookingfragment, container, false);
        Button buttonBooking = (Button) v.findViewById(R.id.buttonBooking);
        buttonBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                timer.start();
            }
        });
        return v;
    }


    CountDownTimer timer = new CountDownTimer(5000, 1000) {
        public void onTick(long millisUntilFinished) {

        }

        public void onFinish() {
            Toast.makeText(getActivity(),"Berhasil,Silahkan Cek No Antrian anda pada menu rekap",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

    };

    ProgressDialog dialog;

    public ProgressDialog makeProgressDialog(
            Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setInverseBackgroundForced(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setMessage("Mohon Tunggu...");
        return dialog;
    }


}
