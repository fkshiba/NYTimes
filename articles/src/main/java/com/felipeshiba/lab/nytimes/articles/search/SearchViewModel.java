package com.felipeshiba.lab.nytimes.articles.search;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.felipeshiba.lab.nytimes.articles.BaseViewModel;
import com.felipeshiba.lab.nytimes.articles.data.list.TopArticlesRepository;
import com.felipeshiba.lab.nytimes.articles.data.search.SearchArticlesRepository;
import com.felipeshiba.lab.nytimes.articles.data.search.SearchArticlesResponseModel;
import com.felipeshiba.lab.nytimes.articles.list.Article;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class SearchViewModel extends BaseViewModel {

    private static final String TAG = SearchViewModel.class.getSimpleName();

    private BehaviorRelay<String> query = BehaviorRelay.createDefault("");
    private BehaviorRelay<Integer> page = BehaviorRelay.createDefault(0);
    private SearchArticlesRepository repository = new SearchArticlesRepository(getApplication());
    private BehaviorRelay<List<Article>> articlesSubject = BehaviorRelay
            .createDefault(Collections.emptyList());


    public SearchViewModel(@NonNull Application application) {
        super(application);

        Disposable disposable = BehaviorRelay.combineLatest(query, page, Pair::new)
                .toFlowable(BackpressureStrategy.LATEST)
                .debounce(1, TimeUnit.SECONDS)
                .subscribe(callApi());
        disposeBag.add(disposable);
    }

    @NonNull
    private Consumer<Pair<String, Integer>> callApi() {
        return queryPagePair ->
                repository.searchArticles(queryPagePair.first, queryPagePair.second)
                        .flatMap(Flowable::fromIterable)
                        .map(responseToArticle())
                        .doOnNext(next -> Log.d(TAG, "callApi: " + next.getTitle()))
                        .toList()
                        .subscribe(articlesSubject);
    }

    private Function<SearchArticlesResponseModel, Article> responseToArticle() {
        return response -> new Article(response.getTitle(), response.getPubDate(),
                response.getSummary(), response.getPicture());
    }

    public void setQuery(String query) {
        this.query.accept(query);
    }

    public void setPage(int page) {
        this.page.accept(page);
    }

    public Flowable<List<Article>> searchArticles() {
        return articlesSubject.toFlowable(BackpressureStrategy.LATEST);
    }
}
