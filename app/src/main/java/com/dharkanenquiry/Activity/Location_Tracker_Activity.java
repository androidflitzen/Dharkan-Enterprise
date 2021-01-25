package com.dharkanenquiry.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dharkanenquiry.Model.AddLatLongModel;
import com.dharkanenquiry.Model.GetLastLatLongModel;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Location_Tracker_Activity extends AppCompatActivity implements OnMapReadyCallback {

    String sales_id,sales_name="";
    ImageView ivBackMap;
    TextView tvTitle;
    WebApi webapi;
    TextView txtAddress,txtCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location__tracker_);

        webapi = Utils.getRetrofitClient().create(WebApi.class);

        if(getIntent()!=null){
            if(getIntent().hasExtra("sales_id")){
                sales_id=getIntent().getStringExtra("sales_id");
                sales_name=getIntent().getStringExtra("sales_name");
            }
        }
        

        ivBackMap=findViewById(R.id.ivBackMap);
        txtAddress=findViewById(R.id.txtAddress);
        tvTitle=findViewById(R.id.tvTitle);
        txtCurrentTime=findViewById(R.id.txtCurrentTime);

        tvTitle.setText(sales_name);

        ivBackMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        getLocation(googleMap);
    }

    private void getLocation(GoogleMap googleMap) {
        if(webapi!=null){
            if(sales_id!=null){
                Call<GetLastLatLongModel> allEnquiryCall = webapi.getLastLatLong(sales_id);
                allEnquiryCall.enqueue(new Callback<GetLastLatLongModel>() {
                    @Override
                    public void onResponse(Call<GetLastLatLongModel> call, Response<GetLastLatLongModel> response) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                if(googleMap!=null){
                                    String latitude=response.body().getResult().get(0).getLatitude();
                                    String longitude=response.body().getResult().get(0).getLongitude();

                                    try {

                                        String dateTime=response.body().getResult().get(0).getCreatedAt();

                                        SimpleDateFormat formatIn = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss a");
                                        SimpleDateFormat formatOut = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(formatIn.parse(dateTime));
                                        String newDate = formatOut.format(calendar.getTime());

                                        txtCurrentTime.setText(newDate);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(latitude), 	Double.parseDouble(longitude)))
                                            .title("Marker"));

                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latitude), 	Double.parseDouble(longitude)), 18));

                                    try {
                                        getAddress1(Double.parseDouble(latitude),Double.parseDouble(longitude));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            } else {
                                Utils.showToast(getApplicationContext(),response.body().getMessage(), R.color.red_dark);
                                txtAddress.setText(response.body().getMessage());
                            }

                        } else {
                            Utils.showToast(getApplicationContext(), "Something Went to Wrong", R.color.red_dark);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetLastLatLongModel> call, Throwable t) {
                        Utils.showToast(getApplicationContext(), "Data Not Found", R.color.red_dark);
                    }
                });
            }
        }
    }

    private void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(Location_Tracker_Activity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String   add = obj.getAddressLine(0);
            String  currentAddress = obj.getSubAdminArea() + ","
                    + obj.getAdminArea();
            double   latitude = obj.getLatitude();
            double longitude = obj.getLongitude();
            String currentCity= obj.getSubAdminArea();
            String currentState= obj.getAdminArea();
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();


            System.out.println("obj.getCountryName()"+obj.getCountryName());
            System.out.println("obj.getCountryCode()"+obj.getCountryCode());
            System.out.println("obj.getAdminArea()"+obj.getAdminArea());
            System.out.println("obj.getPostalCode()"+obj.getPostalCode());
            System.out.println("obj.getSubAdminArea()"+obj.getSubAdminArea());
            System.out.println("obj.getLocality()"+obj.getLocality());
            System.out.println("obj.getSubThoroughfare()"+obj.getSubThoroughfare());


            Log.v("IGA", "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getAddress1(double lat, double lng) throws IOException {
        Geocoder myLocation = new Geocoder(this, Locale.getDefault());
        List<Address> myList = myLocation.getFromLocation(lat,lng, 1);
        Address address = (Address) myList.get(0);
        String addressStr = "";
        addressStr += address.getAddressLine(0);
        //addressStr += address.getAddressLine(1) + ", ";
       // addressStr += address.getAddressLine(2);

        txtAddress.setText(addressStr);
        System.out.println("===========addr   "+addressStr);

    }
}