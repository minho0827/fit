package com.intellisyscorp.fitzme_android.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

import com.intellisyscorp.fitzme_android.R;

import org.apache.commons.lang3.StringUtils;


public class AlertDialogUtil {

    /**
     * AlertDialog
     ************************************************************************************************************************************************/
    private static AlertDialog dialog;

    /**
     * 기본 팝업		:: 메세지 1개 & 버튼 1개
     ************************************************************************************************************************************************/
    public static void showSingleDialog(Context context, String message, OnSingleClickListener positiveCallback) {

        dismissDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_alert_single_popup, null);

        AppCompatTextView txtMessage = v.findViewById(R.id.txt_message);
        AppCompatTextView btnConfirm = v.findViewById(R.id.btn_confirm);

        if (StringUtils.isNotEmpty(message)) {
            txtMessage.setText(message);
        }

        // 확인버튼
        if (positiveCallback == null) {
            positiveCallback = new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismissDialog();
                }
            };
        }

        btnConfirm.setOnClickListener(positiveCallback);
        btnConfirm.setVisibility(View.VISIBLE);

        builder.setView(v);

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    /**
     * 기본 팝업		:: 메세지 1개 & 버튼 1개
     ************************************************************************************************************************************************/
    public static void calendarAddDialog(Context context, String message, OnSingleClickListener positiveCallback) {

        dismissDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_alert_add_calendar_popup, null);

        AppCompatTextView txtMessage = v.findViewById(R.id.txt_message);
        AppCompatTextView btnConfirm = v.findViewById(R.id.btn_confirm);

        if (StringUtils.isNotEmpty(message)) {
            txtMessage.setText(message);
        }

        // 확인버튼
        if (positiveCallback == null) {
            positiveCallback = new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismissDialog();
                }
            };
        }

        btnConfirm.setOnClickListener(positiveCallback);
        btnConfirm.setVisibility(View.VISIBLE);

        builder.setView(v);

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    /**
     * myoutfit AlertDialog
     ************************************************************************************************************************************************/
    private static AlertDialog outfitDialog;

    /**
     * 아웃핏 다이얼로그 팝업		:: 메세지 1개 & 버튼 1개
     ************************************************************************************************************************************************/
    public static void showOutfitDialog(Context context, String message, OnSingleClickListener positiveCallback) {

        dismissDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_alert_single_popup, null);

        AppCompatTextView txtMessage = v.findViewById(R.id.txt_message);
        AppCompatTextView btnConfirm = v.findViewById(R.id.btn_confirm);

        if (StringUtils.isNotEmpty(message)) {
            txtMessage.setText(message);
        }

        // 확인버튼
        if (positiveCallback == null) {
            positiveCallback = new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismissDialog();
                }
            };
        }

        btnConfirm.setOnClickListener(positiveCallback);
        btnConfirm.setVisibility(View.VISIBLE);

        builder.setView(v);

        outfitDialog = builder.create();
        outfitDialog.setCancelable(false);
        outfitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        outfitDialog.show();
    }

    /**
     * 기본 팝업 		:: 메세지 1개 / 버튼 2개
     ************************************************************************************************************************************************/
    public static void showDoubleDialog(Context context, String message, OnSingleClickListener positiveCallback, OnSingleClickListener negativeCallback) {

        dismissDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_alert_double_popup, null);

        AppCompatTextView txtMessage = v.findViewById(R.id.txt_message);
        AppCompatTextView btnCancel = v.findViewById(R.id.btn_cancel);
        AppCompatTextView btnOk = v.findViewById(R.id.btn_ok);

        if (StringUtils.isNotEmpty(message)) {
            txtMessage.setText(message);
        }

        // 예
        if (positiveCallback == null) {
            positiveCallback = new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismissDialog();
                }
            };
        }

        // 아니오
        if (negativeCallback == null) {
            negativeCallback = new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismissDialog();
                }
            };
        }

        btnCancel.setOnClickListener(negativeCallback);
        btnOk.setOnClickListener(positiveCallback);

        btnCancel.setVisibility(View.VISIBLE);
        btnOk.setVisibility(View.VISIBLE);

        builder.setView(v);

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 기본 팝업		:: 메세지 2개 & 버튼 1개
     ************************************************************************************************************************************************/
    public static void showSingleLargeDialog(Context context, String message, String address, OnSingleClickListener positiveCallback) {

        dismissDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_alert_single_large_popup, null);

        AppCompatTextView txtMessage = v.findViewById(R.id.txt_message);
        AppCompatTextView txtMessage_address = v.findViewById(R.id.txt_message_address);
        AppCompatTextView btnOk = v.findViewById(R.id.btn_ok);

        if (StringUtils.isNotEmpty(message)) {
            txtMessage.setText(message);
        }

        if (StringUtils.isNotEmpty(address)) {
            txtMessage_address.setText(address);
        }

        // 예
        if (positiveCallback == null) {
            positiveCallback = new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismissDialog();
                }
            };
        }

        btnOk.setOnClickListener(positiveCallback);

        btnOk.setVisibility(View.VISIBLE);

        builder.setView(v);

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 기본 팝업	    :: 메세지 2개 & 버튼 2개
     ************************************************************************************************************************************************/
    public static void showDoubleLargeDialog(Context context, String message, String address, OnSingleClickListener positiveCallback, OnSingleClickListener negativeCallback) {

        dismissDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_alert_double_large_popup, null);

        AppCompatTextView txtMessage = v.findViewById(R.id.txt_message);
        AppCompatTextView txtMessage_address = v.findViewById(R.id.txt_message_address);
        AppCompatTextView btnCancel = v.findViewById(R.id.btn_cancel);
        AppCompatTextView btnOk = v.findViewById(R.id.btn_ok);

        if (StringUtils.isNotEmpty(message)) {
            txtMessage.setText(message);
        }

        if (StringUtils.isNotEmpty(address)) {
            txtMessage_address.setText(address);
        }

        // 예
        if (positiveCallback == null) {
            positiveCallback = new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismissDialog();
                }
            };
        }

        // 아니오
        if (negativeCallback == null) {
            negativeCallback = new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismissDialog();
                }
            };
        }

        btnCancel.setOnClickListener(negativeCallback);
        btnOk.setOnClickListener(positiveCallback);

        btnCancel.setVisibility(View.VISIBLE);
        btnOk.setVisibility(View.VISIBLE);

        builder.setView(v);

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 메세지 2개 & 버튼 2개
     ************************************************************************************************************************************************/
    public static void showDoubleWalletCopyDialog(Context context, String message, String address, OnSingleClickListener positiveCallback, OnSingleClickListener negativeCallback) {

        dismissDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_alert_double_large_popup, null);

        AppCompatTextView txtMessage = v.findViewById(R.id.txt_message);
        AppCompatTextView txtMessage_address = v.findViewById(R.id.txt_message_address);
        AppCompatTextView btnCancel = v.findViewById(R.id.btn_cancel);
        AppCompatTextView btnOk = v.findViewById(R.id.btn_ok);

        btnCancel.setText(context.getString(R.string.str_cancel));

        if (StringUtils.isNotEmpty(message)) {
            txtMessage.setText(message);
        }

        if (StringUtils.isNotEmpty(address)) {
            txtMessage_address.setText(address);
        }

        // 예
        if (positiveCallback == null) {
            positiveCallback = new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismissDialog();
                }
            };
        }

        // 아니오
        if (negativeCallback == null) {
            negativeCallback = new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismissDialog();
                }
            };
        }

        btnCancel.setOnClickListener(negativeCallback);
        btnOk.setOnClickListener(positiveCallback);

        btnCancel.setVisibility(View.VISIBLE);
        btnOk.setVisibility(View.VISIBLE);

        builder.setView(v);

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 권한 팝업	    :: 메세지 1개 / 버튼 2개
     ************************************************************************************************************************************************/
    public static void showPermissionCheckDialog(Context context, String message, OnSingleClickListener positiveCallback, OnSingleClickListener negativeCallback) {

        dismissDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_alert_permission_popup, null);

        AppCompatTextView txtMessage = v.findViewById(R.id.txt_message);
        AppCompatTextView btnCancel = v.findViewById(R.id.btn_cancel);
        AppCompatTextView btnOk = v.findViewById(R.id.btn_ok);

        if (StringUtils.isNotEmpty(message)) {
            txtMessage.setText(message);
        }

        // 예
        if (positiveCallback == null) {
            positiveCallback = new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismissDialog();
                }
            };
        }

        // 아니오
        if (negativeCallback == null) {
            negativeCallback = new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismissDialog();
                }
            };
        }

        btnCancel.setOnClickListener(negativeCallback);
        btnOk.setOnClickListener(positiveCallback);

        btnCancel.setVisibility(View.VISIBLE);
        btnOk.setVisibility(View.VISIBLE);

        builder.setView(v);

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * Dialog 종료
     ************************************************************************************************************************************************/
    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dialog = null;
            }
        }
    }
}