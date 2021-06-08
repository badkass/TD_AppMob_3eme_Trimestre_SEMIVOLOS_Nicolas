package com.example.Persistance_donnees_sql;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static SharedPreferencesManager instance;
    private static Context ctx;

    public static final String SHAREDPREF_NAME = "MyPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_USEREMAIL = "email";

    private SharedPreferencesManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
        return instance;
    }

    //Sauvegarder dans les SharedPreferences les informations de l'utilisateur
    public boolean userLogin(String username, String email) {
        SharedPreferences myPref = ctx.getSharedPreferences(SHAREDPREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = myPref.edit();

        myEditor.putString(KEY_USERNAME,username);
        myEditor.putString(KEY_USEREMAIL,email);
        myEditor.apply();

        return true;
    }

    //Utilisateur loggé ? Lis les SharedPreferences
    public boolean isLoggedIn() {
        SharedPreferences myPref = ctx.getSharedPreferences(SHAREDPREF_NAME,Context.MODE_PRIVATE);

        if (!myPref.getString(KEY_USERNAME, "").equals("")) {
            return true;
        } else {
            return false;
        }
    }

    //Clear des SharedPreferences
    public boolean logout() {
        SharedPreferences myPref = ctx.getSharedPreferences(SHAREDPREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = myPref.edit();

        myEditor.clear();
        myEditor.apply();

        return true;
    }

    public String getUsername() {
        SharedPreferences myPref = ctx.getSharedPreferences(SHAREDPREF_NAME,Context.MODE_PRIVATE);
        return myPref.getString(KEY_USERNAME, "");
    }

    public String getUserEmail() {
        SharedPreferences myPref = ctx.getSharedPreferences(SHAREDPREF_NAME,Context.MODE_PRIVATE);
        return myPref.getString(KEY_USEREMAIL, "");
    }

}
