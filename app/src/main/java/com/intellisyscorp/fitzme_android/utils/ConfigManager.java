package com.intellisyscorp.fitzme_android.utils;

import android.util.Log;

import com.intellisyscorp.fitzme_android.models.ConfigVO;
import com.intellisyscorp.fitzme_android.network.UserRestService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigManager {
    private static final String TAG = "ConfigManager";

    // TODO(sjkim): combine all name map into one?
    private Map<String, String> mTagMap;
    private Map<String, String> mColorMap;
    private Map<String, String> mWeatherMap;
    private Map<String, String> mPartMap;
    private Map<String, ConfigVO.PublicData.CategoryData> mCategoryMap;
    private boolean mIsReady = false;

    private ConfigManager() {
        mTagMap = new HashMap<>();
        mColorMap = new HashMap<>();
        mWeatherMap = new HashMap<>();
        mCategoryMap = new HashMap<>();
        mPartMap = new HashMap<>();

        UserRestService service = RetroUtil.getService(UserRestService.class);
        final Call<ConfigVO> call = service.getConfig();

        call.enqueue(new Callback<ConfigVO>() {
            @Override
            public void onResponse(Call<ConfigVO> call, Response<ConfigVO> response) {
                if (response.isSuccessful()) {
                    final ConfigVO body = response.body();
                    ConfigVO.PublicData public_ = body.getPublic_();

                    // tag
                    for (ConfigVO.PublicData.TagData t : public_.getTag()) {
                        mTagMap.put(t.getName(), t.getName_ko());
                    }

                    for (ConfigVO.PublicData.ColorData t : public_.getColor()) {
                        mColorMap.put(t.getName(), t.getRgb());
                    }

                    // weather
                    for (ConfigVO.PublicData.WeatherData w : public_.getWeather()) {
                        mWeatherMap.put(w.getName(), w.getName_ko());
                    }

                    for (ConfigVO.PublicData.PartData p : public_.getPart()) {
                        mPartMap.put(p.getName_ko(), p.getName_en());
                    }

                    // category
                    for (ConfigVO.PublicData.CategoryData c : public_.getCategory()) {
                        mCategoryMap.put(c.getName_en(), c);
                    }

                    mIsReady = true;
                }
            }

            @Override
            public void onFailure(Call<ConfigVO> call, Throwable e) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
            }
        });

    }

    private static class ConfigManagerHolder {
        public static final ConfigManager INSTANCE = new ConfigManager();
    }

    public static ConfigManager getInstance() {
        return ConfigManagerHolder.INSTANCE;
    }

    public String translateTag(String tag) {
        return mTagMap.get(tag);
    }

    public String translateWeather(String w) {
        return mWeatherMap.get(w);
    }

    public String translateColor(String color) {
        return mColorMap.get(color);
    }

    public String translatePart(String part) {
        return mPartMap.get(part);
    }

    public String translateCategory(String category) {
        return mCategoryMap.get(category).getName_ko();
    }

    public String translateCategoryToPart(String category) {
        return mCategoryMap.get(category).getPart();
    }

    public List<String> getCategory() {
        return ConvertSetToList(mCategoryMap.keySet());
    }

    public List<String> getColor() {
        return ConvertSetToList(mColorMap.keySet());
    }

    public List<String> getTag() {
        return ConvertSetToList(mTagMap.keySet());
    }

    private List<String> ConvertSetToList(Set<String> input) {
        List<String> result = new ArrayList<>();
        result.addAll(input);
        return result;
    }

    public boolean isReady() {
        return mIsReady;
    }
}
