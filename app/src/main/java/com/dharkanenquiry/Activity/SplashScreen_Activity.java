package com.dharkanenquiry.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.dharkanenquiry.vasudhaenquiry.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreen_Activity extends AppCompatActivity {

    @BindView(R.id.ivSplashLogo)
    ImageView ivSplashLogo;

    Animation animZoomout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_);
        ButterKnife.bind(this);


        animZoomout = AnimationUtils.loadAnimation( SplashScreen_Activity.this, R.anim.bounce);

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

                startActivity(new Intent(SplashScreen_Activity.this, Login_Activity.class));
                overridePendingTransition(R.anim.feed_in, R.anim.feed_out);
                finish();

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

}
