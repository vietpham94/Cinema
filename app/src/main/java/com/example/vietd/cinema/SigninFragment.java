
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

    String u, p; // user , pass send from server when login success
    private Emitter.Listener resultSignin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.setMessage("Loading...");
                    ShowDialog();
                    JSONObject jsonObject = (JSONObject) args[0];
                    String data = null;

                    try {
                        data = jsonObject.getString("result");
                        u = jsonObject.getString("user");
                        p = jsonObject.getString("pass");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (data == "true") {

//                        Toast.makeText(getActivity(), u, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.ACTION_UPDATE);
//                        intent.putExtra("username", u);
//                        intent.putExtra("password", p);
//                        getActivity().sendBroadcast(intent);
                        Toast.makeText(getActivity(), "Login Complete", Toast.LENGTH_SHORT).show();
                        CreateUserLoginSeassion(u, p);

//                        Intent intent = new Intent(MainActivity.ACTION_UPDATE);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        getActivity().sendBroadcast(intent);

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        HideDialog();
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
                String username = user.getText().toString();
                String password = pass.getText().toString();
                if (user.length() > 0 && pass.length() > 0) {
                    CheckSignin(username, password);
                } else {
                    Toast.makeText(getActivity(), "Please Enter username and password", Toast.LENGTH_SHORT).show();
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
        progressDialog.setMessage("Sign in.....");
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

    private void CreateUserLoginSeassion(String user, String pass) {
        editor = getActivity().getSharedPreferences(userSessionManager.PREFER_NAME, userSessionManager.PRIVATE_MODE).edit();
        editor.putBoolean(userSessionManager.IS_USER_LOGIN, true);
        editor.putString(userSessionManager.KEY_USERNAME, user);
        editor.putString(userSessionManager.KEY_PASSWORD, pass);
        editor.commit();
    }


}

