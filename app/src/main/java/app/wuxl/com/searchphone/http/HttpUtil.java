package app.wuxl.com.searchphone.http;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Http请求封装
 * Author:wuxianglong;
 * Time:2017/8/1.
 */
public class HttpUtil {

    // apikey申请地址http://apistore.baidu.com/apiworks/servicedetail/498.html
    private static final String APIKEY = "5e97386678637e7ef161c77b4b02fdcb";

    String mUrl;
    Map<String, String> mParm;
    HttpResponse mHttpResponse;

    Handler mHandler = new Handler(Looper.getMainLooper());

    private final OkHttpClient client = new OkHttpClient();

    public interface HttpResponse {

        void onSuccess(Object object);

        void onFail(String error);
    }

    public HttpUtil(HttpResponse response) {
        mHttpResponse = response;
    }

    public void sendPostHttp(String url, Map<String, String> parm) {
        sendHttp(url, parm, true);
    }

    public void sendGettHttp(String url, Map<String, String> parm) {
        sendHttp(url, parm, false);
    }

    private void sendHttp(String url, Map<String, String> parm, boolean isPost) {
        mUrl = url;
        mParm = parm;
        //HTTP请求逻辑
        run(isPost);
    }

    private void run(boolean isPost) {
        final Request request = createRequest(isPost);
        //创建请求队列
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mHttpResponse != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mHttpResponse.onFail("请求错误");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (mHttpResponse == null) {
                    return;
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!response.isSuccessful()) {
                            mHttpResponse.onFail("请求失败：code" + response);
                        } else {
                            try {
                                mHttpResponse.onSuccess(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                                mHttpResponse.onFail("结果转换失败");
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 创建request请求
     *
     * @param isPost
     * @return
     */
    private Request createRequest(boolean isPost) {
        Request request;
        if (isPost) {
            MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder();
            requestBodyBuilder.setType(MultipartBody.FORM);
            //遍历map请求参数
            Iterator<Map.Entry<String, String>> iterator = mParm.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                requestBodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            request = new okhttp3.Request.Builder().url(mUrl)
                    .addHeader("apikey", APIKEY)
                    .post(requestBodyBuilder.build()).build();

        } else {
            String urlStr = mUrl + "?" + MapParmToString(mParm);
            request = new Request.Builder().url(urlStr)
                    .addHeader("apikey", APIKEY)
                    .build();
        }
        return request;
    }

    /**
     * 拼接
     *
     * @param parm
     * @return
     */
    private String MapParmToString(Map<String, String> parm) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = parm.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            stringBuilder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        String str = stringBuilder.toString().substring(0, stringBuilder.length());
        return str;
    }

}
