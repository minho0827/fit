package com.intellisyscorp.fitzme_android.activtiy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.adapter.SelectCodiGridListAdapter;
import com.intellisyscorp.fitzme_android.models.OutfitVO;
import com.intellisyscorp.fitzme_android.network.CommonApiCall;
import com.intellisyscorp.fitzme_android.view.CustomViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MyOutfitDetailActivity extends AppCompatActivity {
    private final static String TAG = "MyOutfitDetailActivity";

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    CollapsingToolbarLayout collapsingToolbar;

    private Context mContext;
    private GridLayoutManager lLayout;
    LinearLayoutManager mLayoutManager;
    LinearLayoutManager mBottomLayoutManager;
    private int mPosition;
    ConstraintLayout viewOutfit;
    ImageButton imgbtnFinish;
    CheckBox cbHeart;

    OutfitVO mOutfit;

    // @BindView(R.id.toolbar_title)
    // AppCompatTextView tvToolbarTitle;

    SelectCodiGridListAdapter mSelectCoidGridListAdapter;
    List<OutfitVO> mCombinationOutfitDataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String outfitStr = intent.getStringExtra("outfit");
        Gson gson = new Gson();
        mOutfit = gson.fromJson(outfitStr, OutfitVO.class);

        setContentView(R.layout.activity_outfit_detail);

        mContext = this;
        mRecyclerView = findViewById(R.id.recyclerview);
        collapsingToolbar = findViewById(R.id.toolbarCollapse);
        imgbtnFinish = findViewById(R.id.imgbtn_finish);
        cbHeart = findViewById(R.id.cb_heart);
        cbHeart.setChecked(mOutfit.is_like());
        cbHeart.setEnabled(false);

        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mBottomLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        CommonApiCall.getRelatedOutfit(mContext, TAG, this, mOutfit.getId());

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        viewOutfit = findViewById(R.id.view_outfit);
        CustomViewUtil.bindOutfit(mContext, mOutfit, viewOutfit);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "toolbar!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setAdapterToRecyclerView(List<OutfitVO> results) {
        mCombinationOutfitDataList = results;
        mSelectCoidGridListAdapter = new SelectCodiGridListAdapter(mContext, mCombinationOutfitDataList);
        mRecyclerView.setAdapter(mSelectCoidGridListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.oufit_detail_menu, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (mPosition == 0) {

//                menu.findItem(R.id.search).setVisible(true);
//                loginItem.setVisible(false);
            } else {
//                menu.findItem(R.id.search).setVisible(false);
//                loginItem.setVisible(true);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 메뉴의 항목을 선택(클릭)했을 때 호출되는 콜백메서드
        int id = item.getItemId();

        switch (id) {
            case R.id.add_calendar:
                CommonApiCall.postUserCalendarWithOutfit(mContext, TAG, mOutfit.getId());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
