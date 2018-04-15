package com.felipeshiba.lab.nytimes.articles.list;

import com.felipeshiba.lab.nytimes.articles.data.list.ArticleResponseModel;
import com.felipeshiba.lab.nytimes.articles.data.list.TopArticlesRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import io.reactivex.Flowable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ArticlesViewModelTest {

    @Mock
    TopArticlesRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(repository.fetchTopArticles()).thenReturn(Flowable.just(
                Collections.singletonList(new ArticleResponseModel())
        ));
    }

    @Test
    public void fetchTopArticles() {
    }
}