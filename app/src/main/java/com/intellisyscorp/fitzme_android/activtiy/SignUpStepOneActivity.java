package com.intellisyscorp.fitzme_android.activtiy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.adapter.AgeGroupListAdapter;
import com.intellisyscorp.fitzme_android.adapter.GenderListAdapter;
import com.intellisyscorp.fitzme_android.models.ConfigVO;
import com.intellisyscorp.fitzme_android.network.UserRestService;
import com.intellisyscorp.fitzme_android.utils.CommonUtil;
import com.intellisyscorp.fitzme_android.utils.Constant;
import com.intellisyscorp.fitzme_android.utils.FitzmeProgressBar;
import com.intellisyscorp.fitzme_android.utils.OnSingleClickListener;
import com.intellisyscorp.fitzme_android.utils.RetroUtil;
import com.intellisyscorp.fitzme_android.utils.ToastUtil;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpStepOneActivity extends BaseActivity {
    private static final String TAG = "SignUpStepOneActivity";
    private InputMethodManager inputMethodManager;                                                   // 키보드 내리기
    private List<ConfigVO.PublicData.GenderData> mGenderList = new ArrayList<>();
    private List<ConfigVO.PublicData.AgegroupData> mAgeList = new ArrayList<>();
    private String mGender;
    private String mAgeGroup;
    boolean isGenderSelect = false;                                                                           // Coin 선택 여부
    boolean isAgeSelect = false;                                                                           // Coin 선택 여부


    private enum GenderSignUp {
        FEMALE("여성"),
        MALE("남성");

        GenderSignUp(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        private String value;
    }

    FitzmeProgressBar mProgressBar = new FitzmeProgressBar();

    @Nullable
    @BindView(R.id.tv_username)
    AppCompatTextView userName;

    @Nullable
    @BindView(R.id.tv_popup_title)
    AppCompatTextView tvPopupTitle;

    @Nullable
    @BindView(R.id.tv_cancel)
    AppCompatTextView tvCancel;

    @Nullable
    @BindView(R.id.tv_gender)
    AppCompatTextView tvGender;

    @Nullable
    @BindView(R.id.tv_age)
    AppCompatTextView tvAge;

    @Nullable
    @BindView(R.id.iv_grid)
    ImageView ivGrid;

    @Nullable
    @BindView(R.id.btn_start)
    AppCompatButton btnStart;
    Context mContext;

    FrameLayout frameLayoutAge;
    FrameLayout frameLayoutGender;

    RecyclerView mRecyclerView;
    private String mStrGender;
    private String mStrAge;

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mContext = this;
        setContentView(R.layout.activity_signup_step_one);
        getIntentInit();
        ButterKnife.bind(this);
        uiInit();


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(btnStart.getWindowToken(), 0);
                if (CommonUtil.isNetworkConnected(mContext)) {

                    validate();

                    callActivity();
//                    finish();

                }

            }
        });

        frameLayoutAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ageGroupListPopup(getString(R.string.msg_age_select));

            }
        });

        frameLayoutGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 키보드 내리기
//                inputMethodManager.hideSoftInputFromWindow(frameLayoutYear.getWindowToken(), 0);
                genderListPopup(getString(R.string.msg_gender_select));

            }
        });

