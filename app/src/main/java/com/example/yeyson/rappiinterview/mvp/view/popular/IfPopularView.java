package com.example.yeyson.rappiinterview.mvp.view.popular;

import com.example.yeyson.rappiinterview.entity.popular.PopularRes;
import com.example.yeyson.rappiinterview.entity.popular.PopularResults;

import java.util.List;

public interface IfPopularView {
    void showProgress();

    void hideProgress();

    void showPopularMovies(List<PopularResults> popularResultsList);
}
