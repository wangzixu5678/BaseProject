package com.example.hdxy.baseproject.UI.home.act;

import com.apkfuns.logutils.LogUtils;
import com.example.hdxy.baseproject.Base.BaseActivity;
import com.example.hdxy.baseproject.Http.HttpManager;
import com.example.hdxy.baseproject.Http.subscriber.DialogObserver;
import com.example.hdxy.baseproject.R;
import com.example.hdxy.baseproject.UI.home.bean.BookInfoBean;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;


public class HomeActivity extends BaseActivity {



    @Override
    protected int getLayoutId() {
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

        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("哈哈"+i);
        }
        LogUtils.d(list);
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
