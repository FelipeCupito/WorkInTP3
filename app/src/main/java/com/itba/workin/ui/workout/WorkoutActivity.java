package com.itba.workin.ui.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.itba.workin.App;
import com.itba.workin.R;
import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.databinding.WorkoutActivityBinding;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.viewmodel.RepositoryViewModelFactory;

public class WorkoutActivity extends AppCompatActivity {

    NavController navController;
    MenuItem clockItem, listItem;
    WorkoutActivityBinding workoutActivitybinding;
    CycleViewModel cycleViewModel;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutActivitybinding = WorkoutActivityBinding.inflate(getLayoutInflater());
        View root = workoutActivitybinding.getRoot();
        setContentView(root);

        ToolbarMainBinding toolbarBinding = ToolbarMainBinding.bind(root);
        toolbarBinding.toolbar.inflateMenu(R.menu.app_bar_menu);
        setSupportActionBar(toolbarBinding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_workout);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        navController.navigate(R.id.workoutSimpleFragment);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        clockItem = menu.findItem(R.id.app_bar_clock);
        listItem = menu.findItem(R.id.app_bar_list);
        listItem.setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_clock) {
            listItem.setVisible(true);
            clockItem.setVisible(false);
            navController.navigate(R.id.action_workoutDetailedFragment_to_workoutSimpleFragment);
            return true;
        } else if (item.getItemId() == R.id.app_bar_list) {
            listItem.setVisible(false);
            clockItem.setVisible(true);
            navController.navigate(R.id.action_workoutSimpleFragment_to_workoutDetailedFragment);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // TODO dialog JUAN
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
