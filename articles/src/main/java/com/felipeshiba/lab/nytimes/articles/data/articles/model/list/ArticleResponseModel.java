package com.felipeshiba.lab.nytimes.articles.data.articles.model.list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponseModel {

    private String title;

    @SerializedName("published_date")
    private String pub_date;

    @SerializedName("abstract")
    private String summary;

    @SerializedName("multimedia")
    private List<MultimediaResponseModel> picture;

    public ArticleResponseModel() {
    }

    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pub_date;
    }

    public String getSummary() {
        return summary;
    }

    public String getPicture() {
        return picture.size() > 0 ? picture.get(0).getUrl() : null;
    }
}
