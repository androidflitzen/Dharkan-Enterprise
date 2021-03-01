package com.dharkanenquiry.Activity;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dharkanenquiry.Fragment.CompetedTask_Fragment;
import com.dharkanenquiry.Fragment.PendingTask_Fragment;
import com.dharkanenquiry.Model.Completedtask;
import com.dharkanenquiry.Model.PendingTask;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Task_Activity extends AppCompatActivity {


    Context context;
    @BindView(R.id.tvTitle_task)
    TextView tvTitleTask;
    @BindView(R.id.ivSearch_task)
    ImageView ivSearchTask;
    /* @BindView(R.id.etSearch_task)
     MaterialSearchBar etSearchTask;*/
    @BindView(R.id.toolbar_task)
    Toolbar toolbarTask;
    @BindView(R.id.task_frame)
    FrameLayout taskFrame;
    @BindView(R.id.tvTaskPending)
    TextView tvTaskPending;
    @BindView(R.id.tvtaskComplete)
    TextView tvtaskComplete;
    @BindView(R.id.ettaskSearch)
    MaterialSearchBar ettaskSearch;
    @BindView(R.id.viewPending)
    View viewPending;
    @BindView(R.id.viewCompleted)
    View viewCompleted;
    @BindView(R.id.rlPending)
    RelativeLayout rlPending;
    @BindView(R.id.rlCompleted)
    RelativeLayout rlCompleted;

    WebApi webapi;

    public static TextView tvTaskPendingTotal;
    @BindView(R.id.lilinearPending)
    LinearLayout lilinearPending;
    public static TextView tvtaskCompleteTotal;
    @BindView(R.id.lilinearComplete)
    LinearLayout lilinearComplete;

    ImageView ivBacktask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_);
        ButterKnife.bind(this);

        context = Task_Activity.this;

        initUi();


    }

    private void initUi() {

        setSupportActionBar(toolbarTask);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvTaskPendingTotal = (TextView) findViewById(R.id.tvTaskPendingTotal);
        tvtaskCompleteTotal = (TextView) findViewById(R.id.tvtaskCompleteTotal);
        ivBacktask = (ImageView) findViewById(R.id.ivBacktask);

        webapi = Utils.getRetrofitClient().create(WebApi.class);

      //  viewPending.setVisibility(View.GONE);
        viewCompleted.setVisibility(View.GONE);

        ivBacktask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvTaskPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tvTaskPending.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                rlPending.setBackground(getResources().getDrawable(R.drawable.task_title_bg));
                rlCompleted.setBackground(getResources().getDrawable(R.drawable.task_bg_sub1));
                tvTitleTask.setText("Pending Task");
                viewPending.setVisibility(View.VISIBLE);
                viewCompleted.setVisibility(View.GONE);
                pushFragment(new PendingTask_Fragment(), "");
            }
        });

        tvtaskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlPending.setBackground(getResources().getDrawable(R.drawable.task_bg_sub1));
                rlCompleted.setBackground(getResources().getDrawable(R.drawable.task_title_bg));
                tvTitleTask.setText("Completed Task");
                viewPending.setVisibility(View.GONE);
                viewCompleted.setVisibility(View.VISIBLE);
                // tvtaskComplete.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                pushFragment(new CompetedTask_Fragment(), "");

            }
        });


    }

    private boolean pushFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.task_frame, fragment, tag)
                    //.addToBackStack("fragment")
                    .commit();
            return true;
        }
        return false;
    }

    public void getallPendingtask() {
        // progressBar.setVisibility(View.VISIBLE);


        Call<PendingTask> pendingTaskCall = webapi.getpendingtask(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
        pendingTaskCall.enqueue(new Callback<PendingTask>() {
            @Override
            public void onResponse(Call<PendingTask> call, Response<PendingTask> response) {

                if (response.body() != null) {

                    if (response.body().getStatus() == 1) {
                        if (response.body().getTotal_pending_tasks().equals("")) {
                            tvTaskPendingTotal.setText("(0)");
                            // tvTitleTask.setText("Pending Task"+"(0)");
                        } else {
                            tvTaskPendingTotal.setText("(" + response.body().getTotal_pending_tasks() + ")");
                            //  tvTitleTask.setText("Pending Task"+" ("+response.body().getTotal_pending_tasks()+")");
                        }

                    } else {
                        if (response.body().getTotal_pending_tasks().equals("")) {
                            tvTaskPendingTotal.setText("(0)");
                            // tvTitleTask.setText("Pending Task"+"(0)");
                        } else {
                            tvTaskPendingTotal.setText("(" + response.body().getTotal_pending_tasks() + ")");
                            //  tvTitleTask.setText("Pending Task"+" ("+response.body().getTotal_pending_tasks()+")");
                        }
                    }


                } else {

                    Utils.showToast(context, response.body().getMessage(), R.color.msg_fail);

                }

                // progressBar.setVisibility(View.GONE);
                //hideSwipeRefresh();
            }

            @Override
            public void onFailure(Call<PendingTask> call, Throwable t) {

                Utils.showToast(context, "Data Not Found", R.color.red_dark);

            }
        });

    }


    public void getAllcomplted() {

        Call<Completedtask> completedtaskCall = webapi.getcompletedtask(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
        completedtaskCall.enqueue(new Callback<Completedtask>() {
            @Override
            public void onResponse(Call<Completedtask> call, Response<Completedtask> response) {
                if (response.body() != null) {

                    if (response.body().getStatus() == 1) {

                        if (response.body().getTotal_completed_tasks().equals("")) {
                            tvtaskCompleteTotal.setText("(0)");
                            // tvTitleTask.setText("Pending Task"+"(0)");
                        } else {
                            tvtaskCompleteTotal.setText("(" + response.body().getTotal_completed_tasks() + ")");
                            //  tvTitleTask.setText("Pending Task"+" ("+response.body().getTotal_pending_tasks()+")");
                        }

                    } else {
                        if (response.body().getTotal_completed_tasks().equals("")) {
                            tvtaskCompleteTotal.setText("(0)");
                            // tvTitleTask.setText("Pending Task"+"(0)");
                        } else {
                            tvtaskCompleteTotal.setText("(" + response.body().getTotal_completed_tasks() + ")");
                            //  tvTitleTask.setText("Pending Task"+" ("+response.body().getTotal_pending_tasks()+")");
                        }
                    }

                } else {
                    Utils.showToast(context, response.body().getMessage(), R.color.msg_fail);
                }
            }

            @Override
            public void onFailure(Call<Completedtask> call, Throwable t) {

            }
        });


    }
/*

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        // progressDialog.dismiss();
        return true;
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        rlPending.setBackground(getResources().getDrawable(R.drawable.task_title_bg));
        rlCompleted.setBackground(getResources().getDrawable(R.drawable.task_bg_sub1));
        tvTitleTask.setText("Pending Task");
        viewPending.setVisibility(View.VISIBLE);
        viewCompleted.setVisibility(View.GONE);
        getallPendingtask();
        getAllcomplted();

        pushFragment(new PendingTask_Fragment(), "");
    }
}
