package com.dharkanenquiry.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dharkanenquiry.Model.Product;
import com.dharkanenquiry.vasudhaenquiry.R;

import java.util.ArrayList;
import java.util.List;

public class        Product_Spn_Adapter_New extends RecyclerView.Adapter<Product_Spn_Adapter_New.MyViewHolder> {

    Context context;
    private Boolean[] selected;
    List<Product.Result> itemList ;
    List<Product.Result> Selectedlist = new ArrayList<>();

    public Product_Spn_Adapter_New(Context context, List<Product.Result> iteamArray) {
        this.context = context;
        this.itemList = iteamArray;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_listview_multipleselect, null);
        MyViewHolder viewHolder = new MyViewHolder(itemLayoutView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder,final int position) {

        holder.alertText.setText(itemList.get(position).getPName());

        if (itemList.get(position).isSelected()){
            holder.alertCheckbox.setChecked(true);
            Selectedlist.add(itemList.get(position));
        }else {
            holder.alertCheckbox.setChecked(false);
        }

        holder.alertCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.alertCheckbox.isChecked()) {

                    Selectedlist.add(itemList.get(position));


                    //Utils.showToast(context,"Ischeck");
                } else {

                    for (int i = 0; i <Selectedlist.size() ; i++) {
                        if(Selectedlist.get(i).getPId().equals(itemList.get(position).getPId())){

                            Selectedlist.remove(i);
                        }

                    }


                }
            }
        });

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

        TextView alertText;
        CheckBox alertCheckbox;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            alertText = (TextView) itemView.findViewById(R.id.alertText);
            alertCheckbox = (CheckBox) itemView.findViewById(R.id.alertCheckbox);
        }
    }

    public List<Product.Result> getSelectedlist(){



        return Selectedlist;
    }
}
