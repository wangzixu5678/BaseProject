package com.example.hdxy.baseproject.Http.api;

import com.example.hdxy.baseproject.UI.home.bean.BookInfoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by hdxy on 2018/12/1.
 */

public interface ApiService {

    @GET("weixin/api/getHomeInfo")
    Observable<ApiResponse<BookInfoBean>> getHomeInfo();
}
