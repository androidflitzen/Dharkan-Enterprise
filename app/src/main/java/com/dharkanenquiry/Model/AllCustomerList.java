package com.dharkanenquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AllCustomerList {

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
    public class Result{

        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("customer_category_name")
        @Expose
        private String customer_category_name;
        @SerializedName("customer_category_id")
        @Expose
        private String customer_category_id;
        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("contact_person")
        @Expose
        private String contactPerson;
        @SerializedName("company_type")
        @Expose
        private String companyType;
        @SerializedName("phone_no")
        @Expose
        private String phoneNo;
        @SerializedName("landline_no")
        @Expose
        private String landlineNo;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("post_code")
        @Expose
        private String postCode;
        @SerializedName("assign_users")
        @Expose
        private String assignUsers;
        @SerializedName("assign_users_id")
        @Expose
        private String assignUsersId;
        @SerializedName("gst_no")
        @Expose
        private String gstNo;
        @SerializedName("credit_days")
        @Expose
        private String creditDays;
        @SerializedName("credit_limit")
        @Expose
        private String creditLimit;
        @SerializedName("is_imp")
        @Expose
        private String isImp;
        @SerializedName("opening_balance")
        @Expose
        private String openingBalance;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("is_walkin")
        @Expose
        private String isWalkin;
        @SerializedName("remark")
        @Expose
        private String remark;
        @SerializedName("is_login_ability")
        @Expose
        private String isLoginAbility;
        @SerializedName("login_id")
        @Expose
        private String loginId;
        @SerializedName("reg_id")
        @Expose
        private String regId;
        @SerializedName("reg_name")
        @Expose
        private String reg_name;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCustomer_category_name() {
            return customer_category_name;
        }

        public void setCustomer_category_name(String customer_category_name) {
            this.customer_category_name = customer_category_name;
        }

        public String getCustomer_category_id() {
            return customer_category_id;
        }

        public void setCustomer_category_id(String customer_category_id) {
            this.customer_category_id = customer_category_id;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
        }

        public String getCompanyType() {
            return companyType;
        }

        public void setCompanyType(String companyType) {
            this.companyType = companyType;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getLandlineNo() {
            return landlineNo;
        }

        public void setLandlineNo(String landlineNo) {
            this.landlineNo = landlineNo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getAssignUsers() {
            return assignUsers;
        }

        public void setAssignUsers(String assignUsers) {
            this.assignUsers = assignUsers;
        }

        public String getAssignUsersId() {
            return assignUsersId;
        }

        public void setAssignUsersId(String assignUsersId) {
            this.assignUsersId = assignUsersId;
        }

        public String getGstNo() {
            return gstNo;
        }

        public void setGstNo(String gstNo) {
            this.gstNo = gstNo;
        }

        public String getCreditDays() {
            return creditDays;
        }

        public void setCreditDays(String creditDays) {
            this.creditDays = creditDays;
        }

        public String getCreditLimit() {
            return creditLimit;
        }

        public void setCreditLimit(String creditLimit) {
            this.creditLimit = creditLimit;
        }

        public String getIsImp() {
            return isImp;
        }

        public void setIsImp(String isImp) {
            this.isImp = isImp;
        }

        public String getOpeningBalance() {
            return openingBalance;
        }

        public void setOpeningBalance(String openingBalance) {
            this.openingBalance = openingBalance;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsWalkin() {
            return isWalkin;
        }

        public void setIsWalkin(String isWalkin) {
            this.isWalkin = isWalkin;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getIsLoginAbility() {
            return isLoginAbility;
        }

        public void setIsLoginAbility(String isLoginAbility) {
            this.isLoginAbility = isLoginAbility;
        }

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getRegId() {
            return regId;
        }

        public String getReg_name() {
            return reg_name;
        }

        public void setReg_name(String reg_name) {
            this.reg_name = reg_name;
        }

        public void setRegId(String regId) {
            this.regId = regId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}
