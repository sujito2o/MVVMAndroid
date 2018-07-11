package com.example.test.mvvm.GithubClient.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test.mvvm.utils.ApiResponse;
import com.example.test.mvvm.MyApp;
import com.example.test.mvvm.R;
import com.example.test.mvvm.databinding.FragmentProjectListBinding;
import com.example.test.mvvm.GithubClient.model.Project;
import com.example.test.mvvm.GithubClient.view.adapter.ProjectAdapter;
import com.example.test.mvvm.GithubClient.view.callback.ProjectClickCallback;
import com.example.test.mvvm.GithubClient.viewModel.ProjectListViewModel;
import com.example.test.mvvm.utils.Resource;
import com.example.test.mvvm.utils.Status;

import java.util.List;

import javax.inject.Inject;

/**
 *
 */
public class ProjectListFragment extends Fragment {

    @Inject
    SharedPreferences sharedPreferences;

    public static final String TAG = "ProjectListFragment";
    //Set operation event in callback
    private final ProjectClickCallback projectClickCallback = new ProjectClickCallback() {
        @Override
        public void onClick(Project project) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(project);
            }

        }
    };
    private ProjectAdapter projectAdapter;
    private FragmentProjectListBinding binding;
    private ProjectListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //dataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false);

        //callback adapter
        projectAdapter = new ProjectAdapter(projectClickCallback);
        //adapter recyclerview
        binding.projectList.setAdapter(projectAdapter);
        //Loading
        binding.setIsLoading(true);
        //rootView

        //testing DI
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("key", "some text...");
        ed.commit();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ProjectListViewModel.class);

        observeViewModel(viewModel);
    }

    //observe
    private void observeViewModel(final ProjectListViewModel viewModel) {

        //Link LifecycleOwner so that it updates when data is updated, add observer within lifecycle
        //The observer receives the event only when it is in the STARTED or RESUMED state


        viewModel.getProjectListObservable().observe(this, listResource -> {
            if(listResource.status == Status.SUCCESS){
                binding.setIsLoading(false);
                projectAdapter.setProjectList(listResource.data);
                Toast.makeText(getActivity(),"Success with code "+ String.valueOf(listResource.statusCode), Toast.LENGTH_SHORT).show();
            }
            else if (listResource.status == Status.LOADING){

                //do nothing
            }
            else{

                Toast.makeText(getActivity(),"Error with code "+ String.valueOf(listResource.statusCode), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        ((MyApp) context.getApplicationContext()).getPersistentComponent().inject(this);

        super.onAttach(context);


    }
}
