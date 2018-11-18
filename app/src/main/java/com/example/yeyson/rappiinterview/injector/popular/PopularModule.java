package com.example.yeyson.rappiinterview.injector.popular;

import com.example.yeyson.rappiinterview.injector.Activityes;
import com.example.yeyson.rappiinterview.model.api.Api;
import com.example.yeyson.rappiinterview.mvp.presentation.popular.PopularPresentation;
import com.example.yeyson.rappiinterview.mvp.view.popular.IfPopularView;
import com.example.yeyson.rappiinterview.ui.activity.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class PopularModule {
    MainActivity activity;
    IfPopularView popularView;

    public PopularModule(IfPopularView popularView) {
        this.popularView = popularView;
    }

    @Provides
    @Activityes
    MainActivity provideMainActivity() {
        return activity;
    }

    @Provides
    @Activityes
    PopularPresentation providePopularPresenter(Api apiSource) {
        return new PopularPresentation(popularView, apiSource);
    }
}
