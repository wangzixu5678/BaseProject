package com.example.hdxy.baseproject.Http.subscriber;


import com.example.hdxy.baseproject.Base.BaseImpl;
import com.example.hdxy.baseproject.Http.exception.ApiException;
import com.google.gson.stream.MalformedJsonException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;


public abstract class BaseObserver<T> implements Observer<T> {

    private BaseImpl mBaseImpl;

    protected abstract void onBaseError(int code, String msg);
    protected abstract void onBaseNext(T data);



    public BaseObserver(BaseImpl baseImpl) {
        mBaseImpl = baseImpl;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mBaseImpl !=null){
            if (d!=null){
                mBaseImpl.addDisposable(d);
            }
        }
    }

    @Override
    public void onNext(T t) {
        if (t!=null){
            onBaseNext(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof CompositeException) {
            CompositeException compositeE=(CompositeException)e;
            for (Throwable throwable : compositeE.getExceptions()) {
                if (throwable instanceof SocketTimeoutException) {
                    onBaseError(ApiException.Code_TimeOut,ApiException.SOCKET_TIMEOUT_EXCEPTION);
                } else if (throwable instanceof ConnectException) {
                    onBaseError(ApiException.Code_UnConnected,ApiException.CONNECT_EXCEPTION);
                } else if (throwable instanceof UnknownHostException) {
                    onBaseError(ApiException.Code_UnConnected,ApiException.CONNECT_EXCEPTION);
                }  else if (throwable instanceof MalformedJsonException) {
                    onBaseError(ApiException.Code_MalformedJson,ApiException.MALFORMED_JSON_EXCEPTION);
                }
            }
        }else {
            String msg = e.getMessage();
            int code;
            if (msg.contains("#")) {
                code = Integer.parseInt(msg.split("#")[0]);
                onBaseError(code, msg.split("#")[1]);
            } else {
                code = ApiException.Code_Default;
                onBaseError(code, msg);
            }
        }
    }

    @Override
    public void onComplete() {

    }


}
