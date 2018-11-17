package com.example.yeyson.rappiinterview.mvp.presentation.popular;

import com.example.yeyson.rappiinterview.entity.popular.PopularRes;
import com.example.yeyson.rappiinterview.entity.popular.PopularResults;
import com.example.yeyson.rappiinterview.model.api.Api;
import com.example.yeyson.rappiinterview.mvp.view.popular.IfPopularView;
import com.example.yeyson.rappiinterview.util.Constants;

import java.util.Locale;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class PopularPresentation implements IfPopularPresenter {

    private IfPopularView view;
    private Api apiSource;
    private Realm realm;

    @Inject
    public PopularPresentation(IfPopularView view, Api apiSource) {
        this.view = view;
        this.apiSource = apiSource;
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public void loadPopularMovies() {
        view.showProgress();

        apiSource.getPopularMovies(Constants.API_KEY, Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(popularResponse -> {
                    view.hideProgress();
                    view.showPopularMovies(popularResponse.getPopularResultsList());
                    realm.executeTransactionAsync(
                            realm1 -> realm1.insertOrUpdate(popularResponse.getPopularResultsList()),
                            () -> Timber.e("onSucces"), Timber::e);
                }, throwable -> {
                    RealmResults<PopularResults> result = realm.where(PopularResults.class).findAll();
                    view.hideProgress();
                    view.showPopularMovies(result);
                    Timber.e(throwable);
                }, () -> view.hideProgress());
    }
}
