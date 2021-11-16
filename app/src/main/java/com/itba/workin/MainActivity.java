package com.itba.workin;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.itba.workin.backend.models.Credentials;
import com.itba.workin.backend.models.Error;
import com.itba.workin.databinding.ActivityMainBinding;
import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.repository.Status;
import com.itba.workin.ui.community.CommunityViewModel;
import com.itba.workin.ui.favorite.FavoriteViewModel;
import com.itba.workin.ui.myRoutines.MyRoutinesViewModel;
import com.itba.workin.viewmodel.RepositoryViewModelFactory;
import com.itba.workin.ui.login.LoginActivity;

public class MainActivity extends AppBarActivity {

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

    public void goToLogin(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
  
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem shareItem = menu.findItem(R.id.app_bar_share);
        shareItem.setVisible(false);
        MenuItem closeItem = menu.findItem(R.id.app_bar_close);
        closeItem.setVisible(false);
        MenuItem ProfileItem = menu.findItem(R.id.app_bar_profile);
        ProfileItem.setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }

    private void defaultResourceHandler(Resource<?> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                break;
            case ERROR:
                Error error = resource.getError();
                String message = "Error: " + error.getDescription() + error.getCode();
                Log.d("UI", message);
                break;
        }
    }
}