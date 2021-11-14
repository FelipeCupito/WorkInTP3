package com.itba.workin;

import android.app.Application;

import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.repository.SportRepository;
import com.itba.workin.repository.UserRepository;


public class App extends Application {

    private AppPreferences preferences;
    private UserRepository userRepository;
    private SportRepository sportRepository;
    private RoutinesRepository routinesRepository;


    public AppPreferences getPreferences() { return preferences; }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SportRepository getSportRepository() {
        return sportRepository;
    }

    public RoutinesRepository getRoutinesRepository() {
        return routinesRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new AppPreferences(this);

        userRepository = new UserRepository(this);

        sportRepository = new SportRepository(this);

        routinesRepository = new RoutinesRepository(this);

    }
}
