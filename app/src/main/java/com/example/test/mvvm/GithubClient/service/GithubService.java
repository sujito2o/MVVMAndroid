package com.example.test.mvvm.GithubClient.service;

import com.example.test.mvvm.GithubClient.model.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface GithubService {

    //Retrofit interface (manage API request)
    String HTTPS_API_GITHUB_URL = "https://api.github.com/";

    //List
    @GET("users/{user}/repos")
    Call<List<Project>> getProjectList(@Path("user") String user);

    //Details
    @GET("/repos/{user}/{reponame}")
    Call<Project> getProjectDetails(@Path("user") String user,@Path("reponame") String projectName);
}
