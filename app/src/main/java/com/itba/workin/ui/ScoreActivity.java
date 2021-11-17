package com.itba.workin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.itba.workin.databinding.ActivityScoreBinding;
import com.itba.workin.domain.MyRoutine;
import com.itba.workin.ui.main.MainActivity;


public class ScoreActivity extends AppCompatActivity {

    private ActivityScoreBinding binding;
    private MyRoutine routine;
    private int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.scoreNextBtn.setOnClickListener(this::goToMainActivity);



        binding.scoreRatingBar.setRating(routine.getScore());
        binding.socreRoutineName.setText(routine.getName());

/*        binding.scoreRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

             *//*   ratingBar.setIsIndicator(false);
                if (fromUser) {
                    App app = (App)getApplication();
                    app.getReviewRepository().addReview(id, new Review((int)rating,"")).observe((LifecycleOwner) context, r -> {
                        if (r.getStatus() == Status.SUCCESS) {
                            Toast.makeText(context,getText(R.string.rated),Toast.LENGTH_SHORT).show();
                            ratingBar.setIsIndicator(true);

                        }else {
                            Resource.defaultResourceHandler(r);
                        }
                    });
                }*//*
            }

        });*/

    }

    public void goToMainActivity(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
