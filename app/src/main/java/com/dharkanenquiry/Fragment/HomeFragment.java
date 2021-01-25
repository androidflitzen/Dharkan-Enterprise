package com.dharkanenquiry.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

    WebApi webapi;
    public BackgroundLocationService gpsService;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, null);

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

        if (SharedPrefsUtils.getSharedPreferenceInt(context, SharedPrefsUtils.TYPE,0)==2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Permission.hasPermissions((Activity) context, permissions)) {
                   // if (SharedPrefsUtils.getSharedPreferenceBoolean(context, SharedPrefsUtils.CHECK_SERVICE, false) == false) {
                    boolean check = isMyServiceRunning(BackgroundLocationService.class);
                    if(check==true){

                    }else {
                        startService();
                    }
                    //}
                } else {
                    Permission.onRequestPermissionsResult((Activity) context, permissions);
                }
            } else {
                startService();
            }
        }
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
}
