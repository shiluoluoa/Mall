package wx.it.mall.application;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MallApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
    //配置OKHttp网络请求框架
    private void initOkHttpUtils(){
       //保持Cookie
        CookieJarImpl cookieJar=new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient=new OkHttpClient().newBuilder()
                .cookieJar(cookieJar)
                .connectTimeout(1000L, TimeUnit.MILLISECONDS)
                .readTimeout(1000L,TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
