package com.itba.workin;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.itba.workin.databinding.RoutineDetailBinding;
import com.itba.workin.databinding.ToolbarMainBinding;

public class RoutineDetailActivity extends AppBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoutineDetailBinding routineDetailBinding = RoutineDetailBinding.inflate(getLayoutInflater());
        View root = routineDetailBinding.getRoot();
        setContentView(root);

        ToolbarMainBinding toolbarBinding = ToolbarMainBinding.bind(root);
        toolbarBinding.toolbar.inflateMenu(R.menu.app_bar_menu);
        setSupportActionBar(toolbarBinding.toolbar);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem shareItem = menu.findItem(R.id.app_bar_share);
        shareItem.setVisible(true);
        MenuItem closeItem = menu.findItem(R.id.app_bar_close);
        closeItem.setVisible(true);
        MenuItem ProfileItem = menu.findItem(R.id.app_bar_profile);
        ProfileItem.setVisible(false);
        MenuItem timerItem = menu.findItem(R.id.app_bar_clock);
        timerItem.setVisible(false);
        MenuItem listItem = menu.findItem(R.id.app_bar_list);
        listItem.setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }
}
