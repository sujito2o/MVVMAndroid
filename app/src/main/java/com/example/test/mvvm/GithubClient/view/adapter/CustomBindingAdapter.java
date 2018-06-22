package com.example.test.mvvm.GithubClient.view.adapter;

import android.databinding.BindingAdapter;
import android.view.View;

public class CustomBindingAdapter {
    //BindingAdapter when defining to xml
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
