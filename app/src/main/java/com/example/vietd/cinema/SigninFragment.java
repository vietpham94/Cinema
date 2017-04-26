
package com.example.vietd.cinema;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * Created by Admin on 4/10/2017.
 */


public class SigninFragment extends Fragment {
    View myView;
    Config config;
    EditText user, pass;
    Button btn_signin, btn_register;
    ProgressDialog progressDialog;
    FragmentManager fragmentManager;
    TextView tv_forgot_password;

    SharedPreferences.Editor editor;
    UserSessionManager userSessionManager;

    private Emitter.Listener resultSignin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = (JSONObject) args[0];
                    String result = null;
                    JSONObject data = null;
                    try {
                        result = jsonObject.getString("result");
                        if (result.equals("true")) {
                            data = jsonObject.getJSONObject("user");
                            Toast.makeText(getActivity(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            CreateUserLoginSeassion(data.getString("username"), data.getString("idcustomer"), data.getString("fullname"), data.getString("email"), data.getString("birthday"), data.getString("gender"), data.getString("identitycard"), data.getString("phone"));
                            HideDialog();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "Lỗi! Vui lòng nhập lại mật khẩu/tên đăng nhập!", Toast.LENGTH_SHORT).show();
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
        myView = inflater.inflate(R.layout.activity_authenticate_signin, container, false);


        ControlFindViewByID();
        ControlButtonEvent();
        config = new Config();
        config.mSocket.connect();
        config.mSocket.on("result_signin", resultSignin);

        userSessionManager = new UserSessionManager(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);


        return myView;
    }

    private void ControlButtonEvent() {
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user.getText().toString().trim();
                String password = pass.getText().toString().trim();
                if (user.length() > 0 && pass.length() > 0) {
                    CheckSignin(username, password);
                } else {
                    if(user.length() > 0 && password.length() <= 0){
                        Toast.makeText(getActivity(), "Vui lòng nhập Mật khẩu!", Toast.LENGTH_SHORT).show();
                    }else{
                        if(user.length() <= 0 && password.length() > 0){
                            Toast.makeText(getActivity(), "Vui lòng nhập Tên tài khoản!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Vui lòng nhập Tên tài khoản và Mật khẩu!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.conent_frame, new SignupFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.conent_frame, new ForgotpasswordFrament())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void ControlFindViewByID() {
        user = (EditText) myView.findViewById(R.id.edt_auth_signin_username);
        pass = (EditText) myView.findViewById(R.id.edt_auth_signin_password);
        tv_forgot_password = (TextView) myView.findViewById(R.id.tv_auth_signin_forgot);
        btn_signin = (Button) myView.findViewById(R.id.btn_auth_signin_signin);
        btn_register = (Button) myView.findViewById(R.id.btn_auth_signin_signup);
    }

    private void CheckSignin(final String user, final String pass) {
        progressDialog.setMessage("Đăng nhập...");
        ShowDialog();
        config.mSocket.emit("signin", user, pass);
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

    private void CreateUserLoginSeassion(String user, String idcustomer, String fullname, String email, String birthday, String gender,String identitycard, String phone) {
        editor = getActivity().getSharedPreferences(userSessionManager.PREFER_NAME, userSessionManager.PRIVATE_MODE).edit();

        editor.putBoolean(userSessionManager.IS_USER_LOGIN, true);
        editor.putString(userSessionManager.KEY_USERNAME, user);
        editor.putString(userSessionManager.KEY_idcustomer, idcustomer);
        editor.putString(userSessionManager.KEY_fullname, fullname);
        editor.putString(userSessionManager.KEY_email, email);
        editor.putString(userSessionManager.KEY_birthday, birthday);
        editor.putString(userSessionManager.KEY_gender, gender);
        editor.putString(userSessionManager.KEY_identitycard, identitycard);
        editor.putString(userSessionManager.KEY_phone, phone);

        editor.commit();
    }


}

