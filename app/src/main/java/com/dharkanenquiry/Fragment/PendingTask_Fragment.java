package com.dharkanenquiry.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dharkanenquiry.Activity.Add_NewTask_Activity;
import com.dharkanenquiry.Activity.Task_Activity;
import com.dharkanenquiry.Adapter.PendingTaskAdapter;
import com.dharkanenquiry.Model.PendingTask;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingTask_Fragment extends Fragment {


    @BindView(R.id.rvTask_Pending)
    RecyclerView rvTaskPending;
    Unbinder unbinder;


    Activity activity;

    Context context;
    WebApi webapi;

    @BindView(R.id.progress_bar)
    SpinKitView progressBar;

    ImageView ivSearch;
    MaterialSearchBar etSearch;

    List<PendingTask.Result> allpendingtasklist = new ArrayList<>();
    List<PendingTask.Result> Searchlist = new ArrayList<>();

    Animation animZoomout, animSlideLeft;
    @BindView(R.id.btn_add_Task)
    FloatingActionButton btnAddTask;

    ProgressDialog progressDialog;


    Task_Activity task_activity;

    TextView tvNoPendingTask;

    /*@BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
*/

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pendingtask_fragment, null);

        unbinder = ButterKnife.bind(this, view);
        context = getActivity();

        webapi = Utils.getRetrofitClient().create(WebApi.class);

        tvNoPendingTask = (TextView) view.findViewById(R.id.tvNoPendingTask);


        animSlideLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);

      /*  EnquiryNew.setTitle("Add Enquiry New Company");
        EnquiryExisting.setTitle("Add Enquiry Existing Company");*/

        ivSearch = activity.findViewById(R.id.ivSearch_task);
        etSearch = activity.findViewById(R.id.ettaskSearch);


        startanimation();

        Sprite doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        rvTaskPending.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvTaskPending.setHasFixedSize(true);
        rvTaskPending.setAdapter(new PendingTaskAdapter(context, Searchlist, PendingTask_Fragment.this));


        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setVisibility(View.VISIBLE);
                etSearch.enableSearch();
               // ((AppCompatActivity) activity).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                //((AppCompatActivity) activity).getSupportActionBar().setDisplayShowHomeEnabled(false);
            }
        });

        etSearch.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    etSearch.setText("");
                    etSearch.setVisibility(View.GONE);
                  //  ((AppCompatActivity) activity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                  //  ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                if (rvTaskPending != null) {
                    rvTaskPending.setAdapter(new PendingTaskAdapter(getActivity(), Searchlist, PendingTask_Fragment.this));
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {


            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        etSearch.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {

                if (text.length() > 0) {
                    performSearch(String.valueOf(text), true);

                   /* startActivity(new Intent(activity, SearchProductActivity.class).putExtra("search_txt", etSearch.getText()));
                    etSearch.setText("");
                    etSearch.disableSearch();*/
                } else if (rvTaskPending != null) {
                    Searchlist.clear();
                    Searchlist.addAll(allpendingtasklist);
                    rvTaskPending.setAdapter(new PendingTaskAdapter(getActivity(), Searchlist, PendingTask_Fragment.this));
                }
                // Utils.hideKeyboard(getActivity());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Add_NewTask_Activity.class);
                //  intent.putExtra("enquiry_id", allpendingtasklist.get(0).getEnquiry_id());
                startActivity(intent);
            }
        });

       /* swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (etSearch.getText().isEmpty()) {
                    getallPendingtask();



                } else {
                    hideSwipeRefresh();
                }

            }
        });*/

        //   getallPendingtask();


        return view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    private void performSearch(String text, boolean showMsg) {

        Searchlist.clear();
        if (allpendingtasklist != null) {
            for (int i = 0; i < allpendingtasklist.size(); i++) {
                if (allpendingtasklist.get(i).getCustomerName() != null && !allpendingtasklist.get(i).getCustomerName().equals("null")  && allpendingtasklist.get(i).getAssignToUser() != null && !allpendingtasklist.get(i).getAssignToUser().equals("null")
                        && allpendingtasklist.get(i).getAssignByUser() != null && !allpendingtasklist.get(i).getAssignByUser().equals("null")  && allpendingtasklist.get(i).getActionSubject() != null && !allpendingtasklist.get(i).getActionSubject().equals("null")
                        && allpendingtasklist.get(i).getActionDetails() != null && !allpendingtasklist.get(i).getActionDetails().equals("null")
                        && allpendingtasklist.get(i).getActionSubject() != null && !allpendingtasklist.get(i).getActionSubject().equals("null")) {
                    if (allpendingtasklist.get(i).getCustomerName().toLowerCase().contains(text.toLowerCase()) ||
                            allpendingtasklist.get(i).getAssignByUser().replace(" ","").toLowerCase().contains(text.toLowerCase()) ||
                            allpendingtasklist.get(i).getAssignToUser().replace(" ","").toLowerCase().contains(text.toLowerCase()) ||
                            allpendingtasklist.get(i).getActionSubject().replace(" ","").toLowerCase().contains(text.toLowerCase())||
                            allpendingtasklist.get(i).getActionDetails().replace(" ","").toLowerCase().contains(text.toLowerCase())) {
                        Searchlist.add(allpendingtasklist.get(i));
                    }
                    Utils.showLog("===  Searchlist.size() " + Searchlist.size());
                }
            }

            if (Searchlist.size() == 0 && showMsg) {
               // Utils.showToast(getActivity(), "No result found!", R.color.red);
            }
            if (rvTaskPending != null) {
                rvTaskPending.setAdapter(new PendingTaskAdapter(getActivity(), Searchlist, PendingTask_Fragment.this));
            }

        }
    }

    private void startanimation() {

        rvTaskPending.setAnimation(animSlideLeft);
    }

    public void getallPendingtask() {
        // progressBar.setVisibility(View.VISIBLE);
        showPrd();

        Call<PendingTask> pendingTaskCall = webapi.getpendingtask(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
        pendingTaskCall.enqueue(new Callback<PendingTask>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<PendingTask> call, Response<PendingTask> response) {

                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {

                        allpendingtasklist.clear();
                        Searchlist.clear();
                        Searchlist.addAll(response.body().getResult());
                        allpendingtasklist.addAll(response.body().getResult());
                        tvNoPendingTask.setVisibility(View.GONE);


                        if (rvTaskPending != null) {
                            rvTaskPending.setAdapter(new PendingTaskAdapter(context, Searchlist, PendingTask_Fragment.this));
                        }


                    } else if (response.body().getStatus() == 0) {

                        tvNoPendingTask.setVisibility(View.VISIBLE);
                        hidePrd();
                    }else {
                        Utils.showToast(getActivity(), "No Pending Task Found!", R.color.msg_fail);
                        hidePrd();
                    }

                } else {
                    tvNoPendingTask.setVisibility(View.GONE);
                    if (response.body().getTotal_pending_tasks().equals("0")) {
                        task_activity.tvTaskPendingTotal.setText("(0)");
                        // tvTitleTask.setText("Pending Task"+"(0)");
                    } else {
                        task_activity.tvTaskPendingTotal.setText("(" + response.body().getTotal_pending_tasks() + ")");
                        //  tvTitleTask.setText("Pending Task"+" ("+response.body().getTotal_pending_tasks()+")");
                    }
                    tvNoPendingTask.setVisibility(View.GONE);


                    Utils.showErrorToast(getActivity());
                    hidePrd();
                }
                hidePrd();

            }

            @Override
            public void onFailure(Call<PendingTask> call, Throwable t) {

                hidePrd();
                tvNoPendingTask.setVisibility(View.GONE);

                Utils.showToast(context, "Data Not Found", R.color.red_dark);

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
        getallPendingtask();

    }

  /*  private void hideSwipeRefresh() {
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }*/


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


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePrd();
    }
}
