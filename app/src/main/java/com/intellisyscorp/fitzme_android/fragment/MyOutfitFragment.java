package com.intellisyscorp.fitzme_android.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.intellisyscorp.fitzme_android.R;
import com.tooltip.Tooltip;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Thread.sleep;

public class MyOutfitFragment extends Fragment implements TabLayout.BaseOnTabSelectedListener {
    private static final String TAG = "MyOutfitFragment";
    private final MyOutfitFragment mFragment = this;
    private OutfitViewPagerAdapter viewPagerAdapter;
    private String weather, garments, parts, seed;

    // View
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab)
    TabLayout mTab;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.tv_spring)
    CheckedTextView tvSpring;

    @BindView(R.id.tv_summer)
    CheckedTextView tvSummer;

    @BindView(R.id.tv_fall)
    CheckedTextView tvFall;

    @BindView(R.id.tv_winter)
    CheckedTextView tvWinter;

    @BindView(R.id.linear_empty_codi)
    LinearLayout linearEmptyCodi;

    Context mContext;


    ////////////////////////////////////////
    // Fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_outfit, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Tablayout & view pager
        viewPagerAdapter = new OutfitViewPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
        mTab.setupWithViewPager(mViewPager);
        mTab.addOnTabSelectedListener(this);
        mTab.setSelectedTabIndicatorColor(Color.parseColor("#c81720"));
        wrapTabIndicatorToTitle(mTab, 10, 10);

        // Floating action button
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip.Builder(view).setTextColor(Color.WHITE).setMargin(-340.0f).
                        setDismissOnClick(true).setArrowEnabled(false)
                        .setText("전체 코디 순서를 새로고침 합니다.")
                        .show();

                seed = UUID.randomUUID().toString();
                refresh();
            }
        });

        tvSpring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSummer.setChecked(false);
                tvSummer.setBackgroundColor(Color.parseColor("#efefef"));

                tvFall.setChecked(false);
                tvFall.setBackgroundColor(Color.parseColor("#efefef"));
                tvFall.setTextColor(Color.parseColor("#606060"));

                tvWinter.setChecked(false);
                tvWinter.setBackgroundColor(Color.parseColor("#efefef"));
                tvWinter.setTextColor(Color.parseColor("#606060"));

                tvSpring.setBackgroundColor(Color.parseColor("#df7f84"));
                weather = "warm";
                refresh();
            }
        });


        tvSummer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSpring.setChecked(false);
                tvSpring.setBackgroundColor(Color.parseColor("#efefef"));

                tvFall.setChecked(false);
                tvFall.setBackgroundColor(Color.parseColor("#efefef"));
                tvFall.setTextColor(Color.parseColor("#606060"));

                tvWinter.setChecked(false);
                tvWinter.setBackgroundColor(Color.parseColor("#efefef"));
                tvWinter.setTextColor(Color.parseColor("#606060"));

                tvSummer.setBackgroundColor(Color.parseColor("#fce596"));
                weather = "hot";
                refresh();
            }
        });


        tvFall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSpring.setChecked(false);
                tvSpring.setBackgroundColor(Color.parseColor("#efefef"));

                tvSummer.setChecked(false);
                tvSummer.setBackgroundColor(Color.parseColor("#efefef"));
                tvSummer.setTextColor(Color.parseColor("#606060"));

                tvWinter.setChecked(false);
                tvWinter.setBackgroundColor(Color.parseColor("#efefef"));
                tvWinter.setTextColor(Color.parseColor("#606060"));

                tvFall.setBackgroundColor(Color.parseColor("#a8c1ed"));
                tvFall.setTextColor(Color.parseColor("#ffffff"));
                weather = "cool";
                refresh();
            }
        });

        tvWinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSpring.setChecked(false);
                tvSpring.setBackgroundColor(Color.parseColor("#efefef"));

                tvSummer.setChecked(false);
                tvSummer.setBackgroundColor(Color.parseColor("#efefef"));
                tvSummer.setTextColor(Color.parseColor("#606060"));

                tvFall.setChecked(false);
                tvFall.setBackgroundColor(Color.parseColor("#efefef"));
                tvFall.setTextColor(Color.parseColor("#606060"));

                tvWinter.setBackgroundColor(Color.parseColor("#606060"));
                tvWinter.setTextColor(Color.parseColor("#ffffff"));
                weather = "cold";
                refresh();
            }
        });
    }

    public void setFixedGarments(String garments, String parts) {
        Log.d("TEST", "send data in outfit fragment");
        this.garments = garments;
        this.parts = parts;
    }


    // End Fragment
    ////////////////////////////////////////

    ////////////////////////////////////////
    // TabLayout.BaseOnTabSelectedListener
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    // End TabLayout.BaseOnTabSelectedListener
    ////////////////////////////////////////

    public void refresh() {
        mViewPager.invalidate();
        viewPagerAdapter.notifyDataSetChanged();
    }

    class OutfitViewPagerAdapter extends FragmentStatePagerAdapter {
        public OutfitViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            // FIXME(sjkim): handle user_like_outfit
            if (i == 0) {
                return new OutfitFragment(mFragment);
            } else if (i == 1) {
                return new UserOutfitFragment(mFragment);
            } else if (i == 2) {
                return new UserOutfitFragment(mFragment);
            } else {
                return null;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "랜덤 코디";
            } else if (position == 1) {
                return "유저 코디";
            } else if (position == 2) {
                return "AI추천 코디";
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }


    public void wrapTabIndicatorToTitle(TabLayout mPartTab, int externalMargin, int internalMargin) {
        View tabStrip = mPartTab.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        settingMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        settingMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        settingMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }

            mPartTab.requestLayout();
        }
    }

    private void settingMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }

    public String getWeather() {
        return weather;
    }

    public String getGarments() {
        return garments;
    }

    public String getParts() {
        return parts;
    }

    public String getSeed() {
        return seed;
    }
}