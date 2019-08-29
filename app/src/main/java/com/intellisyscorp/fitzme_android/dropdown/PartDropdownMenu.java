package com.intellisyscorp.fitzme_android.dropdown;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.adapter.PartDropdownAdapter;
import com.intellisyscorp.fitzme_android.models.ConfigVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin2 on 10/Jul/2018.
 */

public class PartDropdownMenu extends PopupWindow {
    private Context context;
    private RecyclerView rvCategory;
    private PartDropdownAdapter dropdownAdapter;
    private List<ConfigVO.PublicData.PartData> mPartDataList = new ArrayList<>();

    public PartDropdownMenu(Context context, List<ConfigVO.PublicData.PartData> partDataList) {
        super(context);
        this.context = context;
        this.mPartDataList = partDataList;
        setupView();
    }

    public void setCategorySelectedListener(PartDropdownAdapter.CategorySelectedListener categorySelectedListener) {
        dropdownAdapter.setCategorySelectedListener(categorySelectedListener);
    }

    private void setupView() {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_category, null);

        rvCategory = view.findViewById(R.id.recyclerview);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvCategory.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        dropdownAdapter = new PartDropdownAdapter(mPartDataList);
        rvCategory.setAdapter(dropdownAdapter);

        setContentView(view);
    }
}
