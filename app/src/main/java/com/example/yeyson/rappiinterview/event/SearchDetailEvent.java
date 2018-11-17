package com.example.yeyson.rappiinterview.event;

import com.example.yeyson.rappiinterview.entity.search.Movie;

public class SearchDetailEvent{
    private Movie results;

    public SearchDetailEvent(Movie results) {
        this.results = results;
    }

    public Movie getResults() {
        return results;
    }

    public void setResults(Movie results) {
        this.results = results;
    }
}
