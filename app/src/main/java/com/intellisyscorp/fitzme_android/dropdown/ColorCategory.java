package com.intellisyscorp.fitzme_android.dropdown;


import com.intellisyscorp.fitzme_android.R;

import java.util.ArrayList;
import java.util.List;

public class ColorCategory {

    public long id;
    public int iconRes;
    public String category;

    public ColorCategory(long id, int iconRes, String category) {
        super();
        this.id = id;
        this.iconRes = iconRes;
        this.category = category;
    }

    public static List<ColorCategory> generateCategoryList() {
        List<ColorCategory> categories = new ArrayList<>();
        String[] programming = {"Outer", "Top", "Bottom", "Dress", "Shoes"};

        for (int i = 0; i < programming.length; i++) {
            categories.add(new ColorCategory(i, R.drawable.sun, programming[i]));
        }
        return categories;
    }

}
