package com.dharkanenquiry.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dharkanenquiry.Adapter.AllUserAdapter;
import com.dharkanenquiry.Adapter.Customer_Spn_Adapter_New;
import com.dharkanenquiry.Model.Customers;
import com.dharkanenquiry.Model.NewTask;
import com.dharkanenquiry.Model.Users;
import com.dharkanenquiry.utils.Helper;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_NewTask_Activity extends AppCompatActivity {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvTaskCompanySpn)
    TextView tvTaskCompanySpn;
    @BindView(R.id.rvCompnyspn)
    RelativeLayout rvCompnyspn;
    @BindView(R.id.tvTaskAssignName)
    TextView tvTaskAssignName;
    @BindView(R.id.rvAssignspn)
    RelativeLayout rvAssignspn;
    @BindView(R.id.tvTaskDeadlineDate)
    TextView tvTaskDeadlineDate;
    @BindView(R.id.rvTaskDeadlineDateSpn)
    RelativeLayout rvTaskDeadlineDateSpn;
    @BindView(R.id.tvTaskDeadlineTime)
    TextView tvTaskDeadlineTime;
    @BindView(R.id.rvTaskDeadlineTimeSpn)
    RelativeLayout rvTaskDeadlineTimeSpn;
    @BindView(R.id.tvMedium)
    TextView tvMedium;
    @BindView(R.id.rvMedium)
    RelativeLayout rvMedium;
    @BindView(R.id.etTaskNote)
    EditText etTaskNote;
    @BindView(R.id.btntaskAssign)
    Button btntaskAssign;

    Context context;
    @BindView(R.id.etTaskTitle)
    EditText etTaskTitle;
    @BindView(R.id.rlTaskTitle)
    RelativeLayout rlTaskTitle;

    String customerid = "",enquiryid="";
    String userid = "";
    String mediumid = "";
    String date = "";
    String time = "";


    Customer_Spn_Adapter_New customer_spn_adapter;

    AllUserAdapter alluseradapter;


    WebApi webapi;

    List<Customers.Result> itemListCustomer = new ArrayList<>();
    List<Customers.Result> itemListCustomerTemp = new ArrayList<>();

    List<Users.Result> itemListUser = new ArrayList<>();
    List<Users.Result> itemListUserTemp = new ArrayList<>();
    @BindView(R.id.progress_bar)
    SpinKitView progressBar;
    @BindView(R.id.tvcompany1)
    TextView tvcompany1;
    @BindView(R.id.tvAssign1)
    TextView tvAssign1;
    @BindView(R.id.tvdate1)
    TextView tvdate1;
    @BindView(R.id.tvTime1)
    TextView tvTime1;
    @BindView(R.id.tvMedium1)
    TextView tvMedium1;
    @BindView(R.id.tvTaskTitle1)
    TextView tvTaskTitle1;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__new_task_);
        ButterKnife.bind(this);

        context = Add_NewTask_Activity.this;

        initUI();


    }

    private void initUI() {

        setSupportActionBar(toolbar);
        tvTitle.setText("New Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       // customerid = getIntent().getStringExtra("customer_id");
      //  enquiryid = getIntent().getStringExtra("enquiry_id");

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        Sprite doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        webapi = Utils.getRetrofitClient().create(WebApi.class);

        getCompanyapi(tvTaskCompanySpn,false);
        getUserApi(tvTaskAssignName,false);

        rvCompnyspn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // getCompanyapi(tvTaskCompanySpn);
                if(itemListCustomer.size()>0){
                    customerDialog(tvTaskCompanySpn);
                }else {
                    getCompanyapi(tvTaskCompanySpn,true);
                }
            }
        });

        rvAssignspn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getUserApi(tvTaskAssignName);

                if(itemListUser.size()>0){
                    userDialog(tvTaskAssignName);
                }else {
                    getUserApi(tvTaskAssignName,true);
                }

            }
        });

        rvMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {
                        "Phone", "Email", "Meeting", "Other"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Add_NewTask_Activity.this);
                builder.setTitle("Select Medium");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        tvMedium.setText(items[item]);
                        tvMedium1.setTextColor(getResources().getColor(R.color.black));
                        mediumid = tvMedium.getText().toString();


                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }

        });

        rvTaskDeadlineDateSpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvTaskDeadlineDate.setText(Helper.getCurrentDate("dd/mm/yyyy"));
                tvTaskDeadlineDate.setTag(Helper.getCurrentDate("yyyy-MM-dd"));

                Helper.pick_Date(Add_NewTask_Activity.this, tvTaskDeadlineDate);

                tvdate1.setTextColor(getResources().getColor(R.color.black));
                date = tvTaskDeadlineDate.getText().toString();
                //  Utils.showToast(context,"date"+date);


              /*  rvTaskDeadlineDateSpn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                    }
                }); */

            }
        });

        rvTaskDeadlineTimeSpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Add_NewTask_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String hours = String.valueOf(selectedHour);
                        String min = String.valueOf(selectedMinute);

                        if (hours.length() == 1) {
                            hours = "0" + hours;
                        }
                        if (min.length() == 1) {
                            min = "0" + min;
                        }


                        tvTime1.setTextColor(getResources().getColor(R.color.black));
                        tvTaskDeadlineTime.setText(hours + ":" + min + ":00");
                        time = tvTaskDeadlineTime.getText().toString();
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


               /* tvTaskDeadlineTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });*/
            }
        });


        btntaskAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvTaskCompanySpn.getText().toString().trim().isEmpty()) {
                    tvcompany1.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Select CompanyName", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (tvTaskAssignName.getText().toString().trim().isEmpty()) {
                    tvAssign1.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Select User", R.color.red_dark);
                    // tvEnquiryProduct.setError("Select ProductName");
                    //tvEnquiryProduct.requestFocus();
                    return;
                }
                if (tvTaskDeadlineDate.getText().toString().trim().isEmpty()) {
                    tvdate1.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Select Date", R.color.red_dark);
                    // tvEnquirySource.setError("Select Source");
                    // tvEnquirySource.requestFocus();
                    return;
                }
                if (tvTaskDeadlineTime.getText().toString().trim().isEmpty()) {
                    tvTime1.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Select Time", R.color.red_dark);
                    //tvImportance.setError("Select Importance");
                    // tvImportance.requestFocus();
                    return;
                }

                if (tvMedium.getText().toString().trim().isEmpty()) {
                    tvMedium1.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Select Medium", R.color.red_dark);
                    // tvEnquiryCatagory.setError("Select Category");
                    // tvEnquiryCatagory.requestFocus();
                    return;
                }
                if (etTaskTitle.getText().toString().trim().isEmpty()) {
                    tvTaskTitle1.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Select TaskTitle", R.color.red_dark);
                    // tvEnquiryCatagory.setError("Select Category");
                    // tvEnquiryCatagory.requestFocus();
                    return;
                }

                showPrd ();
                Utils.showLog("==userid"+SharedPrefsUtils.getSharedPreferenceString(Add_NewTask_Activity.this, SharedPrefsUtils.USER_ID)+""+"customerid"+customerid+""+"date"+tvTaskDeadlineDate.getText().toString()+""+"time"+time+""+"mediumid"+mediumid+""+"userid"+userid+""+"titilenote"+etTaskTitle.getText().toString()+""+"note"+etTaskNote.getText().toString());

                Call<NewTask> newTaskCall = webapi.addnewtask(SharedPrefsUtils.getSharedPreferenceString(Add_NewTask_Activity.this, SharedPrefsUtils.USER_ID), customerid, tvTaskDeadlineDate.getText().toString(), time, mediumid, userid, etTaskTitle.getText().toString(), etTaskNote.getText().toString());
                newTaskCall.enqueue(new Callback<NewTask>() {
                    @Override
                    public void onResponse(Call<NewTask> call, Response<NewTask> response) {


                        if (response.body().getStatus() == 1) {

                            finish();

                            Utils.showToast(context, "New Task Assign Successful", R.color.green_fed);
                        } else {
                            Utils.showToast(context, "Oops Something wrong", R.color.red_dark);
                        }
                      hidePrd();
                    }

                    @Override
                    public void onFailure(Call<NewTask> call, Throwable t) {
                      hidePrd();
                        Utils.showErrorToast(Add_NewTask_Activity.this);

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

                    hidePrd();
                    if (response.body().getStatus() == 1) {

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
                    Utils.showToast(context, "Please Try Again", R.color.red_dark);
                }
            }
        });
    }


    private void getCompanyapi(final TextView view,boolean checkDialog) {

       showPrd();

        itemListCustomer.clear();
        itemListCustomerTemp.clear();

        Call<Customers> getCustomersCall = webapi.getallcustomers(SharedPrefsUtils.getSharedPreferenceString(Add_NewTask_Activity.this, SharedPrefsUtils.USER_ID));
        getCustomersCall.enqueue(new Callback<Customers>() {
            @Override
            public void onResponse(Call<Customers> call, Response<Customers> response) {
                if (response.body().getResult() != null) {

                    if (response.body().getStatus() == 1) {

                        itemListCustomer.addAll(response.body().getResult());
                        itemListCustomerTemp.addAll(response.body().getResult());
                        //   Utils.showLog("=== onres");

                        Collections.sort(itemListCustomerTemp, new Comparator<Customers.Result>() {
                            @Override
                            public int compare(final Customers.Result object1, final Customers.Result object2) {
                                return object1.getCustomerName().compareTo(object2.getCustomerName());
                            }
                        });

                        if(checkDialog==true){
                            customerDialog(view);
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
            public void onFailure(Call<Customers> call, Throwable t) {
              hidePrd();
                Utils.showToast(context, "Please Try Again", R.color.red_dark);

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

        tvDailogTitle.setText("User");

        edtSearchLocation.setHint("Search user");
        for (int i = 0; i < itemListUser.size(); i++) {
            arrayListTemp.add(itemListUser.get(i).getName());
            arrayListId.add(itemListUser.get(i).getUserId());
        }

        itemListUserTemp.clear();
        itemListUserTemp.addAll(itemListUser);

        alluseradapter = new AllUserAdapter(context, itemListUserTemp);
        list_location.setLayoutManager(new LinearLayoutManager(Add_NewTask_Activity.this, LinearLayoutManager.VERTICAL, false));
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
                userid = itemListUserTemp.get(position).getUserId();

                // Utils.showLog("userId"+userid);
                tvAssign1.setTextColor(getResources().getColor(R.color.black));
                tvTaskAssignName.setText(itemListUserTemp.get(position).getName());
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

    private void customerDialog(final TextView textView) {

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

        tvDailogTitle.setText("Customers");

        edtSearchLocation.setHint("Search Customer");
        for (int i = 0; i < itemListCustomer.size(); i++) {
            arrayListTemp.add(itemListCustomer.get(i).getCustomerName());
            arrayListId.add(itemListCustomer.get(i).getCustomerId());
        }

        customer_spn_adapter = new Customer_Spn_Adapter_New(context, itemListCustomerTemp);
        list_location.setLayoutManager(new LinearLayoutManager(Add_NewTask_Activity.this, LinearLayoutManager.VERTICAL, false));
        list_location.setAdapter(customer_spn_adapter);

        customer_spn_adapter.setOnItemClickListener(new Customer_Spn_Adapter_New.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                customerid = itemListCustomerTemp.get(position).getCustomerId();

                //Utils.showLog("custmerid" + customerid);
                tvcompany1.setTextColor(getResources().getColor(R.color.black));
                tvTaskCompanySpn.setText(itemListCustomerTemp.get(position).getCustomerName());
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
                    itemListCustomerTemp.clear();
                    // arrayListId.clear();

                    for (int j = 0; j < itemListCustomer.size(); j++) {
                        String word = edtSearchLocation.getText().toString().toLowerCase();
                        if (itemListCustomer.get(j).getCustomerName().toLowerCase().contains(word)) {
                            itemListCustomerTemp.add(itemListCustomer.get(j));
                            //arrayListId.add(itemListCustomer.get(j).getCustomerId());
                        }
                    }
                    customer_spn_adapter.notifyDataSetChanged();
                    //list_location.setAdapter(new Customer_Spn_Adapter_New(context, itemListCustomerTemp));
                } else {
                    arrayListTemp.clear();
                    arrayListId.clear();
                    for (int i = 0; i < itemListCustomer.size(); i++) {
                        itemListCustomerTemp.add(itemListCustomer.get(i));
                        //arrayListId.add(itemListCustomer.get(i).getCustomerId());
                    }
                    customer_spn_adapter.notifyDataSetChanged();
                    // list_location.setAdapter(new Customer_Spn_Adapter_New(context, itemListCustomerTemp));
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

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        // progressDialog.dismiss();
        return true;
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
