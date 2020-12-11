package com.dharkanenquiry.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dharkanenquiry.Model.Customers;
import com.dharkanenquiry.vasudhaenquiry.R;

import java.util.ArrayList;
import java.util.List;

public class Customer_Spn_Adapter_New extends RecyclerView.Adapter<Customer_Spn_Adapter_New.MyViewHolder> {

    Context context;
    private Boolean[] selected;
    List<Customers.Result> itemList ;
    List<Customers.Result> Selectedlist = new ArrayList<>();
    OnItemClickListener mItemClickListener;

    public Customer_Spn_Adapter_New(Context context, List<Customers.Result> iteamArray) {
        this.context = context;
        this.itemList = iteamArray;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_single, null);
        MyViewHolder viewHolder = new MyViewHolder(itemLayoutView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.alertTextView.setText(itemList.get(position).getCustomerName());
        holder.liMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null)
                    mItemClickListener.onItemClick(position);
            }
        });


       // Utils.showLog("=== onBindViewHolder "+holder.alertCheckbox.isChecked()+" "+itemList.get(position).getCustomerName());
       /* holder.alertCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.alertCheckbox.isChecked()) {

                    *//*for (int i = 0; i <Selectedlist.size() ; i++) {
                        if(Selectedlist.get(i).getCustomerId().equals(itemList.get(position).getCustomerId())){

                            Selectedlist.add(itemList.get(position));
                        }

                    }*//*
                   Selectedlist.add(itemList.get(position));


                    //Utils.showToast(context,"Ischeck");
                } else {

                    for (int j = 0; j <Selectedlist.size() ; j++) {
                        if(Selectedlist.get(j).getCustomerId().equals(itemList.get(position).getCustomerId())){

                            Selectedlist.remove(j);
                        }

                    }


                }
            }
        });*/

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return
                itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView alertTextView,listTextViewSpinner;
        CheckBox alertCheckbox;
        LinearLayout   liMain ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            liMain = (LinearLayout) itemView.findViewById(R.id.liMain);
            alertTextView = (TextView) itemView.findViewById(R.id.alertTextView);
            //listTextViewSpinner = (TextView) itemView.findViewById(R.id.listTextViewSpinner);
            alertCheckbox = (CheckBox) itemView.findViewById(R.id.alertCheckbox);

        }
    }

    public List<Customers.Result> getSelectedlist(){



        return Selectedlist;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }
}
