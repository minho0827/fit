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

import java.util.ArrayList;

public class MyOutfitDetailGridLsitAdapter extends RecyclerView.Adapter<MyOutfitDetailGridLsitAdapter.ViewHolder> {

    private static final String TAG = "MyOutfitDetailGridLsitAdapter";
    private ArrayList<String> mStrDummyList = new ArrayList<>();
    private Context mContext;


    public MyOutfitDetailGridLsitAdapter(Context c, ArrayList<String> combinationOutfitDataList) {

        this.mContext = c;
        this.mStrDummyList = combinationOutfitDataList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_outfit_detail_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {


        Glide.with(mContext)
                .load(mStrDummyList.get(position))
                .apply(new RequestOptions().override(500, 700))
                .into(viewHolder.ivOutfit);


    }

    @Override
    public int getItemCount() {

        return mStrDummyList.size();

    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView ivOutfit;

        public ViewHolder(View itemView) {
            super(itemView);
            ivOutfit = itemView.findViewById(R.id.iv_outfit);


        }


        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }


}
