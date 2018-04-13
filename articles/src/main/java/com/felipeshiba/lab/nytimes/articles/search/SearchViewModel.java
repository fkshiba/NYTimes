package com.felipeshiba.lab.nytimes.articles.search;

import android.app.Application;
import android.support.annotation.NonNull;

import com.felipeshiba.lab.nytimes.articles.BaseViewModel;
import com.felipeshiba.lab.nytimes.articles.data.articles.ArticleRepository;
import com.felipeshiba.lab.nytimes.articles.data.articles.model.list.ArticleResponseModel;
import com.felipeshiba.lab.nytimes.articles.data.articles.model.search.SearchArticlesResponseModel;
import com.felipeshiba.lab.nytimes.articles.list.Article;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.Collections;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends BaseViewModel {

    private ArticleRepository repository = new ArticleRepository(getApplication());
    private BehaviorRelay<List<Article>> articlesRelay =
            BehaviorRelay.createDefault(Collections.emptyList());

    public SearchViewModel(@NonNull Application application) {
        super(application);

        Disposable disposable = repository.searchArticles("", 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Flowable::fromIterable)
                .map(responseToArticle())
                .toList()
                .subscribe(articlesRelay);
        disposableBag.add(disposable);
    }

    public Flowable<List<Article>> searchArticles() {
        return articlesRelay.toFlowable(BackpressureStrategy.DROP);
    }

    private Function<SearchArticlesResponseModel, Article> responseToArticle() {
        return response -> new Article(response.getTitle(), response.getPubDate(),
                response.getSummary(), response.getPicture());
    }
}
