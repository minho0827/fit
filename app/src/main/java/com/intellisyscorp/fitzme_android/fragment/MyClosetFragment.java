package com.intellisyscorp.fitzme_android.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.activtiy.QuickAddItemActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// reference (tablayout & view pager): https://www.javatpoint.com/android-tablayout

public class MyClosetFragment extends Fragment implements TabLayout.BaseOnTabSelectedListener {
    private static final String TAG = "MyClosetFragment";

    // View
    @BindView(R.id.part_view_pager)
    ViewPager mPartViewPager;

    @BindView(R.id.part_tab)
    TabLayout mPartTab;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    ////////////////////////////////////////
    // Fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_closet, container, false);
        ButterKnife.bind(this, v);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Tablayout & view pager
        PartViewPagerAdapter viewPagerAdapter = new PartViewPagerAdapter(getChildFragmentManager());
        mPartViewPager.setAdapter(viewPagerAdapter);
        mPartTab.setupWithViewPager(mPartViewPager);
        mPartTab.addOnTabSelectedListener(this);
        mPartTab.setSelectedTabIndicatorColor(Color.parseColor("#c81720"));
        wrapTabIndicatorToTitle(mPartTab, 30, 30);

        // Floating action button
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuickAddItemActivity.class));
            }
        });


    }

    // End Fragment
    ////////////////////////////////////////

    ////////////////////////////////////////
    // TabLayout.BaseOnTabSelectedListener
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mPartViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    // End TabLayout.BaseOnTabSelectedListener
    ////////////////////////////////////////

    class PartViewPagerAdapter extends FragmentPagerAdapter {
        private List<String> mPartNames;
        private List<String> mPartKorNames;


        public PartViewPagerAdapter(FragmentManager fm) {
            super(fm);

            mPartNames = new ArrayList<>();
            mPartNames.add("top");
            mPartNames.add("bottom");
            mPartNames.add("outer");
            mPartNames.add("dress");
            mPartNames.add("shoes");

            mPartKorNames = new ArrayList<>();
            mPartKorNames.add("상의");
            mPartKorNames.add("하의");
            mPartKorNames.add("아우터");
            mPartKorNames.add("드레스");
            mPartKorNames.add("구두");

            assert mPartNames.size() == mPartKorNames.size();
        }

        @Override
        public Fragment getItem(int i) {
            assert i < mPartNames.size();

            return UserGarmentFragment.newInstance(mPartNames.get(i));
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            assert position < mPartNames.size();

            return mPartKorNames.get(position);
        }

        @Override
        public int getCount() {
            return mPartNames.size();
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
}
