package com.dharkanenquiry.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.dharkanenquiry.vasudhaenquiry.R;

public class Network {

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isOnline = (networkInfo != null && networkInfo.isConnected());
        if (!isOnline)
            Utils.showToast(context, context.getResources().getString(R.string.no_internet_connection),R.color.msg_fail);

        return isOnline;
    }
}
