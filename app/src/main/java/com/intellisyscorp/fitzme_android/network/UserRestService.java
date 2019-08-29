package com.intellisyscorp.fitzme_android.network;

import com.intellisyscorp.fitzme_android.models.CalendarVO;
import com.intellisyscorp.fitzme_android.models.CombinationOutfitVO;
import com.intellisyscorp.fitzme_android.models.ConfigResponse;
import com.intellisyscorp.fitzme_android.models.ConfigVO;
import com.intellisyscorp.fitzme_android.models.GarmentPagingVO;
import com.intellisyscorp.fitzme_android.models.GarmentVO;
import com.intellisyscorp.fitzme_android.models.JWTSerailizerVO;
import com.intellisyscorp.fitzme_android.models.OutfitPagingVO;
import com.intellisyscorp.fitzme_android.models.UserCalendarResponseVO;
import com.intellisyscorp.fitzme_android.models.UserGarmentPagingVO;
import com.intellisyscorp.fitzme_android.models.UserGarmentResponseVO;
import com.intellisyscorp.fitzme_android.models.UserOutfitLikeResponseVO;
import com.intellisyscorp.fitzme_android.models.UserOutfitPagingVO;
import com.intellisyscorp.fitzme_android.models.UserOutfitResponseVO;
import com.intellisyscorp.fitzme_android.models.WeatherVO;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/*
Fitzme API restservice
*/

public interface UserRestService {


    /**
     * oauth
     ************************************************************************************************************************************************/
    @Headers("Content-Type: application/json")
    @POST("social/{provider}/")
    Call<JWTSerailizerVO> getRequestSocial(@Path("provider") String provider, @Body Map<String, Object> param);


    /**
     * config
     ************************************************************************************************************************************************/
    @GET("/api/v1/config/")
    Call<ConfigVO> getConfig();

    /**
     * weather
     ************************************************************************************************************************************************/
    @GET("/api/v1/weather/")
    Call<List<WeatherVO>> getWeather(@Query("longitude") double longitude,
                                     @Query("latitude") double latitude);


    /**
     * 이메일 중복화인
     ************************************************************************************************************************************************/
    @GET("api/v1/user/")
    Call<Boolean> getRequestUserEmailConfirm(@Query("email") String email);


    /**
     * 서비스에 필요한 설정 정보 및 유저 개인 설정 정보를 반환합니다.
     ************************************************************************************************************************************************/
    @GET("/api/v1/config/")
    Call<ConfigResponse> getRequestConfig(@QueryMap Map<String, Object> param);

    /**
     * garment 전체 샘플 옷 리스트를 제공합니다.
     ************************************************************************************************************************************************/
    @GET("/api/v1/garment/")
    Call<GarmentPagingVO> getGarment(@QueryMap Map<String, Object> param);

    /**
     * A unique integer value identifying this 의류.
     ************************************************************************************************************************************************/
    @GET("/api/v1/garment/{id}")
    Call<GarmentVO> requestGetGarmentInfomation(@Query("id") int id);

    /**
     * 유저 코디 리스트 반환
     ************************************************************************************************************************************************/
    @GET("/api/v1/outfit")
    Call<OutfitPagingVO> getOutfit(@QueryMap Map<String, Object> param);

    /**
     * 유저 단일 코디 정보를 얻어옵니다.
     ************************************************************************************************************************************************/
    @GET("/api/v1/outfit/{id}")
    Call<CombinationOutfitVO> requestGetUserOutfit(@Query("id") int id);

    /**
     * 유저 옷장에 옷을 추가합니다.
     * param: garment,nickname
     ************************************************************************************************************************************************/
    @POST("/api/v1/user_garment/")
    Call<UserGarmentResponseVO> postUserGarment(@Body Map<String, Object> param);

    @POST("/api/v1/user_garment/")
    Call<List<UserGarmentResponseVO>> postUserGarmentBulk(@Body List<Map<String, Object>> params);

