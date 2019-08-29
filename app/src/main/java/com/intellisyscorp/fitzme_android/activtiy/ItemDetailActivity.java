package com.intellisyscorp.fitzme_android.activtiy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.models.GarmentVO;
import com.intellisyscorp.fitzme_android.network.CommonApiCall;
import com.intellisyscorp.fitzme_android.utils.ConfigManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDetailActivity extends BaseActivity {
    private static final String TAG = "ItemDetailActivity";

    private Toolbar mToolbar;
    Context mContext;
    FrameLayout framePart, frameTag, frameColor;

    @BindView(R.id.tv_category)
    AppCompatTextView tvCategory;

    @BindView(R.id.tv_garment_name)
    AppCompatTextView tvGarmentName;

    @BindView(R.id.toolbar_title)
    AppCompatTextView tvToolbarTitle;

    @BindView(R.id.imgbtn_finish)
    ImageButton imgbtnFinish;

    @BindView(R.id.img_garment)
    ImageView imgGarment;

    @BindView(R.id.imgbtn_like)
    AppCompatImageButton imgbtnLike;

    @BindView(R.id.tv_spring)
    TextView tvSpring;

    @BindView(R.id.tv_summer)
    TextView tvSummer;

    @BindView(R.id.tv_fall)
    TextView tvFall;

    @BindView(R.id.tv_winter)
    TextView tvWinter;

    @BindView(R.id.iv_shopping)
    ImageView ivShopping;

    @BindView(R.id.tv_brand)
    AppCompatTextView tvBrand;

    @BindView(R.id.layout_tags)
    LinearLayout layoutTags;

    @BindView(R.id.layout_colors)
    LinearLayout layoutColors;

    private GarmentVO mGarment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        String garmentStr = intent.getStringExtra("garment");
        Gson gson = new Gson();
        mGarment = gson.fromJson(garmentStr, GarmentVO.class);
        mContext = this;
        frameColor = findViewById(R.id.frame_color);
        framePart = findViewById(R.id.frame_part);
        frameTag = findViewById(R.id.frame_tag);

        setToolbar();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
        tvToolbarTitle.setText("아이템 상세정보");
        tvToolbarTitle.setTextColor(Color.BLACK);

        imgbtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //like button
        imgbtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Glide.with(mContext)
                .load(mGarment.getImage())
                .apply(new RequestOptions().override(500, 700))
                .into(imgGarment);

        tvSpring.setBackgroundColor(Color.parseColor("#efefef"));
        tvSummer.setBackgroundColor(Color.parseColor("#efefef"));
        tvFall.setBackgroundColor(Color.parseColor("#efefef"));
        tvFall.setTextColor(Color.parseColor("#606060"));
        tvWinter.setBackgroundColor(Color.parseColor("#efefef"));
        tvWinter.setTextColor(Color.parseColor("#606060"));

        for (String weather : mGarment.getWeathers()) {
            if (weather.equals("warm")) {
                tvSpring.setBackgroundColor(Color.parseColor("#df7f84"));
            } else if (weather.equals("hot")) {
                tvSummer.setBackgroundColor(Color.parseColor("#fce596"));
            } else if (weather.equals("cool")) {
                tvFall.setBackgroundColor(Color.parseColor("#a8c1ed"));
                tvFall.setTextColor(Color.parseColor("#ffffff"));
            } else if (weather.equals("cold")) {
                tvWinter.setBackgroundColor(Color.parseColor("#606060"));
                tvWinter.setTextColor(Color.parseColor("#ffffff"));
            } else {
                assert false;
            }
        }

        tvCategory.setText(mGarment.getPart() + " > " + mGarment.getCategory());

        tvBrand.setText("STYLENANDA"); // Hard-coded brand. After fixing crawling part, it'll be assigned from GarmentVO

        for (String tag : mGarment.getTags()) {
            // TODO(sjkim): use padding or margin
            String tag_ = "#" + ConfigManager.getInstance().translateTag(tag) + " ";
            TextView tvTag = new TextView(mContext);
            tvTag.setText(tag_);

            layoutTags.addView(tvTag);
        }

        for (String color : mGarment.getColors()) {
            String color_ = ConfigManager.getInstance().translateColor(color);
            ImageView ivColor = new ImageView(mContext);
            ivColor.setLayoutParams(new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ivColor.getLayoutParams().height = 70;
            ivColor.getLayoutParams().width = 70;

            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(ivColor.getLayoutParams());
            marginParams.setMargins(3, 12, 0, 10);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
            ivColor.setLayoutParams(layoutParams);
            ivColor.setBackgroundColor(Color.parseColor(color_));

            if (color_.equals("#ffffff")) {
                ivColor.setBackgroundResource(R.drawable.white_color_stroke);
            }
            layoutColors.addView(ivColor);
        }

        // TODO(sjkim): handle shopping
        // TODO(sjkim): how to get detail about shopping?
        Glide.with(mContext)
                .load(mGarment.getImage())
                .apply(new RequestOptions().override(200, 400))
                .into(ivShopping);

        Button mBtnAddCalendar = findViewById(R.id.btn_add_calendar);
        mBtnAddCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonApiCall.postUserCalendarWithGarment(mContext, TAG, mGarment.getId());
            }
        });

    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }
}
