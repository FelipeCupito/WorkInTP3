package com.itba.workin.ui.myRoutines;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyRoutinesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyRoutinesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MyRoutines fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}