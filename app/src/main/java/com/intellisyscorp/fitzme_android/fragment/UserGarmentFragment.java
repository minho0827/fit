package com.intellisyscorp.fitzme_android.fragment;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.activtiy.MainActivity;
import com.intellisyscorp.fitzme_android.interfaces.OnBackPressedListener;
import com.intellisyscorp.fitzme_android.models.GarmentVO;
import com.intellisyscorp.fitzme_android.models.UserGarmentVO;
import com.intellisyscorp.fitzme_android.network.CommonApiCall;
import com.intellisyscorp.fitzme_android.paging.UserGarmentPagedListAdapter;
import com.intellisyscorp.fitzme_android.paging.UserGarmentViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class UserGarmentFragment extends Fragment implements
        UserGarmentPagedListAdapter.UserGarmentPagedListListener, OnBackPressedListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "UserGarmentFragment";

    private boolean editMode = false;

    private RecyclerView mRecyclerView;
    private LinearLayout mLinearCalender;
    UserGarmentPagedListAdapter mListAdapter;
    private Context mContext;
    LinearLayoutManager mLayoutManager;
    private String mPartName;
    private Button btnDelete;
    private Button btnCodi;
    private Button btnAddCalendar;
    private UserGarmentViewModel mViewModel;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout mRefreshLayout;


    public UserGarmentFragment(String part) {
        mPartName = part;
    }

    public static Fragment newInstance(String part) {
        return new UserGarmentFragment(part);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_garment, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserGarmentFragment mFragment = this;

        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = view.findViewById(R.id.recyclerview);
        mLinearCalender = view.findViewById(R.id.linear_calender);

        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // create paged list adapter for recyclerview
        mViewModel = ViewModelProviders.of(this, new UserGarmentViewModel.MyFactory(mPartName)).get(UserGarmentViewModel.class);
        mListAdapter = new UserGarmentPagedListAdapter(this.getContext(), this);
        mViewModel.getPagedList().observe(this, new Observer<PagedList<UserGarmentVO>>() {
            @Override
            public void onChanged(@Nullable PagedList<UserGarmentVO> items) {

                //in case of any changes
                //submitting the items to adapter
                mListAdapter.submitList(items);
            }
        });
        mRecyclerView.setAdapter(mListAdapter);

        btnAddCalendar = view.findViewById(R.id.btn_add_calendar);
        btnAddCalendar.setOnClickListener(v -> {
            List<UserGarmentVO> selectedItems = mListAdapter.getSelectedItems();

            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            List<Map<String, Object>> callParams = new ArrayList<>();
            for (int i = 0, n = selectedItems.size(); i < n; ++i) {
                HashMap<String, Object> param = new HashMap<>();
                param.put("garment", selectedItems.get(i).getGarment().getId());
                param.put("date", df.format(today));

                callParams.add(param);
            }

            CommonApiCall.postUserCalendarBulk(mContext, TAG, this, callParams);

            // Recover view
            visibleEditMode(false);
            mListAdapter.setEditMode(false);
        });


        //코디보기
        btnCodi = view.findViewById(R.id.btn_codi);
        btnCodi.setOnClickListener(v -> {
            List<UserGarmentVO> selectedItems = mListAdapter.getSelectedItems();

            List<Map<String, Object>> callParams = new ArrayList<>();
            String garments = "";
            List<String> partList = new ArrayList<>();
            for (int i = 0, n = selectedItems.size(); i < n; i++) {
                GarmentVO garment = selectedItems.get(i).getGarment();
                garments += garment.getId();
                if (i != n - 1)
                    garments += ',';
            }

            String[] partArray = {"top", "bottom", "outer", "dress", "shoes"};
            String parts = "";
            for (int i = 0, n = partArray.length; i < n; i++) {
                if (partArray[i] != mPartName) {
                    if (!parts.isEmpty())
                        parts += ',';
                    parts += partArray[i];
                }
            }

            Log.d("TEST", "send data to main activity");
            ((MainActivity) getActivity()).viewOutfitWithFixedGarment(garments, parts);
        });

        btnDelete = view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> {
            List<UserGarmentVO> selectedItems = mListAdapter.getSelectedItems();
            String ids = "";
            for (int i = 0, n = selectedItems.size(); i < n; ++i) {
                ids += String.valueOf(selectedItems.get(i).getId());

                if (i != n - 1) {
                    ids += ',';
                }
            }

            CommonApiCall.deleteUserGarmentBulk(mContext, TAG, this, ids);

            // Recover view
            visibleEditMode(false);
            mListAdapter.setEditMode(false);
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // FIXME: not implemented
        Toast.makeText(mContext, " User selected something ", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // FIXME: not implemented
//        MenuInflater menuInflater = getActivity().getMenuInflater();
//        menuInflater.inflate(R.menu.context_menu_documents_fragment, menu);
    }

    @Override
    public void visibleEditMode(boolean editMode) {
        if (editMode) {
            mLinearCalender.setVisibility(View.VISIBLE);
        } else {
            mLinearCalender.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {

    }

    public boolean isEditMode() {
        return editMode;
    }


    /////////////////////////////////////////
    // implements SwipeRefreshLayout.OnRefreshListener
    @Override
    public void onRefresh() {
        refresh();
    }
    // end SwipeRefreshLayout.OnRefreshListener
    /////////////////////////////////////////

    public void refresh() {
        // Recover view
        visibleEditMode(false);
        mListAdapter.setEditMode(false);

        // Refetch data
        mViewModel.invalidateDataSource();

        // Stop refreshing UI
        mRefreshLayout.setRefreshing(false);
    }
}
