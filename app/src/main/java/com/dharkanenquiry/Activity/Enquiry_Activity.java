package com.dharkanenquiry.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dharkanenquiry.Adapter.AllEnquiryAdapter;
import com.dharkanenquiry.Model.AllEnquiry;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Enquiry_Activity extends AppCompatActivity {


    Context context;
    Activity activity;
    WebApi webapi;
    Unbinder unbinder;

    String category_id;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.view)
    View view;
    @BindView(R.id.etSearch)
    MaterialSearchBar etSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvEnquiry)
    RecyclerView rvEnquiry;

    @BindView(R.id.progress_bar)
    SpinKitView progressBar;

    ProgressDialog progressDialog;

    Animation animZoomout, animSlideLeft;

    Boolean isaddnewQnuiry = false;

    List<AllEnquiry.Result> allenquiryList = new ArrayList<>();
    List<AllEnquiry.Result> Searchlist = new ArrayList<>();

    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    //String user_id;
    DecimalFormat decimalFormat = new DecimalFormat("##,##,##,###.##");
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    final public static int finalposiotin = 0;
    @BindView(R.id.EnquiryNew)
    FloatingActionButton EnquiryNew;
    @BindView(R.id.EnquiryExisting)
    FloatingActionButton EnquiryExisting;
    @BindView(R.id.btn_add_enquiry)
    FloatingActionsMenu btnAddEnquiry;


    ImageView ivBackEnquiry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_);

        ButterKnife.bind(this);

        context = Enquiry_Activity.this;


        initUI();


    }

    private void startanimation() {

        rvEnquiry.setAnimation(animZoomout);
    }

    private void initUI() {
        activity = Enquiry_Activity.this;
        decimalFormat.setMinimumFractionDigits(2);
        setSupportActionBar(toolbar);
        tvTitle.setText("Enquiry List");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        ivBackEnquiry = (ImageView) findViewById(R.id.ivBackEnquiry);

        EnquiryNew.setTitle("Add Enquiry New Company");
        EnquiryExisting.setTitle("Add Enquiry Existing Company");

        animSlideLeft = AnimationUtils.loadAnimation(context, R.anim.zoom_in);

        startanimation();


        Sprite doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);

        webapi = Utils.getRetrofitClient().create(WebApi.class);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setVisibility(View.VISIBLE);
                etSearch.enableSearch();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);

            }
        });

        //user_id = SharedPrefsUtils.USER_ID;

        ivBackEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rvEnquiry.setLayoutManager(new LinearLayoutManager(Enquiry_Activity.this, LinearLayoutManager.VERTICAL, false));
        rvEnquiry.setHasFixedSize(true);
        rvEnquiry.setAdapter(new AllEnquiryAdapter(activity, Searchlist));
        rvEnquiry.scrollToPosition(Searchlist.size());
        //  getAllEnquirylist();


        EnquiryExisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isaddnewQnuiry = true;

                Intent intent = new Intent(Enquiry_Activity.this, Add_NewEnquiryExisting_Activity.class);
                startActivity(intent);
                btnAddEnquiry.collapse();

            }
        });

        EnquiryNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isaddnewQnuiry = true;
                Intent intent = new Intent(Enquiry_Activity.this, Add_NewEnquiry_Acitivity.class);
                startActivity(intent);
                btnAddEnquiry.collapse();

            }
        });


        etSearch.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    etSearch.setText("");
                    etSearch.setVisibility(View.GONE);
                   // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                   // getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                if (rvEnquiry != null) {
                    rvEnquiry.setAdapter(new AllEnquiryAdapter(activity, Searchlist));
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
                } else if (rvEnquiry != null) {
                    Searchlist.clear();
                    Searchlist.addAll(allenquiryList);
                    rvEnquiry.setAdapter(new AllEnquiryAdapter(activity, Searchlist));
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
                    getAllEnquirylist();


                } else {
                    hideSwipeRefresh();
                }

            }
        });

        getAllEnquirylist();
    }

    private void performSearch(String text, boolean showMsg) {

        Searchlist.clear();
        if (allenquiryList != null) {
            for (int i = 0; i < allenquiryList.size(); i++) {
                if (allenquiryList.get(i).getCustomerName() != null && !allenquiryList.get(i).getCustomerName().equals("null") &&
                        allenquiryList.get(i).getSalesPerson() != null && !allenquiryList.get(i).getSalesPerson().equals("null") &&
                        allenquiryList.get(i).getCity() != null && !allenquiryList.get(i).getCity().equals("null") &&
                        allenquiryList.get(i).getRegion() != null && !allenquiryList.get(i).getRegion().equals("null"))  {

                    if (allenquiryList.get(i).getCustomerName().replace(" ","").toLowerCase().contains(text.toLowerCase()) ||
                            allenquiryList.get(i).getUnique_id().replace(" ","").toLowerCase().contains(text.toLowerCase())||
                            allenquiryList.get(i).getCity().replace(" ","").toLowerCase().contains(text.toLowerCase()) ||
                            allenquiryList.get(i).getSalesPerson().replace(" ","").toLowerCase().contains(text.toLowerCase()) ||
                            allenquiryList.get(i).getRegion().replace(" ","")
                                    .toLowerCase().contains(text.toLowerCase())) {
                        Searchlist.add(allenquiryList.get(i));

                    }
                    // Utils.showLog("===  Searchlist.size() " + Searchlist.size());
                }
            }

            if (Searchlist.size() == 0 && showMsg) {
              //  Utils.showToast(activity, "No result found!", R.color.red);
            }
            if (rvEnquiry != null) {
                rvEnquiry.setAdapter(new AllEnquiryAdapter(activity, Searchlist));
            }

        }
    }

    public void getAllEnquirylist() {

        //   progressBar.setVisibility(View.VISIBLE);
        showPrd();

        final Call<AllEnquiry> enquiryCall = webapi.allenquiry(SharedPrefsUtils.getSharedPreferenceString(this, SharedPrefsUtils.USER_ID));

        // Utils.showToast(context, "user" + SharedPrefsUtils.getSharedPreferenceString(this, SharedPrefsUtils.USER_ID));

        enquiryCall.enqueue(new Callback<AllEnquiry>() {
            @Override
            public void onResponse(Call<AllEnquiry> call, Response<AllEnquiry> response) {

                if (response.body() != null) {

                    hidePrd();
                    if (response.body().getStatus() == 1) {
                        hidePrd();
                        //progressBar.setVisibility(View.GONE);
                        allenquiryList.clear();
                        Searchlist.clear();
                        Searchlist.addAll(response.body().getResult());
                        allenquiryList.addAll(response.body().getResult());
                        rvEnquiry.setAdapter(new AllEnquiryAdapter(Enquiry_Activity.this, Searchlist));


                    } else {
                        Utils.showToast(getApplicationContext(), response.body().getMessage(), R.color.msg_fail);
                        //progressBar.setVisibility(View.GONE);
                    }
                    hideSwipeRefresh();

                } else {
                    Utils.showErrorToast(getApplicationContext());
                    hidePrd();
                    //progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AllEnquiry> call, Throwable t) {

                hideSwipeRefresh();
                hidePrd();
                //progressBar.setVisibility(View.GONE);
                Utils.showToast(context, "Data Not Found", R.color.red_dark);

            }
        });


    }


 /*   @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        // progressDialog.dismiss();
        return true;
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        // hidePrd();
        // getAllEnquirylist();
        // rvEnquiry.scrollToPosition(Searchlist.size());


        if (isaddnewQnuiry) {
            // isaddnewQnuiry = true;
            Utils.showLog("==isaddnewQnuiry" + isaddnewQnuiry);

            getAllEnquirylist();


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


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePrd();
    }
}
