package com.example.yeyson.rappiinterview.injector.upcomming;

import com.example.yeyson.rappiinterview.injector.Fragments;
import com.example.yeyson.rappiinterview.model.api.Api;
import com.example.yeyson.rappiinterview.mvp.presentation.upcomming.UpcomingPresenter;
import com.example.yeyson.rappiinterview.mvp.view.upcomming.IUpcomingView;
import com.example.yeyson.rappiinterview.ui.fragment.UpcomingFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class UpcomingModule {
    IUpcomingView view;
    UpcomingFragment fragment;

    public UpcomingModule(IUpcomingView view) {
        this.view = view;
    }

    @Provides
    @Fragments
    UpcomingFragment provideFragment() {
        return fragment;
    }

    @Provides
    @Fragments
    UpcomingPresenter provideTopRatedPresenter(Api apiSource) {
        return new UpcomingPresenter(view, apiSource);
    }
}

