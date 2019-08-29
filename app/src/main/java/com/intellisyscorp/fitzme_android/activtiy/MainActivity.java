package com.intellisyscorp.fitzme_android.activtiy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.fragment.CalendarFragment;
import com.intellisyscorp.fitzme_android.fragment.MyClosetFragment;
import com.intellisyscorp.fitzme_android.fragment.MyOutfitFragment;
import com.intellisyscorp.fitzme_android.fragment.MypageFragment;
import com.intellisyscorp.fitzme_android.network.CommonApiCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// Reference (BottomNavigationView: https://dev-imaec.tistory.com/11, https://dev-imaec.tistory.com/12)

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    // View
    @BindView(R.id.toolbar)
    Toolbar tbToolbar;

    @BindView(R.id.sky)
    ImageView ivSky;

    @BindView(R.id.temperature)
    AppCompatTextView tvTemperature;

    @BindView(R.id.view)
    View view;

    @BindView(R.id.toolbar_title)
    AppCompatTextView tvToolbarTitle;

    @BindView(R.id.area)
    AppCompatTextView tvArea;

    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;

    @BindView(R.id.frame_weather_status)
    FrameLayout frameWeatherStatus;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNav;

    // Member variables
    private List<Fragment> mFragments;
    private int mCurrentItemID;

    ////////////////////////////////////////
    // AppCompatActivity
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Bottom navigation
        mFragments = new ArrayList<>();
        mFragments.add(new MyClosetFragment());
        mFragments.add(null);
        mFragments.add(new CalendarFragment());
        mFragments.add(new MypageFragment());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, new MyClosetFragment()).commitAllowingStateLoss();

        mBottomNav.setOnNavigationItemSelectedListener(this);

        // Toolbar and header
        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
        tvToolbarTitle.setText("MY CLOSET");
        tvToolbarTitle.setTextColor(Color.BLACK);

        // Get current weather and update view
        getWeatherAndUpdate();
    }
    // End AppCompatActivity
    ////////////////////////////////////////

    ////////////////////////////////////////
    // BottomNavigationView.OnNavigationItemSelectedListener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Already selected item
        if (menuItem.getItemId() == mCurrentItemID) return false;

        // Change fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (menuItem.getItemId()) {
            case R.id.bottom_nav_item_0:
                mCurrentItemID = menuItem.getItemId();
                transaction.replace(R.id.frame_layout, mFragments.get(0)).commitAllowingStateLoss();
                // FIXME(sjkim)
                view.setVisibility(View.VISIBLE);
                frameWeatherStatus.setVisibility(View.VISIBLE);
                tbToolbar.setVisibility(View.VISIBLE);
                tvToolbarTitle.setText("MY CLOSET");
                return true;

            case R.id.bottom_nav_item_1:
                mCurrentItemID = menuItem.getItemId();
                transaction.replace(R.id.frame_layout, new MyOutfitFragment()).commitAllowingStateLoss();
                view.setVisibility(View.VISIBLE);
                tbToolbar.setVisibility(View.VISIBLE);
                tvToolbarTitle.setText("MY OUTFITS");
                return true;

            case R.id.bottom_nav_item_2:
                mCurrentItemID = menuItem.getItemId();
                frameWeatherStatus.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                tbToolbar.setVisibility(View.GONE);

                transaction.replace(R.id.frame_layout, mFragments.get(2)).commitAllowingStateLoss();
                return true;

            case R.id.bottom_nav_item_3:
                mCurrentItemID = menuItem.getItemId();
                frameWeatherStatus.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                tbToolbar.setVisibility(View.GONE);
                transaction.replace(R.id.frame_layout, mFragments.get(3)).commitAllowingStateLoss();
                return true;
            default:
                return false;
        }
    }
    // BottomNavigationView.OnNavigationItemSelectedListener
    ////////////////////////////////////////

    public void viewOutfitWithFixedGarment(String garments, String parts) {
        MyOutfitFragment myOutfit = new MyOutfitFragment();
        myOutfit.setFixedGarments(garments, parts);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, myOutfit).commitAllowingStateLoss();
    }

    private void getWeatherAndUpdate() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            int MY_PERMISSION_ACCESS_FINE_LOCATION = 0;
            int MY_PERMISSION_ACCESS_COARSE_LOCATION = 0;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
            Log.d(TAG, "위치 정보 permissions 획득");
        }

        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude;
        double latitude;

        // FIXME(sjkim): handling LocationManager problem
        try {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        } catch (NullPointerException e) {
            Log.d(TAG, "Can't get location information", e);

            longitude = 127.024612;
            latitude = 37.532600;
        }

        CommonApiCall.getWeather(getApplicationContext(), TAG, longitude, latitude, tvTemperature, ivSky);
    }
}
