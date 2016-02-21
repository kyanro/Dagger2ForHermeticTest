package com.kyanro.dagger2forhermetictest.Modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ppp on 2016/02/21.
 */
@Module
public class AppModule {

    Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return app.getApplicationContext();
    }
}
