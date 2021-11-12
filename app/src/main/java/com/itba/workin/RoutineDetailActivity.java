package com.itba.workin;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.itba.workin.databinding.ActivityMainBinding;
import com.itba.workin.databinding.RoutineDetailBinding;

public class RoutineDetailActivity extends AppCompatActivity {

    public static final String TAG = "Lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "SecondaryActivity: onCreate()");

        RoutineDetailBinding binding = RoutineDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "SecondaryActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "SecondaryActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "SecondaryActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "SecondaryActivity: onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG, "SecondaryActivity: onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "SecondaryActivity: onDestroy()");
    }
}
