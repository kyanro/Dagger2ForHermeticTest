package com.kyanro.dagger2forhermetictest;

import android.app.Application;

import com.kyanro.dagger2forhermetictest.Modules.AppModule;
import com.kyanro.dagger2forhermetictest.Modules.NetworkModule;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ppp on 2016/02/21.
 */
public class MyApp extends Application {

    public AppComponent mAppComponent;

    @Singleton
    @Component(modules = {NetworkModule.class, AppModule.class})
    interface AppComponent {
        Picasso getPicasso();

        void inject(MainActivity activity);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppComponent == null) {
            mAppComponent = DaggerMyApp_AppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }

        Picasso.setSingletonInstance(mAppComponent.getPicasso());
    }
}
