package com.example.yeyson.rappiinterview.event;

import com.example.yeyson.rappiinterview.entity.upcoming.UpcomingResults;

public class UpcomingDetailEvent {
    private UpcomingResults results;

    public UpcomingDetailEvent(UpcomingResults results) {
        this.results = results;
    }

    public UpcomingResults getResults() {
        return results;
    }

    public void setResults(UpcomingResults results) {
        this.results = results;
    }
}


