package com.example.yeyson.rappiinterview.event;

import com.example.yeyson.rappiinterview.entity.toprated.TopRatedResults;

public class TopRatedDetailEvent {
    private TopRatedResults results;

    public TopRatedDetailEvent(TopRatedResults results) {
        this.results = results;
    }

    public TopRatedResults getResults() {
        return results;
    }

    public void setResults(TopRatedResults results) {
        this.results = results;
    }
}
