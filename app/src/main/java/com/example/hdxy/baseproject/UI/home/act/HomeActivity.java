package com.example.hdxy.baseproject.UI.home.act;

import com.example.hdxy.baseproject.Base.BaseActivity;
import com.example.hdxy.baseproject.R;
import com.example.hdxy.baseproject.UI.home.bean.BookInfoBean;
import com.example.hdxy.baseproject.UI.home.mvp.contract.HomeContract;
import com.example.hdxy.baseproject.UI.home.mvp.presenter.HomePresenter;
import com.hjq.toast.ToastUtils;

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
        setBackTitle("HomeActivity",null);
        presenter.getBookInfoFromNet(this);
        presenter.textRxjavaLife(this);
    }


    @Override
    public void onBookInfoSuccess(BookInfoBean data) {

    }

    @Override
    public void onBookInfoError(int code, String msg) {
        ToastUtils.show(code + msg);
    }
}
