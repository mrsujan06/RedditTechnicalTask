package com.zero.reddit;

import android.app.Application;

import com.zero.reddit.dagger.AppComponent;
import com.zero.reddit.dagger.DaggerAppComponent;

public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
