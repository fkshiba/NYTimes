package com.felipeshiba.lab.nytimes.articles.data;


import android.content.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Class responsible for making HTTP requests
 */
public abstract class BaseRepository<T> {

    private static final String BASE_URL = "http://api.nytimes.com/svc/";
    private static final String API_KEY_INDEX = "api-key";
    private static final String API_KEY_VALUE = "f34cf8b70c2741ed9ca5cd1803127f95";

    private static Retrofit retrofit;
    private Map<Class, T> services = new HashMap<>();
    private Context context;

    public BaseRepository(Context context) {
        this.context = context;
    }

    /**
     * Builds a Retrofit client
     *
     * @return The Retrofit instance
     */
    private Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(httpClient().build()).build();
        }
        return retrofit;
    }

    /**
     * Builds a OkHttp client
     *
     * @return The OkHttp instance
     */
    private OkHttpClient.Builder httpClient() {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        return new OkHttpClient.Builder()
                .cache(new Cache(context.getCacheDir(), cacheSize))
            .connectTimeout(60, SECONDS)
            .readTimeout(60, SECONDS)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter(API_KEY_INDEX, API_KEY_VALUE)
                            .build();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);

                });
    }

    /**
     * Get the Retrofit "implementation" of the Endpoint class
     *
     * @return The Endpoint
     */
    protected T get() {
        Class<T> endpointClass = getEndpointClass();
        T endpoint = services.get(endpointClass);
        if (endpoint == null) {
            endpoint = getRetrofit().create(endpointClass);
            services.put(endpointClass, endpoint);
        }
        return endpoint;
    }

    /**
     * Return the Retrofit Endpoint interface definition
     *
     * @return The Endpoint class
     */
    public abstract Class<T> getEndpointClass();
}
