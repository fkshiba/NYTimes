package com.felipeshiba.lab.nytimes.articles.data.search;

import android.content.Context;

import com.felipeshiba.lab.nytimes.articles.data.BaseRepository;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class SearchArticlesRepository extends BaseRepository<SearchArticlesRepository.SearchArticlesService> {

    public SearchArticlesRepository(Context context) {
        super(context);
    }

    interface SearchArticlesService {
        @GET("search/v2/articlesearch.json?fl=abstract,headline,multimedia,pub_date")
        Flowable<SearchResultsResponseModel> searchArticles(@Query("q") String query,
                                                            @Query("page") int page);
    }

    @Override
    public Class<SearchArticlesService> getEndpointClass() {
        return SearchArticlesService.class;
    }

    public Flowable<List<SearchArticlesResponseModel>> searchArticles(String query, int page) {
        return get().searchArticles(query, page)
                .map(searchResults -> searchResults.getResponse().getDocs());
    }
}
