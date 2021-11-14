package com.itba.workin;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.itba.workin.backend.models.Credentials;
import com.itba.workin.backend.models.Error;
import com.itba.workin.databinding.ActivityMainBinding;
import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.Status;

public class MainActivity extends AppBarActivity {

    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View root = mainBinding.getRoot();
        setContentView(root);

        // TODO sacar de aca va en login
        App app = (App) getApplication();
        Credentials credentials = new Credentials("johndoe", "1234567890");
        app.getUserRepository().login(credentials).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                app.getPreferences().setAuthToken(r.getData().getToken());
                Log.d("UI", "Conectado a api"); //TODO remove
            } else {
                defaultResourceHandler(r);
            }
        });

        ToolbarMainBinding toolbarBinding = ToolbarMainBinding.bind(root);
        toolbarBinding.toolbar.inflateMenu(R.menu.app_bar_menu);
        setSupportActionBar(toolbarBinding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(mainBinding.navView, navController);
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