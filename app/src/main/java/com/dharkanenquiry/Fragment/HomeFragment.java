package com.dharkanenquiry.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dharkanenquiry.Activity.Customerlist_Activiy;
import com.dharkanenquiry.Activity.Enquiry_Activity;
import com.dharkanenquiry.Activity.Task_Activity;
import com.dharkanenquiry.Model.AllEnquiry;
import com.dharkanenquiry.Model.AllTasklist;
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


    LinearLayout liEnquiry, liTasks ,liCustomerlist;
    TextView txt_use_name;
    Context context;
    @BindView(R.id.txt_Username)
    TextView txtUsername;
    Unbinder unbinder;

    Animation animZoomout, animSlideLeft;

    public TextView tvEnquiryTotal;
   public TextView tvTaskTotal;
    AllEnquiry allenquiryList ;

    List<AllEnquiry> enquiryList = new ArrayList();

    WebApi webapi;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, null);

        unbinder = ButterKnife.bind(this, view);
        context = getActivity();

        liEnquiry = view.findViewById(R.id.liEnquiry);
        liTasks = view.findViewById(R.id.liTasks);
        liCustomerlist = view.findViewById(R.id.liCustomerlist);


        tvEnquiryTotal = view.findViewById(R.id.tvEnquiryTotal);
        tvTaskTotal = view.findViewById(R.id.tvTaskTotal);

        animZoomout = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
        animSlideLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_in);

        startanimation();

        initUI();


        return view;
    }

    private void startanimation() {

        txtUsername.setAnimation(animSlideLeft);

        liEnquiry.setAnimation(animZoomout);
        liTasks.setAnimation(animZoomout);
        liCustomerlist.setAnimation(animZoomout);


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

        allenquiry();
    }

    public void allenquiry() {

        Call<AllEnquiry> allEnquiryCall = webapi.allenquiry(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
        allEnquiryCall.enqueue(new Callback<AllEnquiry>() {
            @Override
            public void onResponse(Call<AllEnquiry> call, Response<AllEnquiry> response) {

                if (response.body() != null) {


                   Utils.showLog("=="+response.body().getTotalEnquiry());
                 if (response.body().getStatus() == 0){


                         tvEnquiryTotal.setText(" (0)");

                 }else {
                     if ( response.body().getTotalEnquiry().equals("0") && response.body().getTotalEnquiry().equals(null)){
                         tvEnquiryTotal.setText(" (0)");

                     }else {
                         tvEnquiryTotal.setText(" ("+ response.body().getTotalEnquiry()+")");
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

    public void allTask(){

        Call<AllTasklist> allTasklistCall = webapi.allTotalTask(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
        allTasklistCall.enqueue(new Callback<AllTasklist>() {
            @Override
            public void onResponse(Call<AllTasklist> call, Response<AllTasklist> response) {

                if (response.body() != null){

                    Utils.showLog("=="+response.body().getTotalTasks());

                    if (response.body().getStatus() == 1){
                        if (response.body().getTotalTasks().equals("")){
                            tvTaskTotal.setText("(0)");

                        }else {
                            tvTaskTotal.setText(" ("+ response.body().getTotalTasks()+")");
                        }
                    }




                }else {
                    Utils.showToast(context, "Something Went to Wrong", R.color.red_dark);
                }

            }

            @Override
            public void onFailure(Call<AllTasklist> call, Throwable t) {
                Utils.showToast(context, "Data Not Found", R.color.red_dark);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        allenquiry();
        allTask();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
