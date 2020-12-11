package com.dharkanenquiry.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dharkanenquiry.Adapter.NotificationAdapter;
import com.dharkanenquiry.MainActivity;
import com.dharkanenquiry.Model.DeleteAllNotification;
import com.dharkanenquiry.Model.Notification;
import com.dharkanenquiry.utils.Network;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification_Activity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Context context;
    WebApi webapi;

    @BindView(R.id.rvNotification)
    RecyclerView rvNotification;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    ProgressDialog progressDialog;

    List<Notification.Result> notificationList = new ArrayList<>();
    @BindView(R.id.tvEmptyNotification)
    TextView tvEmptyNotification;

    TextView tvNotificationClear;
    ImageView ivBackNotify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_);
        ButterKnife.bind(this);

        context = Notification_Activity.this;

        initUI();


    }

    private void initUI() {

        setSupportActionBar(toolbar);
        tvTitle.setText("Notification");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        ivBackNotify = (ImageView) findViewById(R.id.ivBackNotify);

        webapi = Utils.getRetrofitClient().create(WebApi.class);
        tvNotificationClear = (TextView) findViewById(R.id.tvNotificationClear);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        if (Network.isNetworkAvailable(Notification_Activity.this)) {
            showPrd();
            getNotifications();
        }

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Network.isNetworkAvailable(Notification_Activity.this)) {
                    getNotifications();
                } else {
                    hideSwipeRefresh();
                }
            }
        });

        ivBackNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        rvNotification.setLayoutManager(new LinearLayoutManager(Notification_Activity.this, LinearLayoutManager.VERTICAL, false));
        rvNotification.setHasFixedSize(true);

        tvNotificationClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openClearNotificationDialog();
            }
        });

       /* if (notificationList.size() > 1){
            tvEmptyNotification.setVisibility(View.GONE);
        }else {
            tvEmptyNotification.setVisibility(View.VISIBLE);
        }*/

    }

    private void openClearNotificationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you want to clear all Notification?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Network.isNetworkAvailable(context)) {
                            ClearAllNotify();
                        }
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void ClearAllNotify() {

        showPrd();
        Call<DeleteAllNotification> deleteAllNotificationCall = webapi.getDeleteAllNotification(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
        deleteAllNotificationCall.enqueue(new Callback<DeleteAllNotification>() {
            @Override
            public void onResponse(Call<DeleteAllNotification> call, Response<DeleteAllNotification> response) {
                hidePrd();
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        hidePrd();
                        Utils.showToast(context, "Clear All Notification Successfully.", R.color.green_fed);
                        Intent intent = new Intent(Notification_Activity.this, Notification_Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }


                } else {

                }
            }

            @Override
            public void onFailure(Call<DeleteAllNotification> call, Throwable t) {
                hidePrd();
                Utils.showToast(context, "Something Went to Wrong!", R.color.red);

            }
        });
    }


    private void getNotifications() {
        showPrd();

        Call<Notification> api = webapi.getnotification(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));

        api.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                hidePrd();
                hideSwipeRefresh();
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {

                        notificationList.clear();
                        notificationList.addAll(response.body().getResult());
                        rvNotification.setAdapter(new NotificationAdapter(context, notificationList));
                        tvEmptyNotification.setVisibility(View.GONE);

                    } else if (response.body().getStatus() == 0) {
                        notificationList.clear();
                        tvEmptyNotification.setVisibility(View.VISIBLE);

                    } else {
                        tvEmptyNotification.setVisibility(View.GONE);
                    }
                } else {
                    Utils.showErrorToast(context);
                    hidePrd();
                    tvEmptyNotification.setVisibility(View.GONE);

                }
                hidePrd();
            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                hidePrd();
                hideSwipeRefresh();
                tvEmptyNotification.setVisibility(View.GONE);
                Utils.showErrorToast(context);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotifications();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String isfirebase = SharedPrefsUtils.getSharedPreferenceString(getApplicationContext(),"isfirebasenotify");
        if (isfirebase.equals("1")){

            SharedPrefsUtils.setSharedPreferenceString(getApplicationContext(),"isfirebasenotify","0");
            Intent intent = new Intent(Notification_Activity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            finish();
        }
    }

  /*  @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();

        return true;
    }*/

    private void hideSwipeRefresh() {
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    private void showPrd() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    private void hidePrd() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}
