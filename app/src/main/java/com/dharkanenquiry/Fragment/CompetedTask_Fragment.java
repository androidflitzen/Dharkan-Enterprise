package com.dharkanenquiry.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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

import com.dharkanenquiry.Activity.Task_Activity;
import com.dharkanenquiry.Adapter.CompletedtaskAdapter;
import com.dharkanenquiry.Model.Completedtask;
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

public class CompetedTask_Fragment extends Fragment {

    Unbinder unbinder;

    Context context;

    Activity activity;
    WebApi webapi;
    CompletedtaskAdapter completedtaskadapter;
    @BindView(R.id.progress_bar)
    SpinKitView progressBar;
    @BindView(R.id.rvTask_Completed)
    RecyclerView rvTaskCompleted;

    ProgressDialog progressDialog;

    ImageView ivSearch;
    MaterialSearchBar etSearch;

    Animation animZoomout, animSlideLeft;

    List<Completedtask.Result> allcompletedtasklist = new ArrayList<>();
    List<Completedtask.Result> Searchlist = new ArrayList<>();
   /* @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;*/

    Task_Activity task_activity;

    TextView tvNoCompleteTask;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.completed_task_fragment, null);

        unbinder = ButterKnife.bind(this, view);

        context = getActivity();

        webapi = Utils.getRetrofitClient().create(WebApi.class);

        tvNoCompleteTask = (TextView) view.findViewById(R.id.tvNoCompleteTask);

        animSlideLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);

        ivSearch = activity.findViewById(R.id.ivSearch_task);
        etSearch = activity.findViewById(R.id.ettaskSearch);

        startanimation();

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        Sprite doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);

        rvTaskCompleted.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ;
        rvTaskCompleted.setHasFixedSize(true);
        rvTaskCompleted.setAdapter(new CompletedtaskAdapter(context, Searchlist, CompetedTask_Fragment.this));

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
                   // ((AppCompatActivity) activity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                   // ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                if (rvTaskCompleted != null) {
                    rvTaskCompleted.setAdapter(new CompletedtaskAdapter(getActivity(), Searchlist, CompetedTask_Fragment.this));
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                if (text.length() > 0) {
                    performSearch(String.valueOf(text), true);

                   /* startActivity(new Intent(activity, SearchProductActivity.class).putExtra("search_txt", etSearch.getText()));
                    etSearch.setText("");
                    etSearch.disableSearch();*/
                } else if (rvTaskCompleted != null) {
                    Searchlist.clear();
                    Searchlist.addAll(allcompletedtasklist);
                    rvTaskCompleted.setAdapter(new CompletedtaskAdapter(getActivity(), Searchlist, CompetedTask_Fragment.this));
                }
                Utils.hideKeyboard(getActivity());

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
                } else if (rvTaskCompleted != null) {
                    Searchlist.clear();
                    Searchlist.addAll(allcompletedtasklist);
                    rvTaskCompleted.setAdapter(new CompletedtaskAdapter(getActivity(), Searchlist, CompetedTask_Fragment.this));
                }
                //Utils.hideKeyboard(getActivity());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
      /*  swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (etSearch.getText().isEmpty()) {
                    getAllCompletdList();


                } else {
                   // hideSwipeRefresh();
                }

            }
        });*/

        //  getAllCompletdList();

        return view;
    }

    private void startanimation() {

        rvTaskCompleted.setAnimation(animZoomout);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    private void performSearch(String text, boolean showMsg) {

        Searchlist.clear();
        if (allcompletedtasklist != null) {
            for (int i = 0; i < allcompletedtasklist.size(); i++) {
                if (allcompletedtasklist.get(i).getCustomerName() != null && !allcompletedtasklist.get(i).getCustomerName().equals("null")
                && allcompletedtasklist.get(i).getAssignToUser() != null && !allcompletedtasklist.get(i).getAssignByUser().equals("null")
                        && allcompletedtasklist.get(i).getActionSubject() != null && !allcompletedtasklist.get(i).getActionSubject().equals("null")
                        && allcompletedtasklist.get(i).getAssignByUser() != null && !allcompletedtasklist.get(i).getAssignByUser().equals("null")
                        && allcompletedtasklist.get(i).getActionDetails() != null && !allcompletedtasklist.get(i).getActionDetails().equals("null")) {
                    if (allcompletedtasklist.get(i).getCustomerName().replace(" ","").toLowerCase().contains(text.toLowerCase().trim()) || allcompletedtasklist.get(i).getAssignByUser().replace(" ","").toLowerCase().contains(text.toLowerCase().trim()) || allcompletedtasklist.get(i).getAssignToUser().replace(" ","").toLowerCase().contains(text.toLowerCase().trim()) || allcompletedtasklist.get(i).getActionSubject().replace(" ","").toLowerCase().contains(text.toLowerCase().trim())|| allcompletedtasklist.get(i).getActionDetails().replace(" ","").toLowerCase().contains(text.toLowerCase().trim())) {
                        Searchlist.add(allcompletedtasklist.get(i));
                    }
                    // Utils.showLog("===  Searchlist.size() " + Searchlist.size());
                }
            }

            if (Searchlist.size() == 0 && showMsg) {
               // Utils.showToast(getActivity(), "No result found!", R.color.red);
            }
            if (rvTaskCompleted != null) {
                rvTaskCompleted.setAdapter(new CompletedtaskAdapter(getActivity(), Searchlist, CompetedTask_Fragment.this));
            }

        }
    }

    public void getAllCompletdList() {
        showPrd();
        // progressBar.setVisibility(View.VISIBLE);

        final Call<Completedtask> completedtaskCall = webapi.getcompletedtask(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
        completedtaskCall.enqueue(new Callback<Completedtask>() {
            @Override
            public void onResponse(Call<Completedtask> call, Response<Completedtask> response) {

                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {

                        allcompletedtasklist.clear();
                        Searchlist.clear();
                        Searchlist.addAll(response.body().getResult());
                        allcompletedtasklist.addAll(response.body().getResult());

                        tvNoCompleteTask.setVisibility(View.GONE);

                        if (rvTaskCompleted != null) {
                            rvTaskCompleted.setAdapter(new CompletedtaskAdapter(context, Searchlist, CompetedTask_Fragment.this));


                        }



                    } else if (response.body().getStatus() == 0) {
                        tvNoCompleteTask.setVisibility(View.VISIBLE);
                        hidePrd();
                    }else {
                        Utils.showToast(getActivity(), "No Completed Task Found!", R.color.msg_fail);
                    }

                } else {

                    if (response.body().getTotal_completed_tasks().equals("0")) {
                        task_activity.tvtaskCompleteTotal.setText("(0)");
                        //  tvTitleTask.setText("Completed Task"+"(0)");
                    } else {
                        task_activity.tvtaskCompleteTotal.setText("(" + response.body().getTotal_completed_tasks() + ")");
                        //    tvTitleTask.setText("Completed Task"+" ("+response.body().getTotal_completed_tasks()+")");

                    }
                    tvNoCompleteTask.setVisibility(View.GONE);
                    Utils.showErrorToast(getActivity());
                    hidePrd();

                }
                hidePrd();

            }

            @Override
            public void onFailure(Call<Completedtask> call, Throwable t) {
                hidePrd();
                tvNoCompleteTask.setVisibility(View.GONE);
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
        getAllCompletdList();
    }

   /* private void hideSwipeRefresh() {
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
