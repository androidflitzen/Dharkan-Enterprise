package com.dharkanenquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EnquiriesActionList {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
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


    public class Result {

        @SerializedName("action_id")
        @Expose
        private String actionId;
        @SerializedName("enquiry_id")
        @Expose
        private String enquiryId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("action_type")
        @Expose
        private String actionType;
        @SerializedName("assign_to_user")
        @Expose
        private String assignToUser;
        @SerializedName("assign_to_user_id")
        @Expose
        private String assign_touserid;
        @SerializedName("assign_by_user")
        @Expose
        private String assignByUser;
        @SerializedName("assign_by_user_id")
        @Expose
        private String assign_byuserid;
        @SerializedName("action_status")
        @Expose
        private String actionStatus;
        @SerializedName("action_date")
        @Expose
        private String actionDate;
        @SerializedName("action_medium")
        @Expose
        private String actionMedium;
        @SerializedName("action_subject")
        @Expose
        private String actionSubject;
        @SerializedName("action_details")
        @Expose
        private String actionDetails;
        @SerializedName("complete_date")
        @Expose
        private String completeDate;
        @SerializedName("complete_subject")
        @Expose
        private String completeSubject;
        @SerializedName("complete_description")
        @Expose
        private String completeDescription;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("last_updated")
        @Expose
        private String lastUpdated;


        public String getAssign_touserid() {
            return assign_touserid;
        }

        public void setAssign_touserid(String assign_touserid) {
            this.assign_touserid = assign_touserid;
        }

        public String getAssign_byuserid() {
            return assign_byuserid;
        }

        public void setAssign_byuserid(String assign_byuserid) {
            this.assign_byuserid = assign_byuserid;
        }

        public String getActionId() {
            return actionId;
        }

        public void setActionId(String actionId) {
            this.actionId = actionId;
        }

        public String getEnquiryId() {
            return enquiryId;
        }

        public void setEnquiryId(String enquiryId) {
            this.enquiryId = enquiryId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getActionType() {
            return actionType;
        }

        public void setActionType(String actionType) {
            this.actionType = actionType;
        }

        public String getAssignToUser() {
            return assignToUser;
        }

        public void setAssignToUser(String assignToUser) {
            this.assignToUser = assignToUser;
        }

        public String getAssignByUser() {
            return assignByUser;
        }

        public void setAssignByUser(String assignByUser) {
            this.assignByUser = assignByUser;
        }

        public String getActionStatus() {
            return actionStatus;
        }

        public void setActionStatus(String actionStatus) {
            this.actionStatus = actionStatus;
        }

        public String getActionDate() {
            return actionDate;
        }

        public void setActionDate(String actionDate) {
            this.actionDate = actionDate;
        }

        public String getActionMedium() {
            return actionMedium;
        }

        public void setActionMedium(String actionMedium) {
            this.actionMedium = actionMedium;
        }

        public String getActionSubject() {
            return actionSubject;
        }

        public void setActionSubject(String actionSubject) {
            this.actionSubject = actionSubject;
        }

        public String getActionDetails() {
            return actionDetails;
        }

        public void setActionDetails(String actionDetails) {
            this.actionDetails = actionDetails;
        }

        public String getCompleteDate() {
            return completeDate;
        }

        public void setCompleteDate(String completeDate) {
            this.completeDate = completeDate;
        }

        public String getCompleteSubject() {
            return completeSubject;
        }

        public void setCompleteSubject(String completeSubject) {
            this.completeSubject = completeSubject;
        }

        public String getCompleteDescription() {
            return completeDescription;
        }

        public void setCompleteDescription(String completeDescription) {
            this.completeDescription = completeDescription;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

    }


}
