package com.example.hdxy.baseproject.Widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by wzx on 2018/5/8.
 */

public abstract class CommonPopupWindow {
    protected View contentView;
    protected PopupWindow mInstance;
    protected Activity mActivity;

    public CommonPopupWindow(Activity activity,int layoutRes, int w, int h) {
        mActivity = activity;
        contentView = LayoutInflater.from(mActivity).inflate(layoutRes, null, false);
        initView();
        initEvent();
        mInstance = new PopupWindow(contentView, w, h, true);
        initWindow();
    }

    public View getContentView() {
        return contentView;
    }

    public PopupWindow getPopupWindow() {
        return mInstance;
    }

    protected abstract void initView();

    protected abstract void initEvent();

    protected void initWindow() {
        mInstance.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mInstance.setOutsideTouchable(true);
        mInstance.setTouchable(true);
        mInstance.setFocusable(true);
        mInstance.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackgroud(mActivity, 1f);
            }
        });
    }




    public void showAsDropDown(View anchor, int xoff, int yoff, float light) {
        mInstance.showAsDropDown(anchor, xoff, yoff);
        darkenBackgroud(mActivity, light);
    }

    public void showAtLocation(View parent, int gravity, int x, int y, float light) {
        mInstance.showAtLocation(parent, gravity, x, y);
        darkenBackgroud(mActivity, light);
    }

    private void darkenBackgroud(Activity activity, Float bgcolor) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgcolor;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }
}