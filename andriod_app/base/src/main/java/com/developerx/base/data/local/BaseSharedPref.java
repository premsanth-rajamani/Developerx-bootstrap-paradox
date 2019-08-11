package com.developerx.base.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;

public class BaseSharedPref {

    private Context context;
    private static String TAG = "BaseSharedPref";
    private SharedPreferences sharedPreferences;
    public String MSG_ILLEGAL_ARGS = "The argument should be the application context!";
    public SharedPreferences.Editor editor;


    public BaseSharedPref() {
    }

    @Inject
    public BaseSharedPref(SharedPreferences sharedPreferences,Context context) {
        this.sharedPreferences = sharedPreferences;
        this.context = context;
        initPreferences(context);
    }

    public void initPreferences(Context applicationContext) {
//        if (applicationContext instanceof Activity) {
//            throw new IllegalArgumentException(MSG_ILLEGAL_ARGS);
//        } else if (sharedPreferences == null) {
        sharedPreferences = applicationContext.getSharedPreferences(
                "coachData", Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();

//        }
    }

    public String getPreference(String preference) {
        if (sharedPreferences == null) {
            return null;
        }
        String s = sharedPreferences.getString(preference, "");
        Log.d(TAG, "getPreference: "+s);
        return s;
    }


    public void clearPreference() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
