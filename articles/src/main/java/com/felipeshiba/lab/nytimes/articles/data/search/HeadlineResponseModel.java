package com.felipeshiba.lab.nytimes.articles.data.search;

public class HeadlineResponseModel {

    private String main;

    public HeadlineResponseModel(String main) {
        this.main = main;
    }

    public HeadlineResponseModel() {
    }

    public String getMain() {
        return main;
    }
}
