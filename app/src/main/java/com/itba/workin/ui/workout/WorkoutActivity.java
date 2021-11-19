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
import androidx.navigation.ui.NavigationUI;

import com.itba.workin.App;
import com.itba.workin.R;
import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.databinding.WorkoutActivityBinding;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.viewmodel.RepositoryViewModelFactory;

public class WorkoutActivity extends AppCompatActivity {

    NavController navController;
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

        App app = (App) getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutinesRepository.class, app.getRoutinesRepository());
        cycleViewModel = new ViewModelProvider(this, viewModelFactory).get(CycleViewModel.class);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        if (id == -1) {
            // TODO check
        }
        cycleViewModel.setRoutineId(id);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
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
