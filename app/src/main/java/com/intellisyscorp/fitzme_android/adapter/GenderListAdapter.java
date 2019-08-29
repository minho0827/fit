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
import com.intellisyscorp.fitzme_android.interfaces.GenderReturnCallback;
import com.intellisyscorp.fitzme_android.interfaces.IRecycleViewSelectPositionCallback;
import com.intellisyscorp.fitzme_android.models.ConfigVO;
import com.intellisyscorp.fitzme_android.utils.Utils;

import java.util.List;


public class GenderListAdapter extends RecyclerView.Adapter<GenderListAdapter.ViewHolder>
        implements IRecycleViewSelectPositionCallback {

    Context mContext;
    private List<ConfigVO.PublicData.GenderData> mGenderList;
    private GenderReturnCallback callback;


    public GenderListAdapter(Context context, List<ConfigVO.PublicData.GenderData> genderList, GenderReturnCallback callback) {
        this.mContext = context;
        this.mGenderList = genderList;
        this.callback = callback;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checkbox_gender, parent, false);

        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final ConfigVO.PublicData.GenderData genderData = mGenderList.get(position);


        viewHolder.setIsRecyclable(false);

        if (genderData != null) {
            viewHolder.tvGender.setText(Utils.getGenderType(genderData.getName_ko()));

            if (mGenderList.get(position).isSelected())
                viewHolder.checkboxCircle.setChecked(true);
            else
                viewHolder.checkboxCircle.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return mGenderList.size();
    }

    @Override
    public void itemClick(int position) {

        if (mGenderList.get(position).isSelected()) {
            // 선택한 아이템이 이전에 선택되어 있지 않는 경우
            // 모든 아이템에 대한 false 적용
            for (int i = 0; i < mGenderList.size(); i++) {
                mGenderList.get(i).setSelected(false);
            }
            // 선택한 아이템에 대한 true 적용
            mGenderList.get(position).setSelected(true);
            if (callback != null)
                callback.itemClick(mGenderList.get(position));
        } else {
            if (callback != null) {
                callback.itemClick(mGenderList.get(position));
            }

        }
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private IRecycleViewSelectPositionCallback listener;
        private RelativeLayout relativeGender;
        private AppCompatTextView tvGender;
        private CheckBox checkboxCircle;

        ViewHolder(View itemView, GenderListAdapter adapter) {
            super(itemView);
            this.listener = adapter;

            relativeGender = itemView.findViewById(R.id.relative_gender);
            tvGender = itemView.findViewById(R.id.tv_gender);
            checkboxCircle = itemView.findViewById(R.id.checkbox_circle);

            relativeGender.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.itemClick(getAdapterPosition());
        }
    }

}