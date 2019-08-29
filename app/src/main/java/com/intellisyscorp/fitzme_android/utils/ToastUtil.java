package com.intellisyscorp.fitzme_android.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast를 편하게 사용하기 위한 Class
 *
 * @author 유민호
 * @since 2018.03.30
 */
public class ToastUtil {

    /**
     * ToastUtil
     ************************************************************************************************************************************************/
    private static Toast toast;

    /**
     * Short Toast Message
     ************************************************************************************************************************************************/
    public static void showToastAsShort(Context context, String message) {
        clearToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Long Toast Message
     ************************************************************************************************************************************************/
    public static void showToastAsLong(Context context, String message) {
        clearToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Toast 객체 초기화
     ************************************************************************************************************************************************/
    private static void clearToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}
