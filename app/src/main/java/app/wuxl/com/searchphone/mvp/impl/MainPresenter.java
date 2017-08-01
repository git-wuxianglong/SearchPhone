package app.wuxl.com.searchphone.mvp.impl;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import app.wuxl.com.searchphone.http.HttpUtil;
import app.wuxl.com.searchphone.model.Phone;
import app.wuxl.com.searchphone.mvp.MvpMainView;

/**
 * Author:wuxianglong;
 * Time:2017/8/1.
 */
public class MainPresenter extends BasePresenter {

    String httpUrl = "http://apis.baidu.com/netpopo/shouji/query";
//    String httpArg = "shouji=13456755448";

    MvpMainView mvpMainView;

    Phone mPhone;

    public Phone getPhoneInfo() {
        return mPhone;
    }

    public MainPresenter(MvpMainView mainView) {
        mvpMainView = mainView;
    }

    public void searchPhoneInfo(String phone) {
        if (phone.length() != 11) {
            mvpMainView.showToast("请输入正确的手机号");
            return;
        }
        mvpMainView.showLoading();
        //http请求
        sendHttp(phone);
    }

    private void sendHttp(String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("shouji", phone);
        HttpUtil httpUtil = new HttpUtil(new HttpUtil.HttpResponse() {
            @Override
            public void onSuccess(Object object) {
                String json = object.toString();
                Log.i("123", "返回json数据： " + json);
                mPhone = parseModelWithGson(json);
                mvpMainView.hidenLoading();
                mvpMainView.updateView();
            }

            @Override
            public void onFail(String error) {
                mvpMainView.showToast(error);
                mvpMainView.hidenLoading();
            }
        });
        httpUtil.sendGettHttp(httpUrl, map);
    }

    /**
     * 使用Gson解析返回的json数据
     *
     * @param json
     * @return
     */
    private Phone parseModelWithGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Phone.class);
    }

}
