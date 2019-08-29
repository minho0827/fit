package com.intellisyscorp.fitzme_android.activtiy;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.listener.ButtonSelectorListener;
import com.intellisyscorp.fitzme_android.models.UserDetailVO;
import com.intellisyscorp.fitzme_android.network.CommonApiCall;
import com.intellisyscorp.fitzme_android.utils.AlertDialogUtil;
import com.intellisyscorp.fitzme_android.utils.CommonUtil;
import com.intellisyscorp.fitzme_android.utils.Constant;
import com.intellisyscorp.fitzme_android.utils.OnSingleClickListener;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

import butterknife.ButterKnife;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;


    /**
     * UI
     ************************************************************************************************************************************************/
    private AppCompatEditText etEmail, etPassword,                                                       // 이름, 이메일, 패스워드
            etPasswordConfirm;                                         // 패스워드 확인, 핸드폰번호, 핸드폰번호 확인
    private AppCompatButton btnSignup;                                                     // 회원가입, 인증번호 확인
    private AppCompatButton btnEmailCheck;                                                     // 회원가입, 인증번호 확인


    /**
     * Application User Local DataBase
     ************************************************************************************************************************************************/
    private UserDetailVO userData;                                                                                      // 유저 정보
    private final static String TAG = "SignUpActivity";
    /**
     * 키보드 내리기
     ************************************************************************************************************************************************/
    private InputMethodManager inputMethodManager;                                                                  // 키보드 내리기

    /**
     * 패스워드 체크
     ************************************************************************************************************************************************/
    private boolean isPasswordCheck = false;                                                                        // 비밀번호 체크 여부


    /**
     * Static String variable
     ************************************************************************************************************************************************/
    private static final String strSlash = "SLASH";                                                                 // 문자 SLASH

    /**
     * UI
     ************************************************************************************************************************************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_signup);
        uiInit();

    }


    /**
     * View Resource Setting
     ************************************************************************************************************************************************/
    @Override
    protected void uiInit() {
        super.uiInit();

        // UI resource
        setToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("이메일로 회원가입");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setTitleTextColor(Color.WHITE);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //What to do on back clicked
                    finish();
                }
            });
        }

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etPasswordConfirm = findViewById(R.id.et_password_confirm);
        btnSignup = findViewById(R.id.btn_signup);
//        btnEmailCheck = findViewById(R.id.btn_email_check);

        // 뒤로가기 버튼 사용 조건
        isBackButtonNotice = false;

        // Application User Local DataBase
//        userData = userDataManager.getUserData();

        // ButtonSelectorListener :: 스크롤뷰에 대한 선택에 대해서 조건을 걸기 위해 특별하게 findViewById를 상단에 작성합니다
        ScrollView fullScroll = findViewById(R.id.fullScroll);
        ButtonSelectorListener buttonSelector = new ButtonSelectorListener(this, fullScroll);

        // 키보드 내리기
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        // 아이디에 한글이 적용되지 않도록 설정
        etEmail.setFilters(new InputFilter[]{filterAlphaNum});

        //
        // TextWatcher
        etPassword.addTextChangedListener(passwordWatcher);
        etPasswordConfirm.addTextChangedListener(passwordWatcher);


        // setOnClickListener & setOnTouchListener
        btnSignup.setOnTouchListener(buttonSelector);
        btnEmailCheck.setOnTouchListener(buttonSelector);


    }


    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


        }
    }

    /**
     * 아이디에 한글이 포함되지 않도록 설정
     ************************************************************************************************************************************************/
    private InputFilter filterAlphaNum = (source, start, end, dest, dstart, dend) -> {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9@.]+$");
        if (!pattern.matcher(source).matches()) {
            return Constant.COMMON_NULL;
        }
        return null;
    };

    /**
     * 비밀번호 일치 확인
     ************************************************************************************************************************************************/
    private TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isPasswordCheck = false;

            if (StringUtils.isNotEmpty(etPassword.getText().toString()) && StringUtils.isNotEmpty(etPasswordConfirm.getText().toString()))
                if (etPassword.getText().toString().equals(etPasswordConfirm.getText().toString()))
                    isPasswordCheck = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    /**
     * View ClickListener
     ************************************************************************************************************************************************/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                // 키보드 내리기
                inputMethodManager.hideSoftInputFromWindow(btnSignup.getWindowToken(), 0);

                if (CommonUtil.isNetworkConnected(mContext)) {

                    // 이메일을 입력하지 않은 경우
                    if (StringUtils.isEmpty(etEmail.getText().toString())) {
                        AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_check_profile_email), new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                AlertDialogUtil.dismissDialog();
                            }
                        });
                        return;
                    }

                    // 이메일 형식이 아닌 경우
                    if (!CommonUtil.isEmail(etEmail.getText().toString())) {
                        AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_confirm_profile_email), new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                AlertDialogUtil.dismissDialog();
                            }
                        });
                        return;
                    }

                    // 이메일에 빈공간이 있는 경우
                    if (CommonUtil.isStringNullSpaceCheck(etEmail.getText().toString())) {
                        AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_input_not_space_email), new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                AlertDialogUtil.dismissDialog();
                            }
                        });
                        return;
                    }

                    // 패스워드를 입력하지 않은 경우
                    if (StringUtils.isEmpty(etPassword.getText().toString())) {
                        AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_check_profile_password), new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                AlertDialogUtil.dismissDialog();
                            }
                        });
                        return;
                    }

                    // 패스워드에 빈공간이 있는 경우
                    if (CommonUtil.isStringNullSpaceCheck(etPassword.getText().toString())) {
                        AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_input_not_space_password), new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                AlertDialogUtil.dismissDialog();
                            }
                        });
                        return;
                    }

                    // 비밀번호가 체크되지 않은 경우
                    if (!isPasswordCheck) {
                        AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_confirm_profile_password), new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                AlertDialogUtil.dismissDialog();
                            }
                        });
                        return;
                    }


                }

            case R.id.layout_back:
                finish();
                break;

//            //이메일 중복체크 버튼
//            case R.id.btn_email_check:
//                if (StringUtils.isEmpty(etEmail.getText().toString())) {
//
//
//                    AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_check_profile_email), new OnSingleClickListener() {
//                        @Override
//                        public void onSingleClick(View v) {
//                            AlertDialogUtil.dismissDialog();
//                        }
//                    });
//                } else {
//
//                    requestGetUserEamilConfirm(etEmail.getText().toString());
//
//                }


//                break;

        }
    }


    //이메일 중복체크
    private void requestGetUserEamilConfirm(String email) {
        CommonApiCall.getUserEamilConfirm(getApplicationContext(), TAG, email);
    }

}
