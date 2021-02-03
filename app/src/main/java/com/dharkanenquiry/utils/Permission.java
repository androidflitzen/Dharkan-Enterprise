package com.dharkanenquiry.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

public class Permission {

    static androidx.appcompat.app.AlertDialog alertDialog = null;

    public static boolean hasPermissions(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    public static void requestPermissions(Activity activity, String[] permissions) {
        List<String> remainingPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                remainingPermissions.add(permission);
            }
        }
        ActivityCompat.requestPermissions(activity, remainingPermissions.toArray(new String[remainingPermissions.size()]),
                101);
    }

    public static void onRequestPermissionsResult1(Activity activity, @NonNull String[] permissions) {
        System.out.println("==========permissions   " + permissions.length);
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                //denied
                Log.e("set to denied", permission);
                ActivityCompat.requestPermissions(activity, new String[]{permission}, 102);
            } else {
                if (ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {

                    //allowed
                    Log.e("set to allowed", permission);
                } else {
                    //set to never ask again
                    openAlertDialog(activity);
                    Log.e("set to never ask again", permission);
                }
            }
        }
    }

   /* @SuppressLint("WrongConstant")
    public static void onRequestPermissionsResult(Activity activity, @NonNull String[] permissions) {
        for (String permission : permissions) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                Log.e("Permission: ", "User Has Denied Permission");
                ActivityCompat.requestPermissions(activity, new String[]{permission}, 300);
            } else if (PermissionChecker.checkCallingOrSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission: ", "User Has Denied Permission with Don't Ask Again");
                openAlertDialog(activity);
                break;
            } else {
                Log.e("Permission: ", "User Has Allowed Permission");
            }
        }
    }*/

    @SuppressLint({"WrongConstant", "NewApi"})
    public static void onRequestPermissionsResult(Activity activity, @NonNull String[] permissions) {
        for (String permission : permissions) {

            if (ContextCompat.checkSelfPermission(
                    activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission: ", "User Has Allowed Permission");
                // You can use the API that requires the permission.
            } else if (activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                Log.e("Permission: ", "User Has Denied Permission with Don't Ask Again");
                openAlertDialog(activity);

            } else {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                Log.e("Permission: ", "User Has Denied Permission");
                activity.requestPermissions(new String[]{permission}, 300);
            }
        }
    }

    @SuppressLint({"WrongConstant", "NewApi"})
    public static void onRequestPermissionsResult2(Activity activity, @NonNull String[] permissions) {
        for (String permission : permissions) {

            if (ContextCompat.checkSelfPermission(
                    activity, permission) ==
                    PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission: ", "User Has Allowed Permission");
                // You can use the API that requires the permission.
            } else if (activity.shouldShowRequestPermissionRationale(permission)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                Log.e("Permission: ", "User Has Denied Permission with Don't Ask Again");
                openAlertDialog(activity);

            } else {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                Log.e("Permission: ", "User Has Denied Permission");
                activity.requestPermissions(new String[]{permission}, 300);
            }
        }
    }

    private static void openAlertDialog(Activity activity) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        builder.setTitle("Permission Needed");
        builder.setMessage("You need to allow Location permission.");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent();
                        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        i.addCategory(Intent.CATEGORY_DEFAULT);
                        i.setData(Uri.parse("package:" + activity.getPackageName()));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        activity.startActivity(i);
                        alertDialog.dismiss();
                    }
                });

        alertDialog = builder.create();
        alertDialog.show();
    }
}
