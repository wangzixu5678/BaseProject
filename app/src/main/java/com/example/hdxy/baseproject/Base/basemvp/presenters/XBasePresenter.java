/*
 * Copyright 2016 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.hdxy.baseproject.Base.basemvp.presenters;


import com.example.hdxy.baseproject.Base.basemvp.contracts.XContract;

/**
 * MVP：Presenter基类
 */

public class XBasePresenter<T extends XContract.View, E extends XContract.Model> {
    protected T view;
    protected E model;

    public void init(Object view, Object model) {
        this.view = (T) view;
        this.model = (E) model;
    }

    /**
     * 当Activity的onCreate或Fragment的onAttach方法执行时将会调用 <br />
     * call this method when Activity's onCreate or Fragment's onAttach method called
     */
    public void start() {}

    /**
     * 当onDestroy或onDetach方法执行时将会调用 <br />
     * call this method when Activity's onDestroy or Fragment's onDetach method called
     */
    public void end() {
        view = null;
        model = null;
    }

    /**
     * view是否还存在 <br />
     * whether view exist
     */
    public boolean viewIsExist()
    {
        return view != null;
    }
}
