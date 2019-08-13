package com.zero.reddit.dagger;

import com.zero.reddit.ui.detail.DetailActivity;
import com.zero.reddit.ui.master.MasterActivity;

import dagger.Component;

@Component(modules = {RedditModule.class})
public interface AppComponent {

    void inject(MasterActivity masterActivity);

    void inject(DetailActivity detailActivity);

}
