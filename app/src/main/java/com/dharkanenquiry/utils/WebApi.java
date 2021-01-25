package com.dharkanenquiry.utils;

import android.widget.ScrollView;

import com.dharkanenquiry.Activity.Add_NewEnquiry_Acitivity;
import com.dharkanenquiry.Model.ActionAdd;
import com.dharkanenquiry.Model.AddLatLongModel;
import com.dharkanenquiry.Model.AddNewEnquiry;
import com.dharkanenquiry.Model.Add_new_Customer;
import com.dharkanenquiry.Model.AllCategoryDeparment;
import com.dharkanenquiry.Model.AllCustomerList;
import com.dharkanenquiry.Model.AllEnquiry;
import com.dharkanenquiry.Model.AllRegion;
import com.dharkanenquiry.Model.AllState;
import com.dharkanenquiry.Model.AllTasklist;
import com.dharkanenquiry.Model.Completedtask;
import com.dharkanenquiry.Model.Customers;
import com.dharkanenquiry.Model.Delete;
import com.dharkanenquiry.Model.DeleteAllNotification;
import com.dharkanenquiry.Model.EditEnquiry;
import com.dharkanenquiry.Model.EnquiriesActionList;
import com.dharkanenquiry.Model.EnquiryCategory;
import com.dharkanenquiry.Model.EnquirySource;
import com.dharkanenquiry.Model.GetLastLatLongModel;
import com.dharkanenquiry.Model.Login;
import com.dharkanenquiry.Model.NewEnquiry;
import com.dharkanenquiry.Model.NewTask;
import com.dharkanenquiry.Model.Notification;
import com.dharkanenquiry.Model.PendingTask;
import com.dharkanenquiry.Model.Product;
import com.dharkanenquiry.Model.Request_S_O;
import com.dharkanenquiry.Model.Request_quotation;
import com.dharkanenquiry.Model.TodoCompleteTask;
import com.dharkanenquiry.Model.UserDetails;
import com.dharkanenquiry.Model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebApi {

    //public static final String BASE_URL = "https://new.earthingcare.com/apis/";
    public static final String BASE_URL = "https://new.earthingcare.com/apis/";



    @GET("users/login_user")
    Call<Login> login(@Query("email") String email, @Query("password") String password, @Query("device_id") String device_id);


    @FormUrlEncoded
    @POST("enquirys/all_enquirys")
    Call<AllEnquiry> allenquiry(@Field("user_id") String user_id,@Field("page") String page);

    @FormUrlEncoded
    @POST("enquirys/all_enquirys")
    Call<AllEnquiry> allenquiry_(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("tasks/total_task")
    Call<AllTasklist> allTotalTask(@Field("user_id") String user_id);


    @GET("enquirys/save_enquiry")
    Call<NewEnquiry> addnewenquiry(@Query("user_id") String user_id,
                                   @Query("customer_id") String customer_id,
                                   @Query("enquiry_for[]") List<String> productidlist,
                                   @Query("enquiry_source") String enquiry_source,
                                   @Query("enquiry_importance") String enquiry_importance,
                                   @Query("enquiry_category") String enquiry_category,
                                   @Query("enquiry_other_details") String enquiry_other_details,
                                   @Query("enquiry_sales_person") String enquiry_sales_person);

    @FormUrlEncoded
    @POST("customers/all_customers")
    Call<Customers> getallcustomers(@Field("user_id") String user_id);

    @GET("enquirys/enquiry_products")
    Call<Product> getallproduct();

    @GET("enquirys/enquiry_source")
    Call<EnquirySource> getallenquirysource();

    @GET("enquirys/enquiry_category")
    Call<EnquiryCategory> getenquirycategory();

    @FormUrlEncoded
    @POST("tasks/pending_tasks")
    Call<PendingTask> getpendingtask(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("tasks/completed_tasks")
    Call<Completedtask> getcompletedtask(@Field("user_id") String user_id);

    @GET("users/all_users")
    Call<Users> getallusers();

    @FormUrlEncoded
    @POST("tasks/add_tasks")
    Call<NewTask> addnewtask(@Field("user_id") String user_id, @Field("customer_id") String customer_id, @Field("action_date") String action_date, @Field("action_time") String action_time, @Field("action_medium") String action_medium, @Field("assign_to_user") String assign_to_user, @Field("action_subject") String action_subject, @Field("action_details") String action_details);


    @FormUrlEncoded
    @POST("tasks/delete_task")
    Call<Delete> deletetask(@Field("action_id") String action_id);

    @FormUrlEncoded
    @POST("enquirys/enquiry_details")
    Call<UserDetails> getuserdetails(@Field("enquiry_id") String enquiry_id);

    @FormUrlEncoded
    @POST("enquirys/edit_enquiry")
    Call<EditEnquiry> geteditenquiryapi(@Field("enquiry_id") String enquiry_id, @Field("user_id") String user_id, @Field("enquiry_for[]") List<String> productidlist, @Field("enquiry_source") String enquiry_source, @Field("enquiry_importance") String enquiry_importance, @Field("enquiry_category") String enquiry_category, @Field("enquiry_other_details") String enquiry_other_details);

    @FormUrlEncoded
    @POST("enquirys/action_update")
    Call<TodoCompleteTask> todocompletetask(@Field("complete_by_user") String complete_by_user, @Field("complete_subject") String complete_subject, @Field("complete_description") String complete_description, @Field("action_id") String action_id, @Field("assign_to_user") String assign_to_user,@Field("assign_by_user")String assign_by_user);

    @FormUrlEncoded
    @POST("enquirys/Request_quotation")
    Call<Request_quotation> todorequestQuotationsent(@Field("enquiry_id") String enquiry_id);


    @FormUrlEncoded
    @POST("enquirys/request_s_o")
    Call<Request_S_O> todorequestSOsent(@Field("enquiry_id") String enquiry_id);

    @FormUrlEncoded
    @POST("users/all_notification")
    Call<Notification> getnotification(@Field("user_id") String user_id);

    @GET("enquirys/all_state")
    Call<AllState> getallstateapi();

    @GET("enquirys/all_category")
    Call<AllCategoryDeparment> getallCategoryDeparment();

    @GET("enquirys/all_region")
    Call<AllRegion> getallregionapi();

    @FormUrlEncoded
    @POST("enquirys/add_new_enquiry")
    Call<AddNewEnquiry> addnewEnquiryApi(@Field("user_id") String user_id, @Field("state") String state, @Field("city") String city, @Field("category_department") String category_department, @Field("company_name") String company_name, @Field("whatsapp_no") String whatsapp_no, @Field("customer_address") String customer_address
            , @Field("landline_no") String landline_no, @Field("email") String email, @Field("gst_no") String gst_no, @Field("assign_users") String assign_users, @Field("enquiry_for[]") List<String> productidlist, @Field("enquiry_other_details") String enquiry_other_details, @Field("enquiry_source") String enquiry_source, @Field("importance") String importance, @Field("region_id") String region_id, @Field("enquiry_category") String enquiry_category);


    @FormUrlEncoded
    @POST("enquirys/enquiry_actions")
    Call<EnquiriesActionList> getactionlistapi(@Field("enquiry_id") String enquiry_id);

    @FormUrlEncoded
    @POST("enquirys/action_add")
    Call<ActionAdd> getActionAddapi(@Field("user_id") String user_id, @Field("customer_id") String customer_id, @Field("action_date") String action_date, @Field("action_time") String action_time, @Field("action_medium") String action_medium, @Field("assign_to_user") String assign_to_user,
                                    @Field("action_subject")String action_subject, @Field("action_details") String action_details,@Field("enquiry_id")String enquiry_id);


    @FormUrlEncoded
    @POST("users/delete_all_notification")
    Call<DeleteAllNotification> getDeleteAllNotification(@Field("user_id")String user_id);

    @FormUrlEncoded
    @POST("customers/all_customers")
    Call<AllCustomerList> getallcustomerlistApi(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("customers/add_customer")
    Call<Add_new_Customer> addnewcustomerapi(@Field("customer_category")String customer_category,@Field("company_name")String company_name,@Field("address")String address,@Field("post_code")String post_code,@Field("city")String city,@Field("state")
                                             String state,@Field("reg_id")String reg_id,@Field("assign_users")String assign_users,@Field("gst_no")String gst_no,@Field("credit_days")String credit_days,@Field("credit_limit")String credit_limit,@Field("opening_balance")String opening_balance,
                                             @Field("phone_no")String phone_no,@Field("landline_no")String landline_no,@Field("email")String email,@Field("is_imp")String is_imp,@Field("user_id")String user_id);


    @FormUrlEncoded
    @POST("customers/edit_customer")
    Call<Add_new_Customer> editcustomerapi(@Field("customer_id")String customer_id,@Field("customer_category")String customer_category,@Field("company_name")String company_name,@Field("address")String address,@Field("post_code")String post_code,@Field("city")String city,@Field("state")
            String state,@Field("reg_id")String reg_id,@Field("assign_users")String assign_users,@Field("gst_no")String gst_no,@Field("credit_days")String credit_days,@Field("credit_limit")String credit_limit,@Field("opening_balance")String opening_balance,
                                           @Field("phone_no")String phone_no,@Field("landline_no")String landline_no,@Field("email")String email,@Field("is_imp")String is_imp,@Field("user_id")String user_id);

    @FormUrlEncoded
    @POST("customers/customer_details")
    Call<AllCustomerList> getcustomerdetailsapi(@Field("customer_id")String customer_id);

    @FormUrlEncoded
    @POST("users/all_users")
    Call<Users> getSalesPerson(@Field("is_sales") String is_sales);

    @FormUrlEncoded
    @POST("users/add_latlong")
    Call<AddLatLongModel> addLatLong(@Field("user_id") String user_id, @Field("latitude") String latitude , @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("users/user_last_latlong")
    Call<GetLastLatLongModel> getLastLatLong(@Field("user_id") String user_id);


}
