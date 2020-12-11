package com.dharkanenquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EnquiryCategory {

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

        @SerializedName("ec_id")
        @Expose
        private String ecId;
        @SerializedName("ec_name")
        @Expose
        private String ecName;

        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getEcId() {
            return ecId;
        }

        public void setEcId(String ecId) {
            this.ecId = ecId;
        }

        public String getEcName() {
            return ecName;
        }

        public void setEcName(String ecName) {
            this.ecName = ecName;
        }

    }

}
