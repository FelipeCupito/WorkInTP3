package com.itba.workin;

import android.app.Application;

import com.itba.workin.repository.ExerciseRepository;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.repository.UserRepository;


public class App extends Application {

    private AppPreferences preferences;
    private UserRepository userRepository;
    private RoutinesRepository routinesRepository;
    private ExerciseRepository exerciseRepository;

    public AppPreferences getPreferences() { return preferences; }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RoutinesRepository getRoutinesRepository() {
        return routinesRepository;
    }

    public ExerciseRepository getExerciseRepository() {
        return exerciseRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new AppPreferences(this);

        userRepository = new UserRepository(this);

        routinesRepository = new RoutinesRepository(this);

        exerciseRepository = new ExerciseRepository(this);

    }
}
