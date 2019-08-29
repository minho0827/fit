package com.intellisyscorp.fitzme_android.paging;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.activtiy.ItemDetailActivity;
import com.intellisyscorp.fitzme_android.models.GarmentVO;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GarmentPagedListAdapter extends PagedListAdapter<GarmentVO, GarmentPagedListAdapter.GarmentViewHolder> {
    private static final String TAG = "GarmentPagedListAdapter";
    private Context mCtx;
    private boolean mEditMode = false;
    private GarmentPagedListListener mListListener;
    private SparseBooleanArray mSeletedItems = new SparseBooleanArray();
    private int count = 0;


    public interface GarmentPagedListListener {
        void visibleEditMode(boolean editMode);
        void setItemCount(int count);
    }

    public GarmentPagedListAdapter(Context ctx, GarmentPagedListListener listListener) {
        super(DIFF_CALLBACK);
        mCtx = ctx;
        mListListener = listListener;
    }

    ///////////////////////////////////////////
    // PagedListAdapter
    @NonNull
    @Override
    public GarmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_user_quick_add_item, viewGroup, false);
        return new GarmentViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull GarmentViewHolder viewHolder, int i) {
        if (getEditMode()) {
            viewHolder.btnCheck.setVisibility(View.VISIBLE);
            viewHolder.btnCheck.setChecked(isSelected(i));
        } else {
            viewHolder.btnCheck.setVisibility(View.GONE);
        }

        final GarmentVO garment = getItem(i);
        viewHolder.tvTitle.setText("STYLENANDA"); // Hard-coded brand. After fixing crawling part, it'll be assigned from GarmentVO

        if (garment != null) {
            Glide.with(mCtx)
                    .load(garment.getImage())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder)
                            .bitmapTransform(new CropCircleTransformation(mCtx))
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .skipMemoryCache(true)
                            .fitCenter())
                    .transition(withCrossFade())
                    .into(viewHolder.imgGarment);
        } else {
            Log.d(TAG, "garment is null");
        }
    }
    // end PagedListAdapter
    ///////////////////////////////////////////

    class GarmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        GarmentPagedListAdapter mListAdapter;
        ImageView imgGarment;
        TextView tvTitle;
        CheckBox btnCheck;

        public GarmentViewHolder(@NonNull View itemView, GarmentPagedListAdapter garmentPagedListAdapter) {
            super(itemView);

            mListAdapter = garmentPagedListAdapter;
            imgGarment = itemView.findViewById(R.id.img_garment);
            tvTitle = itemView.findViewById(R.id.tv_title);
            btnCheck = itemView.findViewById(R.id.btn_check);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListAdapter.getEditMode()) { // toggle multiple selection
                mListAdapter.toggleSelected(getAdapterPosition());
                btnCheck.setChecked(mListAdapter.isSelected(getAdapterPosition()));
            } else {
                GarmentVO garment = getItem(getAdapterPosition());
                Gson gson = new Gson();
                String garmentStr = gson.toJson(garment);

                Intent intent = new Intent(mCtx, ItemDetailActivity.class);
                Bundle extras = new Bundle();

                extras.putString("garment", garmentStr);
                intent.putExtras(extras);
                mCtx.startActivity(intent);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mListAdapter.getEditMode())
                return false;

            mListAdapter.setEditMode(true);
            mListAdapter.toggleSelected(getAdapterPosition());
            mListListener.visibleEditMode(true);
            notifyDataSetChanged();

            return true;
        }
    }

    private static DiffUtil.ItemCallback<GarmentVO> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<GarmentVO>() {
                @Override
                public boolean areItemsTheSame(GarmentVO oldItem, GarmentVO newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(GarmentVO oldItem, GarmentVO newItem) {
                    // FIXME(sjkim): needs override?
                    return oldItem.equals(newItem);
                }
            };

    private void toggleSelected(int pos) {
        if (mSeletedItems.get(pos, false))
            count--;
        else
            count++;
        mListListener.setItemCount(count);
        mSeletedItems.put(pos, !mSeletedItems.get(pos, false));
    }

    private boolean isSelected(int pos) {
        return mSeletedItems.get(pos, false);
    }

    public List<GarmentVO> getSelectedItems() {
        List<GarmentVO> ret = new ArrayList<>();
        for (int i = 0, n = mSeletedItems.size(); i < n; ++i) {
            if (mSeletedItems.valueAt(i)) {
                int pos = mSeletedItems.keyAt(i);
                GarmentVO garment = getItem(pos);
                ret.add(garment);
            }
        }

        return ret;
    }

    public void setEditMode(boolean mode) {
        mEditMode = mode;

        if (!mode) {
            mSeletedItems.clear();
            count = 0;
        }
    }

    public boolean getEditMode() {
        return mEditMode;
    }
}
