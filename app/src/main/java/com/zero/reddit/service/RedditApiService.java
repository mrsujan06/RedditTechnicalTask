package com.zero.reddit.service;

import com.zero.reddit.model.comments.CommentResponse;
import com.zero.reddit.model.posts.RedditResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedditApiService {

    @GET("/.json?")
    Observable<RedditResponse> getPosts(@Query("after") String after);

    @GET("/r/{subreddit}/comments/{id}/.json?")
    Observable<List<CommentResponse>> getComments(@Path("subreddit") String subreddit, @Path("id") String id);
}
