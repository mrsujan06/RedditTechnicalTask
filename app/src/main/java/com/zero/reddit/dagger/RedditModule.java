package com.zero.reddit.dagger;

import com.zero.reddit.interactor.RedditServiceInteractor;
import com.zero.reddit.interactor.RedditServiceInteractorImp;

import dagger.Module;
import dagger.Provides;

@Module
public class RedditModule {

    @Provides
    public RedditServiceInteractor getRedditService() {
        return new RedditServiceInteractorImp();
    }

}
