package com.itba.workin.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.itba.workin.App;
import com.itba.workin.R;
import com.itba.workin.backend.models.Review;
import com.itba.workin.databinding.ActivityScoreBinding;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.repository.Status;
import com.itba.workin.ui.main.MainActivity;


public class ScoreActivity extends AppCompatActivity {

    private ActivityScoreBinding binding;
    private RoutinesRepository routinesRepository;
    private int id;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.scoreNextBtn.setOnClickListener(this::goToMainActivity);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        if (id == -1) {
            // TODO check
        }

        routinesRepository = ((App) getApplication()).getRoutinesRepository();
        context = this;

        binding.scoreRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setIsIndicator(false);
                if (fromUser) {
                    routinesRepository.addReview(id, new Review((int)rating,"")).observe((LifecycleOwner) context, r -> {
                        if (r.getStatus() == Status.SUCCESS) {
                            Toast.makeText(context,getText(R.string.rated),Toast.LENGTH_SHORT).show();
                            ratingBar.setIsIndicator(true);

                        }else {
                            Resource.defaultResourceHandler(r);
                        }
                    });
                }
            }

        });
    }

    public void goToMainActivity(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
