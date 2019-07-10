package com.example.hdxy.baseproject.UI.home.act;
import android.view.View;

import com.example.hdxy.baseproject.Base.BaseActivity;
import com.example.hdxy.baseproject.R;
import com.example.hdxy.baseproject.UI.home.bean.BookInfoBean;
import com.example.hdxy.baseproject.UI.home.mvp.contract.HomeContract;
import com.example.hdxy.baseproject.UI.home.mvp.presenter.HomePresenter;
import com.example.hdxy.baseproject.Widget.TitleBar;
import com.hjq.toast.ToastUtils;

/**
 * Created by hdxy on 2018/12/4.
 */

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {


    private TitleBar mTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }
    @Override
    protected void initIntent() {

    }
    @Override
    protected void initCircle() {
        presenter.getBookInfoFromNet(this);
        presenter.textRxjavaLife(this);

        mTitleBar = findViewById(R.id.titlebar);
        mTitleBar.bind(this);
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
