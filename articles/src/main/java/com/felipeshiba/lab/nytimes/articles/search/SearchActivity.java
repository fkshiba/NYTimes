package com.felipeshiba.lab.nytimes.articles.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.felipeshiba.lab.nytimes.articles.R;

public class SearchActivity extends AppCompatActivity {

    private SearchView fieldSearch;
    private RecyclerView listSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        fieldSearch = findViewById(R.id.field_search);


        listSearch = findViewById(R.id.list_search);
    }
}
