package com.dharkanenquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllEnquiry {


    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("total_enquiry")
    @Expose
    private String totalEnquiry;
    @SerializedName("live_enquiry")
    @Expose
    private String liveEnquiry;
    @SerializedName("quotation_sent")
    @Expose
    private String quotationSent;
    @SerializedName("auto_followup")
    @Expose
    private Integer autoFollowup;
    @SerializedName("closed_enquiry")
    @Expose
    private String closedEnquiry;
    @SerializedName("order_success")
    @Expose
    private String orderSuccess;
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

    public String getTotalEnquiry() {
        return totalEnquiry;
    }

    public void setTotalEnquiry(String totalEnquiry) {
        this.totalEnquiry = totalEnquiry;
    }

    public String getLiveEnquiry() {
        return liveEnquiry;
    }

    public void setLiveEnquiry(String liveEnquiry) {
        this.liveEnquiry = liveEnquiry;
    }

    public String getQuotationSent() {
        return quotationSent;
    }

    public void setQuotationSent(String quotationSent) {
        this.quotationSent = quotationSent;
    }

    public Integer getAutoFollowup() {
        return autoFollowup;
    }

    public void setAutoFollowup(Integer autoFollowup) {
        this.autoFollowup = autoFollowup;
    }

    public String getClosedEnquiry() {
        return closedEnquiry;
    }

    public void setClosedEnquiry(String closedEnquiry) {
        this.closedEnquiry = closedEnquiry;
    }

    public String getOrderSuccess() {
        return orderSuccess;
    }

    public void setOrderSuccess(String orderSuccess) {
        this.orderSuccess = orderSuccess;
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

        @SerializedName("added_on")
        @Expose
        private String addedOn;
        @SerializedName("enquiry_id")
        @Expose
        private String enquiryId;
        @SerializedName("unique_id")
        @Expose
        private String uniqueId;
        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("enquiry_status")
        @Expose
        private String enquiryStatus;
        @SerializedName("sales_person")
        @Expose
        private String salesPerson;
        @SerializedName("importance")
        @Expose
        private String importance;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("quotation_request_status")
        @Expose
        private String quotationRequestStatus;
        @SerializedName("quotation_url")
        @Expose
        private String quotationUrl;
        @SerializedName("order_request_status")
        @Expose
        private String orderRequestStatus;
        @SerializedName("order_url")
        @Expose
        private String orderUrl;
        @SerializedName("region")
        @Expose
        private String region;

        public String getAddedOn() {
            return addedOn;
        }

        public void setAddedOn(String addedOn) {
            this.addedOn = addedOn;
        }

        public String getEnquiryId() {
            return enquiryId;
        }

        public void setEnquiryId(String enquiryId) {
            this.enquiryId = enquiryId;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getEnquiryStatus() {
            return enquiryStatus;
        }

        public void setEnquiryStatus(String enquiryStatus) {
            this.enquiryStatus = enquiryStatus;
        }

        public String getSalesPerson() {
            return salesPerson;
        }

        public void setSalesPerson(String salesPerson) {
            this.salesPerson = salesPerson;
        }

        public String getImportance() {
            return importance;
        }

        public void setImportance(String importance) {
            this.importance = importance;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getQuotationRequestStatus() {
            return quotationRequestStatus;
        }

        public void setQuotationRequestStatus(String quotationRequestStatus) {
            this.quotationRequestStatus = quotationRequestStatus;
        }

        public String getQuotationUrl() {
            return quotationUrl;
        }

        public void setQuotationUrl(String quotationUrl) {
            this.quotationUrl = quotationUrl;
        }

        public String getOrderRequestStatus() {
            return orderRequestStatus;
        }

        public void setOrderRequestStatus(String orderRequestStatus) {
            this.orderRequestStatus = orderRequestStatus;
        }

        public String getOrderUrl() {
            return orderUrl;
        }

        public void setOrderUrl(String orderUrl) {
            this.orderUrl = orderUrl;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

    }

}
