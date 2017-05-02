package com.example.vietd.cinema;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
 * Created by Admin on 4/10/2017.
 */


public class SignupFragment extends Fragment {

    View myView;
    FragmentManager fragmentManager;
    Config config;
    private EditText edt_username, edt_password, edt_email, edt_phone, edt_fullname, edt_passport, edt_address;
    private Button btn_signup;
    private ProgressDialog progressDialog;
    private TextView btn_signin;
    private int flag = 0;
    private Emitter.Listener onSignup = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = (JSONObject) args[0];
                    String data = null;
                    try {
                        flag = jsonObject.getInt("flag");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (flag != 0) {
                        try {
                            data = jsonObject.getString("result1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (data == "true") {
                            HideDialog();
                            Toast.makeText(getActivity(), "Đăng ký hoàn tất", Toast.LENGTH_SHORT).show();

                            fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.conent_frame, new SigninFragment())
                                    .addToBackStack(null)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .commit();
                        } else {
                            Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                            HideDialog();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_authenticate_signup, container, false);

        ControlFindViewByID();
        ControlButtonEvent();

        config = new Config();
        config.mSocket.connect();
        config.mSocket.on("result_signup", onSignup);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        return myView;
    }

    private void ControlButtonEvent() {
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.conent_frame, new SigninFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edt_username.getText().toString().trim();
                String pass = edt_password.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String phone = edt_phone.getText().toString().trim();
                String fullname = edt_fullname.getText().toString().trim();
                String passport = edt_passport.getText().toString().trim();
                String address = edt_address.getText().toString().trim();

                if (flag != 0) {
                    if (user.length() > 0 && pass.length() > 0 && email.length() > 0 && phone.length() > 0 && fullname.length() > 0 && passport.length() > 0 && address.length() > 0) {
                        if (CheckValidEmail(email)) {
                            try {
                                progressDialog.setMessage("Đăng ký....");
                                ShowDialog();
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("user", user);
                                jsonObject.put("pass", pass);
                                jsonObject.put("email", email);
                                jsonObject.put("phone", phone);
                                jsonObject.put("fullname", fullname);
                                jsonObject.put("passport", passport);
                                jsonObject.put("address", address);
                                jsonObject.toString();
                                config.mSocket.emit("signup", jsonObject);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            HideDialog();
                            Toast.makeText(getActivity(), "Vui nhập đúng cấu trúc Email. ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        HideDialog();
                        Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    edt_username.setBackgroundResource(R.color.Error);
                }
            }
        });
    }

    private void ControlFindViewByID() {
        edt_username = (EditText) myView.findViewById(R.id.edt_auth_signup_username);
        edt_password = (EditText) myView.findViewById(R.id.edt_auth_signup_password);
        edt_email = (EditText) myView.findViewById(R.id.edt_auth_signup_email);
        edt_phone = (EditText) myView.findViewById(R.id.edt_auth_signup_phone);
        edt_fullname = (EditText) myView.findViewById(R.id.edt_auth_signup_fullname);
        edt_passport = (EditText) myView.findViewById(R.id.edt_auth_signup_passport);
        edt_address = (EditText) myView.findViewById(R.id.edt_auth_signup_address);
        btn_signup = (Button) myView.findViewById(R.id.btn_auth_signup);
        btn_signin = (TextView) myView.findViewById(R.id.btn_auth_signup_signin);

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

    private boolean CheckValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)) {
            return true;
        }
        return false;
    }
}

