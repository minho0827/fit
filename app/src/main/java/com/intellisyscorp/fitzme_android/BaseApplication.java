package com.intellisyscorp.fitzme_android;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;
import com.intellisyscorp.fitzme_android.models.ConfigVO;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;


public class BaseApplication extends MultiDexApplication {
    private final static String TAG = "BaseApplication";
    public final static String APP_NAME = "Fitzme";
    private static volatile BaseApplication instance = null;

    /**
     * 예외처리 ExceptionHandler
     ************************************************************************************************************************************************/
    private Thread.UncaughtExceptionHandler androidDefaultUEH;                                                      // 기본 Exception Handler
    private UncaughtExceptionHandler unCatchExceptionHandler;                                                       // 커스텀 Exception Handler

    /**
     * 현재 사용되는 Class Name
     ************************************************************************************************************************************************/
    public static String CLASS_NAME;                                                                                // App 현재 Class Name


    public static String APP_VERSION;
    private static BaseApplication mContext;
    private static ConfigVO mConfigVO;

    public static ConfigVO getConfigVO() {
        return mConfigVO;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        KakaoSDK.init(new BaseApplication.KakaoSDKAdapter());
//        APP_VERSION = Utils.getCurrentVersion(getContext());
    }


    /**
     * 에외처리 ExceptionHandler
     ************************************************************************************************************************************************/
    public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {

            // 알람 매니저
            // Intent restartIntent = new Intent(getApplicationContext(), SplashActivity.class);
            // PendingIntent runner = PendingIntent.getActivity(getApplicationContext(), 99, restartIntent, PendingIntent.FLAG_ONE_SHOT);
            // AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE); am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5000, runner);


            // 시스템 종료 (비정상처리될 가능성 높음)
            androidDefaultUEH.uncaughtException(thread, throwable);
        }
    }

    /**
     * 현재 사용되는 Class Name
     ************************************************************************************************************************************************/
    public static void setClassName(String className) {
        CLASS_NAME = className;
    }

    /**
     * 에외처리 ExceptionHandler Getter
     ************************************************************************************************************************************************/
    public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return unCatchExceptionHandler;
    }

    /**
     * Application Get Context
     ************************************************************************************************************************************************/
    public static Context getContext() {
        return mContext;
    }

    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }


    /**
     * /**
     * singleton 애플리케이션 객체를 얻는다.
     *
     * @return singleton 애플리케이션 객체
     */
    public static BaseApplication getGlobalApplicationContext() {
        if (instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }

    private static class KakaoSDKAdapter extends KakaoAdapter {
        /**
         * Session Config에 대해서는 default값들이 존재한다.
         * 필요한 상황에서만 override해서 사용하면 됨.
         *
         * @return Session의 설정값.
         */
        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[]{AuthType.KAKAO_ACCOUNT};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public boolean isSecureMode() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return BaseApplication.getGlobalApplicationContext();
                }
            };
        }
    }
}
