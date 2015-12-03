package com.femlite.app.di;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 /**
 * Module for anything API related.
 */
@Module(includes = ApplicationModule.class)
public class ApiBaseModule {

    private static boolean instrumentationEnabled = false;

    private static final int RETROFIT_DEFAULT_TIMEOUT = 15;

    public static void enableInstrumentation() {
        instrumentationEnabled = true;
    }

    public static boolean isInstrumentation() {
        return instrumentationEnabled;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(RETROFIT_DEFAULT_TIMEOUT, SECONDS);
        client.setReadTimeout(RETROFIT_DEFAULT_TIMEOUT, SECONDS);
        client.setWriteTimeout(RETROFIT_DEFAULT_TIMEOUT, SECONDS);

        client.interceptors().add(chain -> {
            Request.Builder builder = chain.request().newBuilder();
            // Add auth headers
            return chain.proceed(builder.build());
        });

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(logging);

        return client;
    }

    @Provides
    @Singleton
    @Named("FbbdRetrofit")
    Retrofit provideRetrofit(OkHttpClient client) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://api.fbbd.com")
                .client(client);
//        if (ApplicationInfo.isNonProduction()) {
//            builder = builder.setLogLevel(RestAdapter.LogLevel.FULL);
//        }
        return builder.build();
    }
}
