package com.dharkanenquiry.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dharkanenquiry.MainActivity;
import com.dharkanenquiry.Model.Login;
import com.dharkanenquiry.vasudhaenquiry.R;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.dharkanenquiry.utils.WebApi;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_Activity extends AppCompatActivity {

    public static EditText username_login, password_login;
    Button btn_login;
    //ProgressBar progressBar;
    ImageView imgVisiblePassword;
    SharedPreferences sh;
    boolean isPasswordChecked = false;
    WebApi webapi;
    @BindView(R.id.progress_bar)
    SpinKitView progressBar;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        username_login = (EditText) findViewById(R.id.username_login);
        password_login = (EditText) findViewById(R.id.password_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        // progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        imgVisiblePassword = (ImageView) findViewById(R.id.img_login_visible_password);

        progressDialog = new ProgressDialog(Login_Activity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        Sprite doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);

        webapi = Utils.getRetrofitClient().create(WebApi.class);


        password_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (password_login.getText().toString().trim().length() == 0)
                    imgVisiblePassword.setVisibility(View.GONE);
                else
                    imgVisiblePassword.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        imgVisiblePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPasswordChecked) {
                    isPasswordChecked = true;
                    password_login.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imgVisiblePassword.setImageResource(R.drawable.ic_visiblity);
                } else {
                    isPasswordChecked = false;
                    password_login.setInputType(129);
                    imgVisiblePassword.setImageResource(R.drawable.ic_visibility_off);
                }
            }
        });

        password_login.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(password_login.getWindowToken(), 0);
                            return true;
                        }
                        return false;
                    }
                });


        sh = SharedPrefsUtils.getSharePref(getApplicationContext());
        checklogin();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://new.earthingcare.com/apis/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        final WebApi service = retrofit.create(WebApi.class);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //  progressBar.setVisibility(View.VISIBLE);
                showPrd();
                final String semail, spassword, token;

                semail = username_login.getText().toString();
                spassword = password_login.getText().toString();
                token = SharedPrefsUtils.getFirebaseToken(Login_Activity.this);
                Utils.showLog("==deviceid" + token);
                Call<Login> login = service.login(semail, spassword, token);
                final ProgressDialog pd = new ProgressDialog(Login_Activity.this);
                //   pd.setMessage("Please Wait...");
                //  pd.show();

                login.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        //  pd.dismiss();


                        if (response.body().getStatus().toString().equals("1")) {

                            //  progressBar.setVisibility(View.GONE);
                            hidePrd();
                            Utils.showLog("==deviceid111" + token);

                            SharedPrefsUtils.setSharedPreferenceString(Login_Activity.this, SharedPrefsUtils.USER_ID, String.valueOf(response.body().getUserId()));
                            SharedPrefsUtils.setSharedPreferenceString(Login_Activity.this, SharedPrefsUtils.USER_NAME, response.body().getUsername());
                            SharedPrefsUtils.setSharedPreferenceString(Login_Activity.this, SharedPrefsUtils.USER_TYPE, response.body().getUserType());

                            // SharedPrefsUtils.setSharedPreferenceString(Login_Activity.this, SharedPrefsUtils.TOKEN, response.body().getUserType());
                            SharedPrefsUtils.setSharedPreferenceBoolean(Login_Activity.this, SharedPrefsUtils.IS_LOG_IN, true);

                            //    Toast.makeText(getApplicationContext(), "user id = " + response.body().getUserId(), Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        } else {
                            //progressBar.setVisibility(View.GONE);
                            hidePrd();
                            Toast.makeText(getApplicationContext(), "Username/Password Wrong", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        //  progressBar.setVisibility(View.GONE);
                        hidePrd();
                        // pd.dismiss();
                        Toast.makeText(Login_Activity.this, "Oops.. Network Not Available", Toast.LENGTH_LONG).show();
                    }
                });
            }


        });

    }


    void checklogin() {
        String userid = sh.getString(SharedPrefsUtils.USER_ID, "");
        if (userid.length() != 0) {
            startActivity(new Intent(Login_Activity.this, MainActivity.class));
            finish();
        }
    }

    private void showPrd() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    private void hidePrd() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePrd();
    }

}
