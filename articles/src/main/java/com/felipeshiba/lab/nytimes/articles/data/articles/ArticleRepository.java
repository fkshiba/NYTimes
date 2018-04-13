package com.felipeshiba.lab.nytimes.articles.data.articles;

import android.content.Context;

import com.felipeshiba.lab.nytimes.articles.data.BaseRepository;
import com.felipeshiba.lab.nytimes.articles.data.articles.model.list.ArticleResponseModel;
import com.felipeshiba.lab.nytimes.articles.data.articles.model.list.ResultsResponseModel;
import com.felipeshiba.lab.nytimes.articles.data.articles.model.search.SearchArticlesResponseModel;
import com.felipeshiba.lab.nytimes.articles.data.articles.model.search.SearchResultsResponseModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ArticleRepository extends BaseRepository<ArticleRepository.ArticleService> {

    public ArticleRepository(Context context) {
        super(context);
    }

    interface ArticleService {
        @GET("topstories/v2/home.json")
        Flowable<ResultsResponseModel> fetchTopArticles();

        @GET("search/v2/articlesearch.json?fl=abstract,headline,multimedia,pub_date")
        Flowable<SearchResultsResponseModel> searchArticles(@Query("q") String query,
                                                            @Query("page") int page);
    }

    @Override
    public Class<ArticleService> getEndpointClass() {
        return ArticleService.class;
    }

    public Flowable<List<ArticleResponseModel>> fetchTopArticles() {
        return get().fetchTopArticles()
                .map(ResultsResponseModel::getResults);
    }

    public Flowable<List<SearchArticlesResponseModel>> searchArticles(String query, int page) {
        return get().searchArticles(query, page)
                .map(searchResults -> searchResults.getResponse().getSearchArticles());
    }
}
