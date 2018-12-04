package com.example.hdxy.baseproject.Http.subscriber;

import com.example.hdxy.baseproject.Base.BaseImpl;
import com.example.hdxy.baseproject.Widget.dialog.LoadingPopWindown;
import com.hjq.toast.ToastUtils;

import io.reactivex.disposables.Disposable;
/**
 * Created by hdxy on 2018/12/3.
 */
public abstract class DialogObserver<T> extends BaseObserver<T> {

    private boolean mHasLoading;
    private LoadingPopWindown mLoadingPopWindown;


    public DialogObserver(BaseImpl baseImpl) {
        super(baseImpl);
    }

    public DialogObserver(BaseImpl baseImpl, boolean hasLoading) {
        super(baseImpl);
        mHasLoading = hasLoading;
        if (hasLoading){
            if (mLoadingPopWindown == null){
                mLoadingPopWindown = new LoadingPopWindown(baseImpl.getContext());
            }
        }
    }

    @Override
    protected void onBaseError(int code, String msg) {
        if (mHasLoading){
            if (mLoadingPopWindown!=null){
                mLoadingPopWindown.dismiss();
            }
        }
        ToastUtils.show(code  + msg);
    }


    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        if (mHasLoading){
            if (mLoadingPopWindown!=null){
                mLoadingPopWindown.show();
            }
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (mHasLoading){
            if (mLoadingPopWindown!=null){
                mLoadingPopWindown.dismiss();
            }
        }
    }
}
