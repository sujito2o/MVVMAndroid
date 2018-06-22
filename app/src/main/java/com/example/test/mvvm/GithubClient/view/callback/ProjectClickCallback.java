package com.example.test.mvvm.GithubClient.view.callback;


import com.example.test.mvvm.GithubClient.model.Project;

/**
 * Interface to communicate click operation
 * @link onClick(Project project)
 * Move to detail screen
 */
public interface ProjectClickCallback {
    void onClick(Project project);
}
