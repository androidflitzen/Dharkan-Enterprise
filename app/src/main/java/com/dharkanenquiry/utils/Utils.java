package com.dharkanenquiry.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.dharkanenquiry.vasudhaenquiry.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {

    public static final String APP_DIRECTORY = ".VasudhaEnquiry";
    public static String NAME = "";
    public static final String defaultFormate = "yyyy-MM-dd HH:mm:ss";

    public static Retrofit getRetrofitClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

    public static WebApi getRetrofitClient(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WebApi.class);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showLog(String msg) {
        Log.v("TAG", msg);
    }

    public static void showToast(Context context, String msg) {
        //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        new CToast(context).simpleToast(msg, Toast.LENGTH_SHORT)
                .setBackgroundColor(R.color.colorPrimary)
                .show();
    }

    public static void showToast(Context context, String msg, int color) {
        //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        new CToast(context).simpleToast(msg, Toast.LENGTH_SHORT)
                .setBackgroundColor(color)
                .show();
    }

    public static void showErrorToast(Context context) {
        //Toast.makeText(context, context.getString(R.string.msg_failed), Toast.LENGTH_SHORT).show();
        new CToast(context).simpleToast("Something went wrong ! Please try again.", Toast.LENGTH_SHORT)
                .setBackgroundColor(R.color.msg_fail)
                .show();
    }

    public static String getItemDir() {
        File dirReports = new File(Environment.getExternalStorageDirectory(),
                APP_DIRECTORY);
        if (!dirReports.exists()) {
            if (!dirReports.mkdirs()) {
                return null;
            }
        }
        return dirReports.getAbsolutePath();
    }

    public static void e(String msg) {
        Log.e("TAG_", msg);
    }


    public static String changeDateFormate(String time, String outputFormate) {
        String inputPattern = defaultFormate;
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputFormate);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}
