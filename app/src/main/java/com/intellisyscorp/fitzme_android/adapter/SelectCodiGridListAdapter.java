package com.intellisyscorp.fitzme_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.models.OutfitVO;
import com.intellisyscorp.fitzme_android.view.CustomViewUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectCodiGridListAdapter extends RecyclerView.Adapter<SelectCodiGridListAdapter.OutfitViewHolder> {

    public interface OuterListListener {
        void visibleEditMode(boolean editMode);
    }

    private OuterListListener outerListListener;
    private static final String TAG = "MyOutfitListAdapter";
    private SparseBooleanArray seletedItems;
    private List<OutfitVO>
            mCombinationOutfitDataList = new ArrayList<>();
    //OuterFragment mOuterFragment;
    private boolean editMode = false;
    private Context mContext;
    private int mGarmentId;

    public SelectCodiGridListAdapter(Context c, List<OutfitVO> combinationOutfitDataList) {
        this.mContext = c;
        this.mCombinationOutfitDataList = combinationOutfitDataList;
    }


    @NonNull
    @Override
    public OutfitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new OutfitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutfitViewHolder viewHolder, final int position) {
        viewHolder.bind(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Intent intent = new Intent(mContext, MyOutfitDetailActivity.class);
//                    Bundle extras = new Bundle();
//                    ArrayList<String> strDummyList = new ArrayList<>();
//                    strDummyList.add(combinationOutfitData.getOuter().getImage());
//                    strDummyList.add(combinationOutfitData.getTop().getImage());
//                    strDummyList.add(combinationOutfitData.getBottom().getImage());
//                    strDummyList.add(combinationOutfitData.getShoes().getImage());
//                    extras.putStringArrayList("strDummyList", strDummyList);
//                    intent.putExtras(extras);
//                    mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.recyclerview_item_outfit;
    }

    @Override
    public int getItemCount() {
        return mCombinationOutfitDataList.size();
    }

    public class OutfitViewHolder extends RecyclerView.ViewHolder {
        // View
        ConstraintLayout viewOutfit;
        CheckBox btnFavorite;

        public OutfitViewHolder(@NonNull View itemView) {
            super(itemView);

            btnFavorite = itemView.findViewById(R.id.btn_favorite);
            viewOutfit = itemView.findViewById(R.id.view_outfit);

            btnFavorite.setVisibility(View.GONE);
        }

        public void bind(int position) {
            OutfitVO outfit = mCombinationOutfitDataList.get(position);
            CustomViewUtil.bindOutfit(mContext, outfit, viewOutfit);
        }
    }
}
