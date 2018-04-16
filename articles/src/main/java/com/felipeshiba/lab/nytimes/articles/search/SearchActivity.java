package com.felipeshiba.lab.nytimes.articles.search;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.felipeshiba.lab.nytimes.articles.R;
import com.felipeshiba.lab.nytimes.articles.data.search.SearchArticlesRepository;
import com.felipeshiba.lab.nytimes.articles.view.EndlessScrollListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SearchActivity extends AppCompatActivity {

    private SearchView fieldSearch;
    private RecyclerView listSearch;
    private SearchViewModel viewModel;
    private SearchArticlesAdapter adapter;
    private CompositeDisposable disposeBag = new CompositeDisposable();

    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        configureViewModel();
        configureFieldSearch();
        configureListSearch();
    }

    private void configureViewModel() {
        viewModel = new SearchViewModel(new SearchArticlesRepository(this));
    }

    private void configureFieldSearch() {
        fieldSearch = findViewById(R.id.field_search);
        fieldSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.setQuery(newText);
                page = 0;
                viewModel.setPage(page);
                return true;
            }
        });
    }

    private void configureListSearch() {
        listSearch = findViewById(R.id.list_search);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        listSearch.setLayoutManager(linearLayoutManager);
        adapter = new SearchArticlesAdapter();
        listSearch.setAdapter(adapter);
        listSearch.addOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore() {
                viewModel.setPage(++page);
            }
        });
    }

    @Override
    protected void onStart() {
        Disposable queryDisposable = viewModel
                .fetchArticles()
                .subscribe(articles -> {
                    if (page > 0) {
                        adapter.addArticles(articles);
                    } else {
                        adapter.setArticles(articles);
                    }
                });
        disposeBag.add(queryDisposable);

        super.onStart();

        page = 0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposeBag.clear();
    }
}
