package com.intellisyscorp.fitzme_android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.activtiy.NoticeActivity;
import com.intellisyscorp.fitzme_android.activtiy.SecurityActivity;
import com.intellisyscorp.fitzme_android.activtiy.VersionActivity;

public class MypageFragment extends Fragment {
    Context mContext;
    private SwipeRefreshLayout mSwipeContainer;
    private static final String TAG = "MypageFragment";
    //    Toolbar toolbar;
    LinearLayout linearNotice, linearSecurity, linearVersion, linearLogout;


    public static MypageFragment newInstance() {
        return new MypageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);
        setHasOptionsMenu(true);
//        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        linearNotice = v.findViewById(R.id.linear_notice);
        linearSecurity = v.findViewById(R.id.linear_security);
        linearVersion = v.findViewById(R.id.linear_version);
        linearLogout = v.findViewById(R.id.linear_logout);

//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setElevation(0);


        //공지사항
        linearNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNoticeActivity();


            }
        });

        //개인/보안
        linearSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSecurityActivity();

            }
        });

        //버전정보
        linearVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callVersionActivity();

            }
        });
        //로그아웃
        linearLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;
    }


    private void callNoticeActivity() {
        Intent intent = new Intent(mContext, NoticeActivity.class);
        Bundle extras = new Bundle();
        intent.putExtras(extras);
        mContext.startActivity(intent);

    }

    private void callSecurityActivity() {
        Intent intent = new Intent(mContext, SecurityActivity.class);
        Bundle extras = new Bundle();
        intent.putExtras(extras);
        mContext.startActivity(intent);

    }

    private void callVersionActivity() {
        Intent intent = new Intent(mContext, VersionActivity.class);
        Bundle extras = new Bundle();
        intent.putExtras(extras);
        mContext.startActivity(intent);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onResume() {
        super.onResume();
        initUI();
    }

    public void initUI() {

    }
}
