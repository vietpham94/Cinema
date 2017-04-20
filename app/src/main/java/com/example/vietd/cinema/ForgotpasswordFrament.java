package com.example.vietd.cinema;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;


/**
 * Created by Optimus on 4/19/2017.
 */

public class ForgotpasswordFrament extends Fragment{

    View myView;
    private EditText edt_email;
    private Button btn_sendcode;
    private TextView tv_incorrectEmail;
    private ProgressDialog progressDialog;
    Config config;

    private Emitter.Listener result_findByEmail = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = (JSONObject) args[0];
                    String data = null;
                    try {
                        data = jsonObject.getString("result_findByEmail");
                        if (data == "true") {
                            tv_incorrectEmail.setText("correct Email.");

                        } else {
                            tv_incorrectEmail.setText("Incorrect Email. Put your Email .");
                            HideDialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };
    private Emitter.Listener result_sendmail = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = (JSONObject) args[0];
                    String data = null;
                    try {
                        data = jsonObject.getString("result1");
                        if (data == "true") {


                        } else {

                            HideDialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.forgot_password_fragment, container, false);

        edt_email = (EditText) myView.findViewById(R.id.edt_auth_forgot_email);
        btn_sendcode = (Button) myView.findViewById(R.id.btn_auth_forgot_sendcode);
        tv_incorrectEmail = (TextView) myView.findViewById(R.id.tv_auth_forgot_incorrectEmail);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        config = new Config();
        config.mSocket.connect();
        config.mSocket.on("result_findByEmail", result_findByEmail);
        config.mSocket.on("result_sendmail",result_sendmail);
        btn_sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_email.length()>0){
                    tv_incorrectEmail.setText("");
                    progressDialog.setMessage("Waiting.....");
                    ShowDialog();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("email", edt_email.getText().toString());
                        jsonObject.toString();

                        config.mSocket.emit("sendMailChangePass",jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity(), "In put your email", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return myView;
    }

    private void ShowDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }

    }

    private void HideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();

        }
    }
}

