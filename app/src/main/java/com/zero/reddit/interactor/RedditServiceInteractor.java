package com.zero.reddit.interactor;

import com.zero.reddit.model.comments.CommentResponse;
import com.zero.reddit.model.posts.RedditResponse;

import java.util.List;

import io.reactivex.Observable;

public interface RedditServiceInteractor {

    Observable<RedditResponse> getPosts(String after);

    Observable<List<CommentResponse>> getComments(String subreddit, String id);

}
