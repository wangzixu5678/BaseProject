package com.example.hdxy.baseproject.Base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hdxy.baseproject.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.WeakHandler;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by hdxy on 2018/11/30.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseImpl {


    private Unbinder unbinder;
    protected SmartRefreshLayout mRefreshLayout;
    protected int mCurrentPager = 1;
    protected static final int PAGERSIZE = 10;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private ImageView mImgLeft;
    private TextView mTvLeft;
    private FrameLayout mFlLeftContent;
    private ImageView mImgRight;
    private TextView mTvRight;
    private FrameLayout mFlRightContent;
    private TextView mTvTitle;
    protected WeakHandler mWeakHandler = new WeakHandler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
        //锁定竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化监测内存泄漏
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);
        //初始化ButterKnife
        unbinder = ButterKnife.bind(this);

        initCommonUI();
        //初始化数据相关
        initAll();


    }

    protected  void initCommonUI(){
        initTitle();
        initRefresh();
    }

    private void initTitle() {
        mImgLeft = ((ImageView) findViewById(R.id.img_left));
        mTvLeft = ((TextView) findViewById(R.id.tv_left));
        mFlLeftContent = ((FrameLayout) findViewById(R.id.fl_left_content));
        mTvTitle = ((TextView) findViewById(R.id.tv_title));
        mImgRight = ((ImageView) findViewById(R.id.img_right));
        mTvRight = ((TextView) findViewById(R.id.tv_right));
        mFlRightContent = ((FrameLayout) findViewById(R.id.fl_right_content));

        if (mFlLeftContent!=null){
            mFlLeftContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickLeft();
                }
            });
        }

        if (mFlRightContent!=null){
            mFlRightContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRight();
                }
            });
        }
    }

    protected void setBackTitle(String title,Object rightRes){
        if (mTvTitle!=null){
            mTvTitle.setText(title);
        }

        if (mImgLeft!=null){
            mImgLeft.setVisibility(View.VISIBLE);
            mImgLeft.setImageResource(R.drawable.back);
        }

        if (rightRes instanceof String){
            if (mTvRight!=null){
                mTvRight.setVisibility(View.VISIBLE);
                mTvRight.setText(((String) rightRes));
            }
        }else if (rightRes instanceof Integer){
            if (mImgRight!=null){
                mImgRight.setVisibility(View.VISIBLE);
                mImgRight.setImageResource((Integer)rightRes);
            }
        }
    }

    protected void setCustomTitle(Object leftRes,String title,Object rightRes){
        if (mTvTitle!=null){
            mTvTitle.setText(title);
        }

        if (rightRes instanceof String){
            if (mTvLeft!=null){
                mTvLeft.setVisibility(View.VISIBLE);
                mTvLeft.setText(((String) leftRes));
            }
        }else if (rightRes instanceof Integer){
            if (mImgLeft!=null){
                mImgLeft.setVisibility(View.VISIBLE);
                mImgLeft.setImageResource((Integer)leftRes);
            }
        }

        if (rightRes instanceof String){
            if (mTvRight!=null){
                mTvRight.setVisibility(View.VISIBLE);
                mTvRight.setText(((String) rightRes));
            }
        }else if (rightRes instanceof Integer){
            if (mImgRight!=null){
                mImgRight.setVisibility(View.VISIBLE);
                mImgRight.setImageResource((Integer)rightRes);
            }
        }
    }

    protected void setLeftTitleColor(int color){
        if (mTvLeft!=null) {
            mTvLeft.setTextColor(color);
        }
    }
    protected void setLeftRightColor(int color){
        if (mTvRight!=null){
            mTvRight.setTextColor(color);
        }
    }

    protected void setLeftImgSize(int width,int height){
        if (mImgLeft!=null){
            ViewGroup.LayoutParams layoutParams = mImgLeft.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            mImgLeft.setLayoutParams(layoutParams);
        }
    }


    protected void setRightImgSize(int width,int height){
        if (mImgRight!=null){
            ViewGroup.LayoutParams layoutParams = mImgRight.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            mImgRight.setLayoutParams(layoutParams);
        }
    }

    private void initRefresh() {
        mRefreshLayout = findViewById(R.id.refreshlayout);
        if (mRefreshLayout!=null){
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

    protected void refreshNetData(){
        mRefreshLayout.setNoMoreData(false);
        mCurrentPager = 1;
        refreshNetInterface();
    }

    protected void loadMoreNetData(){
        mCurrentPager ++;
        loadMoreNetInterface();
    }

    /**
     * 下拉刷新网络请求调用
     */
    protected void refreshNetInterface(){

    }

    /**
     * 加载更多网络请求调用
     */
    protected void loadMoreNetInterface(){

    }


    protected  void onClickLeft(){

    }
    protected  void onClickRight(){

    }






    protected abstract int getLayoutId();

    protected abstract void initAll();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable!=null){
            mCompositeDisposable.clear();
        }
        unbinder.unbind();
        EventBus.getDefault().unregister(this);

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
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable!=null) {
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
    public Activity getActivity(){
        return this;
    }


    @Override
    public void finish() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.finish();
    }
}
