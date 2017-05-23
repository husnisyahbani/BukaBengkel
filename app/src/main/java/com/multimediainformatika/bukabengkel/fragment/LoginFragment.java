package com.multimediainformatika.bukabengkel.fragment;

import android.content.Intent;
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
import com.multimediainformatika.bukabengkel.Belanja;
import com.multimediainformatika.bukabengkel.R;
import com.multimediainformatika.bukabengkel.database.Session;
import com.multimediainformatika.bukabengkel.database.SessionData;
import com.multimediainformatika.bukabengkel.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eksmud on 15/05/2017.
 */

public class LoginFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login, container, false);
        final EditText username = (EditText)v.findViewById(R.id.input_username) ;
        final EditText password = (EditText)v.findViewById(R.id.input_password) ;
        Button login = (Button)v.findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(username,password)){
                    loginToServer(username.getText().toString(),password.getText().toString());
                }else{
                    MyAlertDialogFragment frg = MyAlertDialogFragment.newInstance("PERHATIAN","Username dan Password tidak boleh kosong ");
                    frg.show(getFragmentManager(),"ALERT");
                }
            }
        });
        return v;
    }

    private boolean isEmpty(EditText edit1, EditText edit2) {
        if (edit1.getText().toString().equals("") || edit2.getText().toString().equals("") ) {
            return true;
        } else {
            return false;
        }
    }


    private void loginToServer(String username, String password) {

        final AQuery aq = new AQuery(getActivity());
        BasicHandle handle = new BasicHandle(username,password);

        Map<String, Object> params = new HashMap<String, Object>();


        aq.auth(handle).progress(Utils.makeProgressDialog(getActivity())).ajax("https://api.bukalapak.com/v2/authenticate.json",params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json != null) {

                    try {
                        String statusOut = json.getString("status");

                        if (statusOut.equals("OK")) {
                            String user_id = json.getString("user_id");
                            String token = json.getString("token");
                            SessionData data = new SessionData();
                            data.user_id = user_id;
                            data.token = token;
                            Session.saveSession(getActivity(), data);
                            startActivity(new Intent(getActivity(), Belanja.class));
                            getActivity().finish();
                        } else {
                            String error = json.getString("message");
                            MyAlertDialogFragment frg = MyAlertDialogFragment.newInstance("LOGIN GAGAL",error);
                            frg.show(getFragmentManager(),"ALERT");
                        }
                    } catch (JSONException e) {
                        MyAlertDialogFragment frg = MyAlertDialogFragment.newInstance("LOGIN GAGAL","JSONException");
                        frg.show(getFragmentManager(),"ALERT");
                    }
                }else{
                    MyAlertDialogFragment frg = MyAlertDialogFragment.newInstance("LOGIN GAGAL","Kosong");
                    frg.show(getFragmentManager(),"ALERT");
                }
            }
        });

    }
}
