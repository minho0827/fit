package com.intellisyscorp.fitzme_android.fragment;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.models.OutfitVO;
import com.intellisyscorp.fitzme_android.paging.OutfitPagedListAdapter;
import com.intellisyscorp.fitzme_android.paging.OutfitViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class OutfitFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "OutfitFragment";

    // View
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    // Member variables
    private MyOutfitFragment myOutfitFragment;
    private OutfitPagedListAdapter mListAdapter;
    private OutfitViewModel mViewModel;
    private LinearLayoutManager mLayoutManager;

    public OutfitFragment(MyOutfitFragment fragment) {
        myOutfitFragment = fragment;
    }

    public static OutfitFragment newInstance(MyOutfitFragment fragment) {
        return new OutfitFragment(fragment);
    }

    ////////////////////////////////////////
    // Fragment
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_outfit, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);

        // Setup recyclerview
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mViewModel = ViewModelProviders.of(this, new OutfitViewModel.MyFactory(myOutfitFragment)).get(OutfitViewModel.class);
        mListAdapter = new OutfitPagedListAdapter(getContext());
        mViewModel.getPagedList().observe(this, new Observer<PagedList<OutfitVO>>() {
            @Override
            public void onChanged(@Nullable PagedList<OutfitVO> items) {

                //in case of any changes
                //submitting the items to adapter
                mListAdapter.submitList(items);
            }
        });
        mRecyclerView.setAdapter(mListAdapter);
    }

    // End Fragment
    ////////////////////////////////////////

    /////////////////////////////////////////
    // implements SwipeRefreshLayout.OnRefreshListener
    @Override
    public void onRefresh() {
        refresh();
    }
    // end SwipeRefreshLayout.OnRefreshListener
    /////////////////////////////////////////

    private void refresh() {
        // Refetch data
        mViewModel.invalidateDataSource();

        // Stop refreshing UI
        mRefreshLayout.setRefreshing(false);
    }
}
