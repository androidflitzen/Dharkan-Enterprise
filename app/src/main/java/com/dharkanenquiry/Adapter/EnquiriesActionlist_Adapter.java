package com.dharkanenquiry.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dharkanenquiry.Activity.UserProfile_Activity;
import com.dharkanenquiry.Model.Delete;
import com.dharkanenquiry.Model.EnquiriesActionList;
import com.dharkanenquiry.Model.TodoCompleteTask;
import com.dharkanenquiry.utils.Network;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiriesActionlist_Adapter extends RecyclerView.Adapter<EnquiriesActionlist_Adapter.ViewHolder> {

    Context context;
    List<EnquiriesActionList.Result> allenquiryActionList = new ArrayList<>();
    ProgressDialog pDialog;
    AlertDialog progressDialog;
    WebApi webapi;

    UserProfile_Activity userProfileActivity;


    public EnquiriesActionlist_Adapter(Context context, List<EnquiriesActionList.Result> allenquiryActionList, UserProfile_Activity userProfileActivity) {
        this.context = context;
        this.allenquiryActionList = allenquiryActionList;
        this.userProfileActivity = userProfileActivity;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");

        progressDialog.setCanceledOnTouchOutside(false);

        webapi = Utils.getRetrofitClient().create(WebApi.class);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.enquiriesaction_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.tvAssignUserName.setText(allenquiryActionList.get(position).getAssignToUser());
        //  holder.tvAssignUserName.setText(allenquiryActionList.get(position).getAssignByUser());
        holder.tvUserMedium.setText(allenquiryActionList.get(position).getActionMedium());

        if (allenquiryActionList.get(position).getActionType().equals("0")) {
            holder.liactionSubmain.setVisibility(View.VISIBLE);
            holder.tvactionSubjecttitle.setText(allenquiryActionList.get(position).getActionSubject());
            holder.tvsubjectDesc.setText(allenquiryActionList.get(position).getActionDetails());
            holder.tvactionSubDate.setText(allenquiryActionList.get(position).getActionDate());

            Utils.showLog("==addbtn" + allenquiryActionList.get(position).getAssign_touserid().equals(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID)) + "     " + allenquiryActionList.get(position).getAssign_touserid() + "     " + SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));

            if (allenquiryActionList.get(position).getAssign_touserid().equals(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID))) {
                holder.actionCompleted.setVisibility(View.VISIBLE);
                holder.tvCompleted.setVisibility(View.VISIBLE);
                holder.actionCompleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        opendailogCompleted(position);
                    }
                });

            }


        } else if (allenquiryActionList.get(position).getActionType().equals("1")) {
            holder.liactionComMain.setVisibility(View.VISIBLE);
            holder.tvactionComSubjecttitle.setText(allenquiryActionList.get(position).getCompleteSubject());
            holder.tvactionComSubjectdesc.setText(allenquiryActionList.get(position).getCompleteDescription());
            holder.tvactionComDate.setText(allenquiryActionList.get(position).getCompleteDate());

        } else {
            holder.liactionSubmain.setVisibility(View.GONE);
            holder.liactionComMain.setVisibility(View.GONE);
        }

        holder.ivDeleteAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDeleteDialog(position);
            }
        });


    }

    private void openDeleteDialog(final int position) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setMessage("Are you sure Delete this Action ?");
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

        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteTaskAPI(final int position) {

        progressDialog.show();
        final WebApi webapi = Utils.getRetrofitClient().create(WebApi.class);

        Call<Delete> deleteCall = webapi.deletetask(allenquiryActionList.get(position).getActionId());
        deleteCall.enqueue(new Callback<Delete>() {
            @Override
            public void onResponse(Call<Delete> call, Response<Delete> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().getStatus() == 1) {

                        allenquiryActionList.remove(position);
                        notifyDataSetChanged();

                    }
                    Utils.showToast(context, "Delete Action Task Successfully.", R.color.green_fed);
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

    @Override
    public int getItemCount() {
        return allenquiryActionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAssignUserName, tvCompleted, tvUserMedium, actionCompleted, tvactionSubjecttitle, tvsubjectDesc, tvactionSubDate, tvactionComSubjecttitle, tvactionComSubjectdesc, tvactionComDate;
        ImageView ivDeleteAction;
        LinearLayout liactionSubmain, liactionComMain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAssignUserName = (TextView) itemView.findViewById(R.id.tvAssignUserName);
            tvUserMedium = (TextView) itemView.findViewById(R.id.tvUserMedium);
            tvactionSubjecttitle = (TextView) itemView.findViewById(R.id.tvactionSubjecttitle);
            tvsubjectDesc = (TextView) itemView.findViewById(R.id.tvsubjectDesc);
            tvactionSubDate = (TextView) itemView.findViewById(R.id.tvactionSubDate);
            tvCompleted = (TextView) itemView.findViewById(R.id.tvCompleted);
            tvactionComSubjecttitle = (TextView) itemView.findViewById(R.id.tvactionComSubjecttitle);
            tvactionComSubjectdesc = (TextView) itemView.findViewById(R.id.tvactionComSubjectdesc);
            tvactionComDate = (TextView) itemView.findViewById(R.id.tvactionComDate);
            actionCompleted = (TextView) itemView.findViewById(R.id.actionCompleted);
            liactionSubmain = (LinearLayout) itemView.findViewById(R.id.liactionSubmain);
            liactionComMain = (LinearLayout) itemView.findViewById(R.id.liactionComMain);
            ivDeleteAction = (ImageView) itemView.findViewById(R.id.ivDeleteAction);


        }


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
                    Utils.showLog("==final" + " " + allenquiryActionList.get(position).getAssignToUser() + "   " + edt_task_subject.getText().toString() + " " + edt_task_description.getText().toString() + " " + allenquiryActionList.get(position).getActionId() + " " + SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));
                    todoCompleted(edt_task_subject.getText().toString(), edt_task_description.getText().toString(), allenquiryActionList.get(position).getActionId(), allenquiryActionList.get(position).getAssign_touserid(), allenquiryActionList.get(position).getAssign_byuserid());
                }

            }
        });


        dialog.show();

    }

    private void todoCompleted(String tasksubject, String taskdescription, String actionid, String assigntouserid, String assignbyuserid) {

        Utils.showLog("==final" + " " + allenquiryActionList.get(0).getAssign_touserid() + "   " + tasksubject + " " + taskdescription + " " + allenquiryActionList.get(0).getActionId() + " " + SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID));


        progressDialog.show();


        Call<TodoCompleteTask> todoCompleteTaskCall = webapi.todocompletetask(SharedPrefsUtils.getSharedPreferenceString(context, SharedPrefsUtils.USER_ID), tasksubject, taskdescription, actionid, assigntouserid, assignbyuserid);
        todoCompleteTaskCall.enqueue(new Callback<TodoCompleteTask>() {
            @Override
            public void onResponse(Call<TodoCompleteTask> call, Response<TodoCompleteTask> response) {
                if (response.body() != null) {

                    if (response.body().getStatus() == 1) {

                        Utils.showToast(context, "Completed action successfully", R.color.green_fed);
                        //  pendingTaskfragment.getallPendingtask();
                        userProfileActivity.getactionlistapi(allenquiryActionList.get(0).getEnquiryId());
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


}
