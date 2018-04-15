package com.felipeshiba.lab.nytimes.articles.data.list;

import android.content.Context;

import com.felipeshiba.lab.nytimes.articles.data.BaseRepository;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public class TopArticlesRepository extends BaseRepository<TopArticlesRepository.TopArticlesService> {

    public TopArticlesRepository(Context context) {
        super(context);
    }

    interface TopArticlesService {
        @GET("topstories/v2/home.json")
        Flowable<ResultsResponseModel> fetchTopArticles();
    }

    @Override
    public Class<TopArticlesService> getEndpointClass() {
        return TopArticlesService.class;
    }

    public Flowable<List<ArticleResponseModel>> fetchTopArticles() {
        return get().fetchTopArticles()
                .map(ResultsResponseModel::getResults);
    }
}
