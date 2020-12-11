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

import com.dharkanenquiry.Model.AllCategoryDeparment;
import com.dharkanenquiry.vasudhaenquiry.R;


import java.util.ArrayList;
import java.util.List;

public class AllCategoryDepaement_Spn_Adapter extends RecyclerView.Adapter<AllCategoryDepaement_Spn_Adapter.ViewHolder> {


    Context context;
    private Boolean[] selected;
    List<AllCategoryDeparment.Result> itemList ;
    List<AllCategoryDeparment.Result> Selectedlist = new ArrayList<>();
    OnItemClickListener mItemClickListener;

    public AllCategoryDepaement_Spn_Adapter(Context context, List<AllCategoryDeparment.Result> iteamArray) {
        this.context = context;
        this.itemList = iteamArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_single, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        holder.alertTextView.setText(itemList.get(position).getCategoryName());
        holder.liMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null)
                    mItemClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView alertTextView,listTextViewSpinner;
        CheckBox alertCheckbox;
        LinearLayout liMain ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            liMain = (LinearLayout) itemView.findViewById(R.id.liMain);
            alertTextView = (TextView) itemView.findViewById(R.id.alertTextView);
            //listTextViewSpinner = (TextView) itemView.findViewById(R.id.listTextViewSpinner);
            alertCheckbox = (CheckBox) itemView.findViewById(R.id.alertCheckbox);

        }


    }

    public List<AllCategoryDeparment.Result> getSelectedlist(){



        return Selectedlist;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }

}
