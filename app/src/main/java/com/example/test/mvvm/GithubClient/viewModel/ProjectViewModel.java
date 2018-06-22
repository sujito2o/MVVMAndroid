package com.example.test.mvvm.GithubClient.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.test.mvvm.MyApp;
import com.example.test.mvvm.GithubClient.model.Project;
import com.example.test.mvvm.GithubClient.service.ProjectRepository;

import javax.inject.Inject;

public class ProjectViewModel extends AndroidViewModel {

    @Inject
    ProjectRepository projectRepository;
    private final LiveData<Project> projectObservable;
    private final String projectID;

    public ObservableField<Project> project = new ObservableField<>();

    public ProjectViewModel(@NonNull Application application, final String projectID) {
        super(application);
        ((MyApp) getApplication()).getPersistentComponent().inject(this);
        this.projectID = projectID;
        projectObservable = projectRepository.getProjectDetails("sujito2o",projectID);
    }

    //Getter
    public LiveData<Project> getObservableProject(){
        return projectObservable;
    }

    //Setter
    public void setProject(Project project){
        this.project.set(project);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;

        private final String projectID;

        public Factory(@NonNull Application application, String projectID) {
            this.application = application;
            this.projectID = projectID;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ProjectViewModel(application, projectID);
        }
    }
}
