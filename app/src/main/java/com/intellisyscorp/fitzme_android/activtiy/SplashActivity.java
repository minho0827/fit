package com.intellisyscorp.fitzme_android.activtiy;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.utils.AlertDialogUtil;
import com.intellisyscorp.fitzme_android.utils.Constant;
import com.intellisyscorp.fitzme_android.utils.FitzmeProgressBar;
import com.intellisyscorp.fitzme_android.utils.OnSingleClickListener;
import com.intellisyscorp.fitzme_android.utils.RequestCodeUtil;
import com.intellisyscorp.fitzme_android.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {


    @BindView(R.id.iv_logo)
    ImageView logo;

    private FitzmeProgressBar mFitzmeProgressBar = new FitzmeProgressBar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        settingSplashMethod();
        ButterKnife.bind(this);
    }

    private void goMain() {
        // 버전 체크 X
        Handler splash_handler = new Handler();
        splash_handler.postDelayed(() -> {
            openActivity(MainActivity.class);
            finish();
        }, 100);
    }

    private void goSignUp() {
        // 버전 체크 X
        Handler splash_handler = new Handler();
        splash_handler.postDelayed(() -> {
            openActivity(LoginActivity.class);
            finish();
        }, 2000);
    }


    /**
     * (1) + (2) + (3) :: SplashActivity 작업 처리
     ************************************************************************************************************************************************/
    private void settingSplashMethod() {
        if (Utils.isNetworkConnected(mContext)) {
            if (Constant.DEBUG_MODE) {

                permissionCheck();

            } else {
                if (requestGooglePlayService()) {

                    // 버전 체크
//                    goMain();
                    goSignUp();
//                    getMainnoticeAppversionCheck();
                }

            }
        } else {
            // 인터넷이 연결되지 않은 경우 팝업창
            AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_not_internet), new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    AlertDialogUtil.dismissDialog();
                    finish();
                }
            });
        }
    }

    private void permissionCheck() {
//        goMain();

    }

    /**
     * 구글 플레이 서비스 체크 여부
     ************************************************************************************************************************************************/
    private boolean requestGooglePlayService() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(mContext);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result, RequestCodeUtil.GooglePlayServiceCheckReqCode.REQ_GOOGLE_CHECK, dialogInterface -> finish()).show();
            }
            return false;
        }
        return true;
    }
}
