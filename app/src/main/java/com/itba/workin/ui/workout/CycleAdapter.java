package com.itba.workin.ui.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itba.workin.R;
import com.itba.workin.domain.MyCycle;
import com.itba.workin.domain.MyCycleExercise;

import java.util.List;

public class CycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Object> dataSet;
    private final static int CYCLE_VIEW = 0, EXERCISE_VIEW = 1;

    public CycleAdapter(List<Object> dataSet) {
        this.dataSet = dataSet;
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
                return new CycleViewHolder(view);
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
                ((ExcerciseViewHolder) holder).bindTo((MyCycleExercise) dataSet.get(position));
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

    public static class ExcerciseViewHolder extends RecyclerView.ViewHolder {
        private final TextView exerciseName;
        private final TextView remainingRepetitions;
        private final TextView remainingTime;

        public ExcerciseViewHolder(@NonNull View view) {
            super(view);
            exerciseName = view.findViewById(R.id.exerciseName);
            remainingRepetitions = view.findViewById(R.id.remainingRepetitions);
            remainingTime = view.findViewById(R.id.remainingTime);
            view.findViewById(R.id.exerciseDetails).setOnClickListener(v -> {
//                int position = getAdapterPosition();
//                Intent intent = new Intent(context, RoutineDetailActivity.class);
//                intent.putExtra("id", ((MyCycleExercise) dataSet.get(position)).getExcercise().getId());
//                context.startActivity(intent);
            });
        }

        public void bindTo(MyCycleExercise exercise) {
            exerciseName.setText(exercise.getExcercise().getName());
            remainingRepetitions.setText(exercise.getRepetitions());
            remainingTime.setText(exercise.getDuration());
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
            repetitions.setText(cycle.getRepetitions());
        }
    }
}
