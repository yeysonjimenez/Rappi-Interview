package com.example.yeyson.rappiinterview.mvp.presentation.search;

import com.example.yeyson.rappiinterview.entity.search.Movie;
import com.example.yeyson.rappiinterview.entity.search.MoviesRes;
import com.example.yeyson.rappiinterview.model.api.Api;
import com.example.yeyson.rappiinterview.mvp.view.search.ISearchView;
import com.example.yeyson.rappiinterview.util.Constants;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class SearchPresenter implements ISearchPresenter {

    private ISearchView view;
    private Api apiSource;
    private Realm realm;

    @Inject
    public SearchPresenter(ISearchView view, Api apiSource) {
        this.view = view;
        this.apiSource = apiSource;
        this.realm = Realm.getDefaultInstance();
    }

    @Override public void searchMovie(String query) {
        apiSource.searchMovie(Constants.API_KEY, Locale.getDefault().getLanguage(), query)
                .debounce(300, MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topRatedResponse -> {
                            view.showSearchResponse(topRatedResponse);
                            realm.executeTransactionAsync(
                                    realm1 -> realm1.insertOrUpdate(topRatedResponse.getResults()),
                                    () -> Timber.e("onSucces"), Timber::e);
                        },
                        e -> {
                            List<Movie> result = realm.copyFromRealm(realm.where(Movie.class).findAll());
                            MoviesRes moviesResponse = new MoviesRes();
                            moviesResponse.setResults(result);
                            view.showSearchResponse(moviesResponse);
                            Timber.e(e);
                        });
    }
}
