package com.dharkanenquiry.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
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

import com.dharkanenquiry.Adapter.EnqryCategory_Spn_Adapter_New;
import com.dharkanenquiry.Adapter.EnqrySource_Spn_Adapter_new;
import com.dharkanenquiry.Adapter.Product_Spn_Adapter_New;
import com.dharkanenquiry.Model.EditEnquiry;
import com.dharkanenquiry.Model.EnquiryCategory;
import com.dharkanenquiry.Model.EnquirySource;
import com.dharkanenquiry.Model.Product;
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

public class EditEnquiry_Activity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    SpinKitView progressBar;
    @BindView(R.id.tvCompnyname1)
    TextView tvCompnyname1;
    @BindView(R.id.tvCompanySpn)
    TextView tvCompanySpn;
    @BindView(R.id.rvCompnyspn)
    RelativeLayout rvCompnyspn;
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
    @BindView(R.id.tvCategory1)
    TextView tvCategory1;
    @BindView(R.id.tvEnquiryCatagory)
    TextView tvEnquiryCatagory;
    @BindView(R.id.rvEnquiryCatSpn)
    RelativeLayout rvEnquiryCatSpn;
    @BindView(R.id.etOtherNote)
    EditText etOtherNote;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    WebApi webAPI;
    Context context;

    List<Product.Result> itemListProduct = new ArrayList<>();
    List<Product.Result> itemListProductTemp = new ArrayList<>();

    List<EnquirySource.Result> itemListEnquirySource = new ArrayList<>();
    List<EnquirySource.Result> itemListEnquirySourceTemp = new ArrayList<>();

    List<EnquiryCategory.Result> itemListEnquiryCategory = new ArrayList<>();
    List<EnquiryCategory.Result> itemListEnquiryCategoryTemp = new ArrayList<>();

    List<String> productidlist = new ArrayList<>();

    String customerid = "", customer_name, saleperson_name, enqProduct, enqSource, enqImprotance, enqCategory, enqOtherNote, enquiry_id, selectedcategorylist = "";
    String sourceid = "", enqProductid, enqSourceid, enqCategoryid;
    String categoryid = "";
    String importanceid = "";

    EnqryCategory_Spn_Adapter_New enqryCategory_spn_adapter;
    EnqrySource_Spn_Adapter_new enqrySource_spn_adapter;
    Product_Spn_Adapter_New product_spn_adapter;

    String FinalSalePerson;
    String FinalCustomer;
    List<String> FinalProductList = new ArrayList<>();
    String FinalSource;
    String FinalImprotance;
    String FinalCategory;
    String FinalOtherNote;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvSalePerson)
    TextView tvSalePerson;
    @BindView(R.id.tvSalePersonName)
    TextView tvSalePersonName;
    @BindView(R.id.rvSalePersons)
    RelativeLayout rvSalePersons;

    Boolean ischecked = false;
    ProgressDialog progressDialog;

    ImageView ivBackEdit;
    UserProfile_Activity userProfileActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_enquiry);
        ButterKnife.bind(this);

        context = EditEnquiry_Activity.this;
        initUI();


    }

    private void initUI() {

        setSupportActionBar(toolbar);
        tvTitle.setText("Edit Profile");
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setDisplayShowHomeEnabled(true);


        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        Sprite doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);

        ivBackEdit = (ImageView) findViewById(R.id.ivBackEdit);
        webAPI = Utils.getRetrofitClient().create(WebApi.class);

        enquiry_id = getIntent().getStringExtra("enquiry_id");
        customer_name = getIntent().getStringExtra("customer_name");
        saleperson_name = getIntent().getStringExtra("sales_person");
        enqProductid = getIntent().getStringExtra("enquiry_for");
        enqProduct = getIntent().getStringExtra("enquiry_for_name");
        enqSourceid = getIntent().getStringExtra("enquiry_source");
        enqSource = getIntent().getStringExtra("enquiry_source_name");
        enqImprotance = getIntent().getStringExtra("enquiry_importance");
        enqCategoryid = getIntent().getStringExtra("enquiry_category");
        enqCategory = getIntent().getStringExtra("enquiry_category_name");
        enqOtherNote = getIntent().getStringExtra("enquiry_other_details");

        productidlist.add(enqProductid);
        FinalSalePerson = saleperson_name;
        FinalCustomer = customer_name;
        FinalProductList = productidlist;
        FinalSource = enqSourceid;
        FinalImprotance = enqImprotance;
        FinalCategory = enqCategoryid;

        tvSalePersonName.setText(saleperson_name);
        tvCompanySpn.setText(customer_name);
        tvEnquiryProduct.setText(enqProduct);
        tvEnquirySource.setText(enqSource);
        tvImportance.setText(enqImprotance);
        tvEnquiryCatagory.setText(enqCategory);
        etOtherNote.setText(enqOtherNote);


        ivBackEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

        rvEnquiryCatSpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getenquirycategoryapi(tvEnquiryCatagory);

            }
        });


        rvImportance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {
                        "High", "Very High", "Medium", "Low", "Very Low"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(EditEnquiry_Activity.this);
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        tvImportance.setText(items[item]);
                        tvImportance1.setTextColor(getResources().getColor(R.color.black));
                        FinalImprotance = tvImportance.getText().toString();


                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }


        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvCompanySpn.getText().toString().trim().isEmpty()) {
                    tvCompnyname1.setTextColor(getResources().getColor(R.color.msg_fail));
                    Utils.showToast(context, "Please Select CompanyName", R.color.msg_fail);
                    //tvCompanySpn.setError("Select CompanyName");
                    //tvCompanySpn.requestFocus();
                    return;
                }
                if (tvEnquiryProduct.getText().toString().trim().isEmpty()) {
                    tvProduct1.setTextColor(getResources().getColor(R.color.msg_fail));
                    Utils.showToast(context, "Please Select Product", R.color.msg_fail);
                    // tvEnquiryProduct.setError("Select ProductName");
                    //tvEnquiryProduct.requestFocus();
                    return;
                }
                if (tvEnquirySource.getText().toString().trim().isEmpty()) {
                    tvSource1.setTextColor(getResources().getColor(R.color.msg_fail));
                    Utils.showToast(context, "Please Select Source", R.color.msg_fail);
                    // tvEnquirySource.setError("Select Source");
                    // tvEnquirySource.requestFocus();
                    return;
                }
                if (tvImportance.getText().toString().trim().isEmpty()) {
                    tvImportance1.setTextColor(getResources().getColor(R.color.msg_fail));
                    Utils.showToast(context, "Please Select Importance", R.color.msg_fail);
                    //tvImportance.setError("Select Importance");
                    // tvImportance.requestFocus();
                    return;
                }

                if (tvEnquiryCatagory.getText().toString().trim().isEmpty()) {
                    tvCategory1.setTextColor(getResources().getColor(R.color.msg_fail));
                    Utils.showToast(context, "Please Select Category", R.color.msg_fail);
                    // tvEnquiryCatagory.setError("Select Category");
                    // tvEnquiryCatagory.requestFocus();
                    return;
                }


                Utils.showLog("==edit" + SharedPrefsUtils.getSharedPreferenceString(EditEnquiry_Activity.this, SharedPrefsUtils.USER_ID) + enquiry_id + " " + productidlist + "   " + FinalSource + " " + FinalImprotance + " " + FinalCategory + " " + etOtherNote.getText().toString());

                showPrd();
                // Call<EditEnquiry> editEnquiryCall = webAPI.editenquiryapi(enquiry_id,productidlist.toString().substring(1,3),sourceid,importanceid,categoryid,etOtherNote.getText().toString());

                Call<EditEnquiry>  editEnquiryCall = webAPI.geteditenquiryapi(enquiry_id,SharedPrefsUtils.getSharedPreferenceString(EditEnquiry_Activity.this, SharedPrefsUtils.USER_ID),productidlist,FinalSource,FinalImprotance,FinalCategory,etOtherNote.getText().toString());
                editEnquiryCall.enqueue(new Callback<EditEnquiry>() {
                    @Override
                    public void onResponse(Call<EditEnquiry> call, Response<EditEnquiry> response) {

                        if (response.body() != null ){

                            if (response.body().getStatus() == 1) {

                                hidePrd();
                                finish();

                                Utils.showToast(context, "Edit Enquiry Successful", R.color.green_fed);

                            }else {
                                Utils.showToast(context, "oops Something wrong", R.color.msg_fail);
                                hidePrd();
                            }

                        }else {
                            Utils.showToast(context, "Please Try Again!", R.color.msg_fail);
                        }
                        hidePrd();
                    }

                    @Override
                    public void onFailure(Call<EditEnquiry> call, Throwable t) {

                        hidePrd();
                        //progressBar.setVisibility(View.GONE);
                        Utils.showLog("==msg"+t.getMessage());
                        Utils.showErrorToast(EditEnquiry_Activity.this);
                    }
                });



            }
        });


    }

    private void getenquirycategoryapi(final TextView view) {

        showPrd();
        //  progressBar.setVisibility(View.VISIBLE);
        itemListEnquiryCategory.clear();
        itemListEnquiryCategoryTemp.clear();

        Call<EnquiryCategory> enquiryCategoryCall = webAPI.getenquirycategory();
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
                        Utils.showToast(context, "No Data Found", R.color.msg_fail);
                        hidePrd();
                    }

                } else {
                    Utils.showToast(context, "Opps Something wrong", R.color.msg_fail);
                }
                hidePrd();
                // progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<EnquiryCategory> call, Throwable t) {
                hidePrd();
                //  progressBar.setVisibility(View.GONE);
                Utils.showToast(context, "Please Try Again", R.color.msg_fail);

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


        edtSearchLocation.setHint("Search EnquiryCategory");

        // Utils.showLog("searchlist"+arrayListTemp);

        for (int i = 0; i < itemListEnquiryCategory.size(); i++) {
            arrayListTemp.add(itemListEnquiryCategory.get(i).getEcName());
            arrayListId.add(itemListEnquiryCategory.get(i).getEcId());
        }

        enqryCategory_spn_adapter = new EnqryCategory_Spn_Adapter_New(context, itemListEnquiryCategoryTemp);

        list_location.setAdapter(new EnqryCategory_Spn_Adapter_New(context, itemListEnquiryCategoryTemp));

        list_location.setLayoutManager(new LinearLayoutManager(EditEnquiry_Activity.this, LinearLayoutManager.VERTICAL, false));
        list_location.setAdapter(enqryCategory_spn_adapter);

        enqryCategory_spn_adapter.setOnItemClickListener(new EnqryCategory_Spn_Adapter_New.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                FinalCategory = itemListEnquiryCategoryTemp.get(position).getEcId();
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

        Call<EnquirySource> getallenquirysource = webAPI.getallenquirysource();
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
                        Utils.showToast(context, "No Data Found", R.color.msg_fail);
                        hidePrd();
                    }

                } else {
                    Utils.showToast(context, "Opps Something wrong", R.color.msg_fail);
                    hidePrd();
                }
                hidePrd();
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<EnquirySource> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Utils.showToast(context, "Please Try Again", R.color.msg_fail);

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
        tvDailogTitle.setText("Enquiry Source");

        edtSearchLocation.setHint("Search EnquirySource");
        for (int i = 0; i < itemListEnquirySource.size(); i++) {
            arrayListTemp.add(itemListEnquirySource.get(i).getEsName());
            arrayListId.add(itemListEnquirySource.get(i).getEsId());
        }

        enqrySource_spn_adapter = new EnqrySource_Spn_Adapter_new(context, itemListEnquirySourceTemp);
        list_location.setLayoutManager(new LinearLayoutManager(EditEnquiry_Activity.this, LinearLayoutManager.VERTICAL, false));
        list_location.setAdapter(enqrySource_spn_adapter);

        enqrySource_spn_adapter.setOnItemClickListener(new EnqrySource_Spn_Adapter_new.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FinalSource = itemListEnquirySourceTemp.get(position).getEsId();
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

        Call<Product> getallproductCall = webAPI.getallproduct();
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

                        String[] prodArray=enqProductid.split(",");
                        Collections.addAll(productidlist, prodArray);
                        for (int i = 0; i <itemListProductTemp.size() ; i++) {
                            for (int j = 0; j <prodArray.length ; j++) {
                                if (itemListProductTemp.get(i).getPId().equals(prodArray[j])){
                                    itemListProductTemp.get(i).setSelected(true);
                                }
                            }
                        }

                        productDialog(view);


                    } else {
                        Utils.showToast(context, "No Data Found", R.color.msg_fail);
                        hidePrd();
                    }

                } else {
                    Utils.showToast(context, "Opps Something wrong", R.color.msg_fail);
                    hidePrd();
                }
                hidePrd();
                //  progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                hidePrd();
                //progressBar.setVisibility(View.GONE);
                Utils.showToast(context, "Please Try Again", R.color.msg_fail);

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

        tvDailogTitle.setText("Enquiry Product");


        final ArrayList<String> arrayListTemp = new ArrayList<>();
        final ArrayList<String> arrayListId = new ArrayList<>();

        edtSearchLocation.setHint("Search Product");
        for (int i = 0; i < itemListProduct.size(); i++) {
            arrayListTemp.add(itemListProduct.get(i).getPName());
            arrayListId.add(itemListProduct.get(i).getPId());
        }


        Collections.sort(itemListProductTemp, new Comparator<Product.Result>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(final Product.Result object1, final Product.Result object2) {

               // return object1.getPName().compareTo(object2.getPName());
                return Boolean.compare(object2.isSelected(),object1.isSelected());
            }
        });

        product_spn_adapter = new Product_Spn_Adapter_New(context, itemListProductTemp);
        list_location.setLayoutManager(new LinearLayoutManager(EditEnquiry_Activity.this, LinearLayoutManager.VERTICAL, false));
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

                productidlist.clear();
                String txtProd="";
                tvEnquiryProduct.setText("");
                for (int i = 0; i < selectedlist.size(); i++) {

                    selectedcategorylist = selectedlist.get(i).getPName();
                    productidlist.add(selectedlist.get(i).getPId());
                    // Utils.showLog("===producrid" + productidlist);

                    tvProduct1.setTextColor(getResources().getColor(R.color.black));
                    txtProd=txtProd+selectedcategorylist+",";

                   /* tvProduct1.setTextColor(getResources().getColor(R.color.black));
                    tvEnquiryProduct.setText(tvEnquirySource.getText() + selectedcategorylist + " ,");*/
                }
                tvEnquiryProduct.setText(txtProd.substring(0,txtProd.length()-1));
                Utils.showLog("=== productidlist "+productidlist);
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
