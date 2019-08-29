package com.intellisyscorp.fitzme_android.activtiy;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.models.JWTSerailizerVO;
import com.intellisyscorp.fitzme_android.models.UserDetailVO;
import com.intellisyscorp.fitzme_android.network.UserRestService;
import com.intellisyscorp.fitzme_android.utils.Constant;
import com.intellisyscorp.fitzme_android.utils.FitzmeProgressBar;
import com.intellisyscorp.fitzme_android.utils.JWTUtils;
import com.intellisyscorp.fitzme_android.utils.PrefsUtils;
import com.intellisyscorp.fitzme_android.utils.RetroUtil;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {


    private UserDetailVO mUserData;
    private static final String TAG = "LoginActivity";
    //naver
    public static OAuthLogin mOAuthLoginModule;
    //google
    private static final int SIGN_IN = 100;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    //facebook
    CallbackManager mCallbackManager;
    private LoginStatusCallback mLoginCallback;
    //kakao
    private static final int REQUEST_SIGIN = 1000;


    private static FitzmeProgressBar mProgressBar = new FitzmeProgressBar();
    private String mJwtToken = "";
    private boolean mValid = false;
    private String mGender = "";
    private String mAgeGroup = "";
    //fake button
    @BindView(R.id.btn_fake_facebook)
    Button btnFakeFacebook;
    @BindView(R.id.btn_fake_naver)
    Button btnFakeNaver;
    @BindView(R.id.btn_fake_kakao)
    Button btnFakeKakao;
    @BindView(R.id.btn_fake_google)
    Button btnFakeGoogle;

    @BindView(R.id.tv_signup)
    AppCompatTextView tvSignup;

    // real button
    @BindView(R.id.btn_oauth_facebook)
    LoginButton btnOauthFacebook;

    @BindView(R.id.btn_oauth_naver)
    OAuthLoginButton btnOAuthNaver;
    @BindView(R.id.btn_oauth_google)
    SignInButton btnOauthGoogle;
    @BindView(R.id.btn_oauth_Kakao)
    com.kakao.usermgmt.LoginButton btnOauthKakao;

    Context mContext;
    private SessionCallback callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isValid = PrefsUtils.getBoolean(this, Constant.USER_VALID, false);

        if (isValid) {
            // 이전에 회원정보를 저장한 이력이있다면 메인화면으로 이동
            String userGender = PrefsUtils.getString(this, Constant.USER_GENDER);
            String userAgerGroup = PrefsUtils.getString(this, Constant.USER_AGE_GROUP);
//            gotoLogin(userId, userPwd);
        } else {
            // 이전에 회원정보를 저장한 이력이 없다면 회원정보를 받는 페이지로 이동

            String jwtToken = PrefsUtils.getString(this, Constant.USER_JWT_TOKEN);
//            callSignUpStepOneActivity();
        }

        setContentView(R.layout.activity_login);
        mContext = this;
        ButterKnife.bind(this);
        uiInit();


        //naver
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                LoginActivity.this
                , getString(R.string.naver_app_id)
                , getString(R.string.naver_app_secret)
                , getString(R.string.app_name)
                //,OAUTH_CALLBACK_INTENT
                // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        );


        /*TODO */
        // preference check
        // jwt 이전에 저장한값이 없으면
        //      sns 로그인 화면 로직 수행
        //          - 성공 B 수행
        //          - 실패시
        // 있다면
        //      Expires check 해서 refresh toekn
        // refresh toekn 실패시
        //      sns로그인 화면
        // B 성공시
        //      valid check후에 있으면 메인 없으면 다음 수집 화면으로 넘김

    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }

    private void setNaver() {
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(this, "clientid", "clientSecret", "clientName");
        btnOAuthNaver.setOAuthLoginHandler(mOAuthLoginHandler);
    }


    /* 성별,나이 받는 액티비티로 이동 */
    private void callSignUpStepOneActivity() {
        Intent intent = new Intent(mContext, SignUpStepOneActivity.class);
        intent.putExtra("ageGroup", mAgeGroup);
        intent.putExtra("gender", mGender);

        Log.d(TAG, "===== ageGroup ===== :" + mAgeGroup);
        Log.d(TAG, "===== gender =====:" + mGender);
        startActivity(intent);
        finish();

    }

    /* MainActivity로 이동 */
