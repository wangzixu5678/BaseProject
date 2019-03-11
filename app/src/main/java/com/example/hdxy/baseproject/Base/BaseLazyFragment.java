package com.example.hdxy.baseproject.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 懒加载
 */
public abstract class BaseLazyFragment extends Fragment {

    private boolean isViewCreated;
    private boolean isUIVisible;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initArg();
        initView(view);
        return view;
    }


    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void initArg();

    protected abstract void loadLazyData();


    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        if (isViewCreated && isUIVisible) {
            loadLazyData();
            isViewCreated = false;
            isUIVisible = false;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isUIVisible = false;
    }
}
