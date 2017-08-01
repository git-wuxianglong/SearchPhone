package app.wuxl.com.searchphone.mvp;

/**
 * Author:wuxianglong;
 * Time:2017/8/1.
 */
public interface MvpMainView extends MvpLoadingView{

    void showToast(String msg);

    void updateView();
}
