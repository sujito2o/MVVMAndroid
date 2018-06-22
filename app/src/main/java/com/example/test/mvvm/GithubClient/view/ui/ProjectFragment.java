package com.example.test.mvvm.GithubClient.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.mvvm.R;
import com.example.test.mvvm.databinding.FragmentProjectDetailsBinding;
import com.example.test.mvvm.GithubClient.model.Project;
import com.example.test.mvvm.GithubClient.viewModel.ProjectViewModel;


public class ProjectFragment extends Fragment {

    private static final String KEY_PROJECT_ID = "project_id";
    private FragmentProjectDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //Inflate the layout with DataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false);

        // Create and set the adapter for the RecyclerView.
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //DI
        ProjectViewModel.Factory factory = new ProjectViewModel.Factory(
                getActivity().getApplication(), getArguments().getString(KEY_PROJECT_ID)
        );

        //Inflect the project_id as a key to obtain the ViewModel instance
        final ProjectViewModel viewModel = ViewModelProviders.of(this, factory).get(ProjectViewModel.class);

        //set ViewModel
        binding.setProjectViewModel(viewModel);

        //app:visibleGone="@{isLoading}"true
        binding.setIsLoading(true);

        //Start data monitoring -> Monitor differences and tell ViewModel
        observeViewModel(viewModel);

    }

    //Methods for monitoring Model data
    public void observeViewModel(final ProjectViewModel viewModel){
       viewModel.getObservableProject().observe(this, new Observer<Project>() {
           @Override
           public void onChanged(@Nullable Project project) {
               if (project != null){

                   binding.setIsLoading(false);

                   viewModel.setProject(project);
               }
           }
       });
    }

    //Pass id with fragment
    public static ProjectFragment forProject(String projectID) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();

        args.putString(KEY_PROJECT_ID, projectID);
        fragment.setArguments(args);

        return fragment;
    }

}
