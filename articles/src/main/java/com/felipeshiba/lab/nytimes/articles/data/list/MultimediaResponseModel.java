package com.felipeshiba.lab.nytimes.articles.data.list;

public class MultimediaResponseModel {

    public MultimediaResponseModel() {
    }

    public MultimediaResponseModel(String url) {
        this.url = url;
    }

    private String url;

    public String getUrl() {
        return url;
    }
}
