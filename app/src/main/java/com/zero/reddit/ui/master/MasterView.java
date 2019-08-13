package com.zero.reddit.ui.master;

import com.zero.reddit.model.posts.RedditResponse;

public interface MasterView {

    void onFetchDataProgress();

    void onFetchDataSuccess(RedditResponse response);

    void onFetchDataError(String error);

    void showLoading();

    void hideLoading();

}
