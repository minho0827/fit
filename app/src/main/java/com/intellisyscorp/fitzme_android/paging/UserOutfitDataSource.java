package com.intellisyscorp.fitzme_android.paging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.intellisyscorp.fitzme_android.fragment.MyOutfitFragment;
import com.intellisyscorp.fitzme_android.models.UserOutfitPagingVO;
import com.intellisyscorp.fitzme_android.models.UserOutfitVO;
import com.intellisyscorp.fitzme_android.network.UserRestService;
import com.intellisyscorp.fitzme_android.utils.RetroUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Reference: https://www.simplifiedcoding.net/android-paging-library-tutorial/

public class UserOutfitDataSource extends PageKeyedDataSource<String, UserOutfitVO> {
    private static final String TAG = "OutfitDataSource";
    private MyOutfitFragment myOutfitFragment;
    private int mPageSize = 20;

    public UserOutfitDataSource(MyOutfitFragment fragment) {
        myOutfitFragment = fragment;
    }

    private String getCursor(String cursor) {
        if (cursor == null || cursor.length() == 0) return cursor;

        int i = cursor.indexOf("?");
        if (i == -1) return null;

        String searchURL = cursor.substring(cursor.indexOf("?") + 1);
        String[] params = searchURL.split("&");

        for (String param : params) {
            String[] temp = param.split("=");

            if (temp[0].equals("cursor")) {
                try {
                    return URLDecoder.decode(temp[1], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.d(TAG, "Error in getting cursor", e);
                    return null;
                }
            }
        }

        return null;
    }

    ///////////////////////////////////////////
    // PageKeyedDataSource
    ///////////////////////////////////////////
    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, UserOutfitVO> callback) {
        // Fetching UserOutfitVO
        UserRestService service = RetroUtil.getService(UserRestService.class);

        HashMap<String, Object> callParams = new HashMap<>();
        callParams.put("page_size", mPageSize);
        if (myOutfitFragment.getWeather() != null)
            callParams.put("weather", myOutfitFragment.getWeather());
        final Call<UserOutfitPagingVO> call = service.getUserOutfit(callParams);
        call.enqueue(new Callback<UserOutfitPagingVO>() {
            @Override
            public void onResponse(Call<UserOutfitPagingVO> call, Response<UserOutfitPagingVO> response) {
                if (response.isSuccessful()) {
                    final UserOutfitPagingVO body = response.body();
                    List<UserOutfitVO> results = body.getResults();

                    callback.onResult(results, getCursor(body.getPrevious()), getCursor(body.getNext()));
                }
            }

            @Override
            public void onFailure(Call<UserOutfitPagingVO> call, Throwable e) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, UserOutfitVO> callback) {
        // Fetching UserUserOutfitVO
        UserRestService service = RetroUtil.getService(UserRestService.class);

        HashMap<String, Object> callParams = new HashMap<>();
        callParams.put("page_size", mPageSize);
        if (myOutfitFragment.getWeather() != null)
            callParams.put("weather", myOutfitFragment.getWeather());
        callParams.put("cursor", params.key);
        final Call<UserOutfitPagingVO> call = service.getUserOutfit(callParams);
        call.enqueue(new Callback<UserOutfitPagingVO>() {
            @Override
            public void onResponse(Call<UserOutfitPagingVO> call, Response<UserOutfitPagingVO> response) {
                if (response.isSuccessful()) {
                    final UserOutfitPagingVO body = response.body();
                    List<UserOutfitVO> results = body.getResults();

                    callback.onResult(results, getCursor(body.getPrevious()));
                }
            }

            @Override
            public void onFailure(Call<UserOutfitPagingVO> call, Throwable e) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, UserOutfitVO> callback) {
        // Fetching UserUserOutfitVO
        UserRestService service = RetroUtil.getService(UserRestService.class);

        HashMap<String, Object> callParams = new HashMap<>();
        callParams.put("page_size", mPageSize);
        if (myOutfitFragment.getWeather() != null)
            callParams.put("weather", myOutfitFragment.getWeather());
        callParams.put("cursor", params.key);
        final Call<UserOutfitPagingVO> call = service.getUserOutfit(callParams);
        call.enqueue(new Callback<UserOutfitPagingVO>() {
            @Override
            public void onResponse(Call<UserOutfitPagingVO> call, Response<UserOutfitPagingVO> response) {
                if (response.isSuccessful()) {
                    final UserOutfitPagingVO body = response.body();
                    List<UserOutfitVO> results = body.getResults();

                    callback.onResult(results, getCursor(body.getNext()));
                }
            }

            @Override
            public void onFailure(Call<UserOutfitPagingVO> call, Throwable e) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
            }
        });
    }

    public static class MyFactory extends UserOutfitDataSource.Factory {
        private MutableLiveData<PageKeyedDataSource<String, UserOutfitVO>> mUserOutfitLiveDataSource = new MutableLiveData<>();
        private MyOutfitFragment myOutfitFragment;

        public MyFactory(MyOutfitFragment fragment) {
            myOutfitFragment = fragment;
        }

        ///////////////////////////////////////////
        // DataSource.Factory
        ///////////////////////////////////////////
        @Override
        public DataSource create() {
            UserOutfitDataSource dataSource = new UserOutfitDataSource(myOutfitFragment);
            mUserOutfitLiveDataSource.postValue(dataSource);

            return dataSource;
        }

        public LiveData<PageKeyedDataSource<String, UserOutfitVO>> getLiveDataSource() {
            return mUserOutfitLiveDataSource;
        }
    }
}
