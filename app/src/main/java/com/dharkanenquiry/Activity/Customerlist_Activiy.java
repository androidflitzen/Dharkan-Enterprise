package com.dharkanenquiry.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dharkanenquiry.Adapter.AllcustomerlistAdapter;
import com.dharkanenquiry.MainActivity;
import com.dharkanenquiry.Model.AllCustomerList;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Customerlist_Activiy extends AppCompatActivity {

    Context context;
    Activity activity;
    WebApi webapi;
    ProgressDialog progressDialog;
    FloatingActionButton btn_new_customer;
    SwipeRefreshLayout swipeRefresh;
    RecyclerView rvCustomerlist;

    MaterialSearchBar etSearch;
    ImageView ivSearch, ivBackCustomerlist;
    TextView tvTitle;

    Boolean isaddnewcustomer = false;

    List<AllCustomerList.Result> allcustomerlist = new ArrayList<>();
    List<AllCustomerList.Result> Searchlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerlist__activiy);

        context = Customerlist_Activiy.this;

        initUI();
    }

    private void initUI() {
        activity = Customerlist_Activiy.this;
        btn_new_customer = (FloatingActionButton) findViewById(R.id.btn_new_customer);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        rvCustomerlist = (RecyclerView) findViewById(R.id.rvCustomerlist);
        etSearch = (MaterialSearchBar) findViewById(R.id.etSearch);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivBackCustomerlist = (ImageView) findViewById(R.id.ivBackCustomerlist);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        tvTitle.setText("Customers List");

        progressDialog = new ProgressDialog(activity);
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

        ivBackCustomerlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customerlist_Activiy.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //onBackPressed();
            }
        });


        rvCustomerlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvCustomerlist.setHasFixedSize(true);
        rvCustomerlist.setAdapter(new AllcustomerlistAdapter(context, Searchlist));
        rvCustomerlist.scrollToPosition(Searchlist.size());

        etSearch.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    etSearch.setText("");
                    etSearch.setVisibility(View.GONE);
                    // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    // getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                if (rvCustomerlist != null) {
                    rvCustomerlist.setAdapter(new AllcustomerlistAdapter(activity, Searchlist));
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
                } else if (rvCustomerlist != null) {
                    Searchlist.clear();
                    Searchlist.addAll(allcustomerlist);
                    rvCustomerlist.setAdapter(new AllcustomerlistAdapter(activity, Searchlist));
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
                    getAllCustomerlist();


                } else {
                    hideSwipeRefresh();
                }

            }
        });


        btn_new_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isaddnewcustomer = true;

                Intent intent = new Intent(Customerlist_Activiy.this,Add_New_Customer_Activity.class);
                startActivity(intent);
            }
        });

        getAllCustomerlist();
    }

    public void getAllCustomerlist() {

        showPrd();

        Call<AllCustomerList> allCustomerListCall = webapi.getallcustomerlistApi(SharedPrefsUtils.getSharedPreferenceString(this, SharedPrefsUtils.USER_ID));
        allCustomerListCall.enqueue(new Callback<AllCustomerList>() {
            @Override
            public void onResponse(Call<AllCustomerList> call, Response<AllCustomerList> response) {

                if (response.body() != null) {

                    hidePrd();
                    if (response.body().getStatus() == 1) {

                        allcustomerlist.clear();
                        Searchlist.clear();
                        Searchlist.addAll(response.body().getResult());
                        allcustomerlist.addAll(response.body().getResult());
                        rvCustomerlist.setAdapter(new AllcustomerlistAdapter(context, Searchlist));

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
            public void onFailure(Call<AllCustomerList> call, Throwable t) {
                hideSwipeRefresh();
                hidePrd();
                //progressBar.setVisibility(View.GONE);
                Utils.showToast(context, "Data Not Found", R.color.red_dark);
            }
        });


    }

    private void performSearch(String text, boolean showMsg) {

        Searchlist.clear();
        if (allcustomerlist != null) {
            for (int i = 0; i < allcustomerlist.size(); i++) {
                if (allcustomerlist.get(i).getCustomerName() != null && !allcustomerlist.get(i).getCustomerName().equals("null") &&
                        allcustomerlist.get(i).getPhoneNo() != null && !allcustomerlist.get(i).getPhoneNo().equals("null") &&
                        allcustomerlist.get(i).getCustomer_category_name() != null && !allcustomerlist.get(i).getCustomer_category_name().equals("null") &&
                        allcustomerlist.get(i).getEmail() != null && !allcustomerlist.get(i).getEmail().equals("null"))  {

                    if (allcustomerlist.get(i).getCustomerName().replace(" ","").toLowerCase().contains(text.toLowerCase()) ||
                            allcustomerlist.get(i).getPhoneNo().replace(" ","").toLowerCase().contains(text.toLowerCase())||
                            allcustomerlist.get(i).getCustomer_category_name().replace(" ","").toLowerCase().contains(text.toLowerCase()) ||
                            allcustomerlist.get(i).getEmail().replace(" ","").toLowerCase().contains(text.toLowerCase())) {
                        Searchlist.add(allcustomerlist.get(i));

                    }
                    // Utils.showLog("===  Searchlist.size() " + Searchlist.size());
                }
            }

            if (Searchlist.size() == 0 && showMsg) {
                //  Utils.showToast(activity, "No result found!", R.color.red);
            }
            if (rvCustomerlist != null) {
                rvCustomerlist.setAdapter(new AllcustomerlistAdapter(activity, Searchlist));
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isaddnewcustomer) {
            // isaddnewQnuiry = true;
            Utils.showLog("==isaddnewcustomer" + isaddnewcustomer);

            getAllCustomerlist();


        }
    }

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
