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

import com.dharkanenquiry.Model.EnquirySource;
import com.dharkanenquiry.vasudhaenquiry.R;

import java.util.ArrayList;
import java.util.List;

public class EnqrySource_Spn_Adapter_new extends RecyclerView.Adapter<EnqrySource_Spn_Adapter_new.MyViewHolder> {

    Context context;
    private Boolean[] selected;
    List<EnquirySource.Result> itemList ;
    List<EnquirySource.Result> Selectedlist = new ArrayList<>();

    OnItemClickListener mItemClickListener;




    public EnqrySource_Spn_Adapter_new(Context context, List<EnquirySource.Result> iteamArray) {
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

        holder.alertTextView.setText(itemList.get(position).getEsName());
        holder.liMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null)
                    mItemClickListener.onItemClick(position);
            }
        });

       /* holder.alertText.setText(itemList.get(position).getEsName());

        holder.alertCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.alertCheckbox.isChecked()) {

                    Selectedlist.add(itemList.get(position));


                    //Utils.showToast(context,"Ischeck");
                } else {

                    for (int i = 0; i <Selectedlist.size() ; i++) {
                        if(Selectedlist.get(i).getEsId().equals(itemList.get(position).getEsId())){

                            Selectedlist.remove(i);
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
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView listTextViewSpinner,alertTextView;
        CheckBox alertCheckbox;
        LinearLayout liMain;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            liMain = (LinearLayout) itemView.findViewById(R.id.liMain);
            alertTextView = (TextView) itemView.findViewById(R.id.alertTextView);
            alertCheckbox = (CheckBox) itemView.findViewById(R.id.alertCheckbox);

        }
    }

    public List<EnquirySource.Result> getSelectedlist(){



        return Selectedlist;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = (OnItemClickListener) mItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }
}
