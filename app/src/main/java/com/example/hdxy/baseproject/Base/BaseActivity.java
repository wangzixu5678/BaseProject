package com.example.hdxy.baseproject.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hdxy.baseproject.Base.basemvp.presenters.XBasePresenter;
import com.example.hdxy.baseproject.Base.basemvp.utils.GenericHelper;
import com.example.hdxy.baseproject.R;
import com.example.hdxy.baseproject.Util.AppManager;
import com.example.hdxy.baseproject.Util.screenadapter.AdaptScreenUtils;
import com.example.hdxy.baseproject.Widget.TitleBar;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.WeakHandler;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by hdxy on 2018/11/30.
 */

public abstract class BaseActivity<T extends XBasePresenter> extends AppCompatActivity implements BaseImpl {
    protected T presenter;
    private Unbinder unbinder;
    protected SmartRefreshLayout mRefreshLayout;
    protected int mCurrentPager = 1;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    protected WeakHandler mWeakHandler = new WeakHandler();
    protected TitleBar mTitleBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            presenter = GenericHelper.newPresenter(this);
            if (presenter != null) {
                presenter.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
        //锁定竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置状态栏
        setStatusBar();
        //初始化监测内存泄漏
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);
        //初始化ButterKnife
        unbinder = ButterKnife.bind(this);
        //初始化上个界面传递过来的值
        initIntent();
        //初始化复用界面
        initCommonUI();
        //初始化数据 UI 所有相关
        initCircle();
    }

    protected void setStatusBar(){
        //设置状态栏为白色
        ImmersionBar.with(this).
                statusBarColor(R.color.white).
                statusBarDarkFont(true).
                fitsSystemWindows(true).
//                keyboardEnable(true).
        init();
        //沉浸式状态栏
//        ImmersionBar.with(getActivity()).statusBarDarkFont(true).init();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initIntent();
        initCommonUI();
        initCircle();
    }

    protected void initCommonUI() {
        initRefreshLayout();
        initTitleBar();
    }

    private void initTitleBar() {
        mTitleBar = findViewById(R.id.titlebar);
    }


    private void initRefreshLayout() {
        mRefreshLayout = findViewById(R.id.refreshlayout);
        if (mRefreshLayout != null) {
            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    refreshNetData();
                }
            });
            mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshLayout) {
                    loadMoreNetData();
                }
            });
        }
    }

    protected void refreshNetData() {
        mRefreshLayout.setNoMoreData(false);
        mCurrentPager = 1;
        getListDataFromNet();
        getDetailDataFromNet();
    }

    protected void loadMoreNetData() {
        mCurrentPager++;
        getListDataFromNet();
    }

    /**
     * 获取列表数据
     */
    protected void getListDataFromNet() {

    }

    /**
     * 加载更多网络请求调用
     */
    protected void getDetailDataFromNet() {

    }


    @Override
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    public Activity getActivity() {
        return this;
    }


    protected abstract int getLayoutId();

    protected abstract void initIntent();

    protected abstract void initCircle();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        if (presenter != null) {
            presenter.end();
        }
        unbinder.unbind();
        AppManager.getInstance().finishActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void finish() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        super.finish();
    }

    @Override
    public Resources getResources() {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 375);
    }

    /**
     * 中间弹出Dialog
     * @param msg 副标题
     * @param cancle 取消按钮
     * @param others 其他选项
     * @param listener 监听
     */
    public void showAlertDialog(String msg, String cancle, String[] others, OnItemClickListener listener) {
        AlertView alertView = new AlertView("提示", msg, cancle, null, others,getContext(), AlertView.Style.Alert, listener);
        alertView.setCancelable(false);
        alertView.show();
    }

    /**
     * 底部弹出Dialog
     * @param title 标题
     * @param msg 副标题
     * @param cancle 取消按钮
     * @param others 其他选项
     * @param listener 监听
     */
    public void showSheetDialog(String title,String msg, String cancle, String[] others, OnItemClickListener listener) {
        AlertView  alertView = new AlertView(title, msg, cancle, null, others,getContext(), AlertView.Style.ActionSheet, listener);
        alertView.setCancelable(false);
        alertView.show();
    }

    /**
     * 成功网络请求回调时 统一加入数据 统一管理RefreshView
     * @param baseQuickAdapter 适配器
     * @param data 原有数据
     * @param netData 新数据
     * @param <T> 泛型T
     */
    public  <T> void  handleRefreshLayoutWhenResponse(BaseQuickAdapter baseQuickAdapter, ArrayList<T> data, ArrayList<T> netData){
        if (mRefreshLayout.getState()== RefreshState.Loading) {
            if (netData != null) {
                data.addAll(netData);
                baseQuickAdapter.notifyDataSetChanged();
                if (netData.size() == 0) {
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
            mRefreshLayout.finishLoadMore(true);
        } else if (mRefreshLayout.getState()== RefreshState.Refreshing) {
            mRefreshLayout.finishRefresh(true);
            data.clear();
            if (netData != null) {
                data.addAll(netData);
            }
            baseQuickAdapter.notifyDataSetChanged();
        } else {
            data.clear();
            if (netData != null) {
                data.addAll(netData);
            }
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 失败网络请求回调时 统一加入数据 统一管理RefreshView
     */
    public void handleRefreshLayoutWhenResponseError() {
        if (mRefreshLayout.getState() == RefreshState.Refreshing) {
            mRefreshLayout.finishRefresh(false);
        } else if (mRefreshLayout.getState() == RefreshState.Loading) {
            mRefreshLayout.finishLoadMore(false);
        } else {
        }
    }

}
