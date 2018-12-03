package com.example.hdxy.baseproject.UI.home.act;

import com.example.hdxy.baseproject.Base.BaseActivity;
import com.example.hdxy.baseproject.Http.HttpManager;
import com.example.hdxy.baseproject.Http.subscriber.DialogObserver;
import com.example.hdxy.baseproject.R;
import com.example.hdxy.baseproject.UI.home.bean.BookInfoBean;
import com.hjq.toast.ToastUtils;

public class HomeActivity extends BaseActivity {



    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initAll() {


        setCustomTitle("完成","标题","完成");

        HttpManager.getInstance().getBookInfoBean(new DialogObserver<BookInfoBean>(this,true) {
            @Override
            protected void onBaseNext(BookInfoBean data) {

            }

            @Override
            protected void onBaseError(int code, String msg) {
                super.onBaseError(code, msg);

            }
        });
    }


    @Override
    protected void onClickLeft() {
        super.onClickLeft();
        ToastUtils.show("点击左侧");
    }

    @Override
    protected void onClickRight() {
        super.onClickRight();
        ToastUtils.show("点击右侧");
    }
}
