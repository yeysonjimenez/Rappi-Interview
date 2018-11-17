package com.example.yeyson.rappiinterview.mvp.view.upcomming;

import com.example.yeyson.rappiinterview.entity.upcoming.UpcomingResults;

import java.util.List;

public interface IUpcomingView {
    void showProgress();

    void hideProgress();

    void showUpComingMovies(List<UpcomingResults> topRatedResultsList);
}
