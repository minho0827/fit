package com.intellisyscorp.fitzme_android.utils;


public class Constant {
    public static final int RETROFIT_TIMEOUT = 5;
    // local server
//    public static final String SERVER_ADDR = "http://172.30.1.16:8080/"; // iphone hotspot
//    public static final String SERVER_ADDR = "http://192.168.1.199:8080/"; // 서울대 local ip
    public static final String SERVER_ADDR = "http://dev.api.fitzme.co.kr/";

    //    public static final String SERVER_ADDR = "http://10.0.2.2:8080/";  // 애뮬레이터
    //dev server
//    public static final String SERVER_ADDR = "http://dev.api.fitzme.co.kr/";
    public static final boolean DEBUG_MODE = false;
    public static String userAgeGroup = "";
    public static String gender = "";
    public static String userJwtToken = "";


    public enum SnsType {
        FACEBOOK("Facebook"),
        KAKAOTALK("KakaoTalk"),
        GOOGLE("Google");

        public String value;

        SnsType(String value) {
            this.value = value;
        }
    }


    /**
     * ReturnCode - Param Key
     ************************************************************************************************************************************************/
    public static final String RS_RETURN_200 = "200";                                            // 정상 Return


    /**
     * 자주 사용되는 String
     ************************************************************************************************************************************************/
    public static final String COMMON_NULL = "";                                                    // null
    public static final String COMMON_NULL2 = "null";                                               // null [String]
    public static final String COMMON_DIVISION = "::";                                              // division
    public static final String PAY_NOT_REQUEST = "n/a";                                             // n/a
    public static final String ONE_1 = "1";                                                         // [1]
    public static final String ZERO_1 = "0";                                                        // [0]
    public static final String ZERO_2 = "00";                                                       // [00]
    public static final String ARROW_RIGHT = " -> ";                                                // [ -> }
    public static final String SPACE = " ";                                                         // [ ]
    public static final String ZUM = ".";                                                           // [.]
    public static final String ZUM_SPLIT = "\\.";                                                   // 특수문자 Split
    public static final String PERCENT_2F = "%.2f";                                                 // 소수점 2자리 이하
    public static final String SLASH = "/";                                                         // [/]
    public static final String OPEN = "open";                                                       // [open]
    public static final String SMS = "SMS";                                                         // [SMS]
    public static final String FEMALE = "female";                                                         // [SMS]
    public static final String MALE = "male";                                                         // [SMS]
    public static final String OTHERS = "others";                                                         // [SMS]


    /**
     * PREFERENCE KEY
     ************************************************************************************************************************************************/
    public static final String PREF_NAME_KEY = "Fitzme";                                         // Preference Ke
    public static final String USER_AGE_GROUP = "USER_AGE_GROUP";
    public static final String USER_JWT_TOKEN = "USER_JWT_TOKEN";
    public static final String USER_JWT = "USER_JWT";
    public static final String USER_GENDER = "USER_GENDER";

    public static final String USER_VALID = "USER_VALID";

    //유저 age group
    public static final String USER_AGE_GROUP_1019 = "10-19";
    public static final String USER_AGE_GROUP_2029 = "20-29";
    public static final String USER_AGE_GROUP_3039 = "30-39";
    public static final String USER_AGE_GROUP_4049 = "40-49";
    public static final String USER_AGE_GROUP_50 = "50+";

    public static final String Y = "Y";                                                              // YES
    public static final String N = "N";

}
