package com.example.yeyson.rappiinterview.mvp.view.toprated;

import com.example.yeyson.rappiinterview.entity.toprated.TopRatedResults;

import java.util.List;

public interface ITopRatedView {
    void showProgress();

    void hideProgress();

    void showTopRatedMovies(List<TopRatedResults> topRatedResultsList);
}

