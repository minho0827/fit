package com.intellisyscorp.fitzme_android.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.intellisyscorp.fitzme_android.models.UserDetailVO;

public class UserDataManager {
    private Context mContext;
    private static UserDataManager instance;
    private UserDetailVO userDetailVO;

    public UserDataManager(Context context) {

    }


    //TODO 이부분 어떻게 처리할지..?
//    static {
//        try {
//            instance = new UserDataManager();
//        } catch (Exception e) {
//            throw new RuntimeException("Exception creating UserDataManager instance.");
//        }
//    }

    public static UserDataManager getInstance() {
        return instance;
    }

    public UserDetailVO getUserData() {
        SharedPreferences preference = mContext.getSharedPreferences(Constant.PREF_NAME_KEY, Context.MODE_PRIVATE);
        String age = preference.getString(Constant.USER_AGE_GROUP, Constant.COMMON_NULL);
        String gender = preference.getString(Constant.USER_GENDER, Constant.COMMON_NULL);
        String accessToekn = preference.getString(Constant.USER_JWT_TOKEN, Constant.COMMON_NULL);

        userDetailVO.setGender(gender);
        userDetailVO.setAccessToekn(accessToekn);
        userDetailVO.setAgegroup(age);


        return userDetailVO;
    }

    public void setUserData(UserDetailVO userData) {
        SharedPreferences preference = mContext.getSharedPreferences(Constant.PREF_NAME_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();

        editor.putString(Constant.USER_AGE_GROUP, userData.getAgegroup());
        editor.putString(Constant.USER_GENDER, userData.getGender());

        editor.apply();
        editor.commit();


    }
}
