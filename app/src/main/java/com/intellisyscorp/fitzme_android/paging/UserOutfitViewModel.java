package com.intellisyscorp.fitzme_android.paging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.intellisyscorp.fitzme_android.fragment.MyOutfitFragment;
import com.intellisyscorp.fitzme_android.models.UserOutfitVO;

public class UserOutfitViewModel extends ViewModel {
    private LiveData<PageKeyedDataSource<String, UserOutfitVO>> mLiveDataSource;
    private LiveData<PagedList<UserOutfitVO>> mPagedList;

    public UserOutfitViewModel(MyOutfitFragment fragment) {
        UserOutfitDataSource.MyFactory factory = new UserOutfitDataSource.MyFactory(fragment);

        mLiveDataSource = factory.getLiveDataSource();

        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(50 /* TODO(sjkim): make global configuration */)
                .build();

        mPagedList = (new LivePagedListBuilder(factory, pagedListConfig)).build();
    }

    public LiveData<PagedList<UserOutfitVO>> getPagedList() {
        return mPagedList;
    }

    public void invalidateDataSource() {
        mLiveDataSource.getValue().invalidate();
    }

    public static class MyFactory implements ViewModelProvider.Factory {
        private MyOutfitFragment myOutfitFragment;

        public MyFactory(MyOutfitFragment fragment) {
            myOutfitFragment = fragment;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserOutfitViewModel(myOutfitFragment);
        }
    }
}
