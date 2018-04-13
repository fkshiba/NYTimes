package com.felipeshiba.lab.nytimes.articles;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends AndroidViewModel {
    protected CompositeDisposable disposableBag = new CompositeDisposable();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public void disposeAll() {
        disposableBag.clear();
    }
}
