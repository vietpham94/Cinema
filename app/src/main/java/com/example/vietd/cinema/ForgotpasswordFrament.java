package com.example.vietd.cinema;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Optimus on 4/19/2017.
 */

public class ForgotpasswordFrament extends Fragment {

    View myView;
    FragmentManager fragmentManager;
    Config config;
    String code = "";
    String email = "";
    private EditText edt_email, edt_code, edt_rmkpass, edt_rmkpass1;
    private Button btn_sendcode, btn_code, btn_rmkpass;
    private TextView tv_incorrectEmail, tv_Email;
    private ProgressDialog progressDialog;

    private Emitter.Listener server_send_code_change_pass = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = (JSONObject) args[0];


                    try {

                        code = jsonObject.getString("code");
                        email = jsonObject.getString("email");


                            Toast.makeText(getActivity(), "1111111111111111111", Toast.LENGTH_SHORT);
                            HideDialog();
                            edt_email.setVisibility(View.GONE);
                            btn_sendcode.setVisibility(View.GONE);
                            tv_Email.setVisibility(View.GONE);
                            tv_incorrectEmail.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "dfgfdgfd4", Toast.LENGTH_SHORT);
                            edt_code.setVisibility(View.VISIBLE);
                            btn_code.setVisibility(View.VISIBLE);

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

        edt_code = (EditText) myView.findViewById(R.id.edt_code);
        btn_code = (Button) myView.findViewById(R.id.btn_confirm);

        tv_incorrectEmail = (TextView) myView.findViewById(R.id.tv_auth_forgot_incorrectEmail);
        tv_Email = (TextView) myView.findViewById(R.id.textView3);
        edt_rmkpass = (EditText) myView.findViewById(R.id.edt_rmkpass);
        edt_rmkpass1 = (EditText) myView.findViewById(R.id.edt_rmkpass1);
        btn_rmkpass = (Button) myView.findViewById(R.id.btn_rmkpass);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        config = new Config();
        config.mSocket.connect();

        config.mSocket.on("server_send_code_change_pass", server_send_code_change_pass);

        btn_sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_email.length() > 0) {
                    tv_incorrectEmail.setText("");
                    progressDialog.setMessage("Vui lòng đợi...");
                    ShowDialog();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        email=edt_email.getText().toString();
                        jsonObject.put("email",email );
                        jsonObject.toString();

                        config.mSocket.emit("sendMailChangePass", jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Nhập Email của bạn", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_code.length() < 0) {
                    Toast.makeText(getActivity(), "Nhập Mã xác nhận", Toast.LENGTH_SHORT).show();
                } else {
                    if (edt_code.getText().toString().equals(code)) {

                        edt_code.setVisibility(View.GONE);
                        btn_code.setVisibility(View.GONE);

                        edt_rmkpass.setVisibility(View.VISIBLE);
                        edt_rmkpass1.setVisibility(View.VISIBLE);
                        btn_rmkpass.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getActivity(), "Nhập Mã xác nhận đã được gửi vào mail của bạn!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_rmkpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_rmkpass.length() < 0 || edt_rmkpass1.length() < 0) {
                    Toast.makeText(getActivity(), "Nhập pass", Toast.LENGTH_SHORT).show();
                } else {
                    if (edt_rmkpass.getText().toString().equals(edt_rmkpass1.getText().toString() )) {
                        config.mSocket.emit("rmkpass", email, edt_rmkpass.getText().toString());
                        Toast.makeText(getActivity().getApplicationContext(), "Cập nhật mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Mật khẩu và Mật khẩu nhập lại phải trùng nhau!", Toast.LENGTH_SHORT).show();
                    }
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

