package com.felipeshiba.lab.nytimes.articles.data.articles.model.search;

import java.util.List;

public class SearchDocsResponseModel {
    private List<SearchArticlesResponseModel> docs;

    public List<SearchArticlesResponseModel> getDocs() {
        return docs;
    }
}
