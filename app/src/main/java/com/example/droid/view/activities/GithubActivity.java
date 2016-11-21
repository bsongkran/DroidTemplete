package com.example.droid.view.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;

import com.example.droid.R;
import com.example.droid.model.domains.github.Repository;


import com.example.droid.model.viewmodels.github.GithubViewModel;
import com.example.droid.databinding.ActivityGithubBinding;
import com.example.droid.view.adapters.RepositoryAdapter;

import java.util.List;


public class GithubActivity extends BaseActivity implements GithubViewModel.DataListener {

    private ActivityGithubBinding binding;
    private GithubViewModel githubViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getApplicationComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_github);
        githubViewModel = new GithubViewModel(this, this);



        binding.setViewModel(githubViewModel);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setupRecyclerView(binding.reposRecyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        githubViewModel.destroy();
    }

    @Override
    public void onRepositoriesChanged(List<Repository> repositories) {
        RepositoryAdapter adapter =
                (RepositoryAdapter) binding.reposRecyclerView.getAdapter();
        adapter.setRepositories(repositories);
        adapter.notifyDataSetChanged();
        hideSoftKeyboard();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        RepositoryAdapter adapter = new RepositoryAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editTextUsername.getWindowToken(), 0);
    }


}
