package com.itba.workin.ui.workout;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.itba.workin.R;
import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.databinding.WorkoutActivityBinding;
import com.itba.workin.ui.AppBarActivity;

public class WorkoutActivity extends AppBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WorkoutActivityBinding workoutActivity = WorkoutActivityBinding.inflate(getLayoutInflater());
        View root = workoutActivity.getRoot();
        setContentView(root);

        ToolbarMainBinding toolbarBinding = ToolbarMainBinding.bind(root);
        toolbarBinding.toolbar.inflateMenu(R.menu.app_bar_menu);
        setSupportActionBar(toolbarBinding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();


    }
}
