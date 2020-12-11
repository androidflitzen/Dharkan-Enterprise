package com.dharkanenquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingTask {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("total_pending_tasks")
    @Expose
    private Integer total_pending_tasks;
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

    public  String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotal_pending_tasks() {
        return total_pending_tasks;
    }

    public void setTotal_pending_tasks(Integer total_pending_tasks) {
        this.total_pending_tasks = total_pending_tasks;
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
        @SerializedName("action_medium")
        @Expose
        private String actionMedium;
        @SerializedName("assign_by_user")
        @Expose
        private String assignByUser;
        @SerializedName("assign_by_user_id")
        @Expose
        private String assignbyuserid;
        @SerializedName("assign_to_user")
        @Expose
        private String assignToUser;
        @SerializedName("assign_to_user_id")
        @Expose
        private String assigntouserid;
        @SerializedName("action_details")
        @Expose
        private String actionDetails;
        @SerializedName("attachment")
        @Expose
        private String attachment;
        @SerializedName("enquiry_id")
        @Expose
        private String enquiry_id;

        public String getAssignbyuserid() {
            return assignbyuserid;
        }

        public void setAssignbyuserid(String assignbyuserid) {
            this.assignbyuserid = assignbyuserid;
        }

        public String getAssigntouserid() {
            return assigntouserid;
        }

        public void setAssigntouserid(String assigntouserid) {
            this.assigntouserid = assigntouserid;
        }

        public String getEnquiry_id() {
            return enquiry_id;
        }

        public void setEnquiry_id(String enquiry_id) {
            this.enquiry_id = enquiry_id;
        }

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

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }
    }
}
