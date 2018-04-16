package com.felipeshiba.lab.nytimes.articles.search;

import com.felipeshiba.lab.nytimes.articles.BaseViewModel;
import com.felipeshiba.lab.nytimes.articles.data.search.SearchArticlesRepository;
import com.felipeshiba.lab.nytimes.articles.data.search.SearchArticlesResponseModel;
import com.felipeshiba.lab.nytimes.articles.list.Article;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class SearchViewModel extends BaseViewModel {

    private static final String TAG = SearchViewModel.class.getSimpleName();

    private BehaviorSubject<String> query = BehaviorSubject.createDefault("");
    private BehaviorSubject<Integer> page = BehaviorSubject.createDefault(0);
    private SearchArticlesRepository repository;

    public SearchViewModel(SearchArticlesRepository repository) {
        this.repository = repository;
    }

    public Observable<List<Article>> fetchArticles() {
        return BehaviorSubject.combineLatest(query, page, SearchRequestModel::new)
                .debounce(500, TimeUnit.MILLISECONDS)
                .doOnNext(System.out::println)
                .flatMap(callApi())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Function<SearchRequestModel, Observable<List<Article>>> callApi() {
        return request ->
                repository.searchArticles(request.getQuery(), request.getPage())
                        .flatMap(Flowable::fromIterable)
                        .map(responseToArticle())
                        .toList()
                        .toObservable();
    }

    private Function<SearchArticlesResponseModel, Article> responseToArticle() {
        return response -> new Article(response.getTitle(), response.getPubDate(),
                response.getSummary(), response.getPicture());
    }

    public void setQuery(String query) {
        this.query.onNext(query);
    }

    public void setPage(int page) {
        this.page.onNext(page);
    }

    public void completeSubjects() {
        this.query.onComplete();
        this.page.onComplete();
    }
}