    /**
     * 유저 옷장 파트별 리스트 반환
     ************************************************************************************************************************************************/
    @GET("/api/v1/user_garment/")
    Call<UserGarmentPagingVO> getUserGarment(@QueryMap Map<String, Object> param);

    /**
     * 유저 옷장 bulk 삭제
     ************************************************************************************************************************************************/
    @DELETE("/api/v1/user_garment/bulk/")
    Call<Void> deleteUserGarmentBulk(@Query("id") String id /* FIXME(sjkim): work-around. id=1,2,3*/);

    /**
     * 유저 코디
     ************************************************************************************************************************************************/
    @GET("/api/v1/user_outfit/")
    Call<UserOutfitPagingVO> getUserOutfit(@QueryMap Map<String, Object> param);

    /**
     * 유저 코디를 추가합니다.
     ************************************************************************************************************************************************/
    @POST("/api/v1/user_outfit/")
    Call<UserOutfitResponseVO> postUserOutfit(@Body Map<String, Object> param);

    /**
     * 코디를 좋아요합니다.(Outfit 정보로)
     ************************************************************************************************************************************************/
    @PUT("/api/v1/user_outfit/like/")
    Call<UserOutfitLikeResponseVO> putUserOutfitLike(@Body Map<String, Object> param);

    /**
     * 코디를 좋아요합니다.(ID 정보로)
     ************************************************************************************************************************************************/
    @PUT("/api/v1/user_outfit/{id}/like/")
    Call<UserOutfitLikeResponseVO> putUserOutfitLikeWithId(@Path("id") int id);

    /**
     * 코디 좋아요를 해제합니다.(Outfit 정보로)
     ************************************************************************************************************************************************/
    @PUT("/api/v1/user_outfit/cancel_like/")
    Call<UserOutfitLikeResponseVO> putUserOutfitLikeOff(@Body Map<String, Object> param);

    /**
     * 코디 좋아요를 해제합니다.(ID 정보로)
     ************************************************************************************************************************************************/
    @PUT("/api/v1/user_outfit/{id}/cancel_like/")
    Call<UserOutfitLikeResponseVO> putUserOutfitLikeOffWithId(@Path("id") int id);

    /**
     * 관련 코디를 받아옵니다.
     ************************************************************************************************************************************************/
    @GET("/api/v1/outfit/{id}/related_outfit/")
    Call<CombinationOutfitVO> getRelatedOutfit(@Path("id") String id);

    /**
     * 토큰 유효성 검사
     ************************************************************************************************************************************************/
    @POST("/token_verify/")
    Call<JWTSerailizerVO> postTokenVerifyCreate(@QueryMap Map<String, Object> param);

    /**
     * API View that returns a refreshed token (with new expiration) based on existing token
     ************************************************************************************************************************************************/
    @POST("/token_refresh/")
    Call<JWTSerailizerVO> postTokenRefresh(@QueryMap Map<String, Object> param);


    /**
     * 유저 달력 월별 리스트 반환
     ************************************************************************************************************************************************/
    @GET("/api/v1/user_calendar/")
    Call<List<CalendarVO>> getUserCalendar();


    /**
     * 오늘 입은 옷으로 히스토리에 추가합니다.
     * param
     * (garment,outfit,image)
     ************************************************************************************************************************************************/
    @POST("/api/v1/user_calendar/")
    Call<UserCalendarResponseVO> postUserCalendar(@Body Map<String, Object> param);

    @POST("/api/v1/user_calendar/")
    Call<List<UserCalendarResponseVO>> postUserCalendarBulk(@Body List<Map<String, Object>> param);

    /**
     * 유저 회원가입
     * Param
     * ( uuid,*gender,*agegroup )
     ************************************************************************************************************************************************/
    @PUT("/api/v1/register/")
    Call<String> putUserRegister(@QueryMap Map<String, Object> param);


    /**
     * 유저 이메일 회원가입
     * Param
     * ( uuid,*gender,*agegroup )
     ************************************************************************************************************************************************/
    @PUT("/api/v1/register/")
    Call<String> putUserEmailRegister(@QueryMap Map<String, Object> param);


}