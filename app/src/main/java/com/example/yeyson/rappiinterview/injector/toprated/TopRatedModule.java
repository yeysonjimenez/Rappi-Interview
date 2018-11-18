package com.example.yeyson.rappiinterview.injector.toprated;

import com.example.yeyson.rappiinterview.injector.Activityes;
import com.example.yeyson.rappiinterview.model.api.Api;
import com.example.yeyson.rappiinterview.mvp.presentation.toprated.TopRatedPresenter;
import com.example.yeyson.rappiinterview.mvp.view.toprated.ITopRatedView;
import com.example.yeyson.rappiinterview.ui.activity.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class TopRatedModule {

    ITopRatedView topRatedView;
    MainActivity activity;

    public TopRatedModule(ITopRatedView topRatedView) {
        this.topRatedView = topRatedView;
    }

    @Provides
    @Activityes
    MainActivity provideMainActivity() {
        return activity;
    }

    @Provides
    @Activityes
    TopRatedPresenter provideTopRatedPresenter(Api apiSource) {
        return new TopRatedPresenter(topRatedView, apiSource);
    }
}
