package com.intellisyscorp.fitzme_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.interfaces.AgeGroupReturnCallback;
import com.intellisyscorp.fitzme_android.interfaces.IRecycleViewSelectPositionCallback;
import com.intellisyscorp.fitzme_android.models.ConfigVO;

import java.util.List;


public class AgeGroupListAdapter extends RecyclerView.Adapter<AgeGroupListAdapter.ViewHolder>
        implements IRecycleViewSelectPositionCallback {

    Context mContext;
    private List<ConfigVO.PublicData.AgegroupData> mAgeGroupList;
    private AgeGroupReturnCallback callback;


    public AgeGroupListAdapter(Context context, List<ConfigVO.PublicData.AgegroupData> ageList, AgeGroupReturnCallback callback) {
        this.mContext = context;
        this.mAgeGroupList = ageList;
        this.callback = callback;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checkbox_age, parent, false);

        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final ConfigVO.PublicData.AgegroupData agegroupData = mAgeGroupList.get(position);


        viewHolder.setIsRecyclable(false);

        if (agegroupData != null) {
            viewHolder.tvAge.setText(agegroupData.getName_ko());

            if (mAgeGroupList.get(position).isSelected())
                viewHolder.checkboxCircle.setChecked(true);
            else
                viewHolder.checkboxCircle.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return mAgeGroupList.size();
    }

    @Override
    public void itemClick(int position) {

        if (mAgeGroupList.get(position).isSelected()) {
            // 선택한 아이템이 이전에 선택되어 있지 않는 경우
            // 모든 아이템에 대한 false 적용
            for (int i = 0; i < mAgeGroupList.size(); i++) {
                mAgeGroupList.get(i).setSelected(false);
            }
            // 선택한 아이템에 대한 true 적용
            mAgeGroupList.get(position).setSelected(true);
            if (callback != null)
                callback.itemClick(mAgeGroupList.get(position));
        } else {
            if (callback != null) {
                callback.itemClick(mAgeGroupList.get(position));
            }

        }
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private IRecycleViewSelectPositionCallback listener;
        private RelativeLayout relativeAge;
        private AppCompatTextView tvAge;
        private CheckBox checkboxCircle;

        ViewHolder(View itemView, AgeGroupListAdapter adapter) {
            super(itemView);
            this.listener = adapter;

            relativeAge = itemView.findViewById(R.id.relative_age);
            tvAge = itemView.findViewById(R.id.tv_age);
            checkboxCircle = itemView.findViewById(R.id.checkbox_circle);

            relativeAge.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.itemClick(getAdapterPosition());
        }
    }

}
