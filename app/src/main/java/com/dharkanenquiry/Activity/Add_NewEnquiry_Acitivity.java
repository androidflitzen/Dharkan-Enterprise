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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dharkanenquiry.Adapter.AllCategoryDepaement_Spn_Adapter;
import com.dharkanenquiry.Adapter.AllRegion_Spn_Adapter;
import com.dharkanenquiry.Adapter.AllState_Spn_Adapter;
import com.dharkanenquiry.Adapter.AllUserAdapter;
import com.dharkanenquiry.Adapter.Customer_Spn_Adapter_New;
import com.dharkanenquiry.Adapter.EnqryCategory_Spn_Adapter_New;
import com.dharkanenquiry.Adapter.EnqrySource_Spn_Adapter_new;
import com.dharkanenquiry.Adapter.Product_Spn_Adapter_New;
import com.dharkanenquiry.Model.AddNewEnquiry;
import com.dharkanenquiry.Model.AllCategoryDeparment;
import com.dharkanenquiry.Model.AllRegion;
import com.dharkanenquiry.Model.AllState;
import com.dharkanenquiry.Model.Customers;
import com.dharkanenquiry.Model.EnquiryCategory;
import com.dharkanenquiry.Model.EnquirySource;
import com.dharkanenquiry.Model.Product;
import com.dharkanenquiry.Model.Users;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_NewEnquiry_Acitivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvCustomerName)
    TextView tvCustomerName;
    @BindView(R.id.etCustomernamespn)
    EditText etCustomernamespn;
    @BindView(R.id.rvCustomername)
    RelativeLayout rvCustomername;
    @BindView(R.id.tvCustomerWhatsNo)
    TextView tvCustomerWhatsNo;
    @BindView(R.id.etCustomerWhatsNospn)
    EditText etCustomerWhatsNospn;
    @BindView(R.id.rvCustomerWhatsno)
    RelativeLayout rvCustomerWhatsno;
    @BindView(R.id.tvCustomerlandlineNo)
    TextView tvCustomerlandlineNo;
    @BindView(R.id.etCustomerlandlineNospn)
    EditText etCustomerlandlineNospn;
    @BindView(R.id.rvCustomerLandlineNO)
    RelativeLayout rvCustomerLandlineNO;
    @BindView(R.id.tvCustomerEmail)
    TextView tvCustomerEmail;
    @BindView(R.id.etCustomerEmailspn)
    EditText etCustomerEmailspn;
    @BindView(R.id.rvCustomerEmail)
    RelativeLayout rvCustomerEmail;
    @BindView(R.id.tvCustomerGSTNo)
    TextView tvCustomerGSTNo;
    @BindView(R.id.etCustomerGSTNospn)
    EditText etCustomerGSTNospn;
    @BindView(R.id.rvCustomerGSTNo)
    RelativeLayout rvCustomerGSTNo;
    @BindView(R.id.tvCustomerAddress)
    TextView tvCustomerAddress;
    @BindView(R.id.etCustomerAddressspn)
    EditText etCustomerAddressspn;
    @BindView(R.id.rvCustomerAddress)
    RelativeLayout rvCustomerAddress;
    @BindView(R.id.liCustomerDeatils)
    LinearLayout liCustomerDeatils;
    @BindView(R.id.tvEnquiryState)
    TextView tvEnquiryState;
    @BindView(R.id.tvStateSpn)
    TextView tvStateSpn;
    @BindView(R.id.rvEnquiryStatespn)
    RelativeLayout rvEnquiryStatespn;
    @BindView(R.id.tvEnquiryCity)
    TextView tvEnquiryCity;
    @BindView(R.id.etEnquiryCitySpn)
    EditText etEnquiryCitySpn;
    @BindView(R.id.rvEnquiryCityspn)
    RelativeLayout rvEnquiryCityspn;
    @BindView(R.id.tvEnquiryCategDepory)
    TextView tvEnquiryCategDepory;
    @BindView(R.id.tvEnquiryCategorySpn)
    TextView tvEnquiryCategorySpn;
    @BindView(R.id.rvEnquiryCategoryDepspn)
    RelativeLayout rvEnquiryCategoryDepspn;
    @BindView(R.id.tvAssignUser)
    TextView tvAssignUser;
    @BindView(R.id.tvAssignUserSpn)
    TextView tvAssignUserSpn;
    @BindView(R.id.rvAssignspn)
    RelativeLayout rvAssignspn;
    @BindView(R.id.tvProduct1)
    TextView tvProduct1;
    @BindView(R.id.tvEnquiryProduct)
    TextView tvEnquiryProduct;
    @BindView(R.id.rvProductspn)
    RelativeLayout rvProductspn;
    @BindView(R.id.tvSource1)
    TextView tvSource1;
    @BindView(R.id.tvEnquirySource)
    TextView tvEnquirySource;
    @BindView(R.id.rvEnquirySourceSpn)
    RelativeLayout rvEnquirySourceSpn;
    @BindView(R.id.tvImportance1)
    TextView tvImportance1;
    @BindView(R.id.tvImportance)
    TextView tvImportance;
    @BindView(R.id.rvImportance)
    RelativeLayout rvImportance;
    @BindView(R.id.tvRegion)
    TextView tvRegion;
    @BindView(R.id.tvEnquiryRegion)
    TextView tvEnquiryRegion;
    @BindView(R.id.rvEnquiryRegionSpn)
    RelativeLayout rvEnquiryRegionSpn;
    @BindView(R.id.tvCategory1)
    TextView tvCategory1;
    @BindView(R.id.tvEnquiryCatagory)
    TextView tvEnquiryCatagory;
    @BindView(R.id.rvEnquiryCatSpn)
    RelativeLayout rvEnquiryCatSpn;
    @BindView(R.id.etOtherNote)
    EditText etOtherNote;
    @BindView(R.id.liCompanyDetail)
    LinearLayout liCompanyDetail;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.progress_bar)
    SpinKitView progressBar;

    Context context;
    WebApi webapi;

    ProgressDialog progressDialog;


    String selectedcategorylist = "", cust_id = "", p_id = "";
    Boolean selectedimportance;
    List<Customers.Result> itemListCustomer = new ArrayList<>();
    List<Customers.Result> itemListCustomerTemp = new ArrayList<>();

    List<Users.Result> itemListUser = new ArrayList<>();
    List<Users.Result> itemListUserTemp = new ArrayList<>();

    List<Product.Result> itemListProduct = new ArrayList<>();
    List<Product.Result> itemListProductTemp = new ArrayList<>();

    List<EnquirySource.Result> itemListEnquirySource = new ArrayList<>();
    List<EnquirySource.Result> itemListEnquirySourceTemp = new ArrayList<>();

    List<EnquiryCategory.Result> itemListEnquiryCategory = new ArrayList<>();
    List<EnquiryCategory.Result> itemListEnquiryCategoryTemp = new ArrayList<>();

    List<AllState.Result> allstateitemList = new ArrayList<>();
    List<AllState.Result> allstateitemListTemp = new ArrayList<>();

    List<AllCategoryDeparment.Result> allCategoryDeptitemList = new ArrayList<>();
    List<AllCategoryDeparment.Result> allCategoryDeptitemListTemp = new ArrayList<>();

    List<AllRegion.Result> allregionitemList = new ArrayList<>();
    List<AllRegion.Result> allregionitemListTemp = new ArrayList<>();


    List<String> productidlist = new ArrayList<>();
    String customerid = "";
    String sourceid = "", allstateid = "", categorydepid = "";
    String categoryid = "",assignuserid = "",regionid="";
    String importanceid = "";


    EnqryCategory_Spn_Adapter_New enqryCategory_spn_adapter;
    EnqrySource_Spn_Adapter_new enqrySource_spn_adapter;
    Product_Spn_Adapter_New product_spn_adapter;
    Customer_Spn_Adapter_New customer_spn_adapter;
    AllUserAdapter alluseradapter;
    AllState_Spn_Adapter allState_spn_adapter;
    AllCategoryDepaement_Spn_Adapter allCategoryDepaement_spn_adapter;
    AllRegion_Spn_Adapter allRegion_spn_adapter;

    Enquiry_Activity enquiry_activity;

    ImageView ivBackEnqNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new__enquiry);
        ButterKnife.bind(this);

        context = Add_NewEnquiry_Acitivity.this;

        initUI();

    }

    private void initUI() {

        setSupportActionBar(toolbar);
        tvTitle.setText("Add New Enquiry (New Company)");
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        ivBackEnqNew = (ImageView) findViewById(R.id.ivBackEnqNew);


        ivBackEnqNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Sprite doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        webapi = Utils.getRetrofitClient().create(WebApi.class);

        rvEnquiryStatespn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAllStateapi(tvStateSpn);
            }
        });

        rvEnquiryCategoryDepspn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategoryDepapi(tvEnquiryCategDepory);
            }
        });

        rvAssignspn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserApi(tvAssignUserSpn);
            }
        });

        rvProductspn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getproductapi(tvEnquiryProduct);

            }
        });


        rvEnquirySourceSpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getenquirysourcapi(tvEnquirySource);

            }
        });

        rvImportance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final CharSequence[] items = {
                        "High", "Very High", "Medium", "Low", "Very Low"
                };



                AlertDialog.Builder builder = new AlertDialog.Builder(Add_NewEnquiry_Acitivity.this);
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        tvImportance.setText(items[item]);
                        tvImportance1.setTextColor(getResources().getColor(R.color.black));
                        importanceid = tvImportance.getText().toString();


                    }
                });
                AlertDialog alert = builder.create();
                alert.show();



            }


        });

        rvEnquiryCatSpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getenquirycategoryapi(tvEnquiryCatagory);

            }
        });

        rvEnquiryRegionSpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getenquiryregionapi(tvEnquiryRegion);
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
                }if (etCustomerWhatsNospn.getText().toString().trim().isEmpty()) {
                    tvCustomerWhatsNo.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Enter WhatsappNo.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }if (etCustomerEmailspn.getText().toString().trim().isEmpty()) {
                    tvCustomerEmail.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Enter EmailAddress.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }if (etCustomerAddressspn.getText().toString().trim().isEmpty()) {
                    tvCustomerAddress.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Enter Customer Address.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }if (tvStateSpn.getText().toString().trim().isEmpty()) {
                    tvEnquiryState.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Please Select State.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (etEnquiryCitySpn.getText().toString().trim().isEmpty()) {
                    tvEnquiryCity.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Please Enter City.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                } if (tvEnquiryCategorySpn.getText().toString().trim().isEmpty()) {
                    tvEnquiryCategDepory.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Please Select Category(Department).", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (tvAssignUserSpn.getText().toString().trim().isEmpty()) {
                    tvAssignUser.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Please Select Assign user.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (tvEnquiryProduct.getText().toString().trim().isEmpty()) {
                    tvProduct1.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Please Select Product.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (tvEnquirySource.getText().toString().trim().isEmpty()) {
                    tvSource1.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Please Select Source.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (tvImportance.getText().toString().trim().isEmpty()) {
                    tvImportance1.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Please Select Importance.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (tvEnquiryRegion.getText().toString().trim().isEmpty()) {
                    tvRegion.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Please Select Region.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (tvEnquiryCatagory.getText().toString().trim().isEmpty()) {
                    tvCategory1.setTextColor(getResources().getColor(R.color.red_dark));
                    Utils.showToast(context, "Please Select Category.", R.color.red_dark);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }

                showPrd();
                // progressBar.setVisibility(View.VISIBLE);

                Utils.showLog("==alldata"+etCustomernamespn.getText().toString()+" "+etCustomerWhatsNospn.getText().toString()+" "+etCustomerlandlineNospn.getText().toString()+" "+etCustomerEmailspn.getText().toString()+" "+etCustomerGSTNospn.getText().toString()+" "+etCustomerAddressspn.getText().toString()+
                        " "+tvStateSpn.getText().toString()+" "+etEnquiryCitySpn.getText().toString()+ " "+categorydepid+" "+assignuserid+ " "+productidlist+" "+sourceid+" "+importanceid+" "+regionid+" "+categoryid+" "+etOtherNote.getText().toString());


                Call<AddNewEnquiry> addNewEnquiryCall = webapi.addnewEnquiryApi(SharedPrefsUtils.getSharedPreferenceString(Add_NewEnquiry_Acitivity.this, SharedPrefsUtils.USER_ID),tvStateSpn.getText().toString(),etEnquiryCitySpn.getText().toString(),categorydepid,etCustomernamespn.getText().toString(),etCustomerWhatsNospn.getText().toString(),etCustomerAddressspn.getText().toString(),etCustomerlandlineNospn.getText().toString(),etCustomerEmailspn.getText().toString(),etCustomerGSTNospn.getText().toString(),assignuserid,productidlist,etOtherNote.getText().toString(),sourceid,importanceid,regionid,categoryid);
                addNewEnquiryCall.enqueue(new Callback<AddNewEnquiry>() {
                    @Override
                    public void onResponse(Call<AddNewEnquiry> call, Response<AddNewEnquiry> response) {

                        if (response.body() != null){

                            if (response.body().getStatus() == 1){
                                hidePrd();
                                finish();
                                Utils.showToast(context, "New Company Enquiry Add Successful", R.color.green_fed);


                            }else {
                                Utils.showToast(context, "oops Something Wrong", R.color.red_dark);
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
                    public void onFailure(Call<AddNewEnquiry> call, Throwable t) {
                        hidePrd();
                        //progressBar.setVisibility(View.GONE);
                        Utils.showErrorToast(Add_NewEnquiry_Acitivity.this);
                    }
                });



            }
        });

    }

    private void getenquiryregionapi(final TextView view) {

        showPrd();
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

                        enquiryRegionDialog(view);


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
                Utils.showToast(context, "Please check your internet", R.color.red_dark);

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

        allRegion_spn_adapter = new AllRegion_Spn_Adapter(context, allregionitemListTemp);

        list_location.setAdapter(new AllRegion_Spn_Adapter(context, allregionitemListTemp));

        list_location.setLayoutManager(new LinearLayoutManager(Add_NewEnquiry_Acitivity.this, LinearLayoutManager.VERTICAL, false));
        list_location.setAdapter(allRegion_spn_adapter);

        allRegion_spn_adapter.setOnItemClickListener(new AllRegion_Spn_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                regionid = allregionitemListTemp.get(position).getRegionId();
                // Utils.showLog("categoryid" + categoryid);
                tvRegion.setTextColor(getResources().getColor(R.color.black));
                tvEnquiryRegion.setText(allregionitemListTemp.get(position).getRegionName());
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

    private void getenquirycategoryapi(final TextView view) {

        showPrd();
        //  progressBar.setVisibility(View.VISIBLE);
        itemListEnquiryCategory.clear();
        itemListEnquiryCategoryTemp.clear();

        Call<EnquiryCategory> enquiryCategoryCall = webapi.getenquirycategory();
        enquiryCategoryCall.enqueue(new Callback<EnquiryCategory>() {
            @Override
            public void onResponse(Call<EnquiryCategory> call, Response<EnquiryCategory> response) {
                if (response.body().getResult() != null) {

                    if (response.body().getStatus() == 1) {
                        hidePrd();
                        itemListEnquiryCategory.addAll(response.body().getResult());
                        itemListEnquiryCategoryTemp.addAll(response.body().getResult());

                        Collections.sort(itemListEnquiryCategoryTemp, new Comparator<EnquiryCategory.Result>() {
                            @Override
                            public int compare(final EnquiryCategory.Result object1, final EnquiryCategory.Result object2) {
                                return object1.getEcName().compareTo(object2.getEcName());
                            }
                        });

                        enquiryCategoryDialog(view);


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
            public void onFailure(Call<EnquiryCategory> call, Throwable t) {
                hidePrd();
                //  progressBar.setVisibility(View.GONE);
                Utils.showToast(context, "Please check your internet", R.color.red_dark);

            }
        });



    }

    private void enquiryCategoryDialog(final TextView textView) {

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

        tvDailogTitle.setText("Category");


        edtSearchLocation.setHint("Search Enquiry Category");

        // Utils.showLog("searchlist"+arrayListTemp);

        for (int i = 0; i < itemListEnquiryCategory.size(); i++) {
            arrayListTemp.add(itemListEnquiryCategory.get(i).getEcName());
            arrayListId.add(itemListEnquiryCategory.get(i).getEcId());
        }

        enqryCategory_spn_adapter = new EnqryCategory_Spn_Adapter_New(context, itemListEnquiryCategoryTemp);

        list_location.setAdapter(new EnqryCategory_Spn_Adapter_New(context, itemListEnquiryCategoryTemp));

        list_location.setLayoutManager(new LinearLayoutManager(Add_NewEnquiry_Acitivity.this, LinearLayoutManager.VERTICAL, false));
        list_location.setAdapter(enqryCategory_spn_adapter);

        enqryCategory_spn_adapter.setOnItemClickListener(new EnqryCategory_Spn_Adapter_New.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                categoryid = itemListEnquiryCategoryTemp.get(position).getEcId();
                // Utils.showLog("categoryid" + categoryid);
                tvCategory1.setTextColor(getResources().getColor(R.color.black));
                tvEnquiryCatagory.setText(itemListEnquiryCategoryTemp.get(position).getEcName());
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
                    itemListEnquiryCategoryTemp.clear();
                    // arrayListId.clear();

                    for (int j = 0; j < itemListEnquiryCategory.size(); j++) {
                        String word = edtSearchLocation.getText().toString().toLowerCase();
                        if (itemListEnquiryCategory.get(j).getEcName().toLowerCase().contains(word)) {
                            itemListEnquiryCategoryTemp.add(itemListEnquiryCategory.get(j));
                            //arrayListId.add(itemListEnquiryCategory.get(j).getEcId());
                        }
                    }
                    list_location.setAdapter(new EnqryCategory_Spn_Adapter_New(context, itemListEnquiryCategoryTemp));
                } else {
                    arrayListTemp.clear();
                    arrayListId.clear();
                    for (int i = 0; i < itemListEnquiryCategory.size(); i++) {
                        itemListEnquiryCategoryTemp.add(itemListEnquiryCategory.get(i));
                        // arrayListId.add(itemListEnquiryCategory.get(i).getEcId());
                    }
                    list_location.setAdapter(new EnqryCategory_Spn_Adapter_New(context, itemListEnquiryCategoryTemp));
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

    private void getenquirysourcapi(final TextView view) {

        showPrd();
        // progressBar.setVisibility(View.VISIBLE);
        itemListEnquirySource.clear();
        itemListEnquirySourceTemp.clear();

        Call<EnquirySource> getallenquirysource = webapi.getallenquirysource();
        getallenquirysource.enqueue(new Callback<EnquirySource>() {
            @Override
            public void onResponse(Call<EnquirySource> call, Response<EnquirySource> response) {
                if (response.body().getResult() != null) {

                    if (response.body().getStatus() == 1) {
                        hidePrd();
                        itemListEnquirySource.addAll(response.body().getResult());
                        itemListEnquirySourceTemp.addAll(response.body().getResult());

                        Collections.sort(itemListEnquirySourceTemp, new Comparator<EnquirySource.Result>() {
                            @Override
                            public int compare(final EnquirySource.Result object1, final EnquirySource.Result object2) {
                                return object1.getEsName().compareTo(object2.getEsName());
                            }
                        });
                        enquirySourceDialog(view);


                    } else {
                        Utils.showToast(context, "No Data Found", R.color.red_dark);
                        hidePrd();
                    }

                } else {
                    Utils.showToast(context, "Opps Something wrong", R.color.red_dark);
                    hidePrd();
                }
                hidePrd();
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<EnquirySource> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Utils.showToast(context, "No Internet Connection", R.color.red_dark);

            }
        });



    }

    private void enquirySourceDialog(final TextView textView) {

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
        tvDailogTitle.setText("Source");

        edtSearchLocation.setHint("Search Enquiry Source");
        for (int i = 0; i < itemListEnquirySource.size(); i++) {
            arrayListTemp.add(itemListEnquirySource.get(i).getEsName());
            arrayListId.add(itemListEnquirySource.get(i).getEsId());
        }

        enqrySource_spn_adapter = new EnqrySource_Spn_Adapter_new(context, itemListEnquirySourceTemp);
        list_location.setLayoutManager(new LinearLayoutManager(Add_NewEnquiry_Acitivity.this, LinearLayoutManager.VERTICAL, false));
        list_location.setAdapter(enqrySource_spn_adapter);

        enqrySource_spn_adapter.setOnItemClickListener(new EnqrySource_Spn_Adapter_new.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                sourceid = itemListEnquirySourceTemp.get(position).getEsId();
                // Utils.showLog("sourcid" + sourceid);
                tvSource1.setTextColor(getResources().getColor(R.color.black));
                tvEnquirySource.setText(itemListEnquirySourceTemp.get(position).getEsName());
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
                    itemListEnquirySourceTemp.clear();
                    // arrayListId.clear();

                    for (int j = 0; j < itemListEnquirySource.size(); j++) {
                        String word = edtSearchLocation.getText().toString().toLowerCase();
                        if (itemListEnquirySource.get(j).getEsName().toLowerCase().contains(word)) {
                            itemListEnquirySourceTemp.add(itemListEnquirySource.get(j));
                            //  arrayListId.add(itemListEnquirySource.get(j).getEsId());
                        }
                    }
                    enqrySource_spn_adapter.notifyDataSetChanged();
                    //list_location.setAdapter(new EnqrySource_Spn_Adapter_new(context, itemListEnquirySourceTemp));
                } else {
                    arrayListTemp.clear();
                    arrayListId.clear();
                    for (int i = 0; i < itemListEnquirySource.size(); i++) {
                        itemListEnquirySourceTemp.add(itemListEnquirySource.get(i));
                        //arrayListId.add(itemListEnquirySource.get(i).getEsId());
                    }
                    enqrySource_spn_adapter.notifyDataSetChanged();
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

    private void getproductapi(final TextView view) {

        showPrd();
        // progressBar.setVisibility(View.VISIBLE);
        itemListProduct.clear();
        itemListProductTemp.clear();

        Call<Product> getallproductCall = webapi.getallproduct();
        getallproductCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.body().getResult() != null) {

                    if (response.body().getStatus() == 1) {
                        hidePrd();
                        itemListProduct.addAll(response.body().getResult());
                        itemListProductTemp.addAll(response.body().getResult());

                        Collections.sort(itemListProductTemp, new Comparator<Product.Result>() {
                            @Override
                            public int compare(final Product.Result object1, final Product.Result object2) {
                                return object1.getPName().compareTo(object2.getPName());
                            }
                        });
                        productDialog(view);


                    } else {
                        Utils.showToast(context, "No Data Found", R.color.red_dark);
                        hidePrd();
                    }

                } else {
                    Utils.showToast(context, "Opps Something wrong", R.color.red_dark);
                    hidePrd();
                }
                hidePrd();
                //  progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                hidePrd();
                //progressBar.setVisibility(View.GONE);
                Utils.showToast(context, "Please Check Your Internet.", R.color.red_dark);

            }
        });


    }

    private void productDialog(final TextView textView) {

        LayoutInflater localView = LayoutInflater.from(context);
        View promptsView = localView.inflate(R.layout.dialog_spinner, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        final EditText edtSearchLocation = (EditText) promptsView.findViewById(R.id.edt_spn_search);
        final RecyclerView list_location = (RecyclerView) promptsView.findViewById(R.id.rv_spn);
        final Button btncancle = (Button) promptsView.findViewById(R.id.btnCancle);
        final Button btnsubmit = (Button) promptsView.findViewById(R.id.btnOhk);
        final LinearLayout liBtn = (LinearLayout) promptsView.findViewById(R.id.liBtn);
        final TextView tvDailogTitle = (TextView) promptsView.findViewById(R.id.tvDailogTitle);

        tvDailogTitle.setText("Product");


        final ArrayList<String> arrayListTemp = new ArrayList<>();
        final ArrayList<String> arrayListId = new ArrayList<>();

        edtSearchLocation.setHint("Search Product");
        for (int i = 0; i < itemListProduct.size(); i++) {
            arrayListTemp.add(itemListProduct.get(i).getPName());
            arrayListId.add(itemListProduct.get(i).getPId());
        }

        product_spn_adapter = new Product_Spn_Adapter_New(context, itemListProductTemp);
        list_location.setLayoutManager(new LinearLayoutManager(Add_NewEnquiry_Acitivity.this, LinearLayoutManager.VERTICAL, false));
        list_location.setAdapter(product_spn_adapter);
        liBtn.setVisibility(View.VISIBLE);
        liBtn.setVisibility(View.VISIBLE);

        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liBtn.setVisibility(View.VISIBLE);
                alertDialog.dismiss();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liBtn.setVisibility(View.VISIBLE);
                List<Product.Result> selectedlist = product_spn_adapter.getSelectedlist();
                alertDialog.dismiss();


                for (int i = 0; i < selectedlist.size(); i++) {

                    selectedcategorylist = selectedlist.get(i).getPName();
                    productidlist.add(selectedlist.get(i).getPId());
                    // Utils.showLog("===producrid" + productidlist);
                    tvProduct1.setTextColor(getResources().getColor(R.color.black));
                    tvEnquiryProduct.setText(tvEnquiryProduct.getText() +selectedcategorylist+" , ");

                }
            }
        });


        edtSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int position, int i1, int i2) {

                if (edtSearchLocation.getText().toString().trim().length() > 0) {
                    itemListProductTemp.clear();
                    //  arrayListId.clear();

                    for (int j = 0; j < itemListProduct.size(); j++) {
                        String word = edtSearchLocation.getText().toString().toLowerCase();
                        if (itemListProduct.get(j).getPName().toLowerCase().contains(word)) {
                            itemListProductTemp.add(itemListProduct.get(j));
                            //arrayListId.add(itemListProduct.get(j).getPId());
                        }
                    }
                    product_spn_adapter.notifyDataSetChanged();
                    // list_location.setAdapter(new Product_Spn_Adapter_New(context, itemListProductTemp));
                } else {
                    arrayListTemp.clear();
                    arrayListId.clear();
                    for (int i = 0; i < itemListProduct.size(); i++) {
                        itemListProductTemp.add(itemListProduct.get(i));
                        //arrayListId.add(itemListProduct.get(i).getPId());
                    }
                    product_spn_adapter.notifyDataSetChanged();
                    //list_location.setAdapter(new Product_Spn_Adapter_New(context, itemListProductTemp));
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

    private void getUserApi(final TextView view) {

        progressBar.setVisibility(View.VISIBLE);
        itemListUser.clear();
        itemListUserTemp.clear();

        Call<Users> getallusers = webapi.getallusers();
        getallusers.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.body().getResult() != null) {

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
                        userDialog(view);


                    } else {
                        Utils.showToast(context, "No Data Found", R.color.red_dark);
                    }

                } else {
                    Utils.showToast(context, "Opps Something wrong", R.color.red_dark);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Utils.showToast(context, "Please check your internet", R.color.red_dark);

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

        alluseradapter = new AllUserAdapter(context, itemListUserTemp);
        list_location.setLayoutManager(new LinearLayoutManager(Add_NewEnquiry_Acitivity.this, LinearLayoutManager.VERTICAL, false));
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
                tvAssignUser.setTextColor(getResources().getColor(R.color.black));
                tvAssignUserSpn.setText(itemListUserTemp.get(position).getName());
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

    private void getCategoryDepapi(final TextView view) {
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

                        enquiryCategoryDepDailog(view);

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
                progressBar.setVisibility(View.GONE);
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

        edtSearchLocation.setHint("Search Enquiry Category(Department)");
        for (int i = 0; i < allstateitemList.size(); i++) {
            arrayListTemp.add(allstateitemList.get(i).getStateName());
            arrayListId.add(allstateitemList.get(i).getStateName());
        }

        allCategoryDepaement_spn_adapter = new AllCategoryDepaement_Spn_Adapter(context, allCategoryDeptitemListTemp);
        list_location.setLayoutManager(new LinearLayoutManager(Add_NewEnquiry_Acitivity.this, LinearLayoutManager.VERTICAL, false));
        list_location.setAdapter(allCategoryDepaement_spn_adapter);

        allCategoryDepaement_spn_adapter.setOnItemClickListener(new AllCategoryDepaement_Spn_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                categorydepid = allCategoryDeptitemListTemp.get(position).getCategoryId();
                // Utils.showLog("sourcid" + sourceid);
                tvEnquiryCategDepory.setTextColor(getResources().getColor(R.color.black));
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


    private void getAllStateapi(final TextView view) {
        showPrd();

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
                        enquirystatedailog(view);

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
                progressBar.setVisibility(View.GONE);
                hidePrd();
                Utils.showToast(context, "No Internet Connection", R.color.red_dark);
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

        edtSearchLocation.setHint("Search Enquiry State");
        for (int i = 0; i < allstateitemList.size(); i++) {
            arrayListTemp.add(allstateitemList.get(i).getStateName());
            arrayListId.add(allstateitemList.get(i).getStateName());
        }

        allState_spn_adapter = new AllState_Spn_Adapter(context, allstateitemListTemp);
        list_location.setLayoutManager(new LinearLayoutManager(Add_NewEnquiry_Acitivity.this, LinearLayoutManager.VERTICAL, false));
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

/*
    @Override
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
