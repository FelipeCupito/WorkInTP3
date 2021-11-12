package com.itba.workin.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.itba.workin.R;

import java.util.ArrayList;
import java.util.Objects;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{
    private final ArrayList<RecycleViewAdapter.RoutineWrapper> dataSet;

    public RecycleViewAdapter(ArrayList<RoutineWrapper> dataSet) {
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
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(dataSet.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // TODO remove
            itemView.setOnClickListener(view1 -> {
                Snackbar.make(itemView, "Element " + getAdapterPosition() + " clicked", BaseTransientBottomBar.LENGTH_LONG).show();
            });
            textView = itemView.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public static class RoutineWrapper implements Comparable<RoutineWrapper> {
        private final int id;
        private final String title;
        private final String imgUrl;
        private final int difficulty;
        private final Integer rating;

        public RoutineWrapper(int id, String title, String imgUrl, int difficulty, Integer rating) {
            this.id = id;
            this.title = title;
            this.imgUrl = imgUrl;
            this.difficulty = difficulty;
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public Integer getRating() {
            return rating;
        }

        public int getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RoutineWrapper that = (RoutineWrapper) o;
            return getId() == that.getId();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId());
        }

        @Override
        public int compareTo(RoutineWrapper o) {
            return Integer.compare(id, o.getId());
        }
    }
}
