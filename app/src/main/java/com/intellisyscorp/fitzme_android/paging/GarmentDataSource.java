package com.intellisyscorp.fitzme_android.paging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.intellisyscorp.fitzme_android.fragment.GarmentFragment;
import com.intellisyscorp.fitzme_android.models.GarmentPagingVO;
import com.intellisyscorp.fitzme_android.models.GarmentVO;
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

public class GarmentDataSource extends PageKeyedDataSource<String, GarmentVO> {
    private static final String TAG = "GarmentDataSource";
    private String mPartName;
    private GarmentFragment mGarmentFragment;
    private int mPageSize = 20;

    public GarmentDataSource(GarmentFragment activity, String partName) {
        mGarmentFragment = activity;
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

    private HashMap<String, Object> setFilterConditions(HashMap<String, Object> params) {
        if (mGarmentFragment.getFilterCategory() != null && mGarmentFragment.getFilterCategory().size() != 0) {
            List<String> rawParams = mGarmentFragment.getFilterCategory();
            String strParams = "";
            for (int i = 0, n = rawParams.size(); i < n; ++i) {
                strParams += rawParams.get(i);

                if (i != n - 1)
                    strParams += ',';
            }
            params.put("category", strParams);
        }

        if (mGarmentFragment.getFilterColor() != null && mGarmentFragment.getFilterColor().size() != 0) {
            List<String> rawParams = mGarmentFragment.getFilterColor();
            String strParams = "";
            for (int i = 0, n = rawParams.size(); i < n; ++i) {
                strParams += rawParams.get(i);

                if (i != n - 1)
                    strParams += ',';
            }
            params.put("color", strParams);
        }

        if (mGarmentFragment.getFilterKey() != null && mGarmentFragment.getFilterKey().size() != 0) {
            List<String> rawParams = mGarmentFragment.getFilterKey();
            String strParams = "";
            for (int i = 0, n = rawParams.size(); i < n; ++i) {
                strParams += rawParams.get(i);

                if (i != n - 1)
                    strParams += ',';
            }
            params.put("key", strParams);
        }

        return params;
    }

    ///////////////////////////////////////////
    // PageKeyedDataSource
    ///////////////////////////////////////////
    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, GarmentVO> callback) {
        // Fetching UserGarmentVO
        UserRestService service = RetroUtil.getService(UserRestService.class);

        HashMap<String, Object> callParams = new HashMap<>();
        callParams.put("part", mPartName);
        callParams.put("page_size", mPageSize);
        callParams = setFilterConditions(callParams);

        final Call<GarmentPagingVO> call = service.getGarment(callParams);
        call.enqueue(new Callback<GarmentPagingVO>() {
            @Override
            public void onResponse(Call<GarmentPagingVO> call, Response<GarmentPagingVO> response) {
                if (response.isSuccessful()) {
                    final GarmentPagingVO body = response.body();
                    List<GarmentVO> results = body.getResults();

                    callback.onResult(results, getCursor(body.getPrevious()), getCursor(body.getNext()));
                } else {
                    Toast.makeText(mGarmentFragment.getContext(), "옷을 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GarmentPagingVO> call, Throwable e) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, GarmentVO> callback) {
        // Fetching UserGarmentVO
        UserRestService service = RetroUtil.getService(UserRestService.class);

        HashMap<String, Object> callParams = new HashMap<>();
        callParams.put("part", mPartName);
        callParams.put("page_size", mPageSize);
        callParams = setFilterConditions(callParams);
        callParams.put("cursor", params.key);

        final Call<GarmentPagingVO> call = service.getGarment(callParams);
        call.enqueue(new Callback<GarmentPagingVO>() {
            @Override
            public void onResponse(Call<GarmentPagingVO> call, Response<GarmentPagingVO> response) {
                if (response.isSuccessful()) {
                    final GarmentPagingVO body = response.body();
                    List<GarmentVO> results = body.getResults();

                    callback.onResult(results, getCursor(body.getPrevious()));
                } else {
                    Toast.makeText(mGarmentFragment.getContext(), "옷을 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GarmentPagingVO> call, Throwable e) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, GarmentVO> callback) {
        // Fetching UserGarmentVO
        UserRestService service = RetroUtil.getService(UserRestService.class);

        HashMap<String, Object> callParams = new HashMap<>();
        callParams.put("part", mPartName);
        callParams.put("page_size", mPageSize);
        callParams = setFilterConditions(callParams);
        callParams.put("cursor", params.key);

        final Call<GarmentPagingVO> call = service.getGarment(callParams);
        call.enqueue(new Callback<GarmentPagingVO>() {
            @Override
            public void onResponse(Call<GarmentPagingVO> call, Response<GarmentPagingVO> response) {
                if (response.isSuccessful()) {
                    final GarmentPagingVO body = response.body();
                    List<GarmentVO> results = body.getResults();

                    callback.onResult(results, getCursor(body.getNext()));
                } else {
                    Toast.makeText(mGarmentFragment.getContext(), "옷을 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GarmentPagingVO> call, Throwable e) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
            }
        });
    }

    public static class MyFactory extends DataSource.Factory {
        private MutableLiveData<PageKeyedDataSource<String, GarmentVO>> mGarmentLiveDataSource = new MutableLiveData<>();
        private GarmentFragment mGarmentFragment;
        private String mPartName;

        public MyFactory(GarmentFragment activity, String partName) {
            mGarmentFragment = activity;
            mPartName = partName;
        }

        ///////////////////////////////////////////
        // DataSource.Factory
        ///////////////////////////////////////////
        @Override
        public DataSource create() {
            GarmentDataSource dataSource = new GarmentDataSource(mGarmentFragment, mPartName);
            mGarmentLiveDataSource.postValue(dataSource);

            return dataSource;
        }

        public LiveData<PageKeyedDataSource<String, GarmentVO>> getLiveDataSource() {
            return mGarmentLiveDataSource;
        }
    }
}
