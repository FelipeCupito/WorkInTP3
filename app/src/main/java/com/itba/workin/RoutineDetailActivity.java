package com.itba.workin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itba.workin.backend.models.Error;
import com.itba.workin.backend.models.FullRoutine;
import com.itba.workin.databinding.RoutineDetailBinding;
import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.models.MyRoutine;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.Status;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

        Intent intent = getIntent();
        MyRoutine routine = (MyRoutine) intent.getSerializableExtra("MyRoutine");
        if (routine == null) throw new IllegalStateException();
        setView(root,routine);
    }

    private void setView(View view, MyRoutine routine) {
        ImageView image= view.findViewById(R.id.image);
        TextView routineName = view.findViewById(R.id.routineName);
        TextView user = view.findViewById(R.id.user);
        TextView date = view.findViewById(R.id.date);
        TextView descriptionText = view.findViewById(R.id.descriptionText);
        TextView exerciseText = view.findViewById(R.id.exerciseText); // TODO
        // TODO category JUAN
        RatingBar rating = view.findViewById(R.id.rating);
        RatingBar difficulty = view.findViewById(R.id.difficulty);

        Picasso.get().load(routine.getRoutineUrl()).placeholder(image.getDrawable()).resize(300,200).into(image);
        routineName.setText(routine.getName());
        user.setText(routine.getUserName());
        date.setText(routine.getDate().toString());
        descriptionText.setText(routine.getDetail());
        rating.setRating(routine.getScore());
        difficulty.setRating(routine.getDifficulty());

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem shareItem = menu.findItem(R.id.app_bar_share);
        shareItem.setVisible(true);
        MenuItem closeItem = menu.findItem(R.id.app_bar_close);
        closeItem.setVisible(true);
        MenuItem ProfileItem = menu.findItem(R.id.app_bar_profile);
        ProfileItem.setVisible(false);

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
