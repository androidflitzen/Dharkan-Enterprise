package com.dharkanenquiry.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dharkanenquiry.Adapter.AllSaleslistAdapter;
import com.dharkanenquiry.Adapter.AllcustomerlistAdapter;
import com.dharkanenquiry.MainActivity;
import com.dharkanenquiry.Model.AllCustomerList;
import com.dharkanenquiry.Model.Users;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesPersonList_Activity extends AppCompatActivity {


    Context context;
    Activity activity;
    WebApi webapi;
    ProgressDialog progressDialog;
    FloatingActionButton btn_new_customer;
    SwipeRefreshLayout swipeRefresh;
    RecyclerView rvSaleslist;

    MaterialSearchBar etSearch;
    ImageView ivSearch, ivBackSaleslist;
    TextView tvTitle;

    Boolean isaddnewcustomer = false;

    List<Users.Result> allcustomerlist = new ArrayList<>();
    List<Users.Result> Searchlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_person_list_);

        initUI();

    }

    private void initUI() {

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        rvSaleslist = (RecyclerView) findViewById(R.id.rvSaleslist);
        etSearch = (MaterialSearchBar) findViewById(R.id.etSearch);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivBackSaleslist = (ImageView) findViewById(R.id.ivBackSaleslist);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        tvTitle.setText("Sales Person List");

        progressDialog = new ProgressDialog(SalesPersonList_Activity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        webapi = Utils.getRetrofitClient().create(WebApi.class);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setVisibility(View.VISIBLE);
                etSearch.enableSearch();
                //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                // getSupportActionBar().setDisplayShowHomeEnabled(false);

            }
        });

        //user_id = SharedPrefsUtils.USER_ID;

        ivBackSaleslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SalesPersonList_Activity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //onBackPressed();
            }
        });


        rvSaleslist.setLayoutManager(new LinearLayoutManager(SalesPersonList_Activity.this, LinearLayoutManager.VERTICAL, false));
        rvSaleslist.setHasFixedSize(true);
        rvSaleslist.setAdapter(new AllSaleslistAdapter(SalesPersonList_Activity.this, Searchlist));
        rvSaleslist.scrollToPosition(Searchlist.size());

        etSearch.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    etSearch.setText("");
                    etSearch.setVisibility(View.GONE);
                    // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    // getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                if (rvSaleslist != null) {
                    rvSaleslist.setAdapter(new AllSaleslistAdapter(SalesPersonList_Activity.this, Searchlist));
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
                } else if (rvSaleslist != null) {
                    Searchlist.clear();
                    Searchlist.addAll(allcustomerlist);
                    rvSaleslist.setAdapter(new AllSaleslistAdapter(SalesPersonList_Activity.this, Searchlist));
                }
                // Utils.hideKeyboard(activity);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (etSearch.getText().isEmpty()) {
                    getAllSaleslist();
                } else {
                    hideSwipeRefresh();
                }
            }
        });


        getAllSaleslist();
    }

    private void getAllSaleslist() {
        showPrd();

        Call<Users> allCustomerListCall = webapi.getSalesPerson("1");
        allCustomerListCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                if (response.body() != null) {

                    hidePrd();
                    if (response.body().getStatus() == 1) {

                        allcustomerlist.clear();
                        Searchlist.clear();
                        Searchlist.addAll(response.body().getResult());
                        allcustomerlist.addAll(response.body().getResult());
                        rvSaleslist.setAdapter(new AllSaleslistAdapter(SalesPersonList_Activity.this, Searchlist));

                    } else {
                        hidePrd();
                        Utils.showToast(getApplicationContext(), response.body().getMessage(), R.color.msg_fail);
                    }
                    hideSwipeRefresh();
                } else {
                    Utils.showToast(getApplicationContext(), response.body().getMessage(), R.color.msg_fail);
                    hidePrd();
                }

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                hideSwipeRefresh();
                hidePrd();
                //progressBar.setVisibility(View.GONE);
                Utils.showToast(SalesPersonList_Activity.this, "Data Not Found", R.color.red_dark);
            }
        });
    }

    private void performSearch(String text, boolean showMsg) {

        Searchlist.clear();
        if (allcustomerlist != null) {
            for (int i = 0; i < allcustomerlist.size(); i++) {
                if (allcustomerlist.get(i).getName() != null)  {

                    if (allcustomerlist.get(i).getName().replace(" ","").toLowerCase().contains(text.toLowerCase()) ) {
                        Searchlist.add(allcustomerlist.get(i));

                    }
                    // Utils.showLog("===  Searchlist.size() " + Searchlist.size());
                }
            }

            if (Searchlist.size() == 0 && showMsg) {
                //  Utils.showToast(activity, "No result found!", R.color.red);
            }
            if (rvSaleslist != null) {
                rvSaleslist.setAdapter(new AllSaleslistAdapter(SalesPersonList_Activity.this, Searchlist));
            }

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

    private void hideSwipeRefresh() {
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }
}