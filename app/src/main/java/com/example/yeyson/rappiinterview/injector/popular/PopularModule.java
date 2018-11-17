package com.example.yeyson.rappiinterview.injector.popular;

import com.example.yeyson.rappiinterview.injector.Fragments;
import com.example.yeyson.rappiinterview.model.api.Api;
import com.example.yeyson.rappiinterview.mvp.presentation.popular.PopularPresentation;
import com.example.yeyson.rappiinterview.mvp.view.popular.IfPopularView;
import com.example.yeyson.rappiinterview.ui.fragment.PopularFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class PopularModule {
    IfPopularView popularView;
    PopularFragment popularFragment;

    public PopularModule(IfPopularView popularView) {
        this.popularView = popularView;
    }

    @Provides
    @Fragments
    PopularFragment provideFragment() {
        return popularFragment;
    }

    @Provides
    @Fragments
    PopularPresentation providePopularPresenter(Api apiSource) {
        return new PopularPresentation(popularView, apiSource);
    }
}
