package com.example.hdxy.baseproject.UI.home.mvp.model;

import android.util.Log;

import com.example.hdxy.baseproject.Http.HttpManager;
import com.example.hdxy.baseproject.Http.api.ApiResponse;
import com.example.hdxy.baseproject.UI.home.bean.BookInfoBean;
import com.example.hdxy.baseproject.UI.home.mvp.contract.HomeContract;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hdxy on 2018/12/4.
 */

public class HomeModel extends HttpManager implements HomeContract.Model {

    @Override
    public void getBookInfo(Observer<BookInfoBean> observer) {

    }

    @Override
    public void textRxjavaLife(Observer<Integer> observer) {
        Observable.just(1,2,3,4,5,6,7,8,9,0)
                .observeOn(Schedulers.io())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        Thread.sleep(1000);
                        return integer;
                    }
                }).subscribe(observer);
    }
}
