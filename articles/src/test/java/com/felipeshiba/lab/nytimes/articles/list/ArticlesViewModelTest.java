package com.felipeshiba.lab.nytimes.articles.list;

import com.felipeshiba.lab.nytimes.articles.data.list.ArticleResponseModel;
import com.felipeshiba.lab.nytimes.articles.data.list.MultimediaResponseModel;
import com.felipeshiba.lab.nytimes.articles.data.list.TopArticlesRepository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ArticlesViewModelTest {

    @Mock
    TopArticlesRepository repository;

    ArticlesViewModel viewModel;
    public static final ArticleResponseModel ARTICLE_RESPONSE_MODEL = new ArticleResponseModel("title",
            "pub_date", "summary",
            Collections.singletonList(new MultimediaResponseModel("url")));
    public static final List<ArticleResponseModel> LIST = Collections.singletonList(ARTICLE_RESPONSE_MODEL);

    public static final Article ARTICLE = new Article("title",
            "pub_date", "summary", "url");

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(repository.fetchTopArticles()).thenReturn(Flowable.just(
                LIST
        ));

        viewModel = new ArticlesViewModel(repository);
    }

    @Test
    public void fetchTopArticles() {
        TestObserver<List<Article>> testObserver = viewModel.fetchTopArticles().test();
        verify(repository).fetchTopArticles();

        testObserver.assertComplete();
        testObserver.assertResult(Collections.singletonList(ARTICLE));
    }
}