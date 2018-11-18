package com.example.yeyson.rappiinterview.injector.upcomming;

import com.example.yeyson.rappiinterview.injector.Activityes;
import com.example.yeyson.rappiinterview.injector.Fragments;
import com.example.yeyson.rappiinterview.model.api.Api;
import com.example.yeyson.rappiinterview.mvp.presentation.upcomming.UpcomingPresenter;
import com.example.yeyson.rappiinterview.mvp.view.upcomming.IUpcomingView;
import com.example.yeyson.rappiinterview.ui.activity.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class UpcomingModule {
    IUpcomingView view;
    MainActivity activity ;

    public UpcomingModule(IUpcomingView view) {
        this.view = view;
    }

    @Provides
    @Activityes
    MainActivity provideMainActivity() {
        return activity;
    }

    @Provides
    @Activityes
    UpcomingPresenter provideTopRatedPresenter(Api apiSource) {
        return new UpcomingPresenter(view, apiSource);
    }
}

