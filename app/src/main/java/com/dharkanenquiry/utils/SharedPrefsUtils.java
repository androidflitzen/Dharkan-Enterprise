package com.dharkanenquiry.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsUtils {

    private final static String PREF_FILE = "VASUDHA_PREF";
    private final static String PREF_FIREBASE = "FIREBASE";
    public static final String NOTIFICATION_ID = "id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";

    public static final String USER_TYPE = "user_type";
    public static final String TYPE = "type";
    public static final String TOKEN = "";
    public static final String USER_ID = "";
    public static final String IS_LOG_IN = "is_login";
    public static final String CHECK_SERVICE = "check_service";

    public static SharedPreferences getSharePref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void setSharedPreferenceString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static void setSharedPreferenceInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public static void setSharedPreferenceBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    public static String getSharedPreferenceString(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getString(key, "");
    }


    public static int getSharedPreferenceInt(Context context, String key, int defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getInt(key, defValue);
    }


    public static boolean getSharedPreferenceBoolean(Context context, String key, boolean defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getBoolean(key, defValue);
    }

    public static void clearPreference(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void setFirebaseToken(Context context, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FIREBASE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("firebase_token", value);
        editor.apply();
    }

    public static String getFirebaseToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FIREBASE, 0);
        return settings.getString("firebase_token", "");
    }

}
