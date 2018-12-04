package com.example.hdxy.baseproject.UI.home.mvp.contract;

import com.example.hdxy.baseproject.Base.BaseImpl;
import com.example.hdxy.baseproject.Base.basemvp.contracts.XContract;
import com.example.hdxy.baseproject.UI.home.bean.BookInfoBean;

import io.reactivex.Observer;

/**
 * Created by hdxy on 2018/12/4.
 */

public interface HomeContract {
    interface View extends XContract.View {
        void onBookInfoSuccess(BookInfoBean data);
        void onBookInfoError(int code, String msg);
    }

    interface Presenter extends XContract.Presenter {
        void getBookInfoFromNet(BaseImpl base);

        void textRxjavaLife(BaseImpl base);
    }

    interface Model extends XContract.Model {
        void getBookInfo(Observer<BookInfoBean> observer);

        void textRxjavaLife(Observer<Integer> observer);
    }
}
