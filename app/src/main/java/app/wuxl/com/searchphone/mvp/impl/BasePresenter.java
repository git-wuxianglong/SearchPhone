package app.wuxl.com.searchphone.mvp.impl;

import android.content.Context;

/**
 * 基类
 * Author:wuxianglong;
 * Time:2017/8/1.
 */
public class BasePresenter {

    Context mContext;

    public void attach(Context context) {
        mContext = context;
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onDestroy() {
        mContext = null;
    }

}
