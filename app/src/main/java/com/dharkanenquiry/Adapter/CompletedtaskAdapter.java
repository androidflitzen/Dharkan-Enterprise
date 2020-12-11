package com.dharkanenquiry.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dharkanenquiry.Activity.Task_Activity;
import com.dharkanenquiry.Fragment.CompetedTask_Fragment;
import com.dharkanenquiry.Model.Completedtask;

import com.dharkanenquiry.Model.Delete;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.dharkanenquiry.utils.Network;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedtaskAdapter extends RecyclerView.Adapter<CompletedtaskAdapter.MyViewHolder> {

    Context context;
    List<Completedtask.Result> allCompletedTaskList;
    Task_Activity task_activity;
    ProgressDialog progressDialog;
    CompetedTask_Fragment competedTask_fragment;


    WebApi webapi;




    public CompletedtaskAdapter(Context context, List<Completedtask.Result> allCompletedTaskList, CompetedTask_Fragment competedTask_fragment) {
        this.context = context;
        this.allCompletedTaskList = allCompletedTaskList;
        this.competedTask_fragment = competedTask_fragment;

        webapi = Utils.getRetrofitClient().create(WebApi.class);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.completed_task_list_new, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tvTaskCdActionSubject.setText(allCompletedTaskList.get(position).getActionSubject());
        holder.tvTaskCdCustomerName.setText(allCompletedTaskList.get(position).getCustomerName());
       // holder.tvTaskCdDeadline.setText(allCompletedTaskList.get(position).getDeadeline());
        holder.tvTaskCdDeadline.setText(allCompletedTaskList.get(position).getDeadeline());
        holder.tvTaskCdMedium.setText(allCompletedTaskList.get(position).getActionMedium());
        holder.tvTaskCdByUser.setText(allCompletedTaskList.get(position).getAssignByUser());
        holder.tvTaskCdToUser.setText(allCompletedTaskList.get(position).getAssignToUser());
        holder.tvTaskCdDetails.setText(allCompletedTaskList.get(position).getActionDetails());
        holder.tvTaskCdComDeatails.setText(allCompletedTaskList.get(position).getCompletedDesc());

        String date = allCompletedTaskList.get(position).getCompletedOn();
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

        holder.ivCdDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDeleteDialog(position);

            }
        });


    }

    private void openDeleteDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.task_delete));
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (Network.isNetworkAvailable(context)) {
                            deleteTaskAPI(position);
                        }
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    private void deleteTaskAPI(final int position) {

        progressDialog.show();
        final WebApi webapi = Utils.getRetrofitClient().create(WebApi.class);

        Call<Delete> deleteCall = webapi.deletetask(allCompletedTaskList.get(position).getActionId());
        deleteCall.enqueue(new Callback<Delete>() {
            @Override
            public void onResponse(Call<Delete> call, Response<Delete> response) {
                if (response.body() != null) {

                    progressDialog.dismiss();
                    if (response.body().getStatus() == 1) {

                        allCompletedTaskList.remove(position);
                        notifyDataSetChanged();
                        competedTask_fragment.getAllCompletdList();
                        getAllcomplted();


                    }
                    Utils.showToast(context, "Delete Completed Task Successful.", R.color.green_fed);
                } else {
                    Utils.showErrorToast(context);

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<Delete> call, Throwable t) {
                progressDialog.dismiss();
                Utils.showErrorToast(context);
            }
        });
    }


    public void getAllcomplted() {

        Call<Completedtask> completedtaskCall = webapi.getcompletedtask(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
        completedtaskCall.enqueue(new Callback<Completedtask>() {
            @Override
            public void onResponse(Call<Completedtask> call, Response<Completedtask> response) {
                if (response.body() != null){

                    if (response.body().getTotal_completed_tasks().equals("0")){
                        task_activity.tvtaskCompleteTotal.setText("(0)");
                        //  tvTitleTask.setText("Completed Task"+"(0)");
                    }else {
                        task_activity.tvtaskCompleteTotal.setText("("+ response.body().getTotal_completed_tasks()+")");
                        //    tvTitleTask.setText("Completed Task"+" ("+response.body().getTotal_completed_tasks()+")");

                    }


                }else {
                    Utils.showToast(context, response.body().getMessage(), R.color.msg_fail);
                }
            }

            @Override
            public void onFailure(Call<Completedtask> call, Throwable t) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return allCompletedTaskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTaskCdActionSubject, tvTaskCdCustomerName, tvTaskCdDeadline, tvdate,tvMonth,tvYear,tvTime,tvTime1, tvTaskCdCompletedOn, tvTaskCdMedium, tvTaskCdByUser, tvTaskCdToUser, tvTaskCdDetails, tvTaskCdComDeatails;
        ImageView ivTaskCdAttachment, ivCdDelete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTaskCdActionSubject = itemView.findViewById(R.id.tvTaskCdActionSubject);
            tvTaskCdCustomerName = itemView.findViewById(R.id.tvTaskCdCustomerName);
            tvTaskCdDeadline = itemView.findViewById(R.id.tvTaskCdDeadline);
            tvTaskCdCompletedOn = itemView.findViewById(R.id.tvTaskCdCompletedOn);
            tvTaskCdMedium = itemView.findViewById(R.id.tvTaskCdMedium);
            tvTaskCdByUser = itemView.findViewById(R.id.tvTaskCdByUser);
            tvTaskCdToUser = itemView.findViewById(R.id.tvTaskCdToUser);
            tvTaskCdDetails = itemView.findViewById(R.id.tvTaskCdDetails);
            tvTaskCdComDeatails = itemView.findViewById(R.id.tvTaskCdComDeatails);
            ivCdDelete = itemView.findViewById(R.id.ivCdDelete);
            tvdate = itemView.findViewById(R.id.tvdate);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTime1 = itemView.findViewById(R.id.tvTime1);



        }
    }
}
