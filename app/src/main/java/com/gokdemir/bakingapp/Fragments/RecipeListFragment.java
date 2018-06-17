package com.gokdemir.bakingapp.Fragments;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gokdemir.bakingapp.Adapter.RecipeListAdapter;
import com.gokdemir.bakingapp.Helpers.ConnectionChecker;
import com.gokdemir.bakingapp.Model.Recipe;
import com.gokdemir.bakingapp.R;
import com.gokdemir.bakingapp.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeListFragment extends Fragment implements RecipeListAdapter.RecipeClickListener {
    public static final String RECIPE_LIST = "recipelist";

    private RecyclerView mRecyclerView;

    private ProgressDialog progressDialog;
    List<Recipe> recipeList;

    private RecipeListAdapter recipeListAdapter;

    Retrofit retrofit;

    private boolean isBundleNull = true;

    public RecipeListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            recipeList = savedInstanceState.getParcelableArrayList(RECIPE_LIST);
            isBundleNull = false;
        }

        View view = inflater.inflate(R.layout.recipe_list_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recipe_rv);
        mRecyclerView.setHasFixedSize(true);

        if(isBundleNull)
            retrofitCall();

        recipeListAdapter = new RecipeListAdapter(this);
        mRecyclerView.setAdapter(recipeListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public void retrofitCall(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<List<Recipe>> call = retrofitInterface.getRecipes();

        onLoading();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeList = response.body();

                recipeListAdapter.setmRecipeList(recipeList);

                onFinishedLoading();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void onLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        if (ConnectionChecker.isOnline(getContext())) {
            progressDialog.setMessage(getContext().getResources().getString(R.string.being_fecthed_info));
            progressDialog.show();
        } else {
            Toast.makeText(getActivity(), getContext().getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        }
    }

    public void onFinishedLoading(){
        mRecyclerView.setVisibility(View.VISIBLE);
        progressDialog.dismiss();
    }

    @Override
    public void onRecipeClick(int position) {
        //open another fragment using activity
        Toast.makeText(getContext(), "Item " + String.valueOf(position) + " is clicked.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSaveInstanceState(Bundle currentState){
        currentState.putParcelableArrayList(RECIPE_LIST, (ArrayList<Recipe>) recipeList);
    }
}