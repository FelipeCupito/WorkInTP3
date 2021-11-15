package com.itba.workin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.itba.workin.databinding.ActivityMainBinding;
import com.itba.workin.databinding.ToolbarMainBinding;

public class MainActivity extends AppBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View root = mainBinding.getRoot();
        setContentView(root);

        ToolbarMainBinding toolbarBinding = ToolbarMainBinding.bind(root);
        toolbarBinding.toolbar.inflateMenu(R.menu.app_bar_menu);
        setSupportActionBar(toolbarBinding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(mainBinding.navView, navController);

        mainBinding.button2.setOnClickListener(view -> {
            Intent intent = new Intent(this, RoutineDetailActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem shareItem = menu.findItem(R.id.app_bar_share);
        shareItem.setVisible(false);
        MenuItem closeItem = menu.findItem(R.id.app_bar_close);
        closeItem.setVisible(false);
        MenuItem ProfileItem = menu.findItem(R.id.app_bar_profile);
        ProfileItem.setVisible(true);
        MenuItem timerItem = menu.findItem(R.id.app_bar_clock);
        timerItem.setVisible(false);
        MenuItem listItem = menu.findItem(R.id.app_bar_list);
        listItem.setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }
}