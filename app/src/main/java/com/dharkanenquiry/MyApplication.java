package com.dharkanenquiry;

import android.app.Application;
import androidx.annotation.NonNull;

import com.dharkanenquiry.utils.SharedPrefsUtils;
import com.dharkanenquiry.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MyApplication extends Application {

    String token="";


    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        Utils.showLog("=== isSuccessful"+task.getResult().getToken());
                        if (!task.isSuccessful()) {
                            return;
                        }
                        if (SharedPrefsUtils.getFirebaseToken(getApplicationContext()).equals("")){
                            if (task.getResult()!=null) {
                                token = task.getResult().getToken();
                                Utils.showLog("==token "+token);
                            }
                            SharedPrefsUtils.setFirebaseToken(getApplicationContext(),token);
                        }

                    }
                });
    }
}
