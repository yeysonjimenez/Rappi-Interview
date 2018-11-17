package com.example.yeyson.rappiinterview.mvp.presentation.upcomming;

import com.example.yeyson.rappiinterview.entity.upcoming.UpcomingResults;
import com.example.yeyson.rappiinterview.model.api.Api;
import com.example.yeyson.rappiinterview.mvp.view.upcomming.IUpcomingView;
import com.example.yeyson.rappiinterview.util.Constants;

import java.util.Locale;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UpcomingPresenter implements IUpcomingPresenter {

    private IUpcomingView view;
    private Api apiSource;
    private Realm realm;

    @Inject
    public UpcomingPresenter(IUpcomingView view, Api apiSource) {
        this.view = view;
        this.apiSource = apiSource;
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public void loadUpcomingMovies() {
        view.showProgress();

        apiSource.getUpcomingMovies(Constants.API_KEY, Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread(), false, 100)
                .subscribe(topRatedResponse -> {
                            view.hideProgress();
                            view.showUpComingMovies(topRatedResponse.getTopRatedResultsList());
                            realm.executeTransactionAsync(
                                    realm1 -> realm1.insertOrUpdate(topRatedResponse.getTopRatedResultsList()));
                        },
                        e -> {
                            RealmResults<UpcomingResults> result = realm.where(UpcomingResults.class).findAll();
                            view.hideProgress();
                            view.showUpComingMovies(result);

                        }, () -> view.hideProgress());
    }
}
