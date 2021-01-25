package com.dharkanenquiry.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.dharkanenquiry.Activity.Login_Activity;
import com.dharkanenquiry.Model.AddLatLongModel;
import com.dharkanenquiry.Model.AllEnquiry;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackgroundLocationService extends Service {
    private final LocationServiceBinder binder = new LocationServiceBinder();
    private static final String TAG = "BackgroundLocationServi";
    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private NotificationManager notificationManager;
   // private final int LOCATION_INTERVAL = 100000;
  //  private final int LOCATION_INTERVAL = 1800000;
  //  private final int LOCATION_INTERVAL = 30000;
    private final int LOCATION_INTERVAL = 3600000;
    private final int LOCATION_DISTANCE = 10;
    WebApi webapi;
    String userId;


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    private class LocationListener implements android.location.LocationListener {
        private Location lastLocation = null;
        private final String TAG = "LocationListener";
        private Location mLastLocation;

        public LocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;
            Log.i(TAG, "LocationChanged: " + location);
            addLatLong(mLastLocation);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + status);
        }

        private void addLatLong(Location mLastLocation) {
            if(webapi!=null && mLastLocation!=null){
                if(userId!=null){
                    Call<AddLatLongModel> allEnquiryCall = webapi.addLatLong(userId, String.valueOf(mLastLocation.getLatitude()),String.valueOf(mLastLocation.getLongitude()));
                    allEnquiryCall.enqueue(new Callback<AddLatLongModel>() {
                        @Override
                        public void onResponse(Call<AddLatLongModel> call, Response<AddLatLongModel> response) {

                            if (response.body() != null) {
                                if (response.body().getStatus() == 1) {

                                } else {

                                }

                            } else {
                                Utils.showToast(getApplicationContext(), "Something Went to Wrong", R.color.red_dark);
                            }
                        }

                        @Override
                        public void onFailure(Call<AddLatLongModel> call, Throwable t) {
                            Utils.showToast(getApplicationContext(), "Data Not Found", R.color.red_dark);
                        }
                    });
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        System.out.println("===========onStartCommand  BackgroundLocationService");
        webapi = Utils.getRetrofitClient().create(WebApi.class);
        userId=SharedPrefsUtils.getSharedPreferenceString(getApplicationContext(), SharedPrefsUtils.USER_ID);
        return START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        startForeground(12345678, getNotification());
       // startForeground(0, getNotification());
        //  locationRepository = new LocationRepository(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }


    public void startTracking() {
        initializeLocationManager();
        mLocationListener = new LocationListener(LocationManager.GPS_PROVIDER);

        try {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {

                    /*LocationRequest locationRequest = LocationRequest.create()
                            .setFastestInterval(1000 * 10) //Do not receive the updated any frequent than 10 sec
                            .setInterval(1000 * 20) // Receive location update every 20 sec
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);*/

                    //  mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListener);

                    SharedPrefsUtils.setSharedPreferenceBoolean(getApplicationContext(), SharedPrefsUtils.CHECK_SERVICE, true);
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, 0, mLocationListener);

                }
            });

        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }catch (Exception ex) {
            Log.d(TAG, "All ex " + ex.getMessage());
        }
    }

    public class LocationServiceBinder extends Binder {
        public BackgroundLocationService getService() {
            return BackgroundLocationService.this;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification getNotification() {

        NotificationChannel channel = new NotificationChannel("channel_01", "My Channel", NotificationManager.IMPORTANCE_NONE);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_01");

      //  Notification.Builder builder = new Notification.Builder(getApplicationContext(), "channel_01").setAutoCancel(true);
        return builder.build();
    }
}