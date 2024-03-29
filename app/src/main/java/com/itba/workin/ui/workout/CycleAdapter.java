package com.itba.workin.ui.workout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.itba.workin.R;
import com.itba.workin.domain.MyCycle;
import com.itba.workin.domain.MyCycleExercise;
import com.itba.workin.ui.exerciseDetail.ExerciseDetail;

import java.util.List;

public class CycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Object> dataSet;
    private final static int CYCLE_VIEW = 0, EXERCISE_VIEW = 1;
    private MyCycleExercise currentExercise;

    public CycleAdapter(List<Object> dataSet) {
        this.dataSet = dataSet;
    }

    public void setCurrentExercise(MyCycleExercise currentExercise) {
        this.currentExercise = currentExercise;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case CYCLE_VIEW:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cycle_item, parent, false);
                return new CycleViewHolder(view);
            case EXERCISE_VIEW:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.exercise_item, parent, false);
                return new ExerciseViewHolder(view);
        }
        throw new IllegalStateException("oncreateviewholderillegal");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);
        switch (itemType) {
            case CYCLE_VIEW:
                ((CycleViewHolder) holder).bindTo((MyCycle) dataSet.get(position));
                break;
            case EXERCISE_VIEW:
                ((ExerciseViewHolder) holder).bindTo((MyCycleExercise) dataSet.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object object = dataSet.get(position);
        if (object instanceof MyCycle) {
            return CYCLE_VIEW;
        } else if (object instanceof MyCycleExercise) {
            return EXERCISE_VIEW;
        }
        throw new IllegalStateException("viewCycleadaptererrorgetitemviewtype");
    }

    public  class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final TextView exerciseName;
        private final TextView remainingRepetitions;
        private final TextView remainingTime;
        private final MaterialCardView card;
        private final ColorStateList cardBackgroundColor;
        private final int uiMode;
        public final int accent;

        public ExerciseViewHolder(@NonNull View view) {
            super(view);
            exerciseName = view.findViewById(R.id.exerciseName);
            remainingRepetitions = view.findViewById(R.id.remainingRepetitions);
            remainingTime = view.findViewById(R.id.remainingTime);
            card = view.findViewById(R.id.exerciseCard);
            cardBackgroundColor = card.getCardBackgroundColor();
            uiMode = view.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            accent = view.getResources().getColor(R.color.dark_grey, null);

            view.findViewById(R.id.exerciseDetails).setOnClickListener(v -> {
                int position = getAdapterPosition();
                Intent intent = new Intent(view.getContext(), ExerciseDetail.class);
                intent.putExtra("id", ((MyCycleExercise) dataSet.get(position)).getExercise().getId());
                view.getContext().startActivity(intent);
            });
        }

        public void bindTo(MyCycleExercise exercise) {
            if (exercise.equals(currentExercise)) {
                if (uiMode == Configuration.UI_MODE_NIGHT_YES)
                    card.setCardBackgroundColor(accent);
                else
                card.setCardBackgroundColor(Color.LTGRAY);
            } else {
                card.setCardBackgroundColor(cardBackgroundColor);
            }
            exerciseName.setText(exercise.getExercise().getName());
            remainingRepetitions.setText(String.valueOf(exercise.getRepetitions()));
            remainingTime.setText(String.valueOf(exercise.getDuration()));
        }
    }

    public static class CycleViewHolder extends RecyclerView.ViewHolder {
        private final TextView cycleName;
        private final TextView repetitions;

        public CycleViewHolder(@NonNull View view) {
            super(view);
            cycleName = view.findViewById(R.id.cycleName);
            repetitions = view.findViewById(R.id.repetitions);
        }

        public void bindTo(MyCycle cycle ) {
            cycleName.setText(cycle.getName());
            repetitions.setText(String.valueOf(cycle.getRepetitions()));
        }
    }
}
