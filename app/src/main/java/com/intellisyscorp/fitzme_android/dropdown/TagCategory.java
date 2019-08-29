package com.intellisyscorp.fitzme_android.dropdown;


import com.intellisyscorp.fitzme_android.R;

import java.util.ArrayList;
import java.util.List;

public class TagCategory {
    public long id;
    public int iconRes;
    public String category;

    public TagCategory(long id, int iconRes, String category) {
        super();
        this.id = id;
        this.iconRes = iconRes;
        this.category = category;
    }

    public static List<TagCategory> generateCategoryList() {
        List<TagCategory> categories = new ArrayList<>();
        String[] programming = {"페미닌", "여성스러운", "20대", "30대"};

        for (int i = 0; i < programming.length; i++) {
            categories.add(new TagCategory(i, R.drawable.sun, programming[i]));
        }
        return categories;
    }

}
