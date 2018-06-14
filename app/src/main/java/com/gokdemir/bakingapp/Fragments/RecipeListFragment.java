package com.gokdemir.bakingapp.Fragments;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gokdemir.bakingapp.Helpers.ConnectionChecker;
import com.gokdemir.bakingapp.Model.Recipe;
import com.gokdemir.bakingapp.R;
import com.gokdemir.bakingapp.RetrofitInterface;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeListFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private ProgressDialog progressDialog;
    List<Recipe> recipeList;

    Retrofit retrofit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_list_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recipe_rv);
        mRecyclerView.setHasFixedSize(true);

        return view;
    }

    public void retrofitCall(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<Recipe> call = retrofitInterface.getRecipes();

        onLoading();

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                recipeList = (List<Recipe>) response.body();

                //create recyclerview adapter
                //send this recipeList to the recyclerview adapter inside the fragment.

                onFinishedLoading();
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
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
}