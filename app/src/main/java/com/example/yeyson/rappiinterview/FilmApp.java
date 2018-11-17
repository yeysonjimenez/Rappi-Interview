package com.example.yeyson.rappiinterview;


import android.app.Application;
import android.content.Context;

import com.example.yeyson.rappiinterview.injector.app.AppComponent;
import com.example.yeyson.rappiinterview.injector.app.AppModule;
import com.example.yeyson.rappiinterview.injector.app.DaggerAppComponent;
import com.example.yeyson.rappiinterview.injector.app.NetworkModule;
import io.realm.Realm;
import timber.log.Timber;

public class FilmApp  extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initInjector();
        Realm.init(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initInjector() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public static FilmApp get(Context context) {
        return (FilmApp) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
