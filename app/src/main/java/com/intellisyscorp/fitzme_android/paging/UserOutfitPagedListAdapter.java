package com.intellisyscorp.fitzme_android.paging;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.activtiy.MyOutfitDetailActivity;
import com.intellisyscorp.fitzme_android.models.UserOutfitLikeResponseVO;
import com.intellisyscorp.fitzme_android.models.UserOutfitVO;
import com.intellisyscorp.fitzme_android.network.UserRestService;
import com.intellisyscorp.fitzme_android.utils.RetroUtil;
import com.intellisyscorp.fitzme_android.view.CustomViewUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOutfitPagedListAdapter extends PagedListAdapter<UserOutfitVO, RecyclerView.ViewHolder> {
    private static final String TAG = "UserOutfitPagedListAdapter";
    private Context mCtx;

    public UserOutfitPagedListAdapter(Context ctx) {
        super(DIFF_CALLBACK);
        mCtx = ctx;
    }

    ///////////////////////////////////////////
    // PagedListAdapter
    @Override
    public int getItemViewType(int position) {
        return R.layout.recyclerview_item_outfit;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(viewType, viewGroup, false);
        return new OutfitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final UserOutfitVO userOutfit = getItem(i);
        ((OutfitViewHolder) viewHolder).bindTo(userOutfit);
    }
    // end PagedListAdapter
    ///////////////////////////////////////////

    class OutfitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // View
        ConstraintLayout viewOutfit;
        CheckBox btnFavorite;

        public OutfitViewHolder(@NonNull View itemView) {
            super(itemView);

            btnFavorite = itemView.findViewById(R.id.btn_favorite);
            viewOutfit = itemView.findViewById(R.id.view_outfit);

            itemView.setOnClickListener(this);
            btnFavorite.setOnClickListener(this);
        }

        public void bindTo(UserOutfitVO userOutfit) {
            btnFavorite.setChecked(userOutfit.is_like());
            CustomViewUtil.bindOutfit(mCtx, userOutfit.getOutfit(), viewOutfit);
        }

        @Override
        public void onClick(View v) {
            UserOutfitVO userOutfit = getItem(getAdapterPosition());

            if (v instanceof FrameLayout) { // Outfit item -> detail view
                Gson gson = new Gson();
                String outfitStr = gson.toJson(userOutfit.getOutfit());

                Intent intent = new Intent(mCtx, MyOutfitDetailActivity.class);
                Bundle extras = new Bundle();

                extras.putString("outfit", outfitStr);
                extras.putInt("user_outfit_id", userOutfit.getId());
                intent.putExtras(extras);
                mCtx.startActivity(intent);
            } else if (v instanceof CheckBox) { // like button -> user like this outfit
                CheckBox cb = (CheckBox) v;

                UserRestService service = RetroUtil.getService(UserRestService.class);
                int id = userOutfit.getId();
                Log.d(TAG, "id : " + id);

                if (cb.isChecked()) {
                    // Like User Outfit
                    Log.d(TAG, "Like User Outfit");
                    final Call<UserOutfitLikeResponseVO> call_ = service.putUserOutfitLikeWithId(id);
                    call_.enqueue(new Callback<UserOutfitLikeResponseVO>() {
                        @Override
                        public void onResponse(Call<UserOutfitLikeResponseVO> call, Response<UserOutfitLikeResponseVO> response) {
                            if (response.isSuccessful()) {
                                Log.d(TAG, "User likes this outfit");
                            } else {
                                Log.d(TAG, "Bad response code: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserOutfitLikeResponseVO> call, Throwable e) {
                            Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
                        }
                    });
                } else {
                    final Call<UserOutfitLikeResponseVO> call_ = service.putUserOutfitLikeOffWithId(id);
                    call_.enqueue(new Callback<UserOutfitLikeResponseVO>() {
                        @Override
                        public void onResponse(Call<UserOutfitLikeResponseVO> call, Response<UserOutfitLikeResponseVO> response) {
                            if (response.isSuccessful()) {
                                Log.d(TAG, "User likes off this outfit");
                            } else {
                                Log.d(TAG, "Bad response code: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserOutfitLikeResponseVO> call, Throwable e) {
                            Log.d(TAG, "onFailure: 데이터 가져오는데 실패..", e);
                        }
                    });
                }
            }
        }
    }

    private static DiffUtil.ItemCallback<UserOutfitVO> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<UserOutfitVO>() {
                @Override
                public boolean areItemsTheSame(UserOutfitVO oldItem, UserOutfitVO newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(UserOutfitVO oldItem, UserOutfitVO newItem) {
                    // FIXME(sjkim): needs override?
                    return oldItem.equals(newItem);
                }
            };
}
