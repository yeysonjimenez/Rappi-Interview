package com.example.yeyson.rappiinterview.injector.search;

import com.example.yeyson.rappiinterview.injector.Activityes;
import com.example.yeyson.rappiinterview.model.api.Api;
import com.example.yeyson.rappiinterview.mvp.presentation.search.SearchPresenter;
import com.example.yeyson.rappiinterview.mvp.view.search.ISearchView;
import com.example.yeyson.rappiinterview.ui.activity.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {
    MainActivity activity;
    ISearchView iSearchView;

    public SearchModule(ISearchView iSearchView) {
        this.iSearchView = iSearchView;
    }

    @Provides
    @Activityes
    MainActivity provideMainActivity() {
        return activity;
    }

    @Provides
    @Activityes
    SearchPresenter provideSearchPresenter(Api apiSource) {
        return new SearchPresenter(iSearchView, apiSource);
    }
}
