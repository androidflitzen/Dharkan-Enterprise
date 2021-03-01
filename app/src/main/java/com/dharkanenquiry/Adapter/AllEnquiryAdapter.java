package com.dharkanenquiry.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dharkanenquiry.Activity.Enquiry_Activity;
import com.dharkanenquiry.Activity.UserProfile_Activity;
import com.dharkanenquiry.Activity.ViewPDF;
import com.dharkanenquiry.Model.AllEnquiry;
import com.dharkanenquiry.Model.Request_S_O;
import com.dharkanenquiry.Model.Request_quotation;
import com.dharkanenquiry.utils.CToast;
import com.dharkanenquiry.utils.FileUtils;
import com.dharkanenquiry.utils.Network;
import com.dharkanenquiry.utils.Permission;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.BuildConfig;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class

AllEnquiryAdapter extends RecyclerView.Adapter<AllEnquiryAdapter.ViewHolder> {

    Context context;
    List<AllEnquiry.Result> allenquiryList = new ArrayList<>();
    ;
    Gson gson;
    WebApi webapi;

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    String uniqID;
    int action, selectedPosition;
    ProgressDialog pDialog;
    DecimalFormat decimalFormat;
    //Dialog dialog;
    AlertDialog progressDialog;
    Enquiry_Activity enquiryActivity;

    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 5;

    public AllEnquiryAdapter(Context context, List<AllEnquiry.Result> allenquiryList, RecyclerView rvEnquiry) {
        this.context = context;
        this.allenquiryList = allenquiryList;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Downloading file. Please wait...");

        webapi = Utils.getRetrofitClient().create(WebApi.class);

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rvEnquiry.getLayoutManager();
        rvEnquiry.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });

    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public boolean getLoaded() {
        return isLoading;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.enquiry_list, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        if (allenquiryList.get(position).getImportance().equals("High")) {
            holder.tvImportance.setBackground(context.getResources().getDrawable(R.drawable.tag_high));
            holder.tvImportance.setText(allenquiryList.get(position).getImportance());

        } else if (allenquiryList.get(position).getImportance().equals("Very High")) {
            holder.tvImportance.setBackground(context.getResources().getDrawable(R.drawable.tag_red));
            holder.tvImportance.setText(allenquiryList.get(position).getImportance());

        } else if (allenquiryList.get(position).getImportance().equals("Medium")) {
            holder.tvImportance.setBackground(context.getResources().getDrawable(R.drawable.tag_medium));
            holder.tvImportance.setText(allenquiryList.get(position).getImportance());
        } else if (allenquiryList.get(position).getImportance().equals("Low")) {
            holder.tvImportance.setBackground(context.getResources().getDrawable(R.drawable.tag_low));
            holder.tvImportance.setText(allenquiryList.get(position).getImportance());
        } else if (allenquiryList.get(position).getImportance().equals("Very Low")) {
            holder.tvImportance.setBackground(context.getResources().getDrawable(R.drawable.tag_vrylow));
            holder.tvImportance.setText(allenquiryList.get(position).getImportance());
        }

        holder.tvImportance.setText(allenquiryList.get(position).getImportance());
        holder.tvEnquiryidNo.setText(allenquiryList.get(position).getUniqueId());

        holder.tvEnquiryStatus1.setText(allenquiryList.get(position).getEnquiryStatus());


        // holder.tvAddedOn1.setText(allenquiryList.get(position).getAddedOn());

        //Utils.showLog("==date"+allenquiryList.get(position).getAddedOn());

        String date = allenquiryList.get(position).getAddedOn();
        String[] datearray = date.split(" ");
        String date1 = String.valueOf(Integer.valueOf(datearray[0]));
        holder.tvdate.setText(date1);
        String month = datearray[1];
        holder.tvMonth.setText(month.substring(0, 3));
        String year = datearray[2];
        holder.tvYear.setText(year);
        String time = datearray[3];
        holder.tvTime.setText(time);
        String time1 = datearray[4];
        holder.tvTime1.setText(time1);


        holder.tvSalePerson1.setText(allenquiryList.get(position).getSalesPerson());
        holder.tvCustomerName1.setText(allenquiryList.get(position).getCustomerName());
        holder.tvSource.setText(allenquiryList.get(position).getSource());
        holder.tvCityname.setText(allenquiryList.get(position).getCity());
        holder.tvRegion.setText(allenquiryList.get(position).getRegion());
        holder.tvRegion.setText(allenquiryList.get(position).getRegion());

        if (allenquiryList.get(position).getQuotationRequestStatus().equals("0")) {
            holder.tvRequeststatus.setText("Request a Quotation");
            holder.tvRequeststatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    opendailogRequestsent(position);

                }
            });

        } else if (allenquiryList.get(position).getQuotationRequestStatus().equals("1")) {
            holder.tvRequeststatus.setText("Quotation Request Sent");
            holder.lirequeststatus.setBackgroundColor(R.drawable.label_tag_sent);
            holder.tvRequeststatus.setTextColor(context.getResources().getColor(R.color.white));
        } else if (allenquiryList.get(position).getQuotationRequestStatus().equals("2")) {
            if (allenquiryList.get(position).getQuotationUrl() != null && !allenquiryList.get(position).getQuotationUrl().equals("")) {
                holder.ivRequeststatusPdf.setVisibility(View.VISIBLE);
                holder.txtRequestCorrectionQ.setVisibility(View.VISIBLE);
                holder.tvRequeststatus.setVisibility(View.GONE);
            }else {
                holder.ivRequeststatusPdf.setVisibility(View.GONE);
                holder.txtRequestCorrectionQ.setVisibility(View.GONE);
            }
        }

        //holder.tvRequeststatus.setText(allenquiryList.get(position).getQuotationRequestStatus());
        if (allenquiryList.get(position).getOrderRequestStatus().equals("0")) {
            holder.tvOrderstatus.setText("Request a Order");

            holder.tvOrderstatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    opendailogRequestSO(position);

                }
            });

        } else if (allenquiryList.get(position).getOrderRequestStatus().equals("1")) {
            holder.tvOrderstatus.setText("Order Request Sent");
            holder.liorderstatus.setBackgroundColor(R.drawable.label_tag_sent);
            holder.tvOrderstatus.setTextColor(context.getResources().getColor(R.color.white));
        } else if (allenquiryList.get(position).getOrderRequestStatus().equals("2")) {
            if (allenquiryList.get(position).getOrderUrl() != null && !allenquiryList.get(position).getOrderUrl().equals("")) {
                holder.ivOrderstatusPdf.setVisibility(View.VISIBLE);
                holder.txtRequestCorrection.setVisibility(View.VISIBLE);
                holder.tvOrderstatus.setVisibility(View.GONE);

                System.out.println("===========pdf url  "+allenquiryList.get(position).getOrderUrl());

            }else {
                holder.ivOrderstatusPdf.setVisibility(View.GONE);
                holder.txtRequestCorrection.setVisibility(View.GONE);
            }
        }
        // holder.tvOrderstatus.setText(allenquiryList.get(position).getOrderRequestStatus());

        holder.ivRequeststatusPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                if (!Network.isNetworkAvailable(context)) {
                    return;
                }
                action = 0;
                selectedPosition = position;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Permission.hasPermissions((Activity) context, permissions)) {
                        downloadFile(position, action);
                    } else {
                        Permission.requestPermissions((Activity) context, permissions);
                    }
                } else {
                    downloadFile(position, action);
                }

            }
        });

        holder.ivOrderstatusPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                if (!Network.isNetworkAvailable(context)) {
                    return;
                }
                action = 0;
                selectedPosition = position;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Permission.hasPermissions((Activity) context, permissions)) {
                        downloadFile1(position, action);
                    } else {
                        Permission.requestPermissions((Activity) context, permissions);
                    }
                } else {
                    downloadFile1(position, action);
                }

            }
        });

        holder.txtRequestCorrection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendailogRequestSO(position);
            }
        });

        holder.txtRequestCorrectionQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                opendailogRequestsent(position);

            }
        });


        holder.liMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UserProfile_Activity.class)
                        .putExtra("enquiry_id", allenquiryList.get(position).getEnquiryId())
                        .putExtra("customer_name", allenquiryList.get(position).getCustomerName())
                        .putExtra("customer_id", allenquiryList.get(position).getCustomerId()));
                ((Activity) context).overridePendingTransition(R.anim.feed_in, R.anim.feed_out);
                /*Intent intent = new Intent(context, UserProfile_Activity.class);
                context.startActivity(intent);*/

            }
        });


    }

    private void opendailogRequestSO(final int position) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setMessage("Are you sure sent request Order?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //  Utils.showLog("==" + allenquiryList.get(position).getEnquiryId());
                        RequestOrderSOAPI(position);
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void RequestOrderSOAPI(final int position) {

        progressDialog.show();

        Call<Request_S_O> deleteenquiryCall = webapi.todorequestSOsent(allenquiryList.get(position).getEnquiryId());
        deleteenquiryCall.enqueue(new Callback<Request_S_O>() {
            @Override
            public void onResponse(Call<Request_S_O> call, Response<Request_S_O> response) {

                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {

                        notifyDataSetChanged();
                        // enquiryActivity.getAllEnquirylist();
                        Utils.showToast(context, "Order Request Sent successful..", R.color.green_fed);

                    } else {
                        Utils.showToast(context, "Something Wrong...", R.color.msg_fail);
                    }

                } else {
                    Utils.showToast(context, "Please try again", R.color.msg_fail);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Request_S_O> call, Throwable t) {
                progressDialog.dismiss();
                Utils.showToast(context, "Opps something went to wrong..", R.color.msg_fail);
            }
        });

    }

    private void opendailogRequestsent(final int position) {


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setMessage("Are you sure sent request Quotation?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //  Utils.showLog("==" + allenquiryList.get(position).getEnquiryId());
                        RequestQuotationAPI(position);
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void RequestQuotationAPI(final int position) {

        progressDialog.show();

        Call<Request_quotation> deleteenquiryCall = webapi.todorequestQuotationsent(allenquiryList.get(position).getEnquiryId());
        deleteenquiryCall.enqueue(new Callback<Request_quotation>() {
            @Override
            public void onResponse(Call<Request_quotation> call, Response<Request_quotation> response) {

                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {

                        notifyDataSetChanged();
                      //  enquiryActivity.getAllEnquirylist();
                        Utils.showToast(context, "Quotation Request Sent successful..", R.color.green_fed);

                    } else {
                        Utils.showToast(context, "Something Wrong...", R.color.msg_fail);
                    }

                } else {
                    Utils.showToast(context, "Please try again", R.color.msg_fail);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Request_quotation> call, Throwable t) {
                progressDialog.dismiss();
                Utils.showToast(context, "Opps something went to wrong..", R.color.msg_fail);
            }
        });


    }

    private void downloadFile1(int position, int action) {
        // Utils.showLog("==== downloadFile1");
        Log.w("ravi",allenquiryList.get(position).getEnquiryId() + "Order_Status_" + ".pdf");
        uniqID = allenquiryList.get(position).getEnquiryId();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
       // File myFile = new File(new File(Utils.getItemDir()), allenquiryList.get(position).getEnquiryId() + "Order_Status_" + ".pdf");

        try {
            DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
            File myFile = new File(new File(Utils.getItemDir()), allenquiryList.get(position).getEnquiryId() + "Order_Status_"+ df.format(new Date()) + ".pdf");
            if (myFile.exists()) {
                if (action == 0) {
                    openPDFFile(myFile,allenquiryList.get(position).getOrderUrl());
                } else if (action == 1) {
                    sendMailInvoice(myFile);
                }
            } else {
                myFile.createNewFile();
                new DownloadFileFromURL(myFile, action).execute(allenquiryList.get(position).getOrderUrl());
            }
        } catch (Exception e) {
            Utils.showToast(context, "Something Wrong...", R.color.msg_fail);
            e.printStackTrace();
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return allenquiryList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvdate)
        TextView tvdate;
        @BindView(R.id.tvMonth)
        TextView tvMonth;
        @BindView(R.id.tvYear)
        TextView tvYear;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvTime1)
        TextView tvTime1;
        @BindView(R.id.tvSource)
        TextView tvSource;
        @BindView(R.id.tvEnquiryidNo)
        TextView tvEnquiryidNo;
        @BindView(R.id.tvEnquiryStatus1)
        TextView tvEnquiryStatus1;
        @BindView(R.id.tvImportance)
        TextView tvImportance;
        @BindView(R.id.tvCustomerName1)
        TextView tvCustomerName1;
        @BindView(R.id.tvSalePerson1)
        TextView tvSalePerson1;
        @BindView(R.id.tvCityname)
        TextView tvCityname;
        @BindView(R.id.tvRegion)
        TextView tvRegion;
        @BindView(R.id.tvRequeststatus)
        TextView tvRequeststatus;
        @BindView(R.id.lirequeststatus)
        LinearLayout lirequeststatus;
        @BindView(R.id.ivRequeststatusPdf)
        ImageView ivRequeststatusPdf;
        @BindView(R.id.tvOrderstatus)
        TextView tvOrderstatus;
        @BindView(R.id.liorderstatus)
        LinearLayout liorderstatus;
        @BindView(R.id.ivOrderstatusPdf)
        ImageView ivOrderstatusPdf;
        @BindView(R.id.liMain)
        LinearLayout liMain;
        @BindView(R.id.txtRequestCorrection)
        TextView txtRequestCorrection;
        @BindView(R.id.txtRequestCorrectionQ)
        TextView txtRequestCorrectionQ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private void downloadFile(int position, int action) {
        uniqID = allenquiryList.get(position).getEnquiryId();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
      //  File myFile = new File(new File(Utils.getItemDir()), allenquiryList.get(position).getEnquiryId() + "Request_Status_" + ".pdf");
        try {
            DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
            File myFile = new File(new File(Utils.getItemDir()), allenquiryList.get(position).getEnquiryId() + "Request_Status_"+ df.format(new Date()) + ".pdf");
            if (myFile.exists()) {
                if (action == 0) {
                    openPDFFile(myFile,allenquiryList.get(position).getOrderUrl());
                } else if (action == 1) {
                    sendMailInvoice(myFile);
                }
            } else {
                myFile.createNewFile();
                new DownloadFileFromURL(myFile, action).execute(allenquiryList.get(position).getQuotationUrl());
            }
        } catch (Exception e) {
            Utils.showToast(context, "Something Wrong...", R.color.msg_fail);
            e.printStackTrace();
        }
    }

    private void openPDFFile(File myFile,String fileUrl) {

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

      /*  Intent defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_VIEW, Intent.CATEGORY_APP_BROWSER);
        //defaultBrowser.setData(Uri.parse("https://docs.google.com/gview?embedded=true&url="+fileUrl));
        defaultBrowser.setData(Uri.parse(fileUrl));
        context.startActivity(defaultBrowser);*/

        /*Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(fileUrl));
        context.startActivity(i);*/

        /*Intent defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER);
        defaultBrowser.setData(Uri.parse(fileUrl));
        //defaultBrowser.setDataAndType(Uri.parse(fileUrl), "application/pdf");
        context.startActivity(defaultBrowser);*/

        Intent intent=new Intent(context, ViewPDF.class);
        //intent.putExtra("url",fileUrl);
        intent.putExtra("MY_FILE",myFile);
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
            pDialog.show();
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
            pDialog.dismiss();
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


}
