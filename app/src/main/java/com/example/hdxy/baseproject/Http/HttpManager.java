package com.example.hdxy.baseproject.Http;

import android.content.Context;

import com.example.hdxy.baseproject.Http.api.ApiResponse;
import com.example.hdxy.baseproject.Http.api.ApiService;
import com.example.hdxy.baseproject.Http.exception.ApiException;
import com.example.hdxy.baseproject.UI.home.bean.BookInfoBean;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by hdxy on 2018/12/1.
 */

public class HttpManager{

    private static final int DEFAULT_TIME = 20;
    private static Retrofit mRetrofit;
    private static HttpManager instance;
    private static Context mContext;
    private final ApiService mApiService;

    public static void init(Context context){
        mContext=context;
    }

    private HttpManager(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .addInterceptor(new StethoInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetAddressConstants.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }


    protected HashMap<String, Object> getHeaderMap() {
        HashMap<String, Object> headerMap = new HashMap<>();

        headerMap.put("token","123456");

        return headerMap;
    }

    protected <T> void toSubscribe(Observable<ApiResponse<T>> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .map(new Function<ApiResponse<T>, T>() {
                    @Override
                    public T apply(@NonNull ApiResponse<T> response) throws Exception {
                        if (response.getCode() != ConstantCode.SUCCESSCODE) {
                            throw new ApiException(response.getCode(), response.getMessage());
                        } else {
                            return response.getResponse();
                        }
                    }
                })
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }





    public void getBookInfoBean(Observer<BookInfoBean> observer){
        toSubscribe(mApiService.getHomeInfo(),observer);
    }



}
