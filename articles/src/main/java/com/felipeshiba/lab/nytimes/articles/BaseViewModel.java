package com.felipeshiba.lab.nytimes.articles;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {
    protected CompositeDisposable disposeBag = new CompositeDisposable();

    public void disposeAll() {
        disposeBag.clear();
    }
}
