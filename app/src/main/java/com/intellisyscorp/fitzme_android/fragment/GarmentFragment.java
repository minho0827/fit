package com.intellisyscorp.fitzme_android.fragment;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.activtiy.QuickAddItemActivity;
import com.intellisyscorp.fitzme_android.interfaces.OnBackPressedListener;
import com.intellisyscorp.fitzme_android.models.GarmentVO;
import com.intellisyscorp.fitzme_android.network.CommonApiCall;
import com.intellisyscorp.fitzme_android.paging.GarmentPagedListAdapter;
import com.intellisyscorp.fitzme_android.paging.GarmentViewModel;
import com.intellisyscorp.fitzme_android.utils.ConfigManager;
import com.intellisyscorp.fitzme_android.utils.MultiSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressLint("ValidFragment")
public class GarmentFragment extends Fragment implements OnBackPressedListener, GarmentPagedListAdapter.GarmentPagedListListener {
    private static final String TAG = "GarmentFragment";

    private boolean editMode = false;

    private RecyclerView mRecyclerView;
    private LinearLayout linearEditMode;
    private AppCompatTextView tvSelectGmtCount;
    private Button btnAddCloset;
    private Context mContext;
    LinearLayoutManager mLayoutManager;
    private QuickAddItemActivity mQuickAddItem;
    private String mPartName;
    private GarmentViewModel mViewModel;

    private GarmentPagedListAdapter adapter;

    private MultiSpinner sCategory, sColor, sTag;
    private ArrayList<String> mFilterCategory = new ArrayList<>();
    private ArrayList<String> mFilterKey = new ArrayList<>();
    private ArrayList<String> mFilterColor = new ArrayList<>();

    public GarmentFragment(QuickAddItemActivity quickAddItem, String part) {
        mQuickAddItem = quickAddItem;
        mPartName = part;
    }

    public static GarmentFragment newInstance(QuickAddItemActivity activity, String part) {
        return new GarmentFragment(activity, part);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_garment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //creating the Adapter
        adapter = new GarmentPagedListAdapter(this.getContext(), this);

        mRecyclerView = view.findViewById(R.id.recyclerview);

        tvSelectGmtCount = view.findViewById(R.id.tv_select_garment_count);
        linearEditMode = view.findViewById(R.id.linear_edit_mode);

        //getting our ItemViewModel
        mViewModel = ViewModelProviders.of(this, new GarmentViewModel.MyFactory(this, mPartName)).get(GarmentViewModel.class);

        //observing the itemPagedList from view model
        mViewModel.getPagedList().observe(this, (@Nullable PagedList<GarmentVO> items) -> {
            //in case of any changes
            //submitting the items to adapter
            adapter.submitList(items);
        });

        mLayoutManager = new GridLayoutManager(getContext(), 3);

        //setting the adapter
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //setting filter
        List<String> category_list = ConfigManager.getInstance().getCategory();
        List<String> category_filtered_list = category_list.stream().filter(c -> mPartName.equals(ConfigManager.getInstance().translateCategoryToPart(c))).collect(Collectors.toList());
        List<String> color_list = ConfigManager.getInstance().getColor();
        List<String> tag_list = ConfigManager.getInstance().getTag();

        List<String> category_ko = new ArrayList<>();
        for (String i : category_filtered_list)
            category_ko.add(ConfigManager.getInstance().translateCategory(i));
        List<String> tag_ko = new ArrayList<>();
        for (String i : tag_list)
            tag_ko.add(ConfigManager.getInstance().translateTag(i));

        sCategory = view.findViewById(R.id.category);
        sCategory.setItems(category_ko, "카테고리", new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                mFilterCategory = IntStream.range(0, selected.length)
                        .filter(i -> selected[i])
                        .mapToObj(i -> category_filtered_list.get(i))
                        .collect(Collectors.toCollection(ArrayList::new));
                refresh();
            }
        });

        sColor = view.findViewById(R.id.color);
        sColor.setItems(color_list, "컬러", new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                mFilterColor = IntStream.range(0, selected.length)
                        .filter(i -> selected[i])
                        .mapToObj(i -> color_list.get(i))
                        .collect(Collectors.toCollection(ArrayList::new));
                refresh();
            }
        });

        sTag = view.findViewById(R.id.tag);
        sTag.setItems(tag_ko, "태그", new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                mFilterKey = IntStream.range(0, selected.length)
                        .filter(i -> selected[i])
                        .mapToObj(i -> tag_list.get(i))
                        .collect(Collectors.toCollection(ArrayList::new));
                refresh();
            }
        });

        btnAddCloset = view.findViewById(R.id.btn_add_closet);
        btnAddCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GarmentVO> selectedItems = adapter.getSelectedItems();

                List<Map<String, Object>> callParams = new ArrayList<>();
                for (int i = 0, n = selectedItems.size(); i < n; ++i) {
                    HashMap<String, Object> param = new HashMap<>();
                    param.put("garment", selectedItems.get(i).getId());
                    param.put("nickname", "notitle");

                    callParams.add(param);
                }

                CommonApiCall.postUserGarmentBulk(mContext, TAG, callParams);

                refresh();
            }
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

//    public void putArguments(Bundle args) {
//        filterCategory = args.getStringArrayList("category");
//        filterColor = args.getStringArrayList("color");
//        filterTag = args.getStringArrayList("tag");
//    }


    @Override
    public void visibleEditMode(boolean editMode) {
        if (editMode) {
            linearEditMode.setVisibility(View.VISIBLE);
        } else {
            linearEditMode.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
    }

    public void refresh() {
        visibleEditMode(false);
        adapter.setEditMode(false);

        // Refetch data
        mViewModel.invalidateDataSource();
        adapter.notifyDataSetChanged();
    }

    public ArrayList<String> getFilterCategory() {
        return mFilterCategory;
    }

    public ArrayList<String> getFilterKey() {
        return mFilterKey;
    }

    public ArrayList<String> getFilterColor() {
        return mFilterColor;
    }

    @Override
    public void setItemCount(int count) {
        tvSelectGmtCount.setText(count + "개 아이템");
    }
}
