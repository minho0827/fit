package com.intellisyscorp.fitzme_android.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.dropdown.ColorCategory;

import java.util.List;

/**
 * Created by Admin2 on 10/Jul/2018.
 */

public class ColorDropdownAdapter extends RecyclerView.Adapter<ColorDropdownAdapter.CategoryViewHolder> {

    private List<ColorCategory> categories;
    private CategorySelectedListener categorySelectedListener;

    public ColorDropdownAdapter(List<ColorCategory> categories) {
        super();
        this.categories = categories;
    }


    public void setCategorySelectedListener(ColorDropdownAdapter.CategorySelectedListener categorySelectedListener) {
        this.categorySelectedListener = categorySelectedListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
        final ColorCategory category = categories.get(position);
        holder.ivIcon.setImageResource(category.iconRes);
        holder.tvCategory.setText(category.category);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categorySelectedListener != null) {
                    categorySelectedListener.onCategorySelected(position, category);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvCategory;
        AppCompatImageView ivIcon;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }
    }

    public interface CategorySelectedListener {
        void onCategorySelected(int position, ColorCategory category);
    }
}
