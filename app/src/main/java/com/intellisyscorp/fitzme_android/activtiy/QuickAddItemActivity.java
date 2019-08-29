package com.intellisyscorp.fitzme_android.activtiy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.fragment.GarmentFragment;
import com.intellisyscorp.fitzme_android.utils.AlertDialogUtil;
import com.intellisyscorp.fitzme_android.utils.BackPressCloseHandler;
import com.intellisyscorp.fitzme_android.utils.OnSingleClickListener;
import com.tooltip.Tooltip;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuickAddItemActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "QuickAddItemActivity";

    // View
    @BindView(R.id.toolbar_title)
    AppCompatTextView tvToolbarTitle;

    @BindView(R.id.imgbtn_camera)
    ImageButton imgbtnCamera;

    @BindView(R.id.imgbtn_finish)
    ImageButton imgbtnFinish;

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    public static final int FILTER = 0;
    //사진으로 전송시 되돌려 받을 번호
    static int REQUEST_PICTURE = 1;
    //앨범으로 전송시 돌려받을 번호
    static int REQUEST_PHOTO_ALBUM = 2;
    Dialog dialog;

    private ArrayList<String> mFilterList = new ArrayList<>();
    private ArrayList<String> mFilterCategory = new ArrayList<>();
    private ArrayList<String> mFilterKey = new ArrayList<>();
    private ArrayList<String> mFilterColor = new ArrayList<>();

    private Context mContext;
    BackPressCloseHandler mBackPressCloseHandler;
    private GarmentFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_add);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mContext = this;
        mBackPressCloseHandler = new BackPressCloseHandler(this);
        ButterKnife.bind(this);
        initUI();
        mViewPager.setAdapter(fragmentStatePagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#c81720"));
        wrapTabIndicatorToTitle(tabLayout, 60, 60);


        imgbtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tvToolbarTitle.setText("아이템 추가");
        tvToolbarTitle.setTextColor(Color.BLACK);


        //카메라 버튼
        imgbtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip.Builder(v).setTextColor(Color.WHITE).setMargin(5.0f).
                        setDismissOnClick(true).setArrowEnabled(false)
                        .setText("해당기능은 구현중입니다.")
                        .show();
            }
        });
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 메뉴의 항목을 선택(클릭)했을 때 호출되는 콜백메서드

        int id = item.getItemId();


        switch (id) {
            case R.id.menu_camera:
                AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_not_support_message), new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        AlertDialogUtil.dismissDialog();
                    }
                });
                // FIXME(sjkim)
                // doTakeCameraAction();
                break;


            case R.id.menufilter:
                callFilterActivity();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /*
    private void doTakeCameraAction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper
                (QuickAddItemActivity.this, R.style.myDialog));
        View customLayout = View.inflate(QuickAddItemActivity.this, R.layout.camera_custom_button, null);
        builder.setView(customLayout);

        customLayout.findViewById(R.id.camera).setOnClickListener(this);
        customLayout.findViewById(R.id.photoAlbum).setOnClickListener(this);

        dialog = builder.create();
        dialog.show();

    }
    */

    private void callFilterActivity() {
        Intent intent = new Intent(mContext, FilterActivity.class);
        intent.putStringArrayListExtra("filter_list", mFilterList);
        intent.putStringArrayListExtra("filter_category", mFilterCategory);
        intent.putStringArrayListExtra("filter_key", mFilterKey);
        intent.putStringArrayListExtra("filter_color", mFilterColor);
        startActivityForResult(intent, FILTER);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == FILTER) {
            if (Activity.RESULT_OK == resultCode) {
                mFilterList = data.getStringArrayListExtra("WORK_KIND_FILTER");
                mFilterCategory = data.getStringArrayListExtra("filter_category");
                mFilterKey = data.getStringArrayListExtra("filter_key");
                mFilterColor = data.getStringArrayListExtra("filter_color");

                // Apply filter and re-fetch
                fragmentStatePagerAdapter.notifyDataSetChanged();
                mViewPager.invalidate();
                //mFragment.refresh();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void applyFilter() {
        fragmentStatePagerAdapter.notifyDataSetChanged();
        mViewPager.invalidate();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.quick_add_menu, menu);

        return true;
    }

    private void initUI() {

        mViewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);


    }

    QuickAddItemActivity activity = this;
    FragmentStatePagerAdapter fragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        private ArrayList<String> partKorNames = new ArrayList<String>() {{
            add("아우터");
            add("하의");
            add("상의");
            add("원피스");
            add("구두");
        }};

        private ArrayList<String> partNames = new ArrayList<String>() {{
            add("outer");
            add("bottom");
            add("top");
            add("dress");
            add("shoes");
        }};

        @Override
        public int getCount() {
            return partKorNames.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return partKorNames.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            mFragment = GarmentFragment.newInstance(activity, partNames.get(position));
            return mFragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
            // return super.getItemPosition(object);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        /* FIXME(sjkim): 카메라는 추후 구현
        switch (v.getId()){
            case R.id.camera:
                //카메라버튼인 경우 일단 다이어로그를 끄고 사진을 찍는 함수를 불러오자
                dialog.dismiss();
                Toast.makeText(mContext, "camera", Toast.LENGTH_SHORT).show();
                doTakePhotoAction();
                break;
            case R.id.photoAlbum:
                //이경우역시 다이어로그를 끄고 앨범을 불러오는 함수를 불러오자!!
                dialog.dismiss();
                doTakeAlbumAction();
                Toast.makeText(mContext, "photoAlbum", Toast.LENGTH_SHORT).show();

                break;

        }
        */
    }


    public void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //임시로 사용 파일의 경로를 생성
        String url = "tmp_" + System.currentTimeMillis() + ".jpg";
//        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

//        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, REQUEST_PICTURE);

        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_PICTURE);
    }


    /**
     * 앨범에서 이미지 가져오기
     */
    public void doTakeAlbumAction() {
//        앨범호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_PHOTO_ALBUM);

        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_PHOTO_ALBUM);
    }
}
