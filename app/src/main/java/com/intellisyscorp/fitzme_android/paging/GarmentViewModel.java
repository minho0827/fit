package com.intellisyscorp.fitzme_android.paging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.intellisyscorp.fitzme_android.fragment.GarmentFragment;
import com.intellisyscorp.fitzme_android.models.GarmentVO;

public class
GarmentViewModel extends ViewModel {
    private LiveData<PageKeyedDataSource<String, GarmentVO>> mLiveDataSource;
    private LiveData<PagedList<GarmentVO>> mPagedList;

    public GarmentViewModel(GarmentFragment activity, String partName) {
        GarmentDataSource.MyFactory factory = new GarmentDataSource.MyFactory(activity, partName);

        mLiveDataSource = factory.getLiveDataSource();

        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(50 /* TODO(sjkim): make global configuration */)
                .build();

        mPagedList = (new LivePagedListBuilder(factory, pagedListConfig)).build();
    }

    public LiveData<PagedList<GarmentVO>> getPagedList() {
        return mPagedList;
    }

    public void invalidateDataSource() {
        mLiveDataSource.getValue().invalidate();
    }

    public static class MyFactory implements ViewModelProvider.Factory {
        private GarmentFragment mActivity;
        private String mPartName;

        public MyFactory(GarmentFragment activity, String partName) {
            mActivity = activity;
            mPartName = partName;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new GarmentViewModel(mActivity, mPartName);
        }
    }
}
