package com.developerx.base.di.modules;

import android.content.Context;

import com.developerx.base.data.network.BaseNetworkClient;
import com.developerx.base.di.ApplicationScope;
import com.developerx.base.util.NetworkUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private String baseUrl;
    private Context context;
    private final static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    public NetworkModule(String baseUrl,Context context){
        this.baseUrl = baseUrl;
        this.context = context;
    }


    private Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            int netWorkState = NetworkUtil.getNetworkState(context);
            if (netWorkState == NetworkUtil.TYPE_NOT_CONNECTED) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);

            switch (netWorkState) {
                case NetworkUtil.TYPE_MOBILE://mobile network
                    int maxAge = 60;
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();

                case NetworkUtil.TYPE_WIFI://wifi network
                    maxAge = 0;
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                default:
                    throw new IllegalStateException("network state is Error!");
            }
        }
    };

    @ApplicationScope
    @Provides
    OkHttpClient getCacheOkHttpClient(){
        return new OkHttpClient.Builder()
                .writeTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                //Setting interceptor, display the log information
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .build();
    }

    @ApplicationScope
    @Provides
    Retrofit getRetrofit(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.MINUTES);
        builder.readTimeout(10,TimeUnit.MINUTES);
        builder.writeTimeout(10,TimeUnit.MINUTES);
        return new Retrofit.Builder()
                .client(getCacheOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @ApplicationScope
    @Provides
    Gson getGson(){
        return new Gson();
    }

    @ApplicationScope
    @Provides
    BaseNetworkClient getBaseNetworkClient(Retrofit retrofit, Gson gson){
        return new BaseNetworkClient(retrofit,gson);
    }

}
