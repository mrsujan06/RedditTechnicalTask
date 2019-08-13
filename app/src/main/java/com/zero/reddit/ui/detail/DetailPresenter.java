package com.zero.reddit.ui.detail;

import com.zero.reddit.interactor.RedditServiceInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

class DetailPresenter {

    private RedditServiceInteractor mInteractor;
    private DetailView mView;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    @Inject
    DetailPresenter(RedditServiceInteractor mInteractor) {
        this.mInteractor = mInteractor;
    }

    void bind(DetailView view) {
        this.mView = view;
    }

    void unbind() {
        this.mView = null;
    }

    /**
     * Gets the CommentResponse from the Api and Pass it to the view
     **/
    void getComments(String subreddit, String id) {
        mDisposable.addAll(mInteractor.getComments(subreddit, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> mView.onFetchDataSuccess(response.get(1)),
                        throwable -> mView.onFetchDataError(throwable.getMessage())));
        mView.onFetchDataProgress();
    }

    void dispose() {
        mDisposable.clear();
    }
}
