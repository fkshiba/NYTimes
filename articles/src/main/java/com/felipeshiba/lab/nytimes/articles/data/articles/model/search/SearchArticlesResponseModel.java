package com.felipeshiba.lab.nytimes.articles.data.articles.model.search;

import com.felipeshiba.lab.nytimes.articles.data.articles.model.list.MultimediaResponseModel;
import com.google.gson.annotations.SerializedName;

public class SearchArticlesResponseModel {

    //abstract,headline,multimedia,pub_date

    @SerializedName("headline")
    private HeadlineResponseModel title;

    @SerializedName("abstract")
    private String summary;

    @SerializedName("pub_date")
    private String pubDate;

    @SerializedName("multimedia")
    private MultimediaResponseModel picture;

    public String getTitle() {
        return title.getMain();
    }

    public String getSummary() {
        return summary;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getPicture() {
        return picture.getUrl();
    }
}
