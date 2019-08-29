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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.activtiy.MyOutfitDetailActivity;
import com.intellisyscorp.fitzme_android.models.OutfitVO;
import com.intellisyscorp.fitzme_android.models.UserOutfitLikeResponseVO;
import com.intellisyscorp.fitzme_android.network.CommonApiCall;
import com.intellisyscorp.fitzme_android.network.UserRestService;
import com.intellisyscorp.fitzme_android.utils.RetroUtil;
import com.intellisyscorp.fitzme_android.view.CustomViewUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutfitPagedListAdapter extends PagedListAdapter<OutfitVO, RecyclerView.ViewHolder> {
    private static final String TAG = "OutfitPagedListAdapter";
    private Context mCtx;

    public OutfitPagedListAdapter(Context ctx) {
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
        final OutfitVO outfit = getItem(i);
        ((OutfitViewHolder) viewHolder).bindTo(outfit);
    }
    // end PagedListAdapter
    ///////////////////////////////////////////

    class OutfitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // View
        ConstraintLayout viewOutfit;
        CheckBox btnFavorite;

        public OutfitViewHolder(@NonNull View itemView) {
            super(itemView);

            btnFavorite = itemView.findViewById(R.id.btn_favorite);
            viewOutfit = itemView.findViewById(R.id.view_outfit);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            btnFavorite.setOnClickListener(this);
        }

        public void bindTo(OutfitVO outfit) {
            btnFavorite.setChecked(outfit.is_like());
            CustomViewUtil.bindOutfit(mCtx, outfit, viewOutfit);
        }

        @Override
        public void onClick(View v) {
            OutfitVO outfit = getItem(getAdapterPosition());

            if (v instanceof FrameLayout) { // Outfit item -> detail view
                Gson gson = new Gson();
                String outfitStr = gson.toJson(outfit);

                Intent intent = new Intent(mCtx, MyOutfitDetailActivity.class);
                Bundle extras = new Bundle();

                extras.putString("outfit", outfitStr);
                intent.putExtras(extras);
                mCtx.startActivity(intent);

            } else if (v instanceof CheckBox) { // like button -> user like this outfit
                CheckBox btnLike = (CheckBox) v;

                UserRestService service = RetroUtil.getService(UserRestService.class);

                HashMap<String, Object> params = new HashMap<>();

//                params.put("nickname", "notitle");
                params.put("id", outfit.getId());


                // TODO(sjkim): set is_like as true
                // params.put("is_like", true);

                if (outfit.getTop() != null)
                    params.put("top", outfit.getTop().getId());
                else
                    params.put("top", null);

                if (outfit.getBottom() != null)
                    params.put("bottom", outfit.getBottom().getId());
                else
                    params.put("bottom", null);

                if (outfit.getOuter() != null)
                    params.put("outer", outfit.getOuter().getId());
                else
                    params.put("outer", null);

                if (outfit.getDress() != null)
                    params.put("dress", outfit.getDress().getId());
                else
                    params.put("dress", null);

                if (outfit.getShoes() != null)
                    params.put("shoes", outfit.getShoes().getId());
                else
                    params.put("shoes", null);


                if (btnLike.isChecked()) {
                    // Add user_like_outfit
                    Log.d(TAG, params.toString());
                    final Call<UserOutfitLikeResponseVO> call_ = service.putUserOutfitLike(params);
                    call_.enqueue(new Callback<UserOutfitLikeResponseVO>() {
                        @Override
                        public void onResponse(Call<UserOutfitLikeResponseVO> call, Response<UserOutfitLikeResponseVO> response) {
                            if (response.isSuccessful()) {
                                Log.d(TAG, "User likes this outfit");
                                Log.d(TAG, response.body().toString());
                                Toast.makeText(mCtx, "코디를 좋아요했습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, response.body().toString());
                                Log.d(TAG, "Bad response code: " + response.code());
                                Toast.makeText(mCtx, "좋아요에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserOutfitLikeResponseVO> call, Throwable e) {
                            Log.d(TAG, "Can't add to user liked outfit, ", e);
                        }
                    });
                } else {
                    final Call<UserOutfitLikeResponseVO> call_ = service.putUserOutfitLikeOff(params);
                    call_.enqueue(new Callback<UserOutfitLikeResponseVO>() {
                        @Override
                        public void onResponse(Call<UserOutfitLikeResponseVO> call, Response<UserOutfitLikeResponseVO> response) {
                            if (response.isSuccessful()) {
                                Log.d(TAG, "User like off this outfit");
                                Toast.makeText(mCtx, "좋아요를 해제했습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "Bad response code: " + response.code());
                                Toast.makeText(mCtx, "좋아요 해제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserOutfitLikeResponseVO> call, Throwable e) {
                            Log.d(TAG, "Can't like off user outfit, ", e);
                        }
                    });
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            openOptionMenuAnonymous(v);
            return true;
        }

        private void openOptionMenuAnonymous(View v) {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.my_outfit_option_menu_items, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    OutfitVO outfit = getItem(getAdapterPosition());

                    switch (item.getItemId()) {
                        case R.id.item_calendar: // Add to calendar
                            CommonApiCall.postUserCalendarWithOutfit(mCtx, TAG, outfit.getId());
                            break;

                        case R.id.item_detail: // Goto detail view
                            Gson gson = new Gson();
                            String outfitStr = gson.toJson(outfit);

                            Intent intent = new Intent(mCtx, MyOutfitDetailActivity.class);
                            Bundle extras = new Bundle();

                            extras.putString("outfit", outfitStr);
                            intent.putExtras(extras);
                            mCtx.startActivity(intent);
                            break;
                    }
                    return true;
                }
            });
            popup.show();
        }
    }

    private static DiffUtil.ItemCallback<OutfitVO> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<OutfitVO>() {
                @Override
                public boolean areItemsTheSame(OutfitVO oldItem, OutfitVO newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(OutfitVO oldItem, OutfitVO newItem) {
                    // FIXME(sjkim): needs override?
                    return oldItem.equals(newItem);
                }
            };
}
