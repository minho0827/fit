package com.intellisyscorp.fitzme_android.paging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.intellisyscorp.fitzme_android.models.UserGarmentVO;

public class UserGarmentViewModel extends ViewModel {
    private LiveData<PageKeyedDataSource<String, UserGarmentVO>> mLiveDataSource;
    private LiveData<PagedList<UserGarmentVO>> mPagedList;

    public UserGarmentViewModel(String partName) {
        UserGarmentDataSource.MyFactory factory = new UserGarmentDataSource.MyFactory(partName);

        mLiveDataSource = factory.getLiveDataSource();

        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(50 /* TODO(sjkim): make global configuration */)
                .build();

        mPagedList = (new LivePagedListBuilder(factory, pagedListConfig)).build();
    }

    public LiveData<PagedList<UserGarmentVO>> getPagedList() {
        return mPagedList;
    }

    public void invalidateDataSource() {
        mLiveDataSource.getValue().invalidate();
    }

    public static class MyFactory implements ViewModelProvider.Factory {
        private String mPartName;

        public MyFactory(String partName) {
            mPartName = partName;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserGarmentViewModel(mPartName);
        }
    }
}
