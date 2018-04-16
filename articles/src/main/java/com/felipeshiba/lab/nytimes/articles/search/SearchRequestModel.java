package com.felipeshiba.lab.nytimes.articles.search;

public class SearchRequestModel {

    private String query;
    private int page;

    public SearchRequestModel(String query, int page) {
        this.query = query;
        this.page = page;
    }

    public String getQuery() {
        return query;
    }

    public int getPage() {
        return page;
    }

    @Override
    public String toString() {
        return query + " " + page;
    }
}
