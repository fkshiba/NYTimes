package com.felipeshiba.lab.nytimes.articles.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.felipeshiba.lab.nytimes.articles.R;
import com.felipeshiba.lab.nytimes.articles.data.articles.ArticleRepository;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ArticlesActivity extends AppCompatActivity {

    private ArticlesViewModel viewModel;
    private CompositeDisposable disposableBag = new CompositeDisposable();
    private ArticleListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        viewModel = new ArticlesViewModel(getApplication());

        RecyclerView mainList = findViewById(R.id.list_main);
        adapter = new ArticleListAdapter();
        mainList.setAdapter(adapter);
        mainList.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Disposable disposable = viewModel
                .fetchTopArticles()
                .subscribe(adapter::setArticles);
        disposableBag.add(disposable);
    }

    @Override
    protected void onStop() {
        super.onStop();

        disposableBag.clear();
        viewModel.disposeAll();
    }
}