//    private void callMainActivity() {
//        Intent intent = new Intent(mContext, SignUpStepOneActivity.class);
//        startActivity(intent);
//    }

    /* 이메일 회원가입 페이지로 이동 */
    private void callEmailSignUpActivity() {
        Intent intent = new Intent(mContext, SignUpActivity.class);
        startActivity(intent);
    }


    public void uiInit() {
        mCallbackManager = CallbackManager.Factory.create();
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
//        Session.getCurrentSession().checkAndImplicitOpen();
        facebookBtnLayoutInit();
        setNaver();
        setGoogle();


        //fake facebookbutton
        btnFakeFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOauthFacebook.performClick();
            }
        });


        //fake naver button
        btnFakeNaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btnOAuthNaver.performClick();
                mOAuthLoginModule.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
            }
        });
        //fake naver button
        btnFakeGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btnOauthGoogle.performClick();
                loginGoogle();


            }
        });

        //fake kakao button
        btnFakeKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOauthKakao.performClick();
            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEmailSignUpActivity();
            }
        });

        btnOauthFacebook.setReadPermissions("email");
        // Other app specific specialization

        /* 페이스북 회원가입 */
        // Callback registration
        btnOauthFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                processLoginByFacebookId(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        Log.d(TAG, "Key Hash: " + getKeyHash(this));


    }

    private void loginGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, SIGN_IN);

    }

    private void setGoogle() {

        FirebaseAuth.getInstance().signOut();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("web client id")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            }

                        } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void facebookBtnLayoutInit() {
        FancyButton facebookLoginBtn = new FancyButton(this);
        facebookLoginBtn.setText("");
        facebookLoginBtn.setBackgroundColor(Color.parseColor("#3b5998"));
        facebookLoginBtn.setFocusBackgroundColor(Color.parseColor("#5474b8"));
        facebookLoginBtn.setTextSize(17);
        facebookLoginBtn.setRadius(5);
        facebookLoginBtn.setIconResource("\uf082");
        facebookLoginBtn.setIconPosition(FancyButton.POSITION_LEFT);
        facebookLoginBtn.setFontIconSize(30);
    }

    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w(TAG, "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }


    public void getJwt(String accessToken, String provider) {
        btnOauthFacebook.setEnabled(false);
        btnOAuthNaver.setEnabled(false);
        btnOauthKakao.setEnabled(false);
        btnOauthGoogle.setEnabled(false);

        requestGetJwt(provider, accessToken);


    }


    // Oauth 리다이렉트 후 자체 jwt 토큰 반환
    private void requestGetJwt(String provider, String accessToken) {

//        showProgress();

//        JWT jwt = new JWT("header.payload.signature");


        HashMap<String, Object> params = new HashMap<>();
        params.put("access_token", accessToken);
        UserRestService service = RetroUtil.getService(UserRestService.class);
        final Call<JWTSerailizerVO> configCall = service.getRequestSocial(provider, params);

        configCall.enqueue(new Callback<JWTSerailizerVO>() {
            @Override
            public void onResponse(Call<JWTSerailizerVO> call, Response<JWTSerailizerVO> response) {
                if (response.isSuccessful()) {
                    JWTSerailizerVO jwtSerailizerVO = response.body();

                    if (jwtSerailizerVO != null) {
                        Log.d(TAG, "==== jwt token  ==== :" + jwtSerailizerVO.getToken());

                        try {
                            String jwtDecode = JWTUtils.decoded(jwtSerailizerVO.getToken());
                            Log.d(TAG, "==== jwtDecode  ==== :" + jwtDecode);

                            String decodingJwt = JWTUtils.decoded(jwtSerailizerVO.getToken());
                            JsonParser parser = new JsonParser();
                            JsonElement element = parser.parse(decodingJwt);
                            mValid = element.getAsJsonObject().get("valid").getAsBoolean();
                            mGender = element.getAsJsonObject().get("gender").getAsString();
                            mAgeGroup = element.getAsJsonObject().get("agegroup").getAsString();

                            Log.d(TAG, "requestGetJwt agegroup :" + mAgeGroup);
                            Log.d(TAG, "requestGetJwt valid :" + mValid);
                            Log.d(TAG, "requestGetJwt gedner :" + mGender);

                            PrefsUtils.setString(LoginActivity.this, Constant.USER_AGE_GROUP, mAgeGroup);
                            PrefsUtils.setString(LoginActivity.this, Constant.USER_GENDER, mGender);
                            PrefsUtils.setString(LoginActivity.this, Constant.USER_JWT_TOKEN, mJwtToken);
                            PrefsUtils.setBoolean(LoginActivity.this, Constant.USER_VALID, mValid);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callSignUpStepOneActivity();
//                        com.intellisyscorp.fitzme_android.utils.Utils.hideProgress(mProgressBar);
                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<JWTSerailizerVO> call, Throwable t) {
                Log.d(TAG, "onFailure: 데이터 가져오는데 실패..");
                t.printStackTrace();
//                com.intellisyscorp.fitzme_android.utils.Utils.hideProgress(mProgressBar);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult requestCode: " + requestCode + ", resultCode: " + resultCode);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        if (requestCode == REQUEST_SIGIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
//            finish();
        }


    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d(TAG, "==== email ==== :" + acct.getEmail());
            Log.d(TAG, "==== id ==== :" + acct.getId());
            Log.d(TAG, "==== profile ==== :" + acct.getPhotoUrl());
            Log.d(TAG, "==== DispName ==== : > " + acct.getDisplayName());
            getJwt(getString(R.string.google_app_id), getString(R.string.provider_google));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    //
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.d(TAG, "onSessionOpened");

            Session.getCurrentSession().getAccessToken();
            Log.d(TAG, "kakao accessToken : " + Session.getCurrentSession().getAccessToken());
            Session.getCurrentSession().getRefreshToken();

//            Session.getCurrentSession().getAuthCodeManager().requestAuthCode
//                    (AuthType.KAKAO_LOGIN_ALL, LoginActivity.this, new AuthCodeCallback() {
//                        @Override
//                        public void onAuthCodeReceived(String authCode) {
//                            Log.d(TAG, "## onAuthCodeReceived: " + authCode);
////                    getJwt(authCode, getString(R.string.provider_kakao));
//
//                        }
//
//                        @Override
//                        public void onAuthCodeFailure(ErrorResult errorResult) {
//                            Log.d(TAG, "## onAuthCodeFailure: " + errorResult);
//                        }
//                    });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }


    // naver 로그인 성공시
    /**
     * OAuthLoginHandler를 startOAuthLoginActivity() 메서드 호출 시 파라미터로 전달하거나 OAuthLoginButton
     * 객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다.
     */
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);

                Log.d(TAG, "====== accessToken ====== :" + accessToken);
                Log.d(TAG, "====== refreshToken ====== :" + refreshToken);
                Log.d(TAG, "====== expiresAt ====== :" + expiresAt);

                getJwt(accessToken, getString(R.string.provider_naver));

            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }

        }


    };


    // 페이스북 로그인 성공시
    private void processLoginByFacebookId(AccessToken accessToken) {
        // App code
        GraphRequest request = GraphRequest.newMeRequest(accessToken,

                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try { // Application code
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String email = object.getString("email");
                            String gender = object.getString("user_gender");
                            String age = object.getString("user_age_range");

                            Log.d(TAG, "newMeRequest");
                            Log.d(TAG, "==== id ==== : " + id);
                            Log.d(TAG, "==== name ==== : " + name);
                            Log.d(TAG, "==== email ==== : " + email);
                            Log.d(TAG, "==== gender ==== : " + gender);
                            Log.d(TAG, "==== age ==== : " + age);

                            mUserData.setGender(gender);
                            userDataManager.setUserData(mUserData);

                            getJwt(getString(R.string.facebook_app_id), getString(R.string.provider_facebook));

                        } catch (JSONException E) {
                            E.printStackTrace();
                        }
                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }


    private void requestAccessTokenInfo() {
        AuthService.getInstance().requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
//                redirectLoginActivity(self);
            }

            @Override
            public void onNotSignedUp() {
                // not happened
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e("failed to get access token info. msg=" + errorResult);
            }

            @Override
            public void onSuccess(AccessTokenInfoResponse accessTokenInfoResponse) {
                long userId = accessTokenInfoResponse.getUserId();
                Logger.d("this access token is for userId=" + userId);

                long expiresInMilis = accessTokenInfoResponse.getExpiresInMillis();
                Logger.d("this access token expires after " + expiresInMilis + " milliseconds.");
            }
        });
    }


    //카카오 로그인 세션 종료 되었을때
    private void onLoginFailed(@Nullable String errorResult) {
        if (StringUtils.isNotEmpty(errorResult)) {
            final String message = "로그인에 실패하였습니다. msg=" + errorResult;
            Log.e(TAG, "onLoginFailed: " + message);
        }
//        com.intellisyscorp.fitzme_android.utils.Utils.hideProgress(mProgressBar);
        Toast.makeText(getBaseContext(), "로그인에 실패 하였습니다", Toast.LENGTH_SHORT).show();
    }

    private void showProgress() {
        if (mProgressBar != null && !isFinishing()) {
            Log.d(TAG, "showProgress()");
            mProgressBar.show(mContext);
        }
    }


}



