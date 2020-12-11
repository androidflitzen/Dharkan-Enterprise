package com.dharkanenquiry.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dharkanenquiry.Model.AllCustomerList;
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

public class Customer_Details_Activity extends AppCompatActivity {

    //AllCustomerList.Result customerlist;

    List<AllCustomerList.Result> customerlist = new ArrayList<>();

    ProgressDialog progressDialog;
    Context context;
    WebApi webapi;
    @BindView(R.id.ivBackCustomer)
    ImageView ivBackCustomer;
    @BindView(R.id.tvCustomerTitle)
    TextView tvCustomerTitle;
    @BindView(R.id.ivEditCustomerDeatil)
    ImageView ivEditCustomerDeatil;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvCustomerName)
    TextView tvCustomerName;
    @BindView(R.id.tvCustomerEmail)
    TextView tvCustomerEmail;
    @BindView(R.id.tvCustomerPostcode)
    TextView tvCustomerPostcode;
    @BindView(R.id.tvcustomerPhn)
    TextView tvcustomerPhn;
    @BindView(R.id.tvCustomerlandlineNo)
    TextView tvCustomerlandlineNo;
    @BindView(R.id.tvCustomerCity)
    TextView tvCustomerCity;
    @BindView(R.id.tvCustomerState)
    TextView tvCustomerState;
    @BindView(R.id.tvCustomrAddress)
    TextView tvCustomrAddress;
    @BindView(R.id.tvCustomerCategory)
    TextView tvCustomerCategory;
    @BindView(R.id.tvCustomerRegion)
    TextView tvCustomerRegion;
    @BindView(R.id.tvCustomerAssignUser)
    TextView tvCustomerAssignUser;
    @BindView(R.id.tvCustomerGSTNo)
    TextView tvCustomerGSTNo;
    @BindView(R.id.tvCustomerCreditDay)
    TextView tvCustomerCreditDay;
    @BindView(R.id.tvCustomerCreditLimit)
    TextView tvCustomerCreditLimit;
    @BindView(R.id.tvCustomrOpeningBalance)
    TextView tvCustomrOpeningBalance;
    @BindView(R.id.cardDetails1)
    CardView cardDetails1;

    String custmoreid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__details_);
        ButterKnife.bind(this);

        InitUi();
    }

    private void InitUi() {

        context = Customer_Details_Activity.this;

        webapi = Utils.getRetrofitClient().create(WebApi.class);


        custmoreid = getIntent().getStringExtra("customer_id");

        ivBackCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Customer_Details_Activity.this, Customerlist_Activiy.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


                // onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);


        ivEditCustomerDeatil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Customer_Details_Activity.this, EditCustomer_details_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("customer_id", customerlist.get(0).getCustomerId());
                intent.putExtra("customername", customerlist.get(0).getCustomerName());
                intent.putExtra("customercatdepid", customerlist.get(0).getCustomer_category_id());
                intent.putExtra("customercatdepname", customerlist.get(0).getCustomer_category_name());
                intent.putExtra("address", customerlist.get(0).getAddress());
                intent.putExtra("postcode", customerlist.get(0).getPostCode());
                intent.putExtra("cityname", customerlist.get(0).getCity());
                intent.putExtra("statename", customerlist.get(0).getState());
                intent.putExtra("regioname", customerlist.get(0).getReg_name());
                intent.putExtra("regionid", customerlist.get(0).getRegId());
                intent.putExtra("assignusername", customerlist.get(0).getAssignUsers());
                intent.putExtra("assignuserid", customerlist.get(0).getAssignUsersId());
                intent.putExtra("gstno", customerlist.get(0).getGstNo());
                intent.putExtra("openingbalance", customerlist.get(0).getOpeningBalance());
                intent.putExtra("creditday", customerlist.get(0).getCreditDays());
                intent.putExtra("creditlimit", customerlist.get(0).getCreditLimit());
                intent.putExtra("phoneno", customerlist.get(0).getPhoneNo());
                intent.putExtra("landline", customerlist.get(0).getLandlineNo());
                intent.putExtra("emailid", customerlist.get(0).getEmail());


                startActivity(intent);

            }
        });


        tvcustomerPhn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tvcustomerPhn.getText().toString()));
                context.startActivity(intent);
            }
        });

        tvCustomerlandlineNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tvCustomerlandlineNo.getText().toString()));
                context.startActivity(intent);
            }
        });

        tvCustomerEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Sendtomail = customerlist.get(0).getEmail();
                openMail(Sendtomail);
            }
        });


        try {
            getAllCustomerlist(custmoreid);
        } catch (Exception e) {
            Log.e("getAllCustomerlist", e.getMessage());
        }
    }

    private void openMail(String Sendtomail) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Sendtomail));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(Intent.createChooser(emailIntent, "Choose..."));
    }


    public void getAllCustomerlist(String custmoreid) {

        showPrd();

        Call<AllCustomerList> allCustomerListCall = webapi.getcustomerdetailsapi(custmoreid);
        allCustomerListCall.enqueue(new Callback<AllCustomerList>() {
            @Override
            public void onResponse(Call<AllCustomerList> call, Response<AllCustomerList> response) {

                if (response.body() != null) {

                    hidePrd();

                    if (response.body().getStatus() == 1) {

                        for (int i = 0; i < response.body().getResult().size(); i++) {
                            customerlist.add(response.body().getResult().get(i));
                        }

                        tvCustomerTitle.setText(customerlist.get(0).getCustomerName());


                        if (customerlist.get(0).getCustomerName().equals("")) {
                            tvCustomerName.setText("-----");
                        } else {
                            tvCustomerName.setText(customerlist.get(0).getCustomerName());
                        }
                        if (customerlist.get(0).getEmail().equals("")) {
                            tvCustomerEmail.setText("-----");
                        } else {
                            tvCustomerEmail.setText(customerlist.get(0).getEmail());
                        }
                        if (customerlist.get(0).getPostCode().equals("")) {
                            tvCustomerPostcode.setText("-----");
                        } else {
                            tvCustomerPostcode.setText(customerlist.get(0).getPostCode());
                        }
                        if (customerlist.get(0).getPhoneNo().equals("")) {
                            tvcustomerPhn.setText("-----");
                        } else {
                            tvcustomerPhn.setText(customerlist.get(0).getPhoneNo());
                        }
                        if (customerlist.get(0).getLandlineNo().equals("")) {
                            tvCustomerlandlineNo.setText("-----");
                        } else {
                            tvCustomerlandlineNo.setText(customerlist.get(0).getLandlineNo());
                        }
                        if (customerlist.get(0).getCity().equals("")) {
                            tvCustomerCity.setText("-----");
                        } else {
                            tvCustomerCity.setText(customerlist.get(0).getCity());
                        }
                        try {
                            if (customerlist.get(0).getReg_name().equals("")) {
                                tvCustomerRegion.setText("-----");
                            } else {
                                tvCustomerRegion.setText(customerlist.get(0).getReg_name());
                            }
                        } catch (Exception e) {
                            Log.e("getReg_name", e.getMessage());
                        }
                        if (customerlist.get(0).getState().equals("")) {
                            tvCustomerState.setText("-----");
                        } else {
                            tvCustomerState.setText(customerlist.get(0).getState());
                        }
                        if (customerlist.get(0).getAddress().equals("")) {
                            tvCustomrAddress.setText("-----");
                        } else {
                            tvCustomrAddress.setText(customerlist.get(0).getAddress());
                        }

                        if (customerlist.get(0).getCustomer_category_name().equals("")) {
                            tvCustomerCategory.setText("-----");
                        } else {
                            tvCustomerCategory.setText(customerlist.get(0).getCustomer_category_name());
                        }

                        if (customerlist.get(0).getAssignUsers().equals("")) {
                            tvCustomerAssignUser.setText("-----");
                        } else {
                            tvCustomerAssignUser.setText(customerlist.get(0).getAssignUsers());
                        }

                        if (customerlist.get(0).getGstNo().equals("")) {
                            tvCustomerGSTNo.setText("-----");
                        } else {
                            tvCustomerGSTNo.setText(customerlist.get(0).getGstNo());
                        }

                        if (customerlist.get(0).getCreditDays().equals("")) {
                            tvCustomerCreditDay.setText("-----");
                        } else {
                            tvCustomerCreditDay.setText(customerlist.get(0).getCreditDays());
                        }

                        if (customerlist.get(0).getCreditLimit().equals("")) {
                            tvCustomerCreditLimit.setText("-----");
                        } else {
                            tvCustomerCreditLimit.setText(customerlist.get(0).getCreditLimit());
                        }

                        if (customerlist.get(0).getOpeningBalance().equals("")) {
                            tvCustomrOpeningBalance.setText("-----");
                        } else {
                            tvCustomrOpeningBalance.setText(customerlist.get(0).getOpeningBalance());
                        }


                    } else {
                        hidePrd();
                        Utils.showToast(getApplicationContext(), response.body().getMessage(), R.color.msg_fail);
                    }

                } else {
                    Utils.showToast(getApplicationContext(), response.body().getMessage(), R.color.msg_fail);
                    hidePrd();
                }

            }

            @Override
            public void onFailure(Call<AllCustomerList> call, Throwable t) {

                hidePrd();
                //progressBar.setVisibility(View.GONE);
                Utils.showToast(context, "Data Not Found", R.color.red_dark);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            getAllCustomerlist(custmoreid);
        } catch (Exception e) {
            Log.e("OnResume", e.getMessage());
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
