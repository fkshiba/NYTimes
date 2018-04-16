package com.felipeshiba.lab.nytimes.articles.data.search;

import com.felipeshiba.lab.nytimes.articles.data.list.MultimediaResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchArticlesResponseModel {

    //abstract,headline,multimedia,pub_date

    @SerializedName("headline")
    private HeadlineResponseModel title;

    @SerializedName("abstract")
    private String summary;

    @SerializedName("pub_date")
    private String pubDate;

    @SerializedName("multimedia")
    private List<MultimediaResponseModel> picture;

    public SearchArticlesResponseModel(HeadlineResponseModel title, String summary, String pubDate, List<MultimediaResponseModel> picture) {
        this.title = title;
        this.summary = summary;
        this.pubDate = pubDate;
        this.picture = picture;
    }

    public SearchArticlesResponseModel() {
    }

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
        return picture.size() > 0 ? picture.get(0).getUrl() : null;
    }

}
