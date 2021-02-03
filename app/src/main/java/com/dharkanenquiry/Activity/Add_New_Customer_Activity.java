package com.dharkanenquiry.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dharkanenquiry.Adapter.AllCategoryDepaement_Spn_Adapter;
import com.dharkanenquiry.Adapter.AllRegion_Spn_Adapter;
import com.dharkanenquiry.Adapter.AllState_Spn_Adapter;
import com.dharkanenquiry.Adapter.AllUserAdapter;
import com.dharkanenquiry.Model.Add_new_Customer;
import com.dharkanenquiry.Model.AllCategoryDeparment;
import com.dharkanenquiry.Model.AllRegion;
import com.dharkanenquiry.Model.AllState;
import com.dharkanenquiry.Model.Users;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_New_Customer_Activity extends AppCompatActivity {

    @BindView(R.id.ivBackAddCustomer)
    ImageView ivBackAddCustomer;
    @BindView(R.id.tvCustomerTitle)
    TextView tvCustomerTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvCustomerName)
    TextView tvCustomerName;
    @BindView(R.id.etCustomernamespn)
    EditText etCustomernamespn;
    @BindView(R.id.rvCustomername)
    RelativeLayout rvCustomername;
    @BindView(R.id.tvCustomerCatDept)
    TextView tvCustomerCatDept;
    @BindView(R.id.tvEnquiryCategorySpn)
    TextView tvEnquiryCategorySpn;
    @BindView(R.id.rvEnquiryCategoryDepspn)
    RelativeLayout rvEnquiryCategoryDepspn;
    @BindView(R.id.tvedtCustomerPostcode)
    TextView tvedtCustomerPostcode;
    @BindView(R.id.etCustomerpostspn)
    EditText etCustomerpostspn;
    @BindView(R.id.rvedtCustomerpostcode)
    RelativeLayout rvedtCustomerpostcode;
    @BindView(R.id.tvedtCustomercity)
    TextView tvedtCustomercity;
    @BindView(R.id.etCustomercityspn)
    EditText etCustomercityspn;
    @BindView(R.id.rvedtCustomercity)
    RelativeLayout rvedtCustomercity;
    @BindView(R.id.tvEnquiryState)
    TextView tvEnquiryState;
    @BindView(R.id.tvStateSpn)
    TextView tvStateSpn;
    @BindView(R.id.rvEnquiryStatespn)
    RelativeLayout rvEnquiryStatespn;
    @BindView(R.id.tvedtcustomerRegion)
    TextView tvedtcustomerRegion;
    @BindView(R.id.tvedtcustomerRegionspn)
    TextView tvedtcustomerRegionspn;
    @BindView(R.id.rvedtRegionSpn)
    RelativeLayout rvedtRegionSpn;
    @BindView(R.id.tvedtAssignUser)
    TextView tvedtAssignUser;
    @BindView(R.id.tvedtAssignUserSpn)
    TextView tvedtAssignUserSpn;
    @BindView(R.id.rvedtAssignspn)
    RelativeLayout rvedtAssignspn;
    @BindView(R.id.tvedtCustomerGSTno)
    TextView tvedtCustomerGSTno;
    @BindView(R.id.etCustomerGSTNospn)
    EditText etCustomerGSTNospn;
    @BindView(R.id.rvedtCustomerGSTno)
    RelativeLayout rvedtCustomerGSTno;
    @BindView(R.id.tvedtCustomerCreditDays)
    TextView tvedtCustomerCreditDays;
    @BindView(R.id.etCustomerCreditDayspn)
    EditText etCustomerCreditDayspn;
    @BindView(R.id.rvedtCustomerCreditDay)
    RelativeLayout rvedtCustomerCreditDay;
    @BindView(R.id.tvedtCustomerCreditLimit)
    TextView tvedtCustomerCreditLimit;
    @BindView(R.id.etCustomerCreditLimitspn)
    EditText etCustomerCreditLimitspn;
    @BindView(R.id.rvedtCustomerCreditLimit)
    RelativeLayout rvedtCustomerCreditLimit;
    @BindView(R.id.tvedtCustomerOpeningBal)
    TextView tvedtCustomerOpeningBal;
    @BindView(R.id.etCustomerOpenBalancespn)
    EditText etCustomerOpenBalancespn;
    @BindView(R.id.rvedtCustomerOpeningBal)
    RelativeLayout rvedtCustomerOpeningBal;
    @BindView(R.id.tvedtCustomerPhnNo)
    TextView tvedtCustomerPhnNo;
    @BindView(R.id.etCustomerPhnNospn)
    EditText etCustomerPhnNospn;
    @BindView(R.id.rvedtCustomerPhnNo)
    RelativeLayout rvedtCustomerPhnNo;
    @BindView(R.id.tvedtCustomerLandline)
    TextView tvedtCustomerLandline;
    @BindView(R.id.etCustomerlandlinespn)
    EditText etCustomerlandlinespn;
    @BindView(R.id.rvedtCustomerlandline)
    RelativeLayout rvedtCustomerlandline;
    @BindView(R.id.tvedtCustomerEmail)
    TextView tvedtCustomerEmail;
    @BindView(R.id.etCustomerEmailspn)
    EditText etCustomerEmailspn;
    @BindView(R.id.rvedtCustomerEmail)
    RelativeLayout rvedtCustomerEmail;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    Context context;
    WebApi webapi;

    ProgressDialog progressDialog;

    List<AllCategoryDeparment.Result> allCategoryDeptitemList = new ArrayList<>();
    List<AllCategoryDeparment.Result> allCategoryDeptitemListTemp = new ArrayList<>();

    List<AllState.Result> allstateitemList = new ArrayList<>();
    List<AllState.Result> allstateitemListTemp = new ArrayList<>();

    List<AllRegion.Result> allregionitemList = new ArrayList<>();
    List<AllRegion.Result> allregionitemListTemp = new ArrayList<>();

    List<Users.Result> itemListUser = new ArrayList<>();
    List<Users.Result> itemListUserTemp = new ArrayList<>();

    AllUserAdapter alluseradapter;

    AllCategoryDepaement_Spn_Adapter allCategoryDepaement_spn_adapter;
    AllState_Spn_Adapter allState_spn_adapter;
    AllRegion_Spn_Adapter allRegion_spn_adapter;

    String categorydepid = "", allstateid = "", regionid = "", assignuserid = "", importanceid = "";
    @BindView(R.id.tvedtCustomerAddress)
    TextView tvedtCustomerAddress;
    @BindView(R.id.etCustomerAddressspn)
    EditText etCustomerAddressspn;
    @BindView(R.id.rvedtCustomerAddress)
    RelativeLayout rvedtCustomerAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__new__customer_);
        ButterKnife.bind(this);

        InitUi();
    }

    private void InitUi() {
        context = Add_New_Customer_Activity.this;

        //  setSupportActionBar(toolbar);
        tvCustomerTitle.setText("Add New Customer");
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setDisplayShowHomeEnabled(true);

        webapi = Utils.getRetrofitClient().create(WebApi.class);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        getCategoryDepapi(tvCustomerCatDept,false);
        getAllStateapi(tvStateSpn,false);
        getenquiryregionapi(tvedtcustomerRegion,false);
        getUserApi(tvedtAssignUser,false);

        ivBackAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rvEnquiryCategoryDepspn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getCategoryDepapi(tvCustomerCatDept);
                if(allCategoryDeptitemList.size()>0){
                    enquiryCategoryDepDailog(tvCustomerCatDept);
                }else {
                    getCategoryDepapi(tvCustomerCatDept,true);
                }
            }
        });

        rvEnquiryStatespn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // getAllStateapi(tvStateSpn);
                if(allstateitemList.size()>0){
                    enquirystatedailog(tvStateSpn);
                }else {
                    getAllStateapi(tvStateSpn,true);
                }
            }
        });

        rvedtRegionSpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // getenquiryregionapi(tvedtcustomerRegion);
                if(allregionitemList.size()>0){
                    enquiryRegionDialog(tvedtcustomerRegion);
                }else {
                    getenquiryregionapi(tvedtcustomerRegion,true);
                }
            }
        });

        rvedtAssignspn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getUserApi(tvedtAssignUser);
                if(itemListUser.size()>0){
                    userDialog(tvedtAssignUser);
                }else {
                    getUserApi(tvedtAssignUser,true);
                }
            }
        });

        rvedtCustomerCreditDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final CharSequence[] items = {
                        "30", "45", "60", "90"
                };


                AlertDialog.Builder builder = new AlertDialog.Builder(Add_New_Customer_Activity.this);
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        tvedtCustomerCreditDays.setText(items[item]);
                        // tvImportance1.setTextColor(getResources().getColor(R.color.black));
                        importanceid = tvedtCustomerCreditDays.getText().toString();


                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }


        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCustomernamespn.getText().toString().trim().isEmpty()) {
                    tvCustomerName.setTextColor(getResources().getColor(R.color.red_dark));
                    etCustomernamespn.requestFocus();
                    Utils.showToast(context, "Enter Customer Name", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (tvEnquiryCategorySpn.getText().toString().trim().isEmpty()) {
                    tvCustomerCatDept.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Select Category Department.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (etCustomercityspn.getText().toString().trim().isEmpty()) {
                    tvedtCustomercity.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Enter City Name.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (tvStateSpn.getText().toString().trim().isEmpty()) {
                    tvEnquiryState.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Select State.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (tvedtcustomerRegionspn.getText().toString().trim().isEmpty()) {
                    tvedtcustomerRegion.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Select Region.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (tvedtAssignUserSpn.getText().toString().trim().isEmpty()) {
                    tvedtAssignUser.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Select Assign User.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (etCustomerPhnNospn.getText().toString().trim().isEmpty()) {
                    tvedtCustomerPhnNo.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Enter Whatsapp/Phone No.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (etCustomerEmailspn.getText().toString().trim().isEmpty()) {
                    tvedtCustomerEmail.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Enter Email Address.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }

                showPrd();

                Utils.showLog("===newadd" + etCustomernamespn.getText().toString() + " " + categorydepid + " " + etCustomerAddressspn.getText().toString() +" " + etCustomerpostspn.getText().toString() + " " + etCustomercityspn.getText().toString() + " " + tvStateSpn.getText().toString() + " " +
                        regionid + " " + assignuserid + " " + etCustomerGSTNospn.getText().toString() + " " + etCustomerCreditDayspn.getText().toString() + " " + etCustomerCreditLimitspn.getText().toString() + " " +
                        etCustomerOpenBalancespn.getText().toString() + " " + etCustomerPhnNospn.getText().toString() + " " + etCustomerlandlinespn.getText().toString() + " " + etCustomerEmailspn.getText().toString() + " " + SharedPrefsUtils.getSharedPreferenceString(Add_New_Customer_Activity.this, SharedPrefsUtils.USER_ID));


                Call<Add_new_Customer> add_new_customerCall = webapi.addnewcustomerapi(categorydepid, etCustomernamespn.getText().toString(),etCustomerAddressspn.getText().toString(), etCustomerpostspn.getText().toString(),etCustomercityspn.getText().toString(),tvStateSpn.getText().toString(),regionid,assignuserid,etCustomerGSTNospn.getText().toString(),etCustomerCreditDayspn.getText().toString(),etCustomerCreditLimitspn.getText().toString(),
                        etCustomerOpenBalancespn.getText().toString(),etCustomerPhnNospn.getText().toString(),etCustomerlandlinespn.getText().toString(), etCustomerEmailspn.getText().toString(),"",SharedPrefsUtils.getSharedPreferenceString(Add_New_Customer_Activity.this, SharedPrefsUtils.USER_ID));
                    add_new_customerCall.enqueue(new Callback<Add_new_Customer>() {
                        @Override
                        public void onResponse(Call<Add_new_Customer> call, Response<Add_new_Customer> response) {
                            if (response.body() != null){

                                if (response.body().getStatus() == 1){
                                    hidePrd();
                                    finish();
                                    Utils.showToast(context, "New Customer Add Successful", R.color.green_fed);

                                }else {
                                   // Utils.showToast(context, "oops Something Wrong", R.color.red);
                                    hidePrd();
                                }

                            }else {

                                Utils.showToast(context, "Try Again", R.color.red_dark);
                                hidePrd();
                            }

                            // finish();
                            hidePrd();
                        }

                        @Override
                        public void onFailure(Call<Add_new_Customer> call, Throwable t) {
                            hidePrd();
                            //progressBar.setVisibility(View.GONE);
                            Utils.showErrorToast(Add_New_Customer_Activity.this);
                        }
                    });
            }
        });


    }


    private void getUserApi(final TextView view,boolean checkDialog) {

        if(checkDialog==true){
            showPrd();
        }

        itemListUser.clear();
        itemListUserTemp.clear();

        Call<Users> getallusers = webapi.getallusers();
        getallusers.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.body().getResult() != null) {

                    if (response.body().getStatus() == 1) {
                        hidePrd();
                        itemListUser.addAll(response.body().getResult());
                        itemListUserTemp.addAll(response.body().getResult());
                        //   Utils.showLog("=== onres");

                        Collections.sort(itemListUserTemp, new Comparator<Users.Result>() {
                            @Override
                            public int compare(final Users.Result object1, final Users.Result object2) {
                                return object1.getName().compareTo(object2.getName());
                            }
                        });

                        if(checkDialog==true){
                            userDialog(view);
                        }


                    } else {
                        Utils.showToast(context, "No Data Found", R.color.red_dark);
                    }

                } else {
                    Utils.showToast(context, "Opps Something wrong", R.color.red_dark);
                }
                hidePrd();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                hidePrd();
                if(checkDialog==true){
                    Utils.showToast(context, "Please check your internet", R.color.red_dark);
                }
            }
        });
    }

    private void userDialog(final TextView textView) {

        LayoutInflater localView = LayoutInflater.from(context);
        View promptsView = localView.inflate(R.layout.dialog_spinner, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        final EditText edtSearchLocation = (EditText) promptsView.findViewById(R.id.edt_spn_search);
        final RecyclerView list_location = (RecyclerView) promptsView.findViewById(R.id.rv_spn);
        final Button btncancle = (Button) promptsView.findViewById(R.id.btnCancle);
        final Button btnsubmit = (Button) promptsView.findViewById(R.id.btnOhk);
        final TextView tvDailogTitle = (TextView) promptsView.findViewById(R.id.tvDailogTitle);

        final ArrayList<String> arrayListTemp = new ArrayList<>();
        final ArrayList<String> arrayListId = new ArrayList<>();

        tvDailogTitle.setText("Assign Users");

        edtSearchLocation.setHint("Search user");
        for (int i = 0; i < itemListUser.size(); i++) {
            arrayListTemp.add(itemListUser.get(i).getName());
            arrayListId.add(itemListUser.get(i).getUserId());
        }

        itemListUserTemp.clear();
        itemListUserTemp.addAll(itemListUser);

        alluseradapter = new AllUserAdapter(context, itemListUserTemp);
        list_location.setLayoutManager(new LinearLayoutManager(Add_New_Customer_Activity.this, LinearLayoutManager.VERTICAL, false));
        list_location.setAdapter(alluseradapter);

        /*alluseradapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                userid = arrayListId.get(position);

                // Utils.showLog("custmerid" + customerid);
                tvTaskAssignName.setText(arrayListTemp.get(position));
                alertDialog.dismiss();
            }
        });*/

        alluseradapter.setOnItemClickListener(new AllUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                assignuserid = itemListUserTemp.get(position).getUserId();

                // Utils.showLog("userId"+userid);
                tvedtAssignUser.setTextColor(getResources().getColor(R.color.black));
                tvedtAssignUserSpn.setText(itemListUserTemp.get(position).getName());
                alertDialog.dismiss();
            }
        });


        edtSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int position, int i1, int i2) {

                if (edtSearchLocation.getText().toString().trim().length() > 0) {
                    itemListUserTemp.clear();
                    // arrayListId.clear();

                    for (int j = 0; j < itemListUser.size(); j++) {
                        String word = edtSearchLocation.getText().toString().toLowerCase();
                        if (itemListUser.get(j).getName().toLowerCase().contains(word)) {
                            itemListUserTemp.add(itemListUser.get(j));
                            // arrayListId.add(itemListUser.get(j).getUserId());
                        }
                    }

                    alluseradapter.notifyDataSetChanged();
                    // list_location.setAdapter(new AllUserAdapter(context, itemListUserTemp));
                } else {
                    arrayListTemp.clear();
                    arrayListId.clear();
                    for (int i = 0; i < itemListUser.size(); i++) {
                        itemListUserTemp.add(itemListUser.get(i));
                        //arrayListId.add(itemListUser.get(i).getUserId());
                    }

                    alluseradapter.notifyDataSetChanged();
                    //list_location.setAdapter(new AllUserAdapter(context, itemListUserTemp));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

     /*   list_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                textView.setText(arrayListTemp.get(position));
                textView.setTag(arrayListId.get(position));
                cust_id = arrayListId.get(position);
                //alertDialog.dismiss();

            }
        });*/


        alertDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = alertDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

    }

    private void getenquiryregionapi(final TextView view,boolean checkDialog) {

        if(checkDialog==true){
            showPrd();
        }
        //  progressBar.setVisibility(View.VISIBLE);
        allregionitemList.clear();
        allregionitemListTemp.clear();

        Call<AllRegion> enquiryCategoryCall = webapi.getallregionapi();
        enquiryCategoryCall.enqueue(new Callback<AllRegion>() {
            @Override
            public void onResponse(Call<AllRegion> call, Response<AllRegion> response) {
                if (response.body().getResult() != null) {

                    if (response.body().getStatus() == 1) {
                        hidePrd();
                        allregionitemList.addAll(response.body().getResult());
                        allregionitemListTemp.addAll(response.body().getResult());

                        Collections.sort(allregionitemListTemp, new Comparator<AllRegion.Result>() {
                            @Override
                            public int compare(final AllRegion.Result object1, final AllRegion.Result object2) {
                                return object1.getRegionName().compareTo(object2.getRegionName());
                            }
                        });

                        if(checkDialog==true){
                            enquiryRegionDialog(view);
                        }

                    } else {
                        Utils.showToast(context, "No Data Found", R.color.red_dark);
                        hidePrd();
                    }

                } else {
                    Utils.showToast(context, "Opps Something wrong", R.color.red_dark);
                }
                hidePrd();
                // progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AllRegion> call, Throwable t) {
                hidePrd();
                //  progressBar.setVisibility(View.GONE);
                if(checkDialog==true){
                    Utils.showToast(context, "Please check your internet", R.color.red_dark);
                }
            }
        });


    }

    private void enquiryRegionDialog(final TextView textView) {

        LayoutInflater localView = LayoutInflater.from(context);
        View promptsView = localView.inflate(R.layout.dialog_spinner, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        final EditText edtSearchLocation = (EditText) promptsView.findViewById(R.id.edt_spn_search);
        final RecyclerView list_location = (RecyclerView) promptsView.findViewById(R.id.rv_spn);
        final Button btncancle = (Button) promptsView.findViewById(R.id.btnCancle);
        final Button btnsubmit = (Button) promptsView.findViewById(R.id.btnOhk);
        final TextView tvDailogTitle = (TextView) promptsView.findViewById(R.id.tvDailogTitle);

        final ArrayList<String> arrayListTemp = new ArrayList<>();
        final ArrayList<String> arrayListId = new ArrayList<>();

        edtSearchLocation.setVisibility(View.VISIBLE);

        tvDailogTitle.setText("Region");


        edtSearchLocation.setHint("Search Enquiry Region");

        // Utils.showLog("searchlist"+arrayListTemp);

        for (int i = 0; i < allregionitemList.size(); i++) {
            arrayListTemp.add(allregionitemList.get(i).getRegionName());
            arrayListId.add(allregionitemList.get(i).getRegionId());
        }

        allregionitemListTemp.clear();
        allregionitemListTemp.addAll(allregionitemList);

        allRegion_spn_adapter = new AllRegion_Spn_Adapter(context, allregionitemListTemp);

        list_location.setAdapter(new AllRegion_Spn_Adapter(context, allregionitemListTemp));

        list_location.setLayoutManager(new LinearLayoutManager(Add_New_Customer_Activity.this, LinearLayoutManager.VERTICAL, false));
        list_location.setAdapter(allRegion_spn_adapter);

        allRegion_spn_adapter.setOnItemClickListener(new AllRegion_Spn_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                regionid = allregionitemListTemp.get(position).getRegionId();
                // Utils.showLog("categoryid" + categoryid);
                tvedtcustomerRegion.setTextColor(getResources().getColor(R.color.black));
                tvedtcustomerRegionspn.setText(allregionitemListTemp.get(position).getRegionName());
                alertDialog.dismiss();
            }
        });

/*
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // List<EnquiryCategory.Result> selectedlist = enqryCategory_spn_adapter.getSelectedlist();
                alertDialog.dismiss();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<EnquiryCategory.Result> selectedlist = enqryCategory_spn_adapter.getSelectedlist();

                alertDialog.dismiss();
                //   Utils.showToast(context, "List" + selectedlist);
                // Toast.makeText(getApplicationContext(), "Items selected are: " + Arrays.toString(selectedlist.toArray()), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < selectedlist.size(); i++) {

                    selectedcategorylist = selectedlist.get(i).getEcName();

                    tvEnquiryCatagory.setText(tvEnquiryCatagory.getText() + selectedcategorylist + " ,");

                }

                for (int i = 0; i < selectedlist.size(); i++) {

                    categoryidlist.add(selectedlist.get(i).getEcId());



                }

            }
        });*/


        edtSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int position, int i1, int i2) {

                if (edtSearchLocation.getText().toString().trim().length() > 0) {
                    allregionitemListTemp.clear();
                    // arrayListId.clear();

                    for (int j = 0; j < allregionitemList.size(); j++) {
                        String word = edtSearchLocation.getText().toString().toLowerCase();
                        if (allregionitemList.get(j).getRegionName().toLowerCase().contains(word)) {
                            allregionitemListTemp.add(allregionitemList.get(j));
                            //arrayListId.add(itemListEnquiryCategory.get(j).getEcId());
                        }
                    }
                    allRegion_spn_adapter.notifyDataSetChanged();
                    // list_location.setAdapter(new AllRegion_Spn_Adapter(context, allregionitemListTemp));
                } else {
                    arrayListTemp.clear();
                    arrayListId.clear();
                    for (int i = 0; i < allregionitemList.size(); i++) {
                        allregionitemListTemp.add(allregionitemList.get(i));
                        // arrayListId.add(itemListEnquiryCategory.get(i).getEcId());
                    }
                    allRegion_spn_adapter.notifyDataSetChanged();
                    // list_location.setAdapter(new AllRegion_Spn_Adapter(context, allregionitemListTemp));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

       /* list_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                textView.setText(arrayListTemp.get(position));
                textView.setTag(arrayListId.get(position));
                cust_id = arrayListId.get(position);
                //alertDialog.dismiss();

            }
        });*/

        alertDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = alertDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

    }


    private void getAllStateapi(final TextView view,boolean checkDialog) {

        if(checkDialog==true){
            showPrd();
        }

        allstateitemList.clear();
        allstateitemListTemp.clear();

        Call<AllState> allStateCall = webapi.getallstateapi();
        allStateCall.enqueue(new Callback<AllState>() {
            @Override
            public void onResponse(Call<AllState> call, Response<AllState> response) {
                if (response.body().getResult() != null) {

                    if (response.body().getStatus() == 1) {
                        hidePrd();
                        allstateitemList.addAll(response.body().getResult());
                        allstateitemListTemp.addAll(response.body().getResult());

                        Collections.sort(allstateitemListTemp, new Comparator<AllState.Result>() {
                            @Override
                            public int compare(final AllState.Result object1, final AllState.Result object2) {
                                return object1.getStateName().compareTo(object2.getStateName());
                            }
                        });

                        if(checkDialog==true){
                            enquirystatedailog(view);
                        }

                    } else {
                        Utils.showToast(context, "No Data Found", R.color.red_dark);
                        hidePrd();
                    }

                } else {
                    Utils.showToast(context, "Opps Something wrong", R.color.red_dark);
                    hidePrd();
                }
                hidePrd();
            }

            @Override
            public void onFailure(Call<AllState> call, Throwable t) {
                hidePrd();
                if(checkDialog==true){
                    Utils.showToast(context, "No Internet Connection", R.color.red_dark);
                }
            }
        });


    }

    private void enquirystatedailog(final TextView textView) {

        LayoutInflater localView = LayoutInflater.from(context);
        View promptsView = localView.inflate(R.layout.dialog_spinner, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        final EditText edtSearchLocation = (EditText) promptsView.findViewById(R.id.edt_spn_search);
        final RecyclerView list_location = (RecyclerView) promptsView.findViewById(R.id.rv_spn);
        final Button btncancle = (Button) promptsView.findViewById(R.id.btnCancle);
        final Button btnsubmit = (Button) promptsView.findViewById(R.id.btnOhk);
        final TextView tvDailogTitle = (TextView) promptsView.findViewById(R.id.tvDailogTitle);

        final ArrayList<String> arrayListTemp = new ArrayList<>();
        final ArrayList<String> arrayListId = new ArrayList<>();

        edtSearchLocation.setVisibility(View.VISIBLE);
        tvDailogTitle.setText("States");

        edtSearchLocation.setHint("Search State");
        for (int i = 0; i < allstateitemList.size(); i++) {
            arrayListTemp.add(allstateitemList.get(i).getStateName());
            arrayListId.add(allstateitemList.get(i).getStateName());
        }

        allstateitemListTemp.clear();
        allstateitemListTemp.addAll(allstateitemList);

        allState_spn_adapter = new AllState_Spn_Adapter(context, allstateitemListTemp);
        list_location.setLayoutManager(new LinearLayoutManager(Add_New_Customer_Activity.this, LinearLayoutManager.VERTICAL, false));
        list_location.setAdapter(allState_spn_adapter);

        allState_spn_adapter.setOnItemClickListener(new AllState_Spn_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                allstateid = allstateitemListTemp.get(position).getStateName();
                // Utils.showLog("sourcid" + sourceid);
                tvEnquiryState.setTextColor(getResources().getColor(R.color.black));
                tvStateSpn.setText(allstateitemListTemp.get(position).getStateName());
                alertDialog.dismiss();
            }
        });

/*
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<EnquirySource.Result> selectedlist = enqrySource_spn_adapter.getSelectedlist();
                alertDialog.dismiss();
                //Utils.showToast(context,"list"+selectedlist);
                for (int i = 0; i < selectedlist.size(); i++) {

                    selectedcategorylist = selectedlist.get(i).getEsName();
                    // sourceidlist.add(selectedlist.get(i).getEsId());
                    //  Utils.showLog("sourceid"+sourceidlist);
                    tvEnquirySource.setText(tvEnquirySource.getText() + selectedcategorylist + " ,");

                }
            }
        });*/


        edtSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int position, int i1, int i2) {

                if (edtSearchLocation.getText().toString().trim().length() > 0) {
                    allstateitemListTemp.clear();
                    // arrayListId.clear();

                    for (int j = 0; j < allstateitemList.size(); j++) {
                        String word = edtSearchLocation.getText().toString().toLowerCase();
                        if (allstateitemList.get(j).getStateName().toLowerCase().contains(word)) {
                            allstateitemListTemp.add(allstateitemList.get(j));
                            //  arrayListId.add(itemListEnquirySource.get(j).getEsId());
                        }
                    }
                    allState_spn_adapter.notifyDataSetChanged();
                    //list_location.setAdapter(new EnqrySource_Spn_Adapter_new(context, itemListEnquirySourceTemp));
                } else {
                    arrayListTemp.clear();
                    arrayListId.clear();
                    for (int i = 0; i < allstateitemList.size(); i++) {
                        allstateitemListTemp.add(allstateitemList.get(i));
                        //arrayListId.add(itemListEnquirySource.get(i).getEsId());
                    }
                    allState_spn_adapter.notifyDataSetChanged();
                    // list_location.setAdapter(new EnqrySource_Spn_Adapter_new(context, itemListEnquirySourceTemp));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

      /*  list_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                textView.setText(arrayListTemp.get(position));
                textView.setTag(arrayListId.get(position));
                cust_id = arrayListId.get(position);
                //alertDialog.dismiss();

            }
        });*/


        alertDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = alertDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }


    private void getCategoryDepapi(final TextView view,boolean checkDialog) {
        showPrd();

        allCategoryDeptitemList.clear();
        allCategoryDeptitemListTemp.clear();

        Call<AllCategoryDeparment> allCategoryDeparmentCall = webapi.getallCategoryDeparment();
        allCategoryDeparmentCall.enqueue(new Callback<AllCategoryDeparment>() {
            @Override
            public void onResponse(Call<AllCategoryDeparment> call, Response<AllCategoryDeparment> response) {
                if (response.body().getResult() != null) {

                    if (response.body().getStatus() == 1) {
                        hidePrd();
                        allCategoryDeptitemList.addAll(response.body().getResult());
                        allCategoryDeptitemListTemp.addAll(response.body().getResult());

                        Collections.sort(allCategoryDeptitemListTemp, new Comparator<AllCategoryDeparment.Result>() {
                            @Override
                            public int compare(final AllCategoryDeparment.Result object1, final AllCategoryDeparment.Result object2) {
                                return object1.getCategoryName().compareTo(object2.getCategoryName());
                            }
                        });

                        if(checkDialog==true){
                            enquiryCategoryDepDailog(view);
                        }

                    } else {
                        Utils.showToast(context, "No Data Found", R.color.red_dark);
                        hidePrd();
                    }

                } else {
                    Utils.showToast(context, "Opps Something wrong", R.color.red_dark);
                    hidePrd();
                }
                hidePrd();
            }

            @Override
            public void onFailure(Call<AllCategoryDeparment> call, Throwable t) {

                hidePrd();
                Utils.showToast(context, "No Internet Connection", R.color.red_dark);
            }
        });

    }

    private void enquiryCategoryDepDailog(final TextView view) {


        LayoutInflater localView = LayoutInflater.from(context);
        View promptsView = localView.inflate(R.layout.dialog_spinner, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        final EditText edtSearchLocation = (EditText) promptsView.findViewById(R.id.edt_spn_search);
        final RecyclerView list_location = (RecyclerView) promptsView.findViewById(R.id.rv_spn);
        final Button btncancle = (Button) promptsView.findViewById(R.id.btnCancle);
        final Button btnsubmit = (Button) promptsView.findViewById(R.id.btnOhk);
        final TextView tvDailogTitle = (TextView) promptsView.findViewById(R.id.tvDailogTitle);

        final ArrayList<String> arrayListTemp = new ArrayList<>();
        final ArrayList<String> arrayListId = new ArrayList<>();

        edtSearchLocation.setVisibility(View.GONE);
        tvDailogTitle.setText("Category(Department)");

        edtSearchLocation.setHint("Search  Category(Department)");
        for (int i = 0; i < allstateitemList.size(); i++) {
            arrayListTemp.add(allstateitemList.get(i).getStateName());
            arrayListId.add(allstateitemList.get(i).getStateName());
        }

        allCategoryDepaement_spn_adapter = new AllCategoryDepaement_Spn_Adapter(context, allCategoryDeptitemListTemp);
        list_location.setLayoutManager(new LinearLayoutManager(Add_New_Customer_Activity.this, LinearLayoutManager.VERTICAL, false));
        list_location.setAdapter(allCategoryDepaement_spn_adapter);

        allCategoryDepaement_spn_adapter.setOnItemClickListener(new AllCategoryDepaement_Spn_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                categorydepid = allCategoryDeptitemListTemp.get(position).getCategoryId();
                // Utils.showLog("sourcid" + sourceid);
                tvCustomerCatDept.setTextColor(getResources().getColor(R.color.black));
                tvEnquiryCategorySpn.setText(allCategoryDeptitemListTemp.get(position).getCategoryName());
                alertDialog.dismiss();
            }
        });

/*
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<EnquirySource.Result> selectedlist = enqrySource_spn_adapter.getSelectedlist();
                alertDialog.dismiss();
                //Utils.showToast(context,"list"+selectedlist);
                for (int i = 0; i < selectedlist.size(); i++) {

                    selectedcategorylist = selectedlist.get(i).getEsName();
                    // sourceidlist.add(selectedlist.get(i).getEsId());
                    //  Utils.showLog("sourceid"+sourceidlist);
                    tvEnquirySource.setText(tvEnquirySource.getText() + selectedcategorylist + " ,");

                }
            }
        });*/


        edtSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int position, int i1, int i2) {

                if (edtSearchLocation.getText().toString().trim().length() > 0) {
                    allCategoryDeptitemListTemp.clear();
                    // arrayListId.clear();

                    for (int j = 0; j < allCategoryDeptitemList.size(); j++) {
                        String word = edtSearchLocation.getText().toString().toLowerCase();
                        if (allCategoryDeptitemList.get(j).getCategoryName().toLowerCase().contains(word)) {
                            allCategoryDeptitemListTemp.add(allCategoryDeptitemList.get(j));
                            //  arrayListId.add(itemListEnquirySource.get(j).getEsId());
                        }
                    }
                    allCategoryDepaement_spn_adapter.notifyDataSetChanged();
                    //list_location.setAdapter(new EnqrySource_Spn_Adapter_new(context, itemListEnquirySourceTemp));
                } else {
                    arrayListTemp.clear();
                    arrayListId.clear();
                    for (int i = 0; i < allCategoryDeptitemList.size(); i++) {
                        allCategoryDeptitemListTemp.add(allCategoryDeptitemList.get(i));
                        //arrayListId.add(itemListEnquirySource.get(i).getEsId());
                    }
                    allCategoryDepaement_spn_adapter.notifyDataSetChanged();
                    // list_location.setAdapter(new EnqrySource_Spn_Adapter_new(context, itemListEnquirySourceTemp));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

      /*  list_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                textView.setText(arrayListTemp.get(position));
                textView.setTag(arrayListId.get(position));
                cust_id = arrayListId.get(position);
                //alertDialog.dismiss();

            }
        });*/


        alertDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = alertDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


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
