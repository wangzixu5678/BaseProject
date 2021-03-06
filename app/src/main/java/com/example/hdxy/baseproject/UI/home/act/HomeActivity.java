package com.example.hdxy.baseproject.UI.home.act;
import android.view.View;

import com.example.hdxy.baseproject.Base.BaseActivity;
import com.example.hdxy.baseproject.R;
import com.example.hdxy.baseproject.UI.home.bean.BookInfoBean;
import com.example.hdxy.baseproject.UI.home.mvp.contract.HomeContract;
import com.example.hdxy.baseproject.UI.home.mvp.presenter.HomePresenter;
import com.example.hdxy.baseproject.Widget.TitleBar;
import com.hjq.toast.ToastUtils;

import java.lang.ref.WeakReference;

/**
 * Created by hdxy on 2018/12/4.
 */

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {




    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }
    @Override
    protected void initIntent() {

    }
    @Override
    protected void initCircle() {
        WeakReference<HomeActivity> weakReference = new WeakReference<>(this);
        presenter.getBookInfoFromNet(weakReference.get());
        presenter.textRxjavaLife(weakReference.get());
        mTitleBar.setBackTitle("我是标题",null);
    }


    @Override
    public void onBookInfoSuccess(BookInfoBean data) {

    }

    @Override
    public void onBookInfoError(int code, String msg) {
        ToastUtils.show(code + msg);
    }
}
