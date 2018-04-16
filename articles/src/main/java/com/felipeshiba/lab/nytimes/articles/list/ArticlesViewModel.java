package com.felipeshiba.lab.nytimes.articles.list;

import com.felipeshiba.lab.nytimes.articles.BaseViewModel;
import com.felipeshiba.lab.nytimes.articles.data.list.ArticleResponseModel;
import com.felipeshiba.lab.nytimes.articles.data.list.TopArticlesRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ArticlesViewModel extends BaseViewModel {

    private TopArticlesRepository repository;

    public ArticlesViewModel(TopArticlesRepository repository) {
        this.repository = repository;
    }

    public Single<List<Article>> fetchTopArticles() {
        return repository.fetchTopArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Flowable::fromIterable)
                .map(responseToArticle())
                .toList();
    }

    private Function<ArticleResponseModel, Article> responseToArticle() {
        return response -> new Article(response.getTitle(), response.getPubDate(),
                response.getSummary(), response.getPicture());
    }
}
