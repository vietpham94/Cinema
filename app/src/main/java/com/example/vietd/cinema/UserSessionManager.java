package com.example.vietd.cinema;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String PREFER_NAME = "Prefname";

    // All Shared Preferences Keys
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_idcustomer = "idcustomer";

    public static final String KEY_fullname = "fullname";

    public static final String KEY_email = "email";

    public static final String KEY_birthday = "birthday";

    public static final String KEY_gender = "gender";

    public static final String KEY_identitycard = "identitycard";

    public static final String KEY_phone = "phone";

    // User name (make variable public to access from outside)
    public static final String KEY_USERNAME = "username";

    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){

        return isUserLoggedIn();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_idcustomer, pref.getString(KEY_idcustomer, null));
        user.put(KEY_fullname, pref.getString(KEY_fullname, null));
        user.put(KEY_email, pref.getString(KEY_email, null));
        user.put(KEY_birthday, pref.getString(KEY_birthday, null));
        user.put(KEY_gender, pref.getString(KEY_gender, null));
        user.put(KEY_identitycard, pref.getString(KEY_identitycard, null));
        user.put(KEY_phone, pref.getString(KEY_phone, null));

        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        editor.clear();
        editor.commit();
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
