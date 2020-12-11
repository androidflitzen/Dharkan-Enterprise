package com.dharkanenquiry.utils;

import android.widget.EditText;

public class Validation {
    private static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean isEmpty(EditText editText) {
        if (editText.getText().toString().trim().length() < 1) {
            return true;
        }
        return false;
    }
    public static boolean isEmpty(EditText editText,String msg) {
        if (editText.getText().toString().trim().length() < 1) {
            editText.setError(msg);
            return true;
        }
        return false;
    }

    public static boolean isValidEmail(EditText editText) {
        String str = editText.getText().toString();
        if (!str.matches(emailPattern)) {
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(EditText editText,String msg) {
        String str = editText.getText().toString();
        if (!str.matches(emailPattern)) {
            editText.setError(msg);
            return false;
        }
        return true;
    }

}
