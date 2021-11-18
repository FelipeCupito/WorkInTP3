package com.itba.workin.ui.main.fragments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itba.workin.R;
import com.itba.workin.ui.routineDetail.RoutineDetailActivity;
import com.itba.workin.domain.MyRoutine;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder>{
    private final List<MyRoutine> dataSet;

    public RoutineAdapter(List<MyRoutine> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routine_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineAdapter.ViewHolder holder, int position) {
        holder.bindTo(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;
        private final RatingBar difficultyBar;
        private final RatingBar scoreBar;

        private final Context context;

        public ViewHolder(@NonNull View view) {
            super(view);
            context = view.getContext();
            textView = view.findViewById(R.id.routineName);
            imageView = view.findViewById(R.id.image);
            difficultyBar = view.findViewById(R.id.difficulty);
            scoreBar = view.findViewById(R.id.rating);
            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Intent intent = new Intent(context, RoutineDetailActivity.class);
                intent.putExtra("id", dataSet.get(position).getId());
                context.startActivity(intent);
            });
        }

        public void bindTo(MyRoutine routine) {
            textView.setText(routine.getName());
            Picasso.get().load(routine.getRoutineUrl()).placeholder(imageView.getDrawable()).resize(150,100).into(imageView);
            difficultyBar.setRating(routine.getDifficulty());
            scoreBar.setRating((float)(routine.getScore()/2.0));
        }
    }
}
