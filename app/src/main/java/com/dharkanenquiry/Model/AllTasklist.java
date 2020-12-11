package com.dharkanenquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllTasklist {


    @SerializedName("total_tasks")
    @Expose
    private String totalTasks;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public String getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(String totalTasks) {
        this.totalTasks = totalTasks;
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

}
