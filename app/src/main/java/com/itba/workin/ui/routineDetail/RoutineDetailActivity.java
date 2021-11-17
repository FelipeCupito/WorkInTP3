package com.itba.workin.ui.routineDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.itba.workin.App;
import com.itba.workin.R;
import com.itba.workin.databinding.RoutineDetailBinding;
import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.domain.MyRoutine;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.login.LoginActivity;
import com.squareup.picasso.Picasso;

public class RoutineDetailActivity extends AppCompatActivity {

    private MyRoutine routine;
    private String cycleText;
    private int id;
    private RoutinesRepository routinesRepository;
    private RoutineDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RoutineDetailBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        routinesRepository = ((App) getApplication()).getRoutinesRepository();

        ToolbarMainBinding toolbarBinding = ToolbarMainBinding.bind(root);
        toolbarBinding.toolbar.inflateMenu(R.menu.app_bar_menu);
        setSupportActionBar(toolbarBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        binding.loading.setVisibility(View.VISIBLE);
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
                switch (r.getStatus()) {
                    case SUCCESS:
                        routine = r.getData();
                        assert routine != null;
                        id = routine.getId();
                        setView();
                        break;
                    case ERROR:
                        if (r.getError().getCode() == 7) { // unauthorized
                            Intent i = new Intent(this, LoginActivity.class);
                            i.putExtra("id",id);
                            startActivity(i);
                            finish();
                        } else {
                            binding.loading.setVisibility(View.GONE);
                            Toast.makeText(root.getContext(), getText(R.string.unexpected_error), Toast.LENGTH_LONG).show();
                            break;
                        }
                }
            });
        } else {
            routine = (MyRoutine) savedInstanceState.getSerializable("MyRoutine");
            setView();
        }
    }

    private void setView() {
        Picasso.get().load(routine.getRoutineUrl()).placeholder(binding.image.getDrawable()).resize(300,200).into(binding.image);
        binding.routineName.setText(routine.getName());
        binding.user.setText(routine.getUserName());
        binding.date.setText(routine.getDate().toString());
        binding.descriptionText.setText(routine.getDetail());
        binding.rating.setRating(routine.getScore());
        binding.difficulty.setRating(routine.getDifficulty());
        binding.categoryName.setText(routine.getCategory());

        binding.routineName.setVisibility(View.VISIBLE);
        binding.image.setVisibility(View.VISIBLE);
        binding.scrollView.setVisibility(View.VISIBLE);
        binding.startRoutine.setVisibility(View.VISIBLE);
        binding.loading.setVisibility(View.GONE);
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
        MenuItem favoriteItem = menu.findItem(R.id.app_bar_favorite);
        favoriteItem.setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
        } else if (item.getItemId() == R.id.app_bar_favorite) {
            routinesRepository.addFavourite(id).observe(this, r -> {
                switch ( r.getStatus()) {
                    case LOADING:
                        item.setEnabled(false);
                        break;
                    case SUCCESS:
                        Toast.makeText(binding.getRoot().getContext(),getText(R.string.favorite_yes),Toast.LENGTH_LONG).show();
                        item.setEnabled(true);
                        break;
                    case ERROR:
                        int code = r.getError().getCode();
                        if (code == 2) { // Data constraint: hay un repetido
                            routinesRepository.deleteFavourite(id).observe(this, r2 -> {
                                switch (r2.getStatus()) {
                                    case SUCCESS:
                                        Toast.makeText(binding.getRoot().getContext(),getText(R.string.favorite_no),Toast.LENGTH_LONG).show();
                                        item.setEnabled(true);
                                        break;
                                    case ERROR:
                                        Toast.makeText(binding.getRoot().getContext(),getText(R.string.unexpected_error),Toast.LENGTH_LONG).show();
                                        break;
                                }
                            });
                            break;
                        }
                        Toast.makeText(binding.getRoot().getContext(),getText(R.string.unexpected_error),Toast.LENGTH_LONG).show();
                        item.setEnabled(true);
                        break;
                }
            });
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
