package com.example.hdxy.baseproject.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hdxy.baseproject.Base.basemvp.presenters.XBasePresenter;
import com.example.hdxy.baseproject.Base.basemvp.utils.GenericHelper;
import com.example.hdxy.baseproject.R;
import com.example.hdxy.baseproject.Widget.TitleBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseFragment<T extends XBasePresenter> extends Fragment {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private T presenter;
    private View mRootView;
    private Unbinder unbinder;
    private int mCurrentPager = 1;
    private RefreshLayout mRefreshLayout;
    private boolean isRequestRefresh;
    private boolean mIsPrepare;
    private boolean mIsForceLoad;
    private boolean mIsFirstLoad;
    private TitleBar mTitleBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            presenter = GenericHelper.newPresenter(this);
            if (presenter != null) {
                presenter.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);

        unbinder = ButterKnife.bind(this,mRootView);

        initArguments(getArguments());

        initCommonUi();

        mIsPrepare = true;
        mIsFirstLoad = true;


        /**
         * 用户编写自己的逻辑代码
         * initView()
         * initData()
         */
        initCircle();

        return mRootView;
    }

    private void initCommonUi() {
        initRefreshLayout();
        initTitleBar();
    }

    private void initTitleBar() {
        mTitleBar = mRootView.findViewById(R.id.titlebar);
    }

    private void initRefreshLayout() {
        mRefreshLayout = mRootView.findViewById(R.id.refreshlayout);
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

    /**
     * 获取布局layout
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 获取arguments
     *
     * @param arguments
     */
    protected abstract void initArguments(Bundle arguments);

    /**
     * 初始化
     */
    protected abstract void initCircle();

    /**
     * 必要时onResume刷新网络接口数据
     */
    @Override
    public void onResume() {
        super.onResume();
        if (isRequestRefresh) {
            refreshNetData();
            isRequestRefresh = false;
        }
    }


    /**
     * 在ViewPager中控制Fragment时显示隐藏方法
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }

    /**
     * 通过Fragment Hide Show的方式显示隐藏方法
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            lazyLoad();
        }
    }

    /**
     * 强制重新调用onLazyLoad();
     */
    protected void setIsForceLoad(boolean forceload) {
        mIsForceLoad = forceload;
    }

    private void lazyLoad() {
        if (mIsPrepare) {
            if (mIsFirstLoad || mIsForceLoad) {
                mIsFirstLoad = false;
                mIsForceLoad = false;
                onLazyLoad();
            }
        }
    }

    /**
     * 需要实现的懒加载调用方法
     */
    protected void onLazyLoad() {

    }


    /**
     * 中间弹出Dialog
     *
     * @param msg      副标题
     * @param cancle   取消按钮
     * @param others   其他选项
     * @param listener 监听
     */
    public void showAlertDialog(String msg, String cancle, String[] others, OnItemClickListener listener) {
        AlertView alertView = new AlertView("提示", msg, cancle, null, others,getContext(), AlertView.Style.Alert, listener);
        alertView.setCancelable(false);
        alertView.show();
    }

    /**
     * 底部弹出Dialog
     *
     * @param title    标题
     * @param msg      副标题
     * @param cancle   取消按钮
     * @param others   其他选项
     * @param listener 监听
     */
    public void showSheetDialog(String title, String msg, String cancle, String[] others, OnItemClickListener listener) {
        AlertView alertView = new AlertView(title, msg, cancle, null, others,getContext(), AlertView.Style.ActionSheet, listener);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        if (presenter != null) {
            presenter.end();
        }
        unbinder.unbind();
    }
}
