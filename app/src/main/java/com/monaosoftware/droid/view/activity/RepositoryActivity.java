package com.monaosoftware.droid.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.monaosoftware.droid.R;
import com.monaosoftware.droid.data.model.Repository;
import com.monaosoftware.droid.data.viewmodel.BaseViewModel;
import com.monaosoftware.droid.data.viewmodel.RepositoryViewModel;
import com.monaosoftware.droid.databinding.ActivityRepositoryBinding;


public class RepositoryActivity extends BaseActivity {

    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

    private ActivityRepositoryBinding binding;
    private RepositoryViewModel repositoryViewModel;

    public static Intent newIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, repository);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repository);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Repository repository = getIntent().getParcelableExtra(EXTRA_REPOSITORY);
        repositoryViewModel = new RepositoryViewModel(this, repository);
        binding.setViewModel(repositoryViewModel);

        //Currently there is no way of setting an activity title using data binding
        setTitle(repository.name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repositoryViewModel.destroy();
    }
}