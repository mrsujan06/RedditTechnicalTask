package com.zero.reddit.interactor;

import com.zero.reddit.model.comments.CommentResponse;
import com.zero.reddit.model.posts.RedditResponse;
import com.zero.reddit.service.RedditApiService;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zero.reddit.constants.Constants.BASE_URL;

public class RedditServiceInteractorImp implements RedditServiceInteractor {

    private RedditApiService mService;

    public RedditServiceInteractorImp() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(RedditApiService.class);

    }

    @Override
    public Observable<RedditResponse> getPosts(String after) {
        return mService.getPosts(after);
    }

    @Override
    public Observable<List<CommentResponse>> getComments(String subreddit, String id) {
        return mService.getComments(subreddit, id);
    }
}
