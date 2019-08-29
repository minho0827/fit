package com.intellisyscorp.fitzme_android.dropdown;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.adapter.ColorDropdownAdapter;

/**
 * Created by Admin2 on 10/Jul/2018.
 */

public class ColorDropdownMenu extends PopupWindow {
    private Context context;
    private RecyclerView rvCategory;
    private ColorDropdownAdapter dropdownAdapter;

    public ColorDropdownMenu(Context context) {
        super(context);
        this.context = context;
        setupView();
    }

    public void setCategorySelectedListener(ColorDropdownAdapter.CategorySelectedListener categorySelectedListener) {
        dropdownAdapter.setCategorySelectedListener(categorySelectedListener);
    }

    private void setupView() {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_category, null);

        rvCategory = view.findViewById(R.id.recyclerview);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvCategory.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        dropdownAdapter = new ColorDropdownAdapter(ColorCategory.generateCategoryList());
        rvCategory.setAdapter(dropdownAdapter);

        setContentView(view);
    }
}
