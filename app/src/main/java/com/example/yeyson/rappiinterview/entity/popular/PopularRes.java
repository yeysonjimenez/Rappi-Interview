package com.example.yeyson.rappiinterview.entity.popular;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularRes {
    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<PopularResults> popularResultsList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<PopularResults> getPopularResultsList() {
        return popularResultsList;
    }

    public void setPopularResultsList(List<PopularResults> popularResultsList) {
        this.popularResultsList = popularResultsList;
    }
}
