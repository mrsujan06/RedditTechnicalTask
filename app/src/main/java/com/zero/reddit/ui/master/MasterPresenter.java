package com.zero.reddit.ui.master;

import com.zero.reddit.interactor.RedditServiceInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

class MasterPresenter {

    private RedditServiceInteractor mInteractor;
    private MasterView mView;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    @Inject
    MasterPresenter(RedditServiceInteractor mInteractor) {
        this.mInteractor = mInteractor;
    }

    void bind(MasterView view) {
        this.mView = view;
    }

    void unbind() {
        this.mView = null;
    }

    /**
     * Gets the RedditResponse from the Api and Pass it to the view
     **/
    void getPosts(String after) {
        mDisposable.addAll(mInteractor.getPosts(after)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResponse ->
                                mView.onFetchDataSuccess(apiResponse),
                        throwable ->
                                mView.onFetchDataError(throwable.getMessage())));
        mView.onFetchDataProgress();
    }

    void dispose() {
        mDisposable.clear();
    }

}
