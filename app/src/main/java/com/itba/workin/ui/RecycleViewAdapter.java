package com.itba.workin.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itba.workin.R;
import com.itba.workin.RoutineDetailActivity;
import com.itba.workin.models.MyRoutine;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{
    private ArrayList<MyRoutine> dataSet;

    public RecycleViewAdapter(ArrayList<MyRoutine> dataSet) {
        this.dataSet = dataSet;
    }

    public void setDataSet(ArrayList<MyRoutine> dataSet) {
        this.dataSet = dataSet;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routine_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(dataSet.get(position).getName());
        Picasso.get().load(dataSet.get(position).getRoutineUrl()).placeholder(holder.getImageView().getDrawable()).resize(150,100).into(holder.getImageView());
        holder.getDifficultyBar().setRating(dataSet.get(position).getDifficulty());
        holder.getScoreBar().setRating(dataSet.get(position).getScore());
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
                intent.putExtra("ID", dataSet.get(position).getId());
                context.startActivity(intent);
            });
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public RatingBar getDifficultyBar() {
            return difficultyBar;
        }

        public RatingBar getScoreBar() {
            return scoreBar;
        }
    }
}
