package com.example.yeyson.rappiinterview.injector.app;

import android.app.Application;
import android.content.Context;

import com.example.yeyson.rappiinterview.util.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return application;
    }

    @Singleton
    @Provides
    Context provideAppContext() {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    RxBus provideRxBus() {
        return RxBus.getInstance();
    }
}
