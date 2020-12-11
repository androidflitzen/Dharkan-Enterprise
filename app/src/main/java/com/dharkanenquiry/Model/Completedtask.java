package com.dharkanenquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Completedtask {


    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("total_completed_tasks")
    @Expose
    private Integer total_completed_tasks;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotal_completed_tasks() {
        return total_completed_tasks;
    }

    public void setTotal_completed_tasks(Integer total_completed_tasks) {
        this.total_completed_tasks = total_completed_tasks;
    }

    public class Result {

        @SerializedName("action_id")
        @Expose
        private String actionId;
        @SerializedName("action_subject")
        @Expose
        private String actionSubject;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("deadeline")
        @Expose
        private String deadeline;
        @SerializedName("completed_on")
        @Expose
        private String completedOn;
        @SerializedName("action_medium")
        @Expose
        private String actionMedium;
        @SerializedName("assign_by_user")
        @Expose
        private String assignByUser;
        @SerializedName("assign_to_user")
        @Expose
        private String assignToUser;
        @SerializedName("action_details")
        @Expose
        private String actionDetails;
        @SerializedName("completed_desc")
        @Expose
        private String completedDesc;
        @SerializedName("attachment")
        @Expose
        private String attachment;

        public String getActionId() {
            return actionId;
        }

        public void setActionId(String actionId) {
            this.actionId = actionId;
        }

        public String getActionSubject() {
            return actionSubject;
        }

        public void setActionSubject(String actionSubject) {
            this.actionSubject = actionSubject;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getDeadeline() {
            return deadeline;
        }

        public void setDeadeline(String deadeline) {
            this.deadeline = deadeline;
        }

        public String getCompletedOn() {
            return completedOn;
        }

        public void setCompletedOn(String completedOn) {
            this.completedOn = completedOn;
        }

        public String getActionMedium() {
            return actionMedium;
        }

        public void setActionMedium(String actionMedium) {
            this.actionMedium = actionMedium;
        }

        public String getAssignByUser() {
            return assignByUser;
        }

        public void setAssignByUser(String assignByUser) {
            this.assignByUser = assignByUser;
        }

        public String getAssignToUser() {
            return assignToUser;
        }

        public void setAssignToUser(String assignToUser) {
            this.assignToUser = assignToUser;
        }

        public String getActionDetails() {
            return actionDetails;
        }

        public void setActionDetails(String actionDetails) {
            this.actionDetails = actionDetails;
        }

        public String getCompletedDesc() {
            return completedDesc;
        }

        public void setCompletedDesc(String completedDesc) {
            this.completedDesc = completedDesc;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

    }

}
