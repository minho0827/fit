package com.intellisyscorp.fitzme_android.network;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.activtiy.MyOutfitDetailActivity;
import com.intellisyscorp.fitzme_android.fragment.CalendarFragment;
import com.intellisyscorp.fitzme_android.fragment.UserGarmentFragment;
import com.intellisyscorp.fitzme_android.models.CalendarVO;
import com.intellisyscorp.fitzme_android.models.CombinationOutfitVO;
import com.intellisyscorp.fitzme_android.models.UserCalendarResponseVO;
import com.intellisyscorp.fitzme_android.models.UserGarmentResponseVO;
import com.intellisyscorp.fitzme_android.models.WeatherVO;
import com.intellisyscorp.fitzme_android.utils.AlertDialogUtil;
import com.intellisyscorp.fitzme_android.utils.RetroUtil;
import com.intellisyscorp.fitzme_android.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonApiCall {
    public static void postUserCalendarWithGarment(Context context, String TAG, Integer garmentID) {
        postUserCalendar(context, TAG, garmentID, null);
    }

    public static void postUserCalendarWithOutfit(Context context, String TAG, String outfitID) {
        postUserCalendar(context, TAG, null, outfitID);
    }

    private static void postUserCalendar(Context context, String TAG, Integer garmentID, String outfitID) {
        UserRestService service = RetroUtil.getService(UserRestService.class);

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        HashMap<String, Object> callParams = new HashMap<>();

        if (outfitID != null)
            callParams.put("outfit", outfitID);

        if (garmentID != null)
            callParams.put("garment", garmentID);

        callParams.put("date", df.format(today));
        final Call<UserCalendarResponseVO> call2 = service.postUserCalendar(callParams);
        call2.enqueue(new Callback<UserCalendarResponseVO>() {
            @Override
            public void onResponse(Call<UserCalendarResponseVO> call, Response<UserCalendarResponseVO> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "New outfit/garment is added to calendar");
                    Toast.makeText(context, "달력에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    AlertDialogUtil.calendarAddDialog(context, "", null);
                } else {
                    Log.d(TAG, "Bad response code: " + response.code());
                    Toast.makeText(context, "추가에 실패하였습니다. 코드 " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserCalendarResponseVO> call, Throwable e) {
                Log.d(TAG, "Can't add outfit/garment to user calendar, ", e);
                Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getWeather(Context context, String TAG, double longitude, double latitude, AppCompatTextView tvTemperature, ImageView ivSky) {
        UserRestService service = RetroUtil.getService(UserRestService.class);

        final Call<List<WeatherVO>> configCall = service.getWeather(longitude, latitude);
        configCall.enqueue(new Callback<List<WeatherVO>>() {
            @Override
            public void onResponse(Call<List<WeatherVO>> call, Response<List<WeatherVO>> response) {
                if (response.isSuccessful()) {
                    final List<WeatherVO> weathers = response.body();
                    if (weathers != null && weathers.size() > 0) {
                        WeatherVO curWeather = weathers.get(0);
                        tvTemperature.setText(curWeather.getTemp() + "°C");

                        if (curWeather.getSky().equals("맑음")) {
                            ivSky.setImageDrawable(context.getResources().getDrawable(R.drawable.sun, null));
                        } else if (curWeather.getSky().equals("구름조금") || curWeather.getSky().equals("구름많음")) {
                            ivSky.setImageDrawable(context.getResources().getDrawable(R.drawable.cloud, null));
                        } else if (curWeather.getSky().equals("흐림")) {
                            ivSky.setImageDrawable(context.getResources().getDrawable(R.drawable.rain, null));
                        }

                        Log.d(TAG, "API(get@weather) success");
//                        Toast.makeText(context, "날씨를 가져왔습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "API(get@weather) fail");
                    Toast.makeText(context, "날씨를 가져오는데 실패했습니다. 코드 " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<WeatherVO>> call, Throwable e) {
                Log.d(TAG, "API(get@weather) fail", e);
                Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void postUserCalendarBulk(Context context, String TAG, UserGarmentFragment userGarmentFragment, List<Map<String, Object>> callParams) {
        UserRestService service = RetroUtil.getService(UserRestService.class);
        final Call<List<UserCalendarResponseVO>> call = service.postUserCalendarBulk(callParams);
        call.enqueue(new Callback<List<UserCalendarResponseVO>>() {
            @Override
            public void onResponse(Call<List<UserCalendarResponseVO>> call, Response<List<UserCalendarResponseVO>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "New outfit is added to calendar");
                    Toast.makeText(context, "달력에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    AlertDialogUtil.calendarAddDialog(context, "", null);
                    userGarmentFragment.refresh();
                } else {
                    Log.d(TAG, "Bad response code: " + response.code());
                    Toast.makeText(context, "달력에 추가하는데 실패했습니다. 코드 " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserCalendarResponseVO>> call, Throwable e) {
                Log.d(TAG, "Can't add garment to user closet", e);
                Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void postUserGarmentBulk(Context context, String TAG, List<Map<String, Object>> callParams) {
        UserRestService service = RetroUtil.getService(UserRestService.class);
        final Call<List<UserGarmentResponseVO>> call = service.postUserGarmentBulk(callParams);
        call.enqueue(new Callback<List<UserGarmentResponseVO>>() {
            @Override
            public void onResponse(Call<List<UserGarmentResponseVO>> call, Response<List<UserGarmentResponseVO>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "garment is added");
                    Toast.makeText(context, "옷장에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Bad response code: " + response.code());
                    Toast.makeText(context, "옷장에 추가하는데 실패했습니다. 코드 " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserGarmentResponseVO>> call, Throwable e) {
                Log.d(TAG, "Can't add garment to user closet", e);
                Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void deleteUserGarmentBulk(Context context, String TAG, UserGarmentFragment userGarmentFragment, String ids) {
        UserRestService service = RetroUtil.getService(UserRestService.class);
        final Call<Void> call = service.deleteUserGarmentBulk(ids);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 204) { // 204 code = No Content
                        Toast.makeText(context, "옷장에서 옷이 삭제되었습니다.", Toast.LENGTH_LONG).show();
                        userGarmentFragment.refresh();
                    } else {
                        Log.d(TAG, "Maybe invalid return code(" + response.code() + ")");
                        Toast.makeText(context, "옷을 삭제하는데 실패했습니다. 코드 " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable e) {
                Log.d(TAG, "Can't remove garments from user closet", e);
                Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getRelatedOutfit(Context context, String TAG, MyOutfitDetailActivity myOutfitDetailActivity, String id) {
        UserRestService service = RetroUtil.getService(UserRestService.class);
        final Call<CombinationOutfitVO> call_ = service.getRelatedOutfit(id);

        call_.enqueue(new Callback<CombinationOutfitVO>() {
            @Override
            public void onResponse(Call<CombinationOutfitVO> call, Response<CombinationOutfitVO> response) {
                if (response.isSuccessful()) {
                    myOutfitDetailActivity.setAdapterToRecyclerView(response.body().getResults());
//                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Bad response code: " + response.code());
                    Toast.makeText(context, "관련 코디를 가져오는데 실패했습니다. 코드 " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CombinationOutfitVO> call, Throwable e) {
                Log.d(TAG, "Can't add to user liked outfit, ", e);
                Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getUserEamilConfirm(Context context, String TAG, String email) {
        UserRestService service = RetroUtil.getService(UserRestService.class);
        final Call<Boolean> userEmailConfirmCall = service.getRequestUserEmailConfirm(email);
        userEmailConfirmCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    final Object object = response.body();
                    if (object != null) {
                        ToastUtil.showToastAsLong(context, "사용가능한 이메일 입니다.");
                    }
                } else {
                    ToastUtil.showToastAsLong(context, "이메일 중복입니다.");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..");
                Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getUserCalendar(Context context, String TAG, View view, CalendarFragment calendarFragment) {
        UserRestService service = RetroUtil.getService(UserRestService.class);
        final Call<List<CalendarVO>> calendarCall = service.getUserCalendar();
        calendarCall.enqueue(new Callback<List<CalendarVO>>() {
            @Override
            public void onResponse(Call<List<CalendarVO>> call, Response<List<CalendarVO>> response) {
                if (response.isSuccessful()) {
                    final List<CalendarVO> body = response.body();
                    calendarFragment.setCalendarView(view, body);
                } else {
                    Toast.makeText(context, "달력을 가져오는데 실패했습니다. 코드 " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CalendarVO>> call, Throwable e) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
                Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
