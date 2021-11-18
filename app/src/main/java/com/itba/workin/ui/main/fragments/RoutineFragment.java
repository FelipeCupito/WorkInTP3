package com.itba.workin.ui.main.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itba.workin.R;
import com.itba.workin.databinding.MainActivityFragmentBinding;
import com.itba.workin.domain.MyRoutine;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public abstract class RoutineFragment extends Fragment {

    private MainActivityFragmentBinding binding;
    private final List<MyRoutine> routines = new ArrayList<>();
    protected RoutineViewModel routineViewModel;
    private RoutineAdapter adapter;
    protected MenuItem defaultOpt, nameOpt, dateOpt, scoreOpt, difficultyOpt, categoryOpt;
    private TextView searchText;
    private ProgressBar loading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = MainActivityFragmentBinding.inflate(inflater, container, false);

        routines.clear();
        List<MyRoutine> prevRoutines = routineViewModel.getPrevRoutines();
        for (int i = 0; i < RoutineViewModel.PAGE_SIZE && i < prevRoutines.size(); i++) {
            routines.add(prevRoutines.get(i));
        }
        adapter = new RoutineAdapter(routines);

        loading = requireActivity().findViewById(R.id.loading);
        searchText = requireActivity().findViewById(R.id.search_text);

        searchText.setOnClickListener(v -> {
            routineViewModel.setSearch(null);
            routineViewModel.restart();
            fetchRoutines();
            searchText.setText(null);
            searchText.setVisibility(View.GONE);
        });

        binding.recyclerview.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
        binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!RoutineFragment.this.binding.recyclerview.canScrollVertically(1)) {
                    routineViewModel.getMoreRoutines();
                }
            }
        });
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.addItemDecoration(new GridSpacingItemDecoration(2,20,false, getContext()));
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchRoutines();
    }

    private void fetchRoutines() {

        routineViewModel.getRoutines().observe(getViewLifecycleOwner(), r -> {
            switch (r.getStatus()) {
                case LOADING:
                    loading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    routines.clear();
                    if (r.getData() != null) {
                        routines.addAll(r.getData());
                        adapter.notifyDataSetChanged();
                    }
                    loading.setVisibility(View.GONE);
                    break;
                case ERROR:
                    loading.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(binding.recyclerview.getContext(),getText(R.string.unexpected_error),Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 200);
                    toast.show();
                    break;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        MenuItem profileItem = menu.findItem(R.id.app_bar_profile);
        MenuItem sortItem = menu.findItem(R.id.app_bar_sort);

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                profileItem.setVisible(false);
                sortItem.setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                requireActivity().invalidateOptionsMenu();
                return true;
            }
        });
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2) {
                    routineViewModel.setSearch(query);
                    routineViewModel.restart();
                    fetchRoutines();
                    searchText.setText(String.format(getString(R.string.search_text), query));
                    searchText.setVisibility(View.VISIBLE);
                    searchItem.collapseActionView();
                    return true;
                }
                Toast toast = Toast.makeText(binding.recyclerview.getContext(),getText(R.string.query_too_short),Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 200);
                toast.show();
                return false;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        defaultOpt = menu.findItem(R.id.sort_default);
        nameOpt = menu.findItem(R.id.sort_name);
        dateOpt = menu.findItem(R.id.sort_date);
        scoreOpt = menu.findItem(R.id.sort_score);
        difficultyOpt = menu.findItem(R.id.sort_difficulty);
        categoryOpt = menu.findItem(R.id.sort_category);
        switch (routineViewModel.getOrder()) {
            case DEFAULT:
                defaultOpt.setChecked(true);
                break;
            case NAME:
                nameOpt.setChecked(true);
                break;
            case DATE:
                dateOpt.setChecked(true);
                break;
            case SCORE:
                scoreOpt.setChecked(true);
                break;
            case DIFFICULTY:
                difficultyOpt.setChecked(true);
                break;
            case CATEGORY:
                categoryOpt.setChecked(true);
                break;
        }
        if (routineViewModel.getSearch() != null) {
            searchText.setText(String.format(getString(R.string.search_text), routineViewModel.getSearch()));
            searchText.setVisibility(View.VISIBLE);
        } else {
            searchText.setVisibility(View.GONE);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort_default) {
            routineViewModel.setOrder(RoutinesRepository.SORT.DEFAULT);
        } else if (item.getItemId() == R.id.sort_name) {
            routineViewModel.setOrder(RoutinesRepository.SORT.NAME);
        } else if (item.getItemId() == R.id.sort_date) {
            routineViewModel.setOrder(RoutinesRepository.SORT.DATE);
        } else if (item.getItemId() == R.id.sort_score) {
            routineViewModel.setOrder(RoutinesRepository.SORT.SCORE);
        } else if (item.getItemId() == R.id.sort_difficulty) {
            routineViewModel.setOrder(RoutinesRepository.SORT.DIFFICULTY);
        } else if (item.getItemId() == R.id.sort_category) {
            routineViewModel.setOrder(RoutinesRepository.SORT.CATEGORY);
        } else {
            return super.onOptionsItemSelected(item);
        }
        defaultOpt.setChecked(false);
        nameOpt.setChecked(false);
        dateOpt.setChecked(false);
        scoreOpt.setChecked(false);
        difficultyOpt.setChecked(false);
        categoryOpt.setChecked(false);
        switch (routineViewModel.getOrder()) {
            case DEFAULT:
                defaultOpt.setChecked(true);
                break;
            case NAME:
                nameOpt.setChecked(true);
                break;
            case DATE:
                dateOpt.setChecked(true);
                break;
            case SCORE:
                scoreOpt.setChecked(true);
                break;
            case DIFFICULTY:
                difficultyOpt.setChecked(true);
                break;
            case CATEGORY:
                categoryOpt.setChecked(true);
                break;
        }
        routineViewModel.restart();
        fetchRoutines();
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
