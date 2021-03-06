package com.dharkanenquiry.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dharkanenquiry.Activity.Customerlist_Activiy;
import com.dharkanenquiry.Activity.Enquiry_Activity;

import com.dharkanenquiry.Activity.Login_Activity;
import com.dharkanenquiry.Activity.SalesPersonList_Activity;
import com.dharkanenquiry.Activity.SplashScreen_Activity;
import com.dharkanenquiry.Activity.Task_Activity;
import com.dharkanenquiry.Model.AllEnquiry;
import com.dharkanenquiry.Model.AllTasklist;
import com.dharkanenquiry.service.BackgroundLocationService;
import com.dharkanenquiry.utils.Permission;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    LinearLayout liEnquiry, liTasks, liCustomerlist, liLocationTracker;
    TextView txt_use_name;
    Context context;
    @BindView(R.id.txt_Username)
    TextView txtUsername;
    Unbinder unbinder;

    Animation animZoomout, animSlideLeft;

    public TextView tvEnquiryTotal;
    public TextView tvTaskTotal;
    AllEnquiry allenquiryList;

    List<AllEnquiry> enquiryList = new ArrayList();
    //private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION};
    //private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
   // private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};

    WebApi webapi;
    public BackgroundLocationService gpsService;
    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, null);

       /* FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                System.out.println("===========token  "+task.getResult());
            }
        });*/

        unbinder = ButterKnife.bind(this, view);
        context = getActivity();

        liEnquiry = view.findViewById(R.id.liEnquiry);
        liTasks = view.findViewById(R.id.liTasks);
        liCustomerlist = view.findViewById(R.id.liCustomerlist);
        liLocationTracker = view.findViewById(R.id.liLocationTracker);

        if (SharedPrefsUtils.getSharedPreferenceInt(context, SharedPrefsUtils.TYPE,0)==1) {
            liLocationTracker.setVisibility(View.VISIBLE);
        } else {
            liLocationTracker.setVisibility(View.GONE);
        }

        tvEnquiryTotal = view.findViewById(R.id.tvEnquiryTotal);
        tvTaskTotal = view.findViewById(R.id.tvTaskTotal);

        animZoomout = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
        animSlideLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_in);

        startanimation();

        boolean check = isMyServiceRunning(BackgroundLocationService.class);
        System.out.println("=======check  " + check);

        initUI();

        return view;
    }

    private void startService() {
        final Intent intent = new Intent(getActivity(), BackgroundLocationService.class);
        getActivity().startService(intent);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        boolean check = isMyServiceRunning(BackgroundLocationService.class);
        System.out.println("==========recheck  " + check);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("=========start service");
                if(gpsService!=null){
                    gpsService.startTracking();
                }
            }
        }, 5000);
    }

    private void startanimation() {

        txtUsername.setAnimation(animSlideLeft);

        liEnquiry.setAnimation(animZoomout);
        liTasks.setAnimation(animZoomout);
        liCustomerlist.setAnimation(animZoomout);
        liLocationTracker.setAnimation(animZoomout);


    }

    private void initUI() {

        txtUsername.setText(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_NAME));

        webapi = Utils.getRetrofitClient().create(WebApi.class);


        liEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Enquiry_Activity.class);
                startActivity(intent);
               /* ((AppCompatActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FramgmetDashbaord, new Enquiry_Fragment())
                        .setCustomAnimations(android.R.anim.fade_out, android.R.anim.fade_in)
                        .commit(); */
            }
        });

        liTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Task_Activity.class);
                startActivity(intent);

            }
        });

        liCustomerlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Customerlist_Activiy.class);
                startActivity(intent);

            }
        });

        liLocationTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SalesPersonList_Activity.class);
                startActivity(intent);
            }
        });

        allenquiry();
    }

    public void allenquiry() {

        Call<AllEnquiry> allEnquiryCall = webapi.allenquiry(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID), String.valueOf(1));
        allEnquiryCall.enqueue(new Callback<AllEnquiry>() {
            @Override
            public void onResponse(Call<AllEnquiry> call, Response<AllEnquiry> response) {

                if (response.body() != null) {


                    Utils.showLog("==" + response.body().getTotalEnquiry());
                    if (response.body().getStatus() == 0) {


                        tvEnquiryTotal.setText(" (0)");

                    } else {
                        if (response.body().getTotalEnquiry().equals("0") && response.body().getTotalEnquiry().equals(null)) {
                            tvEnquiryTotal.setText(" (0)");

                        } else {
                            tvEnquiryTotal.setText(" (" + response.body().getTotalEnquiry() + ")");
                        }
                    }

                } else {
                    Utils.showToast(context, "Something Went to Wrong", R.color.red_dark);
                }

            }

            @Override
            public void onFailure(Call<AllEnquiry> call, Throwable t) {
                Utils.showToast(context, "Data Not Found", R.color.red_dark);
            }
        });

    }

    public void allTask() {

        Call<AllTasklist> allTasklistCall = webapi.allTotalTask(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
        allTasklistCall.enqueue(new Callback<AllTasklist>() {
            @Override
            public void onResponse(Call<AllTasklist> call, Response<AllTasklist> response) {

                if (response.body() != null) {

                    Utils.showLog("==" + response.body().getTotalTasks());

                    if (response.body().getStatus() == 1) {
                        if (response.body().getTotalTasks().equals("")) {
                            tvTaskTotal.setText("(0)");

                        } else {
                            tvTaskTotal.setText(" (" + response.body().getTotalTasks() + ")");
                        }
                    }


                } else {
                    Utils.showToast(context, "Something Went to Wrong", R.color.red_dark);
                }

            }

            @Override
            public void onFailure(Call<AllTasklist> call, Throwable t) {
                Utils.showToast(context, "Data Not Found", R.color.red_dark);
            }
        });

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            String name = className.getClassName();
            if (name.endsWith("BackgroundLocationService")) {
                gpsService = ((BackgroundLocationService.LocationServiceBinder) service).getService();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            if (className.getClassName().equals("BackgroundLocationService")) {
                gpsService = null;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        allenquiry();
        allTask();

        /*if (SharedPrefsUtils.getSharedPreferenceInt(context, SharedPrefsUtils.TYPE,0)==2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Permission.hasPermissions((Activity) context, permissions)) {
                    boolean check = isMyServiceRunning(BackgroundLocationService.class);
                    checkGPSONOFF();
                } else {
                    Permission.onRequestPermissionsResult2((Activity) context, permissions);
                }
            } else {
               checkGPSONOFF();
            }
        }*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // unbinder.unbind();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                System.out.println("===========true");
                return true;
            }
        }
        System.out.println("===========false");
        return false;
    }

    private void checkGPSONOFF(){
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean checkGPS;
        /*if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(getActivity())) {
            Toast.makeText(getActivity(),"Gps already enabled....",Toast.LENGTH_SHORT).show();

        }*/
        // Todo Location Already on  ... end

        if(!hasGPSDevice(getActivity())){
            Toast.makeText(getActivity(),"Gps not Supported",Toast.LENGTH_SHORT).show();
        }

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(getActivity())) {
            //Toast.makeText(getActivity(),"Gps not enabled",Toast.LENGTH_SHORT).show();
            enableLoc();
        }else{
            //Toast.makeText(getActivity(),"Gps already enabled",Toast.LENGTH_SHORT).show();
            if(isMyServiceRunning(BackgroundLocationService.class)==false){
                startService();
            }
        }
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void enableLoc() {

        //if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            System.out.println("=========cancel");
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(getActivity(), REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        //}
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
// Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startService();
                        break;
                    case Activity.RESULT_CANCELED:
                        enableLoc();
                        break;
                }
                break;
        }
    }*/
}
