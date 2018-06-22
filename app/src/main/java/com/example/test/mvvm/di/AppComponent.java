package com.example.test.mvvm.di;

import com.example.test.mvvm.GithubClient.view.ui.MainActivity;
import com.example.test.mvvm.GithubClient.view.ui.ProjectListFragment;
import com.example.test.mvvm.GithubClient.viewModel.ProjectListViewModel;
import com.example.test.mvvm.GithubClient.viewModel.ProjectViewModel;

import javax.inject.Singleton;

import dagger.Component;

    @Singleton
    @Component(modules={AppModule.class, PersistentModule.class,ApiModule.class})
    public interface AppComponent {
        void inject(MainActivity activity);
        void inject(ProjectListFragment projectListFragment);


        void inject(ProjectListViewModel projectListViewModel);

        void inject(ProjectViewModel projectViewModel);
    }


