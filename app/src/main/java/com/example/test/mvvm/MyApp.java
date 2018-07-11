package com.example.test.mvvm;

import android.app.Application;

import com.example.test.mvvm.di.ApiModule;
import com.example.test.mvvm.di.AppComponent;
import com.example.test.mvvm.di.AppModule;
import com.example.test.mvvm.di.DaggerAppComponent;
import com.example.test.mvvm.di.PersistentModule;

public class  MyApp extends Application {
    private AppComponent mPersistentComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mPersistentComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .persistentModule(new PersistentModule())
                .apiModule(new ApiModule("https://api.github.com/"))
                .build();

    }

    public AppComponent getPersistentComponent() {
        return mPersistentComponent;
    }
}