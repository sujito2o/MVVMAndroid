package com.example.test.mvvm.GithubClient.service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.test.mvvm.GithubClient.model.Project;
import com.example.test.mvvm.utils.AbsentLiveData;
import com.example.test.mvvm.utils.ApiResponse;
import com.example.test.mvvm.utils.AppExecutors;
import com.example.test.mvvm.utils.NetworkBoundResource;
import com.example.test.mvvm.utils.RateLimiter;
import com.example.test.mvvm.utils.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Data provider for ViewModel
 * Wrap response in LiveData Object
 */
public class ProjectRepository {

    //Retrofit interface

    private final GithubService githubService;

    private final AppExecutors appExecutors;

    private RateLimiter<String> repoListRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    @Inject
    public ProjectRepository(GithubService githubService, AppExecutors appExecutors) {
        this.githubService = githubService;
        this.appExecutors = appExecutors;
    }


    public LiveData<Resource<List<Project>>> getProjectList(String userId) {
        return new NetworkBoundResource<List<Project>, List<Project>>(appExecutors) {

            // Temp ResultType
            private List<Project> tempresults;

            @Override
            protected void saveCallResult(@NonNull List<Project> item) {

                // if you don't care about order
                tempresults = item;
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Project> data) {
                // always fetch.
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Project>> loadFromDb() {
                if (tempresults == null) {
                    return AbsentLiveData.create();
                } else {
                    return new LiveData<List<Project>>() {
                        @Override
                        protected void onActive() {
                            super.onActive();
                            setValue(tempresults);
                        }
                    };
                }
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Project>>> createCall() {
                return githubService.getProjectList(userId);
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(userId);
            }
        }.asLiveData();

    }





    //Request the API and return the response in LiveData (Details)
    public LiveData<Project> getProjectDetails(String userID, String projectName) {
        final MutableLiveData<Project> data = new MutableLiveData<>();
//
//        githubService.getProjectDetails(userID,projectName).enqueue(new Callback<Project>() {
//            @Override
//            public void onResponse(Call<Project> call, Response<Project> response) {
//                simulateDelay();
//                data.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<Project> call, Throwable t) {
//                //TODO :null assignment not good for error handling
//                data.setValue(null);
//            }
//        });
        return data;
    }


    //
    //NOTE: When infinite loop is executed with parallel processing of multithreaded, load of CPU becomes large, resource is consumed, which causes heavy PC operation such as memory leak.
    //Therefore, you can use the sleep method during multithreaded processing to pause processing and reduce the CPU load.
    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
