package com.intellisyscorp.fitzme_android.activtiy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.intellisyscorp.fitzme_android.BaseApplication;
import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.models.ConfigVO;
import com.intellisyscorp.fitzme_android.utils.AES256;
import com.intellisyscorp.fitzme_android.utils.AlertDialogUtil;
import com.intellisyscorp.fitzme_android.utils.CommonUtil;
import com.intellisyscorp.fitzme_android.utils.FitzmeProgressBar;
import com.intellisyscorp.fitzme_android.utils.OnSingleClickListener;
import com.intellisyscorp.fitzme_android.utils.UserDataManager;


public class BaseActivity extends AppCompatActivity {


    FitzmeProgressBar mProgressBar = new FitzmeProgressBar();

    final static String TAG = "BaseActivity";


    private UserManager userManager;
    /**
     * Application 전역 정보 인터페이스
     ************************************************************************************************************************************************/
    protected Context mContext;

    /**
     * Application 암호화 :: AES256
     ************************************************************************************************************************************************/
    protected AES256 aes256 = new AES256();                                                                         // AES256 임호화


    protected boolean isBackButtonNotice = true;                                                                    // Back Key 눌렀을 때 App 종료 문구 출력 여부
    private boolean isBackPressed = false;
    protected RelativeLayout layoutBack;                                                           // Top Area Layout Common Left, Right

    /**
     * Application User Local DataBase
     ************************************************************************************************************************************************/
    protected UserDataManager userDataManager;                                                                      // 유저 데이터


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        userDataManager = new UserDataManager(mContext);
        Thread.setDefaultUncaughtExceptionHandler(((BaseApplication) getApplication()).getUncaughtExceptionHandler());
        BaseApplication.setClassName(mContext.getClass().getCanonicalName());

    }


    public ConfigVO getConfigVO() {
        return ((BaseApplication) getApplication()).getConfigVO();
    }
//
//    /**
//     * Back Key 동작.
//     ************************************************************************************************************************************************/
//    @Override
//    public void onBackPressed() {
//        if (!isBackButtonNotice || isBackPressed) {
//            finish();
//            return;
//        }
//
//        isBackPressed = true;
//        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_back_button));
//
//        new Handler().postDelayed(() -> isBackPressed = false, 2000);
//    }
    //test

    /**
     * 현재 Activity를 종료하지 않고 새로운 Activity를 start할 때 사용.
     ************************************************************************************************************************************************/
    protected void openActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    /**
     * 기존에 stack에 쌓여있던 모든 Activity는 제거하고 새로운 Activity를 실행함.
     ************************************************************************************************************************************************/
    public void redirectActivityAllClear(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    /**
     * Common UI 초기화 :: TopArea
     ************************************************************************************************************************************************/
    protected void uiInit() {
        layoutBack = getRelativeLayout(R.id.layout_back);

        if (!CommonUtil.isNetworkConnected(mContext)) {
            AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_not_internet), new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    AlertDialogUtil.dismissDialog();
                    finish();
                    return;
                }
            });
        }
    }


    /**
     * 레이아웃 resource 참조
     ************************************************************************************************************************************************/
    protected RelativeLayout getRelativeLayout(int resourceId) {
        return (RelativeLayout) findViewById(resourceId);
    }


    private void showProgress() {
        if (mProgressBar != null && !isFinishing()) {
            Log.d(TAG, "showProgress()");
            mProgressBar.show(mContext);
        }
    }

}
