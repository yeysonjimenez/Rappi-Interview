package com.example.yeyson.rappiinterview.injector.toprated;

import com.example.yeyson.rappiinterview.injector.Fragments;
import com.example.yeyson.rappiinterview.model.api.Api;
import com.example.yeyson.rappiinterview.mvp.presentation.toprated.TopRatedPresenter;
import com.example.yeyson.rappiinterview.mvp.view.toprated.ITopRatedView;
import com.example.yeyson.rappiinterview.ui.fragment.TopRatedFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class TopRatedModule {

    ITopRatedView topRatedView;
    TopRatedFragment fragment;

    public TopRatedModule(ITopRatedView topRatedView) {
        this.topRatedView = topRatedView;
    }

    @Provides
    @Fragments
    TopRatedFragment provideFragment() {
        return fragment;
    }
    @Provides
    @Fragments
    TopRatedPresenter provideTopRatedPresenter(Api apiSource) {
        return new TopRatedPresenter(topRatedView,apiSource);
    }
}
