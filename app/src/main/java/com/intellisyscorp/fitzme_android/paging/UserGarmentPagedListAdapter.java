package com.intellisyscorp.fitzme_android.paging;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.activtiy.ItemDetailActivity;
import com.intellisyscorp.fitzme_android.models.UserGarmentVO;
import com.intellisyscorp.fitzme_android.utils.ConfigManager;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class UserGarmentPagedListAdapter extends PagedListAdapter<UserGarmentVO, UserGarmentPagedListAdapter.UserGarmentViewHolder> {
    private static final String TAG = "UserGarmentPagedListAdapter";
    private Context mContext;
    private UserGarmentPagedListListener mListListener;
    private SparseBooleanArray mSeletedItems = new SparseBooleanArray();
    private boolean mEditMode = false;

    public interface UserGarmentPagedListListener {
        void visibleEditMode(boolean editMode);
    }

    public UserGarmentPagedListAdapter(Context context, UserGarmentPagedListListener listListener) {
        super(DIFF_CALLBACK);
        mContext = context;
        mListListener = listListener;
    }

    ///////////////////////////////////////////
    // PagedListAdapter
    @NonNull
    @Override
    public UserGarmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_user_garment_item, viewGroup, false);
        return new UserGarmentViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserGarmentViewHolder viewHolder, int i) {
        if (getEditMode()) {
            viewHolder.btnCheck.setVisibility(View.VISIBLE);
            viewHolder.btnCheck.setChecked(isSelected(i));
        } else {
            viewHolder.btnCheck.setVisibility(View.GONE);
        }

        final UserGarmentVO userGarment = getItem(i);

        String strTags = "";
        for (String tag : userGarment.getGarment().getTags()) {
            strTags += "#" + ConfigManager.getInstance().translateTag(tag) + " ";
        }

        String strWeathers = "";
        for (String weather : userGarment.getGarment().getWeathers()) {
            strTags += ConfigManager.getInstance().translateWeather(weather) + ", ";
        }

        viewHolder.tvTitle.setText(userGarment.getGarment().getCategory());
        viewHolder.tvWeather.setText(strWeathers);
        viewHolder.tvTags.setText(strTags);

        if (userGarment.getFrequency() == 0) {
            viewHolder.tvGarmentPutOnCount.setVisibility(View.GONE);
        } else {
            viewHolder.tvGarmentPutOnCount.setText(userGarment.getFrequency() + "회");
        }

        if (userGarment != null) {
            Glide.with(mContext)
                    .load(userGarment.getGarment().getImage())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder)
                            .bitmapTransform(new CropCircleTransformation(mContext))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .fitCenter())
                    .transition(withCrossFade())
                    .into(viewHolder.imgGarment);
        }
    }

    // end PagedListAdapter
    ///////////////////////////////////////////

    class UserGarmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        UserGarmentPagedListAdapter mListAdapter;
        ImageView imgGarment;
        CheckBox btnCheck;
        LinearLayout layoutCheck;
        AppCompatTextView tvTitle;
        AppCompatTextView tvWeather;
        AppCompatTextView tvTags;
        AppCompatTextView tvTemp;
        AppCompatTextView tvGarmentPutOnCount;

        public UserGarmentViewHolder(@NonNull View itemView, UserGarmentPagedListAdapter listAdapter) {
            super(itemView);

            mListAdapter = listAdapter;

            btnCheck = itemView.findViewById(R.id.btn_check);
            imgGarment = itemView.findViewById(R.id.img_garment);
            layoutCheck = itemView.findViewById(R.id.layout_check);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvWeather = itemView.findViewById(R.id.tv_weather);
            tvTags = itemView.findViewById(R.id.tv_hash_tag);
            tvGarmentPutOnCount = itemView.findViewById(R.id.tv_garment_put_on_count);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListAdapter.getEditMode()) { // toggle multiple selection
                mListAdapter.toggleSelected(getAdapterPosition());
                btnCheck.setChecked(mListAdapter.isSelected(getAdapterPosition()));

            } else {
                UserGarmentVO garment = getItem(getAdapterPosition());
                Gson gson = new Gson();
                String garmentStr = gson.toJson(garment.getGarment());

                Intent intent = new Intent(mContext, ItemDetailActivity.class);
                Bundle extras = new Bundle();

                extras.putString("garment", garmentStr);
                intent.putExtras(extras);
                mContext.startActivity(intent);
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

    private static DiffUtil.ItemCallback<UserGarmentVO> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<UserGarmentVO>() {
                @Override
                public boolean areItemsTheSame(UserGarmentVO oldItem, UserGarmentVO newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(UserGarmentVO oldItem, UserGarmentVO newItem) {
                    // FIXME(sjkim): needs override?
                    return oldItem.equals(newItem);
                }
            };

    private void toggleSelected(int pos) {
        mSeletedItems.put(pos, !mSeletedItems.get(pos, false));
    }

    private boolean isSelected(int pos) {
        return mSeletedItems.get(pos, false);
    }

    public List<UserGarmentVO> getSelectedItems() {
        List<UserGarmentVO> ret = new ArrayList<>();
        for (int i = 0, n = mSeletedItems.size(); i < n; ++i) {
            if (mSeletedItems.valueAt(i)) {
                int pos = mSeletedItems.keyAt(i);
                UserGarmentVO garment = getItem(pos);
                ret.add(garment);
            }
        }

        return ret;
    }

    public void setEditMode(boolean mode) {
        mEditMode = mode;

        if (!mode) {
            mSeletedItems.clear();
        }
    }

    public boolean getEditMode() {
        return mEditMode;
    }
}
