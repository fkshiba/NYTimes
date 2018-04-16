package com.felipeshiba.lab.nytimes.articles.list;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.felipeshiba.lab.nytimes.articles.R;
import com.felipeshiba.lab.nytimes.articles.data.list.TopArticlesRepository;
import com.felipeshiba.lab.nytimes.articles.data.search.SearchArticlesRepository;
import com.felipeshiba.lab.nytimes.articles.search.SearchActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ArticlesActivity extends AppCompatActivity {

    private ArticlesViewModel viewModel;
    private CompositeDisposable disposeBag = new CompositeDisposable();
    private ArticleListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        viewModel = new ArticlesViewModel(new TopArticlesRepository(this));

        RecyclerView mainList = findViewById(R.id.list_main);
        adapter = new ArticleListAdapter();
        mainList.setAdapter(adapter);
        mainList.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Disposable disposable = viewModel.fetchTopArticles().subscribe(adapter::setArticles);
        disposeBag.add(disposable);
    }

    @Override
    protected void onStop() {
        super.onStop();

        disposeBag.clear();
        viewModel.disposeAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            startActivity(new Intent(this, SearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
