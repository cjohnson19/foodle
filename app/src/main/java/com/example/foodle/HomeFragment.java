package com.example.foodle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodle.db.IngredientDB;
import com.example.foodle.db.RecipeDB;
import com.example.foodle.model.Pantry;
import com.example.foodle.model.PantryViewModel;
import com.example.foodle.model.Recipe;
import com.example.foodle.model.RecipeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<Recipe> recipeList;
    RecyclerView recyclerView;
    MainActivity mainActivity;
    private PantryViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /**
         * Set up Recycler View
         */

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainActivity = (MainActivity) getActivity();

        recipeList = RecipeDB.getStandardRecipes();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(PantryViewModel.class);
        viewModel.getPantry().observe(getViewLifecycleOwner(), pantry -> {
            // Update the list UI
            updatePantry();
        });
        updatePantry();
    }

    public void updatePantry() {
        RecipeAdapter recipeAdapter = new RecipeAdapter(getContext(), recipeList, viewModel.getPantry().getValue());
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
