package com.intellisyscorp.fitzme_android.paging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.intellisyscorp.fitzme_android.models.UserGarmentPagingVO;
import com.intellisyscorp.fitzme_android.models.UserGarmentVO;
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

public class UserGarmentDataSource extends PageKeyedDataSource<String, UserGarmentVO> {
    private static final String TAG = "UserGarmentDataSource";
    private String mPartName = "";
    private int mPageSize = 20;

    public UserGarmentDataSource(String partName) {
        mPartName = partName;
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
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, UserGarmentVO> callback) {
        // Fetching UserUserGarmentVO
        UserRestService service = RetroUtil.getService(UserRestService.class);

        HashMap<String, Object> callParams = new HashMap<>();
        callParams.put("part", mPartName);
        callParams.put("page_size", mPageSize);
        final Call<UserGarmentPagingVO> call = service.getUserGarment(callParams);
        call.enqueue(new Callback<UserGarmentPagingVO>() {
            @Override
            public void onResponse(Call<UserGarmentPagingVO> call, Response<UserGarmentPagingVO> response) {
                if (response.isSuccessful()) {
                    final UserGarmentPagingVO body = response.body();
                    List<UserGarmentVO> results = body.getResults();

                    callback.onResult(results, getCursor(body.getPrevious()), getCursor(body.getNext()));
                    Log.d(TAG, "load initial");
                }
            }

            @Override
            public void onFailure(Call<UserGarmentPagingVO> call, Throwable e) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, UserGarmentVO> callback) {
        // Fetching UserUserGarmentVO
        UserRestService service = RetroUtil.getService(UserRestService.class);

        HashMap<String, Object> callParams = new HashMap<>();
        callParams.put("part", mPartName);
        callParams.put("page_size", mPageSize);
        callParams.put("cursor", params.key);
        final Call<UserGarmentPagingVO> call = service.getUserGarment(callParams);
        call.enqueue(new Callback<UserGarmentPagingVO>() {
            @Override
            public void onResponse(Call<UserGarmentPagingVO> call, Response<UserGarmentPagingVO> response) {
                if (response.isSuccessful()) {
                    final UserGarmentPagingVO body = response.body();
                    List<UserGarmentVO> results = body.getResults();

                    callback.onResult(results, getCursor(body.getPrevious()));
                    Log.d(TAG, "load before");
                }
            }

            @Override
            public void onFailure(Call<UserGarmentPagingVO> call, Throwable e) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, UserGarmentVO> callback) {
        // Fetching UserUserGarmentVO
        UserRestService service = RetroUtil.getService(UserRestService.class);

        HashMap<String, Object> callParams = new HashMap<>();
        callParams.put("part", mPartName);
        callParams.put("page_size", mPageSize);
        callParams.put("cursor", params.key);
        final Call<UserGarmentPagingVO> call = service.getUserGarment(callParams);
        call.enqueue(new Callback<UserGarmentPagingVO>() {
            @Override
            public void onResponse(Call<UserGarmentPagingVO> call, Response<UserGarmentPagingVO> response) {
                if (response.isSuccessful()) {
                    final UserGarmentPagingVO body = response.body();
                    List<UserGarmentVO> results = body.getResults();

                    callback.onResult(results, getCursor(body.getNext()));
                    Log.d(TAG, "load after");
                }
            }

            @Override
            public void onFailure(Call<UserGarmentPagingVO> call, Throwable e) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
            }
        });
    }

    public static class MyFactory extends UserGarmentDataSource.Factory {
        private MutableLiveData<PageKeyedDataSource<String, UserGarmentVO>> mUserGarmentLiveDataSource = new MutableLiveData<>();
        private String mPartName;

        public MyFactory(String partName) {
            mPartName = partName;
        }

        ///////////////////////////////////////////
        // DataSource.Factory
        ///////////////////////////////////////////
        @Override
        public DataSource create() {
            UserGarmentDataSource dataSource = new UserGarmentDataSource(mPartName);
            mUserGarmentLiveDataSource.postValue(dataSource);

            return dataSource;
        }

        public LiveData<PageKeyedDataSource<String, UserGarmentVO>> getLiveDataSource() {
            return mUserGarmentLiveDataSource;
        }
    }
}
