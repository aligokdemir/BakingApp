package com.gokdemir.bakingapp;

import com.gokdemir.bakingapp.Model.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<Recipe> getRecipes();

}