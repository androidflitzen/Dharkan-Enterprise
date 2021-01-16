package com.dharkanenquiry.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.dharkanenquiry.Adapter.AllEnquiryAdapter;
import com.dharkanenquiry.Model.AllEnquiry;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchEnquiry_Activity extends AppCompatActivity {

    @BindView(R.id.etSearch)
    MaterialSearchBar etSearch;
    @BindView(R.id.rvEnquiry)
    RecyclerView rvEnquiry;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    AllEnquiryAdapter allEnquiryAdapter;

    ArrayList<AllEnquiry.Result> allDataList = new ArrayList<>();
    ArrayList<AllEnquiry.Result> allenquiryList = new ArrayList<>();

    WebApi webapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_enquiry_);
        ButterKnife.bind(this);

        rvEnquiry.setLayoutManager(new LinearLayoutManager(SearchEnquiry_Activity.this, LinearLayoutManager.VERTICAL, false));
        rvEnquiry.setHasFixedSize(true);
        allEnquiryAdapter=new AllEnquiryAdapter(this, allenquiryList,rvEnquiry);
        rvEnquiry.setAdapter(allEnquiryAdapter);
        rvEnquiry.scrollToPosition(allDataList.size());

        getAllData();


        etSearch.setVisibility(View.VISIBLE);
        etSearch.enableSearch();
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        etSearch.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    etSearch.setText("");
                    etSearch.setVisibility(View.GONE);
                    finish();
                    // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    // getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                /*if (rvEnquiry != null) {
                    allEnquiryAdapter=new AllEnquiryAdapter(SearchEnquiry_Activity.this, allDataList,rvEnquiry);
                    rvEnquiry.setAdapter(allEnquiryAdapter);
                }*/
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
                } else if (rvEnquiry != null) {
                    allDataList.clear();
                    allDataList.addAll(allenquiryList);
                    allEnquiryAdapter=new AllEnquiryAdapter(SearchEnquiry_Activity.this, allDataList,rvEnquiry);
                    rvEnquiry.setAdapter(allEnquiryAdapter);
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
                getAllData();
            }
        });

    }

    private void performSearch(String text, boolean showMsg) {

        allDataList.clear();
        if (allenquiryList != null) {
            for (int i = 0; i < allenquiryList.size(); i++) {
                if (allenquiryList.get(i).getCustomerName() != null && !allenquiryList.get(i).getCustomerName().equals("null") &&
                        allenquiryList.get(i).getSalesPerson() != null && !allenquiryList.get(i).getSalesPerson().equals("null") &&
                        allenquiryList.get(i).getCity() != null && !allenquiryList.get(i).getCity().equals("null") &&
                        allenquiryList.get(i).getRegion() != null && !allenquiryList.get(i).getRegion().equals("null"))  {

                    if (allenquiryList.get(i).getCustomerName().replace(" ","").toLowerCase().contains(text.toLowerCase()) ||
                            allenquiryList.get(i).getUniqueId().replace(" ","").toLowerCase().contains(text.toLowerCase())||
                            allenquiryList.get(i).getCity().replace(" ","").toLowerCase().contains(text.toLowerCase()) ||
                            allenquiryList.get(i).getSalesPerson().replace(" ","").toLowerCase().contains(text.toLowerCase()) ||
                            allenquiryList.get(i).getRegion().replace(" ","")
                                    .toLowerCase().contains(text.toLowerCase())) {
                        allDataList.add(allenquiryList.get(i));

                    }
                    // Utils.showLog("===  Searchlist.size() " + Searchlist.size());
                }
            }

            if (allDataList.size() == 0 && showMsg) {
                //  Utils.showToast(activity, "No result found!", R.color.red);
            }
            if (rvEnquiry != null) {
                allEnquiryAdapter=new AllEnquiryAdapter(SearchEnquiry_Activity.this, allDataList,rvEnquiry);
                rvEnquiry.setAdapter(allEnquiryAdapter);
            }

        }
    }


    public void getAllData() {

        webapi = Utils.getRetrofitClient().create(WebApi.class);
        final Call<AllEnquiry> enquiryCall = webapi.allenquiry_(SharedPrefsUtils.getSharedPreferenceString(this, SharedPrefsUtils.USER_ID));

        enquiryCall.enqueue(new Callback<AllEnquiry>() {
            @Override
            public void onResponse(Call<AllEnquiry> call, Response<AllEnquiry> response) {

                if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
                    swipeRefresh.setRefreshing(false);
                }

                if (response.body() != null) {

                    if (response.body().getStatus() == 1) {
                        allDataList.addAll(response.body().getResult());
                        allenquiryList.addAll(allDataList);
                        allEnquiryAdapter.notifyDataSetChanged();

                    } else {

                    }

                } else {
                    Utils.showErrorToast(getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<AllEnquiry> call, Throwable t) {

                if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
                    swipeRefresh.setRefreshing(false);
                }

                Utils.showToast(SearchEnquiry_Activity.this, "Data Not Found", R.color.red_dark);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}