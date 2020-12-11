package com.dharkanenquiry;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.dharkanenquiry.Activity.Login_Activity;
import com.dharkanenquiry.Activity.Notification_Activity;
import com.dharkanenquiry.Fragment.HomeFragment;
import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.vasudhaenquiry.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.ivLogout)
    ImageView ivLogout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ImageView ivNotification ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pushFragment(new HomeFragment(), "home");

        ivNotification = (ImageView) findViewById(R.id.ivNotification) ;

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Do you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SharedPrefsUtils.clearPreference(MainActivity.this);
                               // clearBackstack();
                                startActivity(new Intent(MainActivity.this, Login_Activity.class));
                               // finish();
                               // moveTaskToBack(true);
                                Login_Activity.username_login.getText().clear();
                                Login_Activity.password_login.getText().clear();


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                // finish();
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Notification_Activity.class);
                startActivity(intent);

            }
        });

    }

    private void clearBackstack() {

        try {
            FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(0);
            getSupportFragmentManager().popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().executePendingTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean pushFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.FramgmetDashbaord, fragment, tag)
                    //.addToBackStack("fragment")
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {



        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                        clearBackstack();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        // super.onBackPressed();
    }
}
