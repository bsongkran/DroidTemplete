package com.example.droid.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.droid.MainApplication;
import com.example.droid.dal.IAppUserRepository;

import com.example.droid.databinding.ActivityMainBinding;
import com.example.droid.view.fragment.SettingsFragment;
import com.example.droid.viewmodel.main.MainViewModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.example.droid.R;

import com.example.droid.view.fragment.GalleryFragment;
import com.example.droid.view.fragment.MapFragment;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainViewModel.DataListener {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private long lastBackPressTime = 0;
    private Toast mToastWarningMessage;


    @Inject
    IAppUserRepository appUserRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inject
        MainApplication.get(getApplicationContext()).getComponent().inject(this);

        //Binding data
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(getApplicationContext(), this , appUserRepository );
        binding.setMainViewModel(mainViewModel);

        setupToolbar();
        setupNavigationDrawer();
        setupSearchView();
        initializeView();
    }

    private void setupToolbar() {
        binding.appBarMainDrawer.toolbar.setTitle("");
        setSupportActionBar(binding.appBarMainDrawer.toolbar);
    }

    private void setupNavigationDrawer() {
      //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarMainDrawer.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);
    }

    private void setupSearchView() {
        binding.appBarMainDrawer.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()){
                    Fragment fragment = getCurrentFragment();
                    if(fragment instanceof MapFragment){
                        ((MapFragment)fragment).searchMapLocation(query);
                    }else if(fragment instanceof GalleryFragment){

                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.appBarMainDrawer.searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                showToolbar(false);
            }

            @Override
            public void onSearchViewClosed() {
                showToolbar(true);
            }
        });
    }

    private void initializeView() {
        //Default Screen
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, SettingsFragment.newInstance()).commit();

    }

    private void showToolbar(boolean isShown) {
        if (isShown) {
            binding.appBarMainDrawer.toolbar.setVisibility(View.VISIBLE);
            binding.appBarMainDrawer.searchView.setVisibility(View.GONE);
        } else {
            binding.appBarMainDrawer.toolbar.setVisibility(View.GONE);
            binding.appBarMainDrawer.searchView.setVisibility(View.VISIBLE);
        }
    }

    private Fragment getCurrentFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_main);
        return fragment;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        binding.appBarMainDrawer.searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (this.lastBackPressTime < System.currentTimeMillis() - 2000) {
                mToastWarningMessage = Toast.makeText(this, this.getString(R.string.warning_message_close_the_app), Toast.LENGTH_LONG);
                mToastWarningMessage.show();
                this.lastBackPressTime = System.currentTimeMillis();
            } else {
                if (mToastWarningMessage != null) {
                    mToastWarningMessage.cancel();
                    moveTaskToBack(true);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.nav_map:
                binding.appBarMainDrawer.toolbarTitle.setText(R.string.toolbar_title_map);
                fragment = MapFragment.newInstance();
                break;
            case R.id.nav_gallery:
                binding.appBarMainDrawer.toolbarTitle.setText(R.string.toolbar_title_gallery);
                fragment = GalleryFragment.newInstance();
                break;
            case R.id.nav_slideshow:
                binding.appBarMainDrawer.toolbarTitle.setText(R.string.toolbar_title_slideshow);
                fragment = GalleryFragment.newInstance();
                break;
            case R.id.nav_github:
                binding.appBarMainDrawer.toolbarTitle.setText(R.string.toolbar_title_gallery);
                openGithubScreen();
                break;
            case R.id.nav_settings:
                binding.appBarMainDrawer.toolbarTitle.setText(R.string.toolbar_title_settings);
                fragment = SettingsFragment.newInstance();
                break;
            default:
                binding.drawerLayout.closeDrawers();
                return false;
        }

        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_main, fragment)
                    .commit();
        }

        binding.drawerLayout.closeDrawers();
        return true;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.destroy();
    }

    @Override
    public void onDataChanged() {

    }

    private void openGithubScreen() {
        Intent intent = new Intent(this, GithubActivity.class);
        startActivity(intent);
    }


}
