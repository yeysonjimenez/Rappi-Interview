package com.example.yeyson.rappiinterview.event;

import com.example.yeyson.rappiinterview.entity.popular.PopularRes;
import com.example.yeyson.rappiinterview.entity.popular.PopularResults;

public class PopularDetailEvent {
    private PopularResults results;

    public PopularDetailEvent(PopularResults results) {
        this.results = results;
    }



    public PopularResults getResults() {
        return results;
    }

    public void setResults(PopularResults results) {
        this.results = results;
    }
}
