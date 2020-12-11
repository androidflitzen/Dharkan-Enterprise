package com.dharkanenquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDetails {

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

        @SerializedName("enquiry_source_id")
        @Expose
        private String enquirysourceid;

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
        @SerializedName("closed_reason")
        @Expose
        private String closedReason;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("products")
        @Expose
        private String products;

        @SerializedName("enquiry_for_id")
        @Expose
        private String enquiryproductid;

        @SerializedName("phone_no")
        @Expose
        private String phoneno;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("other_details")
        @Expose
        private String otherDetails;
        @SerializedName("enquiry_approch")
        @Expose
        private String enquiryApproch;
        @SerializedName("enquiry_category")
        @Expose
        private String enquiryCategory;

        @SerializedName("enquiry_category_id")
        @Expose
        private String enquirycategoryid;

        public String getEnquirysourceid() {
            return enquirysourceid;
        }

        public void setEnquirysourceid(String enquirysourceid) {
            this.enquirysourceid = enquirysourceid;
        }

        public String getEnquiryproductid() {
            return enquiryproductid;
        }

        public void setEnquiryproductid(String enquiryproductid) {
            this.enquiryproductid = enquiryproductid;
        }

        public String getEnquirycategoryid() {
            return enquirycategoryid;
        }

        public void setEnquirycategoryid(String enquirycategoryid) {
            this.enquirycategoryid = enquirycategoryid;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public void setPhoneno(String phoneno) {
            this.phoneno = phoneno;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

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

        public String getClosedReason() {
            return closedReason;
        }

        public void setClosedReason(String closedReason) {
            this.closedReason = closedReason;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProducts() {
            return products;
        }

        public void setProducts(String products) {
            this.products = products;
        }

        public String getOtherDetails() {
            return otherDetails;
        }

        public void setOtherDetails(String otherDetails) {
            this.otherDetails = otherDetails;
        }

        public String getEnquiryApproch() {
            return enquiryApproch;
        }

        public void setEnquiryApproch(String enquiryApproch) {
            this.enquiryApproch = enquiryApproch;
        }

        public String getEnquiryCategory() {
            return enquiryCategory;
        }

        public void setEnquiryCategory(String enquiryCategory) {
            this.enquiryCategory = enquiryCategory;
        }

    }

}
