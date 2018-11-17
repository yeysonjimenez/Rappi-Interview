package com.example.yeyson.rappiinterview.mvp.presentation.toprated;

import com.example.yeyson.rappiinterview.entity.toprated.TopRatedResults;
import com.example.yeyson.rappiinterview.model.api.Api;
import com.example.yeyson.rappiinterview.mvp.view.toprated.ITopRatedView;
import com.example.yeyson.rappiinterview.util.Constants;

import java.util.Locale;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class TopRatedPresenter implements ITopRatedPresenter {

    private ITopRatedView view;
    private Api apiSource;
    private Realm realm;

    @Inject
    public TopRatedPresenter(ITopRatedView view, Api apiSource) {
        this.view = view;
        this.apiSource = apiSource;
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public void loadTopRatedMovies() {
        view.showProgress();

        apiSource.getTopRatedMovies(Constants.API_KEY, Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topRatedResponse -> {
                            view.hideProgress();
                            view.showTopRatedMovies(topRatedResponse.getTopRatedResultsList());
                            realm.executeTransactionAsync(
                                    realm1 -> realm1.insertOrUpdate(topRatedResponse.getTopRatedResultsList()),
                                    () -> Timber.e("onSucces"), Timber::e);
                        },
                        e -> {
                            RealmResults<TopRatedResults> result = realm.where(TopRatedResults.class).findAll();
                            view.hideProgress();
                            view.showTopRatedMovies(result);
                            Timber.e(e);
                        }, () -> view.hideProgress());
    }
}
