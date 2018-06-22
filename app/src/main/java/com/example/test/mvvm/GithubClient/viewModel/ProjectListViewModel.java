package com.example.test.mvvm.GithubClient.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.test.mvvm.MyApp;
import com.example.test.mvvm.GithubClient.model.Project;
import com.example.test.mvvm.GithubClient.service.ProjectRepository;

import java.util.List;

import javax.inject.Inject;

/**
 *
 * https : //developer.android.com/topic /libraries/architecture/livedata.html#transformations_of_livedata
 */
public class ProjectListViewModel extends AndroidViewModel {

    @Inject
    ProjectRepository projectRepository;
    //LiveData to be monitored
    private final LiveData<List<Project>> projectListObservable;

    public ProjectListViewModel(Application application){
        super(application);
        ((MyApp) getApplication()).getPersistentComponent().inject(this);

        //Retrieve an instance from the Repository and call getProjectList to add it to the LiveData object。
        //If you need to do this, you can simply do this with the Transformations class。
        projectListObservable = projectRepository.getProjectList("sujito2o");
    }

    //Publish the LiveData acquired by the constructor so that the UI can be observed
    public LiveData<List<Project>> getProjectListObservable() {
        return projectListObservable;
    }

    public void myCustomMethod()
    {



    }
}
