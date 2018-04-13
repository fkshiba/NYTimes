package com.felipeshiba.lab.nytimes.articles.list;

public class Article {
    private String title;
    private String pubDate;
    private String summary;
    private String picture;

    public Article(String title, String pubDate, String summary, String picture) {
        this.title = title;
        this.pubDate = pubDate;
        this.summary = summary;
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
