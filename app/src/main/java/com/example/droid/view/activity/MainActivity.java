package com.example.droid.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.droid.R;

import com.example.droid.databinding.ActivityMainBinding;
import com.example.droid.data.viewmodel.BaseViewModel;
import com.example.droid.data.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity implements BaseViewModel , MainViewModel.DataListener  {


    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this, this);
        binding.setViewModel(mainViewModel);
        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onDataChanged() {

    }
}
