package com.example.test.mvvm.GithubClient.view.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.test.mvvm.MyApp;
import com.example.test.mvvm.R;
import com.example.test.mvvm.GithubClient.model.Project;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    Context context;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApp) getApplication()).getPersistentComponent().inject(this);

        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("key", "some text...");
        ed.commit();
        if (savedInstanceState == null) {
            //Fragment of project list
            ProjectListFragment fragment = new ProjectListFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, ProjectListFragment.TAG)
                    .commit();
        }
    }

    //Transition to detail screen
    public void show(Project project) {
        ProjectFragment projectFragment = ProjectFragment.forProject(project.name);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("project")
                .replace(R.id.fragment_container, projectFragment, null)
                .commit();
    }
}
