package com.dharkanenquiry.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.dharkanenquiry.vasudhaenquiry.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashScreen_Activity extends AppCompatActivity {

    @BindView(R.id.ivSplashLogo)
    ImageView ivSplashLogo;

    Animation animZoomout;
    private String appVersion;

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_);
        ButterKnife.bind(this);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);

        animZoomout = AnimationUtils.loadAnimation( SplashScreen_Activity.this, R.anim.bounce);
        appVersion = getVersionCode(SplashScreen_Activity.this);

        startanimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /*if (sharedPreferences.getBoolean(SharePref.isLoggedIn, false)) {
                    startActivity(new Intent(Splash_Screen.this, Activity_Home.class));
                    overridePendingTransition(R.anim.feed_in, R.anim.feed_out);
                } else {
                    startActivity(new Intent(Splash_Screen.this, Activity_Login.class));
                    overridePendingTransition(R.anim.feed_in, R.anim.feed_out);
                }
                finish();*/

                try {
                    VersionChecker versionChecker = new VersionChecker();
                    String latestVersion = versionChecker.execute().get();
                    if(Double.parseDouble(latestVersion) == Double.parseDouble(appVersion)){
                        startActivity(new Intent(SplashScreen_Activity.this, Login_Activity.class));
                        overridePendingTransition(R.anim.feed_in, R.anim.feed_out);
                        finish();
                    }else {
                        updateApp();
                    }
                }catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }




             /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                } if {
                    //next();
                    if (SharedPrefsUtils.getSharedPreferenceBoolean(SplashScreen_Activity.this,SharedPrefsUtils.IS_LOG_IN, false)) {
                        startActivity(new Intent(SplashScreen_Activity.this, MainActivity.class));
                        overridePendingTransition(R.anim.feed_in, R.anim.feed_out);
                    } else {
                        startActivity(new Intent(SplashScreen_Activity.this, Login_Activity.class));
                        overridePendingTransition(R.anim.feed_in, R.anim.feed_out);
                    }
                }*/
            }
        }, 1500);

    }

    private void startanimation() {

        ivSplashLogo.setAnimation(animZoomout);

    }

    private void updateApp() {
        LayoutInflater localView = LayoutInflater.from(SplashScreen_Activity.this);
        View promptsView = localView.inflate(R.layout.app_update_dialog, null);

        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(SplashScreen_Activity.this);
        alertDialogBuilder.setView(promptsView);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);

        Button btnUpdate = (Button) promptsView.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        alertDialog.show();
    }

    public class VersionChecker extends AsyncTask<String, String, String> {

        String newVersion;

        @Override
        protected String doInBackground(String... params) {

            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div.hAyfc:nth-child(4) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newVersion;
        }
    }

    public static String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "0";
    }

}
