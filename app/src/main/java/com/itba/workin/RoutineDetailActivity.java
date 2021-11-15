package com.itba.workin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.itba.workin.databinding.RoutineDetailBinding;
import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.domain.MyRoutine;
import com.itba.workin.repository.Status;
import com.squareup.picasso.Picasso;

public class RoutineDetailActivity extends AppBarActivity {

    private MyRoutine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoutineDetailBinding routineDetailBinding = RoutineDetailBinding.inflate(getLayoutInflater());
        View root = routineDetailBinding.getRoot();
        setContentView(root);

        ToolbarMainBinding toolbarBinding = ToolbarMainBinding.bind(root);
        toolbarBinding.toolbar.inflateMenu(R.menu.app_bar_menu);
        setSupportActionBar(toolbarBinding.toolbar);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            int id;
            if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_VIEW)) {
                Uri data = intent.getData();
                if (data != null && data.getQueryParameter("id") != null && !data.getQueryParameter("id").equals("")) {
                    id = Integer.parseInt(data.getQueryParameter("id"));
                } else {
                    id = -1;
                }
            } else {
                id = intent.getIntExtra("id", -1);
            }
            if (id == -1) {
                // TODO check
            }
            App app = (App) getApplication();
            app.getRoutinesRepository().getRoutine(id).observe(this, r -> {
                if (r.getStatus() == Status.SUCCESS) {
                    routine = r.getData();
                    assert routine != null;
                    setView(root, routine);
                } else {
//                      defaultResourceHandler(r); //TODO
                }
            });
        } else {
            routine = (MyRoutine) savedInstanceState.getSerializable("MyRoutine");
            setView(root,routine);
        }

    }

    private void setView(View view, @NonNull MyRoutine routine) {
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("MyRoutine",routine);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "http://workin.app/routine?id=" + routine.getId());
            intent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(intent, null);
            startActivity(shareIntent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
