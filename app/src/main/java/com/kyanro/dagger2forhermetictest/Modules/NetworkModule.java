package com.kyanro.dagger2forhermetictest.Modules;

import android.content.Context;

import com.kyanro.dagger2forhermetictest.Network.GithubClient;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ppp on 2016/02/21.
 */
@Module
public class NetworkModule {
    @Singleton
    @Provides
    public GithubClient provideGithubClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(GithubClient.class);
    }

    @Singleton
    @Provides
    public Picasso providePicasso(Context context) {
        return new Picasso.Builder(context).build();
    }
}