//        requestMe();
    }

    private void getIntentInit() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mGender = bundle.getString("gender");
        mAgeGroup = bundle.getString("ageGroup");
        Log.d(TAG, "gender: " + mGender);
        Log.d(TAG, "ageGroup: " + mAgeGroup);


    }

    private void callActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        Bundle extras = new Bundle();
        intent.putExtras(extras);
        mContext.startActivity(intent);

    }


    //나이 팝업
    private void ageGroupListPopup(String strTtile) {
        if (CollectionUtils.isNotEmpty(mAgeList)) {
            // age 팝업 Layout 설정
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_age_popup_list, null);
            final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            // 팝업 타이틀
            AppCompatTextView tvPopupTitle = view.findViewById(R.id.tv_popup_title);
            tvPopupTitle.setText(strTtile);

            AppCompatTextView tvCancel = view.findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    popupWindow.dismiss();
                }
            });

            mRecyclerView = view.findViewById(R.id.recyclerview);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setHasFixedSize(true);


            AgeGroupListAdapter ageGroupListAdapter = new AgeGroupListAdapter(mContext, mAgeList, ageData -> {
                if (ageData != null) {
                    isAgeSelect = true;
                    tvAge.setText(ageData.getName_ko());
                    mStrAge = ageData.getName_ko();


                }
                popupWindow.dismiss();
            });
            mRecyclerView.setAdapter(ageGroupListAdapter);

            //팝업 화면 위치 설정
            popupWindow.setFocusable(true);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        }
    }


    // gender 팝업
    private void genderListPopup(String strTtile) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_gender_popup_list, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        // 팝업 타이틀
        AppCompatTextView tvPopupTitle = view.findViewById(R.id.tv_popup_title);
        tvPopupTitle.setText(strTtile);

        AppCompatTextView tvCancel = view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                popupWindow.dismiss();
            }
        });

        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);


        GenderListAdapter genderListAdapter = new GenderListAdapter(mContext, mGenderList, genderData -> {
            if (genderData != null) {
                isGenderSelect = true;

                tvGender.setText(genderData.getName_ko());
                mStrGender = genderData.getName_ko();


            }
            popupWindow.dismiss();
        });
        mRecyclerView.setAdapter(genderListAdapter);

        //팝업 화면 위치 설정
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    protected void uiInit() {
        frameLayoutAge = findViewById(R.id.frame_layout_age);
        frameLayoutGender = findViewById(R.id.frame_layout_gender);
        // 키보드 내리기
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        genderStatus(mGender);
        ageGroupStatus(mAgeGroup);
        getConfigData();

    }

    private void getConfigData() {
        if (getConfigVO() != null) {
            mAgeList = getConfigVO().getPublic_().getAgegroup();
            mGenderList = getConfigVO().getPublic_().getGender();
        }
    }

    private void ageGroupStatus(String mAgeGroup) {

        switch (mAgeGroup) {
            case Constant.USER_AGE_GROUP_1019:
                tvAge.setText("10대");
                break;

            case Constant.USER_AGE_GROUP_2029:
                tvAge.setText("20대");
                break;

            case Constant.USER_AGE_GROUP_3039:
                tvAge.setText("30대");
                break;

            case Constant.USER_AGE_GROUP_4049:
                tvAge.setText("20대");
                break;

            case Constant.USER_AGE_GROUP_50:
                tvAge.setText("50대이상");
                break;

            default:
                tvAge.setText("나이");


        }
    }

    private void genderStatus(String mGender) {

        switch (mGender) {
            case Constant.FEMALE:
                tvGender.setText("여성");
                break;

            case Constant.MALE:
                tvGender.setText("남성");
                break;

        }
    }

    private void showProgress() {
        if (mProgressBar != null && !isFinishing()) {
            Log.d(TAG, "showProgress()!!!");
            mProgressBar.show(mContext);
        }
    }


//    /* kakao 카카오 로그인 */
//    private void requestMe() {
//        List<String> keys = new ArrayList<>();
//        keys.add("properties.nickname");
//        keys.add("properties.profile_image");
//        keys.add("kakao_account.age_range");
//        keys.add("kakao_account.has_age_range");  //계정 연령대 소유 여부
//        keys.add("kakao_account.gender");
//        keys.add("kakao_account.has_gender");  // 계정 성별 소유 여부
//
//
//        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
//            @Override
//            public void onFailure(ErrorResult errorResult) {
//                String message = "failed to get user info. msg=" + errorResult;
//                Logger.d(message);
//            }
//
//            @Override
//            public void onSessionClosed(ErrorResult errorResult) {
//            }
//
//            /* kakao 카카오 로그인 성공시 */
//
//            @Override
//            public void onSuccess(MeV2Response response) {
//                Logger.d("user id : " + response.getId());
//                Logger.d("email: " + response.getKakaoAccount().getEmail());
//                Logger.d("gender: " + response.getKakaoAccount().getGender());
//                Logger.d("age: " + response.getKakaoAccount().getAgeRange());
//
//                if (Gender.FEMALE == response.getKakaoAccount().getGender()) {
//                    mGender = GenderSignUp.FEMALE.getValue();
//                } else {
//                    mGender = GenderSignUp.MALE.getValue();
//                }
//                tvGender.setText(mGender);
//
//
//                ConfigVO configVo = ((BaseApplication) getApplication()).getConfigVO();
////                configVo.getPublicData().getGender()
//            }
//        });
//    }

    // 회원가입
    private void requestPutUserRegister() {

        HashMap params = new HashMap();
//        params.put("gender",);
//        params.put("agegroup",);


        UserRestService service = RetroUtil.getService(UserRestService.class);
        final Call<Integer> configCall = service.putUserRegister(params);
        configCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    int result = response.body();
                    if (result == 200) {

                    } else {

                        Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..");

            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String strGender = tvGender.getText().toString();
        String strAgeGroup = tvAge.getText().toString();

        if (strGender.isEmpty()) {
            ToastUtil.showToastAsShort(mContext, "성별을 입력해주세요.");
            valid = false;
        } else {
            tvGender.setError(null);

        }

        if (strAgeGroup.isEmpty()) {
            ToastUtil.showToastAsShort(mContext, "나이를 입력해주세요.");
            valid = false;
        } else {
            tvAge.setError(null);

        }

        return valid;
    }

}
