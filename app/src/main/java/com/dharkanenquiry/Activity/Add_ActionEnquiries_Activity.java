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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dharkanenquiry.Adapter.AllUserAdapter;
import com.dharkanenquiry.Model.ActionAdd;
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

public class Add_ActionEnquiries_Activity extends AppCompatActivity {

    String enquiry_id, customerid;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    SpinKitView progressBar;
    @BindView(R.id.tvAssign1)
    TextView tvAssign1;
    @BindView(R.id.tvTaskAssignName)
    TextView tvTaskAssignName;
    @BindView(R.id.rvAssignspn)
    RelativeLayout rvAssignspn;
    @BindView(R.id.tvdate1)
    TextView tvdate1;
    @BindView(R.id.tvTaskDeadlineDate)
    TextView tvTaskDeadlineDate;
    @BindView(R.id.rvTaskDeadlineDateSpn)
    RelativeLayout rvTaskDeadlineDateSpn;
    @BindView(R.id.tvTime1)
    TextView tvTime1;
    @BindView(R.id.tvTaskDeadlineTime)
    TextView tvTaskDeadlineTime;
    @BindView(R.id.rvTaskDeadlineTimeSpn)
    RelativeLayout rvTaskDeadlineTimeSpn;
    @BindView(R.id.tvMedium1)
    TextView tvMedium1;
    @BindView(R.id.tvMedium)
    TextView tvMedium;
    @BindView(R.id.rvMedium)
    RelativeLayout rvMedium;
    @BindView(R.id.tvTaskTitle1)
    TextView tvTaskTitle1;
    @BindView(R.id.etTaskTitle)
    EditText etTaskTitle;
    @BindView(R.id.rlTaskTitle)
    RelativeLayout rlTaskTitle;
    @BindView(R.id.etTaskNote)
    EditText etTaskNote;
    @BindView(R.id.btntaskAssign)
    Button btntaskAssign;

    String enquiryid = "";
    String userid = "";
    String mediumid = "";
    String date = "";
    String time = "";

    ProgressDialog progressDialog;

    AllUserAdapter alluseradapter;

    ImageView ivBackAction;

    List<Users.Result> itemListUser = new ArrayList<>();
    List<Users.Result> itemListUserTemp = new ArrayList<>();
    WebApi webapi;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__action_enquiries_);
        ButterKnife.bind(this);
        context = Add_ActionEnquiries_Activity.this;
        InitUi();
    }

    private void InitUi() {

        setSupportActionBar(toolbar);
        tvTitle.setText("Add Action");

     //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        enquiryid = getIntent().getStringExtra("enquiry_id");
        customerid = getIntent().getStringExtra("customer_id");



        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        Sprite doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        webapi = Utils.getRetrofitClient().create(WebApi.class);

        ivBackAction = (ImageView) findViewById(R.id.ivBackAction);

        ivBackAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rvAssignspn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserApi(tvTaskAssignName);
            }
        });

        rvMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {
                        "Phone", "Email", "Meeting", "Other"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Add_ActionEnquiries_Activity.this);
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

                Helper.pick_Date(Add_ActionEnquiries_Activity.this, tvTaskDeadlineDate);

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
                mTimePicker = new TimePickerDialog(Add_ActionEnquiries_Activity.this, new TimePickerDialog.OnTimeSetListener() {
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

                if (tvTaskAssignName.getText().toString().trim().isEmpty()) {
                    tvAssign1.setTextColor(getResources().getColor(R.color.red));
                    Utils.showToast(context, "Select User", R.color.red);
                    // tvEnquiryProduct.setError("Select ProductName");
                    //tvEnquiryProduct.requestFocus();
                    return;
                }
                if (tvTaskDeadlineDate.getText().toString().trim().isEmpty()) {
                    tvdate1.setTextColor(getResources().getColor(R.color.red));
                    Utils.showToast(context, "Select Date", R.color.red);
                    // tvEnquirySource.setError("Select Source");
                    // tvEnquirySource.requestFocus();
                    return;
                }
                if (tvTaskDeadlineTime.getText().toString().trim().isEmpty()) {
                    tvTime1.setTextColor(getResources().getColor(R.color.red));
                    Utils.showToast(context, "Select Time", R.color.red);
                    //tvImportance.setError("Select Importance");
                    // tvImportance.requestFocus();
                    return;
                }

                if (tvMedium.getText().toString().trim().isEmpty()) {
                    tvMedium1.setTextColor(getResources().getColor(R.color.red));
                    Utils.showToast(context, "Select Medium", R.color.red);
                    // tvEnquiryCatagory.setError("Select Category");
                    // tvEnquiryCatagory.requestFocus();
                    return;
                }
                if (etTaskTitle.getText().toString().trim().isEmpty()) {
                    tvTaskTitle1.setTextColor(getResources().getColor(R.color.red));
                    Utils.showToast(context, "Select TaskTitle", R.color.red);
                    // tvEnquiryCatagory.setError("Select Category");
                    // tvEnquiryCatagory.requestFocus();
                    return;
                }

                //  progressBar.setVisibility(View.VISIBLE);

                showPrd();
                Utils.showLog("==userid" + SharedPrefsUtils.getSharedPreferenceString(Add_ActionEnquiries_Activity.this, SharedPrefsUtils.USER_ID) + "" + "customerid" + customerid + "" + "date" + tvTaskDeadlineDate.getText().toString() + "" + "time" + time + "" + "mediumid" + mediumid + "" + "userid" + userid + "" + "titilenote" + etTaskTitle.getText().toString() + "" + "note" + etTaskNote.getText().toString() + " " + enquiryid);

                Call<ActionAdd> newTaskCall = webapi.getActionAddapi(SharedPrefsUtils.getSharedPreferenceString(Add_ActionEnquiries_Activity.this, SharedPrefsUtils.USER_ID), customerid, tvTaskDeadlineDate.getText().toString(), time, mediumid, userid, etTaskTitle.getText().toString(), etTaskNote.getText().toString(), enquiryid);
                newTaskCall.enqueue(new Callback<ActionAdd>() {
                    @Override
                    public void onResponse(Call<ActionAdd> call, Response<ActionAdd> response) {

                        hidePrd();
                        if (response.body().getStatus() == 1) {

                            finish();

                            Utils.showToast(context, "New action assign Successful", R.color.green_fed);
                        } else {
                            Utils.showToast(context, "Oops something wrong", R.color.red);
                        }
                        //progressBar.setVisibility(View.GONE);
                        hidePrd();
                    }

                    @Override
                    public void onFailure(Call<ActionAdd> call, Throwable t) {
                        // progressBar.setVisibility(View.GONE);
                        hidePrd();
                        Utils.showErrorToast(Add_ActionEnquiries_Activity.this);

                    }
                });


            }
        });


    }

    private void getUserApi(final TextView view) {

      //  progressBar.setVisibility(View.VISIBLE);
        showPrd();
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
                        userDialog(view);


                    } else {
                        Utils.showToast(context, "No Data Found", R.color.red);
                        hidePrd();
                    }

                } else {
                    Utils.showToast(context, "Opps Something Wrong", R.color.red);
                    hidePrd();
                }
                hidePrd();
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
              //  progressBar.setVisibility(View.GONE);
                hidePrd();
                Utils.showToast(context, "Please Try Again", R.color.red);

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

        alluseradapter = new AllUserAdapter(context, itemListUserTemp);
        list_location.setLayoutManager(new LinearLayoutManager(Add_ActionEnquiries_Activity.this, LinearLayoutManager.VERTICAL, false));
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

 /*   @Override
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
