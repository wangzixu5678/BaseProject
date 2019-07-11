package com.example.hdxy.baseproject.UI.home.mvp.presenter;

import com.apkfuns.logutils.LogUtils;
import com.example.hdxy.baseproject.Base.BaseImpl;
import com.example.hdxy.baseproject.Base.basemvp.presenters.XBasePresenter;
import com.example.hdxy.baseproject.Http.subscriber.DialogObserver;
import com.example.hdxy.baseproject.UI.home.bean.BookInfoBean;
import com.example.hdxy.baseproject.UI.home.mvp.contract.HomeContract;
import com.example.hdxy.baseproject.UI.home.mvp.model.HomeModel;

/**
 * Created by hdxy on 2018/12/4.
 */

public class HomePresenter extends XBasePresenter<HomeContract.View,HomeModel> implements HomeContract.Presenter {

    @Override
    public void getBookInfoFromNet(BaseImpl base) {
        model.getBookInfo(new DialogObserver<BookInfoBean>(base) {
            @Override
            protected void onBaseNext(BookInfoBean data) {
                view.onBookInfoSuccess(data);
            }

            @Override
            protected void onBaseError(int code, String msg) {
                super.onBaseError(code, msg);
                view.onBookInfoError(code,msg);
            }
        });
    }

    @Override
    public void textRxjavaLife(BaseImpl base) {
        model.textRxjavaLife(new DialogObserver<Integer>(base) {
            @Override
            protected void onBaseNext(Integer data) {

            }
        });
    }
}
