package com.itba.workin.ui.main;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.itba.workin.App;
import com.itba.workin.R;
import com.itba.workin.databinding.ActivityMainBinding;
import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.repository.Status;
import com.itba.workin.ui.login.LoginActivity;
import com.itba.workin.ui.main.fragments.community.CommunityViewModel;
import com.itba.workin.ui.main.fragments.favorite.FavoriteViewModel;
import com.itba.workin.ui.main.fragments.myRoutines.MyRoutinesViewModel;
import com.itba.workin.viewmodel.RepositoryViewModelFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    App app;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("login",MODE_PRIVATE);
        if(!sp.getBoolean("logged",false)){
            goToLogin();
        }

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View root = mainBinding.getRoot();
        setContentView(root);
        app = (App) getApplication();

        ToolbarMainBinding toolbarBinding = ToolbarMainBinding.bind(root);
        toolbarBinding.toolbar.inflateMenu(R.menu.app_bar_menu);
        setSupportActionBar(toolbarBinding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(mainBinding.navView, navController);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resetViewModels();
    }

    private void resetViewModels() {
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutinesRepository.class, app.getRoutinesRepository());
        FavoriteViewModel favoriteViewModel = new ViewModelProvider(this,viewModelFactory).get(FavoriteViewModel.class);
        viewModelFactory = new RepositoryViewModelFactory<>(RoutinesRepository.class, app.getRoutinesRepository());
        CommunityViewModel communityViewModel = new ViewModelProvider(this,viewModelFactory).get(CommunityViewModel.class);
        viewModelFactory = new RepositoryViewModelFactory<>(RoutinesRepository.class, app.getRoutinesRepository());
        MyRoutinesViewModel myRoutinesViewModel = new ViewModelProvider(this,viewModelFactory).get(MyRoutinesViewModel.class);

        favoriteViewModel.restart();
        communityViewModel.restart();
        myRoutinesViewModel.restart();
    }

    public void goToLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem ProfileItem = menu.findItem(R.id.app_bar_profile);
        ProfileItem.setVisible(true);
        MenuItem sortItem = menu.findItem(R.id.app_bar_sort);
        sortItem.setVisible(true);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchItem.setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out_option) {
            logOut();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void logOut(){
        app.getUserRepository().logout().observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                app.getPreferences().setAuthToken(null);
                sp = getSharedPreferences("login", MODE_PRIVATE);
                sp.edit().putBoolean("logged",false).apply();
                goToLogin();
            } else {
                Resource.defaultResourceHandler(r);
            }
        });
    }
}