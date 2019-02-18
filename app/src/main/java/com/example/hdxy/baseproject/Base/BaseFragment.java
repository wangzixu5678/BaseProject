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

import com.example.hdxy.baseproject.Base.basemvp.presenters.XBasePresenter;
import com.example.hdxy.baseproject.Base.basemvp.utils.GenericHelper;
import com.example.hdxy.baseproject.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseFragment<T extends XBasePresenter> extends Fragment {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private T presenter;
    private View mRootView;
    private Unbinder unbinder;
    private ImageView mImgLeft;
    private TextView mTvLeft;
    private FrameLayout mFlLeftContent;
    private TextView mTvTitle;
    private ImageView mImgRight;
    private TextView mTvRight;
    private FrameLayout mFlRightContent;
    private int mCurrentPager = 1;
    private RefreshLayout mRefreshLayout;
    private boolean mIsVisible;
    private boolean mIsPrepare;

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

        unbinder = ButterKnife.bind(this, mRootView);

        initArguments(getArguments());

        initCommonUI();

        mIsPrepare = true;

        /**
         * 用户编写自己的逻辑代码
         * initView()
         * initData()
         */
        initCircle();

        return mRootView;
    }


    private void initCommonUI() {
        initTitle();
        initRefresh();
    }
    protected void setBackTitle(String title,Object rightRes){
        if (mTvTitle!=null){
            mTvTitle.setText(title);
        }

        if (mImgLeft!=null){
            mImgLeft.setVisibility(View.VISIBLE);
            mImgLeft.setImageResource(R.drawable.back);
        }
        if (rightRes!=null){
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

        if (mFlLeftContent!=null){
            mFlLeftContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
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
    protected void setCustomTitle(Object leftRes,String title,Object rightRes){
        if (mTvTitle!=null){
            mTvTitle.setText(title);
        }

        if (leftRes!=null){
            if (leftRes instanceof String){
                if (mTvLeft!=null){
                    mTvLeft.setVisibility(View.VISIBLE);
                    mTvLeft.setText(((String) leftRes));
                }
            }else if (leftRes instanceof Integer){
                if (mImgLeft!=null){
                    mImgLeft.setVisibility(View.VISIBLE);
                    mImgLeft.setImageResource((Integer)leftRes);
                }
            }
        }

        if (rightRes!=null){
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
    protected  void onClickLeft(){

    }
    protected  void onClickRight(){

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
        mRefreshLayout = mRootView.findViewById(R.id.refreshlayout);
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

    private void initTitle() {
        mImgLeft = ((ImageView) mRootView.findViewById(R.id.img_left));
        mTvLeft = ((TextView) mRootView.findViewById(R.id.tv_left));
        mFlLeftContent = ((FrameLayout) mRootView.findViewById(R.id.fl_left_content));
        mTvTitle = ((TextView) mRootView.findViewById(R.id.tv_title));
        mImgRight = ((ImageView) mRootView.findViewById(R.id.img_right));
        mTvRight = ((TextView) mRootView.findViewById(R.id.tv_right));
        mFlRightContent = ((FrameLayout) mRootView.findViewById(R.id.fl_right_content));
    }

    protected abstract int getLayoutId();

    protected abstract void initArguments(Bundle arguments);

    protected abstract void initCircle();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.mIsVisible = isVisibleToUser;

        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }

    /**
     * 用户可见时执行的操作
     *
     * @author 漆可
     * @date 2016-5-26 下午4:09:39
     */
    protected void onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            onLazyLoad();
        }
    }

    /**
     * 懒加载，仅当用户可见且view初始化结束后才会执行
     *
     * @author 漆可
     * @date 2016-5-26 下午4:10:20
     */
    protected void onLazyLoad(){

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
