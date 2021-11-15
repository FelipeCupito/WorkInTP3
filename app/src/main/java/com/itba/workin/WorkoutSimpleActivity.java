package com.itba.workin;

import android.os.Bundle;
import android.view.View;

import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.databinding.WorkoutSimpleBinding;

public class WorkoutSimpleActivity extends AppBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WorkoutSimpleBinding routineDetailBinding = WorkoutSimpleBinding.inflate(getLayoutInflater());
        View root = routineDetailBinding.getRoot();
        setContentView(root);

        ToolbarMainBinding toolbarBinding = ToolbarMainBinding.bind(root);
        toolbarBinding.toolbar.inflateMenu(R.menu.app_bar_menu);
        setSupportActionBar(toolbarBinding.toolbar);
    }
}
