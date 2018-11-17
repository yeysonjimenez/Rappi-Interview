package com.example.yeyson.rappiinterview.injector.detail;

import com.example.yeyson.rappiinterview.injector.Activityes;
import com.example.yeyson.rappiinterview.ui.activity.FilmDetailActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailModule {
    FilmDetailActivity activity;

    @Provides
    @Activityes
    FilmDetailActivity provideDetailActivity() {
        return activity;
    }
}
