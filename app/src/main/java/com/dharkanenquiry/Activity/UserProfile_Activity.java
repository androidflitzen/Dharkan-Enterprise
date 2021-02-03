package com.dharkanenquiry.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dharkanenquiry.Adapter.EnquiriesActionlist_Adapter;
import com.dharkanenquiry.Model.AllEnquiry;
import com.dharkanenquiry.Model.EnquiriesActionList;
import com.dharkanenquiry.Model.UserDetails;
import com.dharkanenquiry.utils.FileUtils;
import com.dharkanenquiry.utils.Network;
import com.dharkanenquiry.utils.Permission;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.BuildConfig;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile_Activity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvUserCityName)
    TextView tvUserCityName;
    @BindView(R.id.tvUserPhoneno)
    TextView tvUserPhoneno;
    @BindView(R.id.tvUsermailId)
    TextView tvUsermailId;
    @BindView(R.id.tvUserAddress)
    TextView tvUserAddress;
    @BindView(R.id.tvUniqId)
    TextView tvUniqId;
    @BindView(R.id.tvUserProduct)
    TextView tvUserProduct;
    @BindView(R.id.tvUserApproch)
    TextView tvUserApproch;
    @BindView(R.id.tvUserImportance)
    TextView tvUserImportance;
    @BindView(R.id.tvUserEnqrSource)
    TextView tvUserenqrSource;
    @BindView(R.id.tvUserEnqrCategory)
    TextView tvUserenqrCategory;
    @BindView(R.id.tvUserOtherNotes)
    TextView tvUserOtherNotes;

    Context context;
    @BindView(R.id.tvCloseReason)
    TextView tvCloseReason;
    @BindView(R.id.liEnquiry)
    LinearLayout liEnquiry;
    @BindView(R.id.cardClose)
    CardView cardClose;

    WebApi webapi;
    UserDetails userDetailsModel;

    @BindView(R.id.progress_bar)
    SpinKitView progressBar;
    @BindView(R.id.cardDetails1)
    CardView cardDetails1;
    @BindView(R.id.linear3)
    LinearLayout linear3;
    @BindView(R.id.linear4)
    LinearLayout linear4;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.linear2)
    LinearLayout linear2;
    @BindView(R.id.linear5)
    LinearLayout linear5;
    @BindView(R.id.cardDetails2)
    CardView cardDetails2;
    @BindView(R.id.ivEditEnquiry)
    ImageView ivEditEnquiry;

    String customername = "";
    @BindView(R.id.tvsalepersoname)
    TextView tvsalepersoname;

    @BindView(R.id.tvCreateOn)
    TextView tvCreateOn;
    @BindView(R.id.linear11)
    LinearLayout linear11;
    @BindView(R.id.tvEnquirystatus)
    TextView tvEnquirystatus;
    @BindView(R.id.linear22)
    LinearLayout linear22;
    @BindView(R.id.tvEnquiryCatagory)
    TextView tvEnquiryCatagory;
    @BindView(R.id.linear55)
    LinearLayout linear55;
    @BindView(R.id.cardDetails3)
    CardView cardDetails3;


    List<AllEnquiry.Result> allenquiryList;

    @BindView(R.id.tvRequeststatus)
    TextView tvRequeststatus;
    @BindView(R.id.ivRequeststatusPdf)
    ImageView ivRequeststatusPdf;
    @BindView(R.id.tvOrderstatus)
    TextView tvOrderstatus;
    @BindView(R.id.ivOrderstatusPdf)
    ImageView ivOrderstatusPdf;

    int action, selectedPosition;
    String uniqID, customerid;

    @BindView(R.id.btnAddAction)
    Button btnAddAction;
    @BindView(R.id.rvaction)
    RecyclerView rvaction;

    ProgressDialog progressDialog;

    List<EnquiriesActionList.Result> allenquiryActionList = new ArrayList<>();
    @BindView(R.id.tvactionMore)
    TextView tvactionMore;
    @BindView(R.id.tvactionLess)
    TextView tvactionLess;
    @BindView(R.id.ivactionMore)
    ImageView ivactionMore;
    @BindView(R.id.ivactionLess)
    ImageView ivactionLess;
    @BindView(R.id.liactionmore)
    LinearLayout liactionmore;

    ImageView ivBackProfile;
    public boolean ismoreclick = false;

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_);
        ButterKnife.bind(this);

        context = UserProfile_Activity.this;

        initUi();

    }

    private void initUi() {

        setSupportActionBar(toolbar);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setDisplayShowHomeEnabled(true);

        ivBackProfile = (ImageView) findViewById(R.id.ivBackProfile);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        Sprite doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        webapi = Utils.getRetrofitClient().create(WebApi.class);

        final String enquiry_id = getIntent().getStringExtra("enquiry_id");
        final String customername = getIntent().getStringExtra("customer_name");
        final String customerid = getIntent().getStringExtra("customer_id");

        tvTitle.setText(customername);

        getUserDetails(enquiry_id);


        rvaction.setLayoutManager(new LinearLayoutManager(UserProfile_Activity.this, LinearLayoutManager.VERTICAL, false));
        rvaction.setHasFixedSize(true);
        rvaction.setAdapter(new EnquiriesActionlist_Adapter(context, allenquiryActionList, UserProfile_Activity.this));
        rvaction.scrollToPosition(allenquiryActionList.size());


        btnAddAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Add_ActionEnquiries_Activity.class);
                intent.putExtra("enquiry_id", enquiry_id);
                intent.putExtra("customer_id", customerid);

                Utils.showLog("==data"+enquiry_id +"  "+ customerid);
                startActivity(intent);

            }
        });

        ivEditEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditEnquiry_Activity.class);
                intent.putExtra("enquiry_id", enquiry_id);
                intent.putExtra("customer_name", userDetailsModel.getResult().get(0).getCustomerName());
                intent.putExtra("sales_person", userDetailsModel.getResult().get(0).getSalesPerson());
                intent.putExtra("enquiry_for", userDetailsModel.getResult().get(0).getEnquiryproductid());
                intent.putExtra("enquiry_for_name", userDetailsModel.getResult().get(0).getProducts());
                intent.putExtra("enquiry_source", userDetailsModel.getResult().get(0).getEnquirysourceid());
                intent.putExtra("enquiry_source_name", userDetailsModel.getResult().get(0).getSource());
                intent.putExtra("enquiry_importance", userDetailsModel.getResult().get(0).getImportance());
                intent.putExtra("enquiry_category", userDetailsModel.getResult().get(0).getEnquirycategoryid());
                intent.putExtra("enquiry_category_name", userDetailsModel.getResult().get(0).getEnquiryCategory());
                intent.putExtra("enquiry_other_details", userDetailsModel.getResult().get(0).getOtherDetails());
                startActivity(intent);
            }
        });


        tvUserPhoneno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tvUserPhoneno.getText().toString()));
                context.startActivity(intent);
            }
        });

        ivBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void getUserDetails(String enquiry_id) {
        showPrd();
        //progressBar.setVisibility(View.VISIBLE);

        final Call<UserDetails> userDetailsCall = webapi.getuserdetails(enquiry_id);
        userDetailsCall.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {

                if (response.body() != null) {
                    hidePrd();
                    userDetailsModel = response.body();
                    if (userDetailsModel.getStatus() == 1) {


                        if (userDetailsModel.getResult().get(0).getClosedReason() != null && !userDetailsModel.getResult().get(0).getClosedReason().equals("")) {
                            cardClose.setVisibility(View.VISIBLE);
                            tvCloseReason.setText(userDetailsModel.getResult().get(0).getClosedReason());
                        } else {
                            cardClose.setVisibility(View.GONE);
                        }

                        tvUsername.setText(userDetailsModel.getResult().get(0).getCustomerName());
                        tvUniqId.setText(userDetailsModel.getResult().get(0).getUniqueId());
                        tvUserCityName.setText(userDetailsModel.getResult().get(0).getCity());
                        tvUserPhoneno.setText(userDetailsModel.getResult().get(0).getPhoneno());
                        tvUsermailId.setText(userDetailsModel.getResult().get(0).getEmail());
                        tvUserAddress.setText(userDetailsModel.getResult().get(0).getAddress());
                        tvUserProduct.setText(userDetailsModel.getResult().get(0).getProducts());
                        tvUserApproch.setText(userDetailsModel.getResult().get(0).getEnquiryApproch());
                        tvUserImportance.setText(userDetailsModel.getResult().get(0).getImportance());
                        tvUserenqrSource.setText(userDetailsModel.getResult().get(0).getSource());
                        tvUserenqrCategory.setText(userDetailsModel.getResult().get(0).getEnquiryCategory());
                        tvUserOtherNotes.setText(userDetailsModel.getResult().get(0).getOtherDetails());
                        tvsalepersoname.setText(userDetailsModel.getResult().get(0).getSalesPerson());
                        tvCreateOn.setText(userDetailsModel.getResult().get(0).getAddedOn());
                        tvEnquirystatus.setText(userDetailsModel.getResult().get(0).getEnquiryStatus());
                        tvEnquiryCatagory.setText(userDetailsModel.getResult().get(0).getEnquiryCategory());

                        if (userDetailsModel.getResult().get(0).getQuotationRequestStatus().equals("0")) {
                            tvRequeststatus.setText("Request a Quotation");
                        } else if (userDetailsModel.getResult().get(0).getQuotationRequestStatus().equals("1")) {
                            tvRequeststatus.setText("Quotation Request Sent");

                        } else if (userDetailsModel.getResult().get(0).getQuotationRequestStatus().equals("2")) {
                            if (userDetailsModel.getResult().get(0).getQuotationUrl() != null && !userDetailsModel.getResult().get(0).getQuotationUrl().equals("")) {
                                ivRequeststatusPdf.setVisibility(View.VISIBLE);
                                tvRequeststatus.setVisibility(View.GONE);
                            }
                        }

                        //holder.tvRequeststatus.setText(allenquiryList.get(position).getQuotationRequestStatus());
                        if (userDetailsModel.getResult().get(0).getOrderRequestStatus().equals("0")) {
                            tvOrderstatus.setText("Request a Order");
                        } else if (userDetailsModel.getResult().get(0).getOrderRequestStatus().equals("1")) {
                            tvOrderstatus.setText("Order Request Sent");
                        } else if (userDetailsModel.getResult().get(0).getOrderRequestStatus().equals("2")) {
                            if (userDetailsModel.getResult().get(0).getOrderUrl() != null && !userDetailsModel.getResult().get(0).getOrderUrl().equals("")) {
                                ivOrderstatusPdf.setVisibility(View.VISIBLE);
                                tvOrderstatus.setVisibility(View.GONE);
                            }
                        }


                        ivRequeststatusPdf.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                final AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                if (!Network.isNetworkAvailable(context)) {
                                    return;
                                }
                                action = 0;
                                //  selectedPosition = position;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (Permission.hasPermissions((Activity) context, permissions)) {
                                        downloadFile(action);
                                    } else {
                                        Permission.requestPermissions((Activity) context, permissions);
                                    }
                                } else {
                                    downloadFile(action);
                                }

                            }
                        });


                        ivOrderstatusPdf.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                final AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                if (!Network.isNetworkAvailable(context)) {
                                    return;
                                }
                                action = 0;
                                //  selectedPosition = position;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (Permission.hasPermissions((Activity) context, permissions)) {
                                        downloadFile1(action);
                                    } else {
                                        Permission.requestPermissions((Activity) context, permissions);
                                    }
                                } else {
                                    downloadFile1(action);
                                }

                            }
                        });



                    } else {

                        Utils.showToast(context, "Opps Something Wrong..", R.color.red_dark);
                        hidePrd();
                    }

                } else {

                    Utils.showErrorToast(UserProfile_Activity.this);
                    hidePrd();
                }
                hidePrd();
                //  progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                hidePrd();
                //progressBar.setVisibility(View.GONE);
                Utils.showErrorToast(UserProfile_Activity.this);

            }
        });

    }

    public void getactionlistapi(String enquiry_id) {
        showPrd();

        Call<EnquiriesActionList> enquiriesActionListCall = webapi.getactionlistapi(enquiry_id);
        enquiriesActionListCall.enqueue(new Callback<EnquiriesActionList>() {
            @Override
            public void onResponse(Call<EnquiriesActionList> call, Response<EnquiriesActionList> response) {

                if (response.body() != null) {

                    if (response.body().getStatus() == 1) {

                        allenquiryActionList.clear();
                        allenquiryActionList.addAll(response.body().getResult());
                        rvaction.setAdapter(new EnquiriesActionlist_Adapter(context, allenquiryActionList, UserProfile_Activity.this));



                    } else {
                      //  liactionmore.setVisibility(View.GONE);
                        //  Utils.showToast(context, "Opps somthing went wrong.!", R.color.msg_fail);
                        hidePrd();
                    }

                } else {
                    Utils.showToast(context, "Data Not Found!", R.color.msg_fail);
                    hidePrd();
                }
                hidePrd();
            }

            @Override
            public void onFailure(Call<EnquiriesActionList> call, Throwable t) {
                hidePrd();
                // Utils.showErrorToast(UserProfile_Activity.this);
                Utils.showToast(context, "Please check your internet!", R.color.msg_fail);

            }
        });

    }

    private void downloadFile1(int action) {

        // Utils.showLog("==== downloadFile1");
        uniqID = userDetailsModel.getResult().get(0).getEnquiryId();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        File myFile = new File(new File(Utils.getItemDir()), userDetailsModel.getResult().get(0).getEnquiryId() + "Order_Status_" + ".pdf");
        try {
            if (myFile.exists()) {
                if (action == 0) {
                    openPDFFile(myFile,userDetailsModel.getResult().get(0).getOrderUrl());
                } else if (action == 1) {
                    sendMailInvoice(myFile);
                }
            } else {
                myFile.createNewFile();
                new DownloadFileFromURL(myFile, action).execute(userDetailsModel.getResult().get(0).getOrderUrl());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadFile(int action) {

        uniqID = userDetailsModel.getResult().get(0).getEnquiryId();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        File myFile = new File(new File(Utils.getItemDir()), userDetailsModel.getResult().get(0).getEnquiryId() + "Request_Status_" + ".pdf");
        try {
            if (myFile.exists()) {
                if (action == 0) {
                    openPDFFile(myFile,userDetailsModel.getResult().get(0).getOrderUrl());
                } else if (action == 1) {
                    sendMailInvoice(myFile);
                }
            } else {
                myFile.createNewFile();
                new DownloadFileFromURL(myFile, action).execute(userDetailsModel.getResult().get(0).getQuotationUrl());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openPDFFile(File myFil,String fileUrl) {

        /*File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.VasudhaEnquiry/" + myFile.getName());
        Utils.showLog("==== openPDFFile " + file.getAbsolutePath());
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setPackage("com.google.android.apps.docs");
        Uri uri = FileUtils.getImageContentUri(context, file);
        target.setDataAndType(uri, "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = Intent.createChooser(target, "Open File");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }*/

       /* Intent defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER);
        defaultBrowser.setData(Uri.parse("https://docs.google.com/gview?embedded=true&url="+fileUrl));
        context.startActivity(defaultBrowser);*/


        Intent intent=new Intent(context, ViewPDF.class);
        //intent.putExtra("url",fileUrl);
        intent.putExtra("MY_FILE",myFil);
        context.startActivity(intent);
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        File myFile;
        int action;

        public DownloadFileFromURL(File myFile, int action) {
            this.myFile = myFile;
            this.action = action;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showPrd();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);
                OutputStream output = new FileOutputStream(myFile);
                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {
            //pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {
            hidePrd();
            if (action == 0) {
                openPDFFile(myFile,file_url);
            } else if (action == 1) {
                sendMailInvoice(myFile);
            }
        }
    }


    private void sendMailInvoice(File myFile) {
        try {
            final Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            //emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"jolly@flitzen.in","darshan@flitzen.co.uk"});
            //emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            //emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Invoice " + uniqID);
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, myFile);
            } else {
                uri = Uri.fromFile(myFile);
            }
            if (uri != null) {
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            }
            context.startActivity(Intent.createChooser(emailIntent, "Sending email..."));
        } catch (Throwable t) {
            Utils.showToast(context, "Request failed try again: " + t.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String enquiry_id = getIntent().getStringExtra("enquiry_id");
        getUserDetails(enquiry_id);
        getactionlistapi(enquiry_id);
    }


    /*@Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        // progressDialog.dismiss();
        return true;
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
