package com.felipeshiba.lab.nytimes.articles.search;

import com.felipeshiba.lab.nytimes.articles.data.list.MultimediaResponseModel;
import com.felipeshiba.lab.nytimes.articles.data.search.HeadlineResponseModel;
import com.felipeshiba.lab.nytimes.articles.data.search.SearchArticlesRepository;
import com.felipeshiba.lab.nytimes.articles.data.search.SearchArticlesResponseModel;
import com.felipeshiba.lab.nytimes.articles.list.Article;

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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchViewModelTest {

    @Mock
    SearchArticlesRepository repository;
    private SearchViewModel viewModel;

    private static final SearchArticlesResponseModel ARTICLE_RESPONSE_MODEL = new SearchArticlesResponseModel(
            new HeadlineResponseModel("title"),
            "pub_date",
            "summary",
            Collections.singletonList(new MultimediaResponseModel("url")));

    private static final List<SearchArticlesResponseModel> LIST = Collections.singletonList(ARTICLE_RESPONSE_MODEL);

    private static final Article ARTICLE = new Article("title",
            "pub_date", "summary", "url");

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(repository.searchArticles(anyString(), anyInt())).thenReturn(Flowable.just(
                LIST
        ));

        viewModel = new SearchViewModel(repository);
    }

    @Test
    public void fetchArticles() {
        TestObserver<List<Article>> testObserver = viewModel.fetchArticles().test();

        viewModel.setPage(1);
        viewModel.setQuery("lalala");
        viewModel.completeSubjects();

        verify(repository).searchArticles(anyString(), anyInt());

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertResult(Collections.singletonList(ARTICLE));
    }
}