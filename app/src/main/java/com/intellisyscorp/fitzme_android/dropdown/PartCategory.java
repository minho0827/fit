package com.intellisyscorp.fitzme_android.dropdown;


import com.intellisyscorp.fitzme_android.R;

import java.util.ArrayList;
import java.util.List;

public class PartCategory {

    public long id;
    public int iconRes;
    public String category;

    public PartCategory(long id, int iconRes, String category) {
        super();
        this.id = id;
        this.iconRes = iconRes;
        this.category = category;
    }

    public static List<PartCategory> generateCategoryList() {
        List<PartCategory> categories = new ArrayList<>();
        String[] programming = {"Outer", "Top", "Bottom", "Dress", "Shoes"};

        for (int i = 0; i < programming.length; i++) {
            categories.add(new PartCategory(i, R.drawable.sun, programming[i]));
        }
        return categories;
    }

}
