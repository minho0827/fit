package com.intellisyscorp.fitzme_android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Map;

public class Utils {

    private static final String PERCENT_06X = "%06X";

    public static String getEllipsedStr(String str, int maxLen) {
        if (StringUtils.isEmpty(str) || str.length() < maxLen) {
            return "";
        } else {
            return str.substring(0, maxLen) + "...";
        }
    }

    /**
     * 터치한 지점이 해당 뷰의 유효한 지점인지 체크 후 리턴 (전체 화면 내에서의 비교 || ButtonSelectorListener)
     */
    public static boolean isAvailableTouch(View v, MotionEvent event) {
        Rect r = new Rect();
        v.getGlobalVisibleRect(r);

        return 0.0f <= event.getX() && event.getX() <= (r.right - r.left) && 0.0f <= event.getY() && event.getY() <= (r.bottom - r.top);
    }


    /**
     * int 값의 color를 16진수 hex값 형태로 반환 (ButtonSelectorListener)
     */
    public static String getTextHexColor(int colorValue) {
        return String.format(PERCENT_06X, (0xFFFFFF & colorValue));
    }

    public static boolean isEmpty(Object obj) {
        if (obj instanceof String) return obj == null || StringUtils.isEmpty(obj.toString());
        else if (obj instanceof Collection<?>)
            return obj == null || ((Collection<?>) obj).isEmpty();
        else if (obj instanceof Map) return obj == null || ((Map) obj).isEmpty();
        else if (obj instanceof Object[]) return obj == null || Array.getLength(obj) == 0;
        else return obj == null;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }


    public static void hideKeyboard(final View view, final Context context) {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }, 50);

    }

    public static String encryptPassword(String pwd) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pwd.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static void showProgress(FitzmeProgressBar progressBar, Context ctx) {
        if (progressBar != null) {
            progressBar.show(ctx);
        }
    }


    public static void hideProgress(FitzmeProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.hide();
        }
    }

    /**
     * 7. 현재 네트워크가 연결되어 있는지 확인
     */
    public static boolean isNetworkConnected(Context context) {
        boolean isConnected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnectedOrConnecting()) {
                isConnected = true;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE && networkInfo.isConnectedOrConnecting()) {
                isConnected = true;
            }
        }

        return isConnected;
    }

    public static boolean isWifiConnection(Context context) {
        //Create object for ConnectivityManager class which returns network related info
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //If connectivity object is not null
        if (connectivity != null) {
            //Get network info - WIFI internet access
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (info != null) {
                //Look for whether device is currently connected to WIFI network
                return info.isConnected();
            }
        }
        return false;
    }

    /**
     * 4. 현재 디바이스의 App 버전을 확인
     */
    public static String getCurrentVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //키보드 show
    public static void showKeyboard(View v, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);

    }


    public static String getGenderType(String gender) {

        switch (gender) {
            case Constant.FEMALE:
                gender = "여성";
                break;
            case Constant.MALE:
                gender = "남성";
                break;
            case Constant.OTHERS:
                gender = "기타";
                break;

            default:
                break;
        }
        return gender;
    }
}
