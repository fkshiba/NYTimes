package com.felipeshiba.lab.nytimes.articles.data.articles.model.list;

import java.util.List;

public class ResultsResponseModel {
    private List<ArticleResponseModel> results;

    public List<ArticleResponseModel> getResults() {
        return results.subList(0, 9);
    }
}
