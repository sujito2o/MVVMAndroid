package com.example.test.mvvm.GithubClient.service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.example.test.mvvm.GithubClient.model.Project;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Data provider for ViewModel
 *Wrap response in LiveData Object
 */
public class ProjectRepository {

    //Retrofit interface

   private GithubService githubService;


    @Inject
    public ProjectRepository(GithubService githubService) {
   this.githubService = githubService;
    }



    //Request the API and return the response in LiveData (list)
    public LiveData<List<Project>> getProjectList(String userId) {
        final MutableLiveData<List<Project>> data = new MutableLiveData<>();

        //Set in MutableLiveData of type List as (as implemented by yourself) with asynchronous request -> Callback in Retrofit
        githubService.getProjectList(userId).enqueue(new Callback<List<Project>>(){
            @Override
            public void onResponse(Call<List<Project>> call,@Nullable Response<List<Project>> response){
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t){
                //TODO: null代入良くない + エラー処理
                data.setValue(null);

            }
        });

        return data;
    }

    //Request the API and return the response in LiveData (Details)
    public LiveData<Project> getProjectDetails(String userID,String projectName) {
        final MutableLiveData<Project> data = new MutableLiveData<>();

        githubService.getProjectDetails(userID,projectName).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                simulateDelay();
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                //TODO :null assignment not good for error handling
                data.setValue(null);
            }
        });
        return data;
    }


    //
    //NOTE: When infinite loop is executed with parallel processing of multithread, load of CPU becomes large, resource is consumed, which causes heavy PC operation such as memory leak.
    //Therefore, you can use the sleep method during multithread processing to pause processing and reduce the CPU load.
    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
