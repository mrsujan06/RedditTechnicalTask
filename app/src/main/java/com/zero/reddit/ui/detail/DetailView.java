package com.zero.reddit.ui.detail;

import com.zero.reddit.model.comments.CommentResponse;

public interface DetailView {

    void onFetchDataProgress();

    void onFetchDataSuccess(CommentResponse response);

    void onFetchDataError(String error);

    void showLoading();

    void hideLoading();

}
