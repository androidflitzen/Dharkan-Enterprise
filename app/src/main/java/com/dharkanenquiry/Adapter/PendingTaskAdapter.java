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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dharkanenquiry.Activity.Task_Activity;
import com.dharkanenquiry.Fragment.PendingTask_Fragment;
import com.dharkanenquiry.Model.Completedtask;
import com.dharkanenquiry.Model.Delete;
import com.dharkanenquiry.Model.PendingTask;
import com.dharkanenquiry.Model.TodoCompleteTask;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.dharkanenquiry.utils.Network;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingTaskAdapter extends RecyclerView.Adapter<PendingTaskAdapter.MyViewHolder> {

    Context context;
    List<PendingTask.Result> allPendingtaskList;
    PendingTask pendingTask;
    ProgressDialog progressDialog;
    PendingTask_Fragment pendingTaskfragment;
    WebApi webapi;

    Task_Activity task_activity;

    public PendingTaskAdapter(Context context, List<PendingTask.Result> allPendingtaskList, PendingTask_Fragment pendingTaskfragment) {
        this.context = context;
        this.allPendingtaskList = allPendingtaskList;
        this.pendingTaskfragment = pendingTaskfragment;

        webapi = Utils.getRetrofitClient().create(WebApi.class);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pending_task_list_new, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tvTaskPdActionSubject.setText(allPendingtaskList.get(position).getActionSubject());
        holder.tvTaskPdCustomerName.setText(allPendingtaskList.get(position).getCustomerName());
     //   holder.tvTaskPdDeadline.setText(allPendingtaskList.get(position).getDeadeline());
        holder.tvTaskPdMedium.setText(allPendingtaskList.get(position).getActionMedium());
        holder.tvTaskPdByUser.setText(allPendingtaskList.get(position).getAssignByUser());
        holder.tvTaskPdToUser.setText(allPendingtaskList.get(position).getAssignToUser());
        holder.tvTaskPdDetails.setText(allPendingtaskList.get(position).getActionDetails());

        String date = allPendingtaskList.get(position).getDeadeline();
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

        holder.ivtaskpendingDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDeleteDialog(position);

            }
        });

        holder.tvbtnCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendailogCompleted(position);
            }
        });

    }

    private void opendailogCompleted(final int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view1 = inflater.inflate(R.layout.dialog_payment_add, null);

        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder1.setView(view1);
        final androidx.appcompat.app.AlertDialog dialog = builder1.create();

        final EditText edt_task_description = (EditText) view1.findViewById(R.id.edt_task_description);
        final EditText edt_task_subject = (EditText) view1.findViewById(R.id.edt_task_subject);


        TextView btn_cancel = (TextView) view1.findViewById(R.id.btn_cancel);
        Button btn_taskCompleted = (Button) view1.findViewById(R.id.btn_taskCompleted);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        btn_taskCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_task_subject.getText().toString().equals("")) {
                    edt_task_subject.setError("Please Enter Subject");
                    edt_task_subject.requestFocus();
                    return;
                } else if (edt_task_description.getText().toString().equals("")) {
                    edt_task_description.setError("Please Enter Description");
                    edt_task_description.requestFocus();

                } else {
                    dialog.dismiss();
                    Utils.showLog("==final" + " " + allPendingtaskList.get(position).getAssigntouserid() + "   " + edt_task_subject.getText().toString() + " " + edt_task_description.getText().toString() + " " + allPendingtaskList.get(position).getActionId() + " " + SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
                    todoCompleted(edt_task_subject.getText().toString(), edt_task_description.getText().toString(), allPendingtaskList.get(position).getActionId(), allPendingtaskList.get(position).getAssigntouserid(), allPendingtaskList.get(position).getAssignbyuserid());
                }

            }
        });


        dialog.show();

    }

    private void todoCompleted(String tasksubject, String taskdescription, String actionid, String assigntouserid, String assignbyuserid) {

        Utils.showLog("==final" + " " + allPendingtaskList.get(0).getAssigntouserid() + "   " + tasksubject + " " + taskdescription + " " + allPendingtaskList.get(0).getActionId() + " " + SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));


        progressDialog.show();


        Call<TodoCompleteTask> todoCompleteTaskCall = webapi.todocompletetask(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID), tasksubject, taskdescription, actionid, assigntouserid, assignbyuserid);
        todoCompleteTaskCall.enqueue(new Callback<TodoCompleteTask>() {
            @Override
            public void onResponse(Call<TodoCompleteTask> call, Response<TodoCompleteTask> response) {
                if (response.body() != null) {

                    if (response.body().getStatus() == 1) {

                        Utils.showToast(context, "Complete task successfully", R.color.green_fed);
                        pendingTaskfragment.getallPendingtask();
                        getallPendingtask();
                        getAllcomplted();
                        progressDialog.dismiss();



                    } else {
                        Utils.showToast(context, "Oops something went to wrong.", R.color.msg_fail);

                    }

                } else {

                    Utils.showToast(context, "Please Try Again", R.color.msg_fail);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TodoCompleteTask> call, Throwable t) {

                progressDialog.dismiss();
                Utils.showToast(context, "Data Not Found!", R.color.msg_fail);
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

        Call<Delete> deleteCall = webapi.deletetask(allPendingtaskList.get(position).getActionId());
        deleteCall.enqueue(new Callback<Delete>() {
            @Override
            public void onResponse(Call<Delete> call, Response<Delete> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().getStatus() == 1) {

                        allPendingtaskList.remove(position);

                        notifyDataSetChanged();

                        pendingTaskfragment.getallPendingtask();
                       getallPendingtask();

                    }
                    Utils.showToast(context, "Delete Pending Task Successful.", R.color.green_fed);
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

    public void getallPendingtask() {
        // progressBar.setVisibility(View.VISIBLE);


        Call<PendingTask> pendingTaskCall = webapi.getpendingtask(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
        pendingTaskCall.enqueue(new Callback<PendingTask>() {
            @Override
            public void onResponse(Call<PendingTask> call, Response<PendingTask> response) {

                if (response.body() != null) {

                    if (response.body().getTotal_pending_tasks().equals("0")) {
                        task_activity.tvTaskPendingTotal.setText("(0)");
                        // tvTitleTask.setText("Pending Task"+"(0)");
                    } else {
                        task_activity.tvTaskPendingTotal.setText("(" + response.body().getTotal_pending_tasks() + ")");
                        //  tvTitleTask.setText("Pending Task"+" ("+response.body().getTotal_pending_tasks()+")");
                    }


                } else {

                    Utils.showToast(context, response.body().getMessage(), R.color.msg_fail);

                }

                // progressBar.setVisibility(View.GONE);
                //hideSwipeRefresh();
            }

            @Override
            public void onFailure(Call<PendingTask> call, Throwable t) {

                Utils.showToast(context, "Data Not Found", R.color.red_dark);

            }
        });

    }

    public void getAllcomplted() {

        Call<Completedtask> completedtaskCall = webapi.getcompletedtask(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
        completedtaskCall.enqueue(new Callback<Completedtask>() {
            @Override
            public void onResponse(Call<Completedtask> call, Response<Completedtask> response) {
                if (response.body() != null){

                    if (response.body().getStatus() == 1){

                        if (response.body().getTotal_completed_tasks().equals("") ){
                            task_activity.tvtaskCompleteTotal.setText("(0)");
                            // tvTitleTask.setText("Pending Task"+"(0)");
                        }else {
                            task_activity.tvtaskCompleteTotal.setText("("+ response.body().getTotal_completed_tasks()+")");
                            //  tvTitleTask.setText("Pending Task"+" ("+response.body().getTotal_pending_tasks()+")");
                        }

                    }else {
                        if (response.body().getTotal_completed_tasks().equals("") ){
                            task_activity.tvtaskCompleteTotal.setText("(0)");
                            // tvTitleTask.setText("Pending Task"+"(0)");
                        }else {
                            task_activity.tvtaskCompleteTotal.setText("("+ response.body().getTotal_completed_tasks()+")");
                            //  tvTitleTask.setText("Pending Task"+" ("+response.body().getTotal_pending_tasks()+")");
                        }
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
        return allPendingtaskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTaskPdActionSubject, tvTaskPdCustomerName, tvdate,tvMonth,tvYear,tvTime,tvTime1,tvTaskPdDeadline, tvTaskPdMedium, tvTaskPdByUser, tvTaskPdToUser, tvTaskPdDetails, tvbtnCompleted;
        ImageView ivTaskPdAttachment, ivtaskpendingDelete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTaskPdActionSubject = itemView.findViewById(R.id.tvTaskPdActionSubject);
            tvTaskPdCustomerName = itemView.findViewById(R.id.tvTaskPdCustomerName);
            tvTaskPdDeadline = itemView.findViewById(R.id.tvTaskPdDeadline);
            tvTaskPdMedium = itemView.findViewById(R.id.tvTaskPdMedium);
            tvTaskPdByUser = itemView.findViewById(R.id.tvTaskPdByUser);
            tvTaskPdToUser = itemView.findViewById(R.id.tvTaskPdToUser);
            tvTaskPdDetails = itemView.findViewById(R.id.tvTaskPdDetails);
            ivtaskpendingDelete = itemView.findViewById(R.id.ivtaskpendingDelete);
            tvbtnCompleted = itemView.findViewById(R.id.tvbtnCompleted);
            tvdate = itemView.findViewById(R.id.tvdate);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTime1 = itemView.findViewById(R.id.tvTime1);



        }
    }
}
