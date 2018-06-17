package com.gokdemir.bakingapp;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gokdemir.bakingapp.Fragments.RecipeListFragment;

public class MainActivity extends AppCompatActivity {

    android.support.v4.app.FragmentManager fm;
    RecipeListFragment recipeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        recipeListFragment = new RecipeListFragment();
        fm.beginTransaction().add(R.id.fragment_container, recipeListFragment).commit();

    }
}