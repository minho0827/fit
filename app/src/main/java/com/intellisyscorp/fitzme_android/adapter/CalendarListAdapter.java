package com.intellisyscorp.fitzme_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.models.CalendarVO;
import com.intellisyscorp.fitzme_android.models.OutfitVO;

import java.util.List;

public class CalendarListAdapter extends RecyclerView.Adapter<CalendarListAdapter.ViewHolder> {
    Context mContext;
    private List<CalendarVO> mCalendarList;

    public CalendarListAdapter(Context context, List<CalendarVO> calendarVOList) {
        this.mContext = context;
        this.mCalendarList = calendarVOList;
    }

    @NonNull
    @Override
    public CalendarListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        ViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_calendar_garment_item, parent, false);
                viewHolder = new GarmentViewHolder(view);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_calendar_top_bottom_outfit, parent, false);
                viewHolder = new TopBottomOutfitViewHolder(view);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_calendar_dress_outfit, parent, false);
                viewHolder = new DressOutfitViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarListAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mCalendarList.get(position).getObj().getGarment() != null)
            return 0;
        if (mCalendarList.get(position).getObj().getOutfit() == null)
            throw new IllegalArgumentException("Garment and Outfit both are null");

        if (mCalendarList.get(position).getObj().getOutfit().getDress() == null)
            return 1;
        else
            return 2;
    }

    @Override
    public int getItemCount() {
        return mCalendarList.size();
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(int position);
    }

    public class GarmentViewHolder extends ViewHolder {
        public ImageView ivGarment;

        public GarmentViewHolder(View v) {
            super(v);
            ivGarment = v.findViewById(R.id.img_garment);
        }

        @Override
        public void bind(int position) {
            Glide.with(mContext)
                    .load(mCalendarList.get(position).getObj().getGarment().getImage())
                    .apply(new RequestOptions().override(300, 300))
                    .into(ivGarment);
        }
    }

    public class TopBottomOutfitViewHolder extends ViewHolder {
        ImageView ivTop, ivBottom, ivOuter, ivShoes;

        public TopBottomOutfitViewHolder(View v) {
            super(v);
            ivTop = v.findViewById(R.id.iv_top);
            ivOuter = v.findViewById(R.id.iv_outer);
            ivShoes = v.findViewById(R.id.iv_shoes);
            ivBottom = v.findViewById(R.id.iv_bottom);
        }

        @Override
        public void bind(int position) {
            OutfitVO outfit = mCalendarList.get(position).getObj().getOutfit();
            Glide.with(mContext)
                    .load(outfit.getTop().getImage())
                    .apply(new RequestOptions().override(150, 150))
                    .into(ivTop);
            Glide.with(mContext)
                    .load(outfit.getBottom().getImage())
                    .apply(new RequestOptions().override(150, 150))
                    .into(ivBottom);
            if (outfit.getOuter() != null) {
                Glide.with(mContext)
                        .load(outfit.getOuter().getImage())
                        .apply(new RequestOptions().override(150, 150))
                        .into(ivOuter);
            }
            if (outfit.getShoes() != null) {
                Glide.with(mContext)
                        .load(outfit.getShoes().getImage())
                        .apply(new RequestOptions().override(150, 150))
                        .into(ivShoes);
            }
        }
    }

    public class DressOutfitViewHolder extends ViewHolder {
        public ImageView ivDress, ivOuter, ivShoes;

        public DressOutfitViewHolder(View v) {
            super(v);
            ivDress = v.findViewById(R.id.iv_dress);
            ivOuter = v.findViewById(R.id.iv_outer);
            ivShoes = v.findViewById(R.id.iv_shoes);
        }

        @Override
        public void bind(int position) {
            OutfitVO outfit = mCalendarList.get(position).getObj().getOutfit();
            Glide.with(mContext)
                    .load(outfit.getDress().getImage())
                    .apply(new RequestOptions().override(150, 150))
                    .into(ivDress);
            if (outfit.getOuter() != null) {
                Glide.with(mContext)
                        .load(outfit.getOuter().getImage())
                        .apply(new RequestOptions().override(150, 150))
                        .into(ivOuter);
            }
            if (outfit.getShoes() != null) {
                Glide.with(mContext)
                        .load(outfit.getShoes().getImage())
                        .apply(new RequestOptions().override(150, 150))
                        .into(ivShoes);
            }
        }
    }

}
