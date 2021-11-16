package com.itba.workin.ui.routineDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.itba.workin.App;
import com.itba.workin.ui.AppBarActivity;
import com.itba.workin.R;
import com.itba.workin.databinding.RoutineDetailBinding;
import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.domain.MyRoutine;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.repository.Status;
import com.squareup.picasso.Picasso;

public class RoutineDetailActivity extends AppBarActivity {

    private MyRoutine routine;
    private String cycleText;
    private int id;
    private RoutinesRepository routinesRepository;
    private View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoutineDetailBinding routineDetailBinding = RoutineDetailBinding.inflate(getLayoutInflater());
        root = routineDetailBinding.getRoot();
        setContentView(root);

        routinesRepository = ((App) getApplication()).getRoutinesRepository();

        ToolbarMainBinding toolbarBinding = ToolbarMainBinding.bind(root);
        toolbarBinding.toolbar.inflateMenu(R.menu.app_bar_menu);
        setSupportActionBar(toolbarBinding.toolbar);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
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
                    id = routine.getId();
                    setView();
                } else {
//                      defaultResourceHandler(r); //TODO
                }
            });
        } else {
            routine = (MyRoutine) savedInstanceState.getSerializable("MyRoutine");
            setView();
        }

        routineDetailBinding.favouriteAction.setOnClickListener(view -> {
            routinesRepository.addFavourite(id).observe(this, r -> {
                switch ( r.getStatus()) {
                    case LOADING:
                        view.findViewById(R.id.favouriteAction).setEnabled(false);
                        break;
                    case SUCCESS:
                        view.findViewById(R.id.favouriteAction).setEnabled(true);
                        break;
                    case ERROR:
                        int code = r.getError().getCode();
                        if (code == 2) { // Data constraint: hay un repetido
                            routinesRepository.deleteFavourite(id).observe(this, r2 -> {
                                switch (r2.getStatus()) {
                                    case SUCCESS:
                                        view.findViewById(R.id.favouriteAction).setEnabled(true);
                                        break;
                                    case ERROR:
                                        // TODO unknown error
                                        break;
                                }
                            });
                            break;
                        } else {
                            // TODO unknown error
                        }
                }
            });
        });
    }

    private void setView() {
        ImageView image= root.findViewById(R.id.image);
        TextView routineName = root.findViewById(R.id.routineName);
        TextView user = root.findViewById(R.id.user);
        TextView date = root.findViewById(R.id.date);
        TextView descriptionText = root.findViewById(R.id.descriptionText);
        TextView exerciseText = root.findViewById(R.id.exerciseText); // TODO
        // TODO category JUAN
        RatingBar rating = root.findViewById(R.id.rating);
        RatingBar difficulty = root.findViewById(R.id.difficulty);

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
