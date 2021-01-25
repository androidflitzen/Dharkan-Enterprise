package com.dharkanenquiry.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dharkanenquiry.Activity.Customer_Details_Activity;
import com.dharkanenquiry.Activity.Location_Tracker_Activity;
import com.dharkanenquiry.Model.AllCustomerList;
import com.dharkanenquiry.Model.Users;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.google.gson.Gson;

import java.util.List;

public class AllSaleslistAdapter extends RecyclerView.Adapter<AllSaleslistAdapter.ViewHolder> {

    Context context;
    List<Users.Result> allCustomerlist;
    Gson gson;
    WebApi webapi;
    ProgressDialog pDialog;
    AlertDialog progressDialog;


    public AllSaleslistAdapter(Context context, List<Users.Result> allcustomerlist){

        this.context = context;
        this.allCustomerlist = allcustomerlist;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        webapi = Utils.getRetrofitClient().create(WebApi.class);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.salespersonlist_iteam, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.tvCustomerName.setText(allCustomerlist.get(position).getName());
        holder.tvCustomerPhone.setText(allCustomerlist.get(position).getPhoneNo());
        holder.tvCustomeremail.setText(allCustomerlist.get(position).getEmail());

        holder.rlViewmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Location_Tracker_Activity.class);
                intent.putExtra("sales_id", allCustomerlist.get(position).getUserId());
                intent.putExtra("sales_name", allCustomerlist.get(position).getName());
                context.startActivity(intent);

            }
        });

        holder.tvCustomerPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +holder.tvCustomerPhone.getText().toString()));
                context.startActivity(intent);
            }
        });

        holder.tvCustomeremail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Sendtomail = allCustomerlist.get(position).getEmail();
              //  openMail(Sendtomail);
            }
        });

    }

    private void openMail(String Sendtomail) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Sendtomail));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(Intent.createChooser(emailIntent, "Choose..."));
    }

    @Override
    public int getItemCount() {
        return allCustomerlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCustomerName,tvCustomerPhone,tvCustomerCategory,tvCustomeremail;
        RelativeLayout rlViewmain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCustomerName = (TextView) itemView.findViewById(R.id.tvCustomerName);
            tvCustomerPhone = (TextView) itemView.findViewById(R.id.tvCustomerPhone);
            tvCustomeremail = (TextView) itemView.findViewById(R.id.tvCustomeremail);
            rlViewmain = (RelativeLayout)itemView.findViewById(R.id.rlViewmain);
        }
    }
}
