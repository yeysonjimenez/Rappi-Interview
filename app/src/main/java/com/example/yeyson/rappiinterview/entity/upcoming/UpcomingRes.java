package com.example.yeyson.rappiinterview.entity.upcoming;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingRes {
    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<UpcomingResults> topRatedResultsList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<UpcomingResults> getTopRatedResultsList() {
        return topRatedResultsList;
    }

    public void setTopRatedResultsList(
            List<UpcomingResults> topRatedResultsList) {
        this.topRatedResultsList = topRatedResultsList;
    }
}
