package com.gokdemir.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gokdemir.bakingapp.Helpers.LoadImageHelper;
import com.gokdemir.bakingapp.Model.Recipe;
import com.gokdemir.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeList;
    private final RecipeClickListener mRecipeClickListener;

    public interface RecipeClickListener{
        void onRecipeClick(int position);
    }

    public RecipeListAdapter(RecipeClickListener mRecipeClickListener){
        this.mRecipeList = new ArrayList<>();
        this.mRecipeClickListener = mRecipeClickListener;
    }

    @Override
    public RecipeListAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card_view_row, parent, false);


        RecipeViewHolder viewHolder = new RecipeViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);

        holder.mRecipeName.setText(recipe.getName());
        holder.mRecipeServings.setText("Servings: " + String.valueOf(recipe.getServings()));
    }

    @Override
    public int getItemCount() {
        return  mRecipeList == null ? 0 : mRecipeList.size();
    }

    public void setmRecipeList(List<Recipe> recipeList){
        if(this.mRecipeList != null )
            this.mRecipeList.clear();
        this.mRecipeList.addAll(recipeList);
        this.notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mRecipeName, mRecipeServings;

        public RecipeViewHolder(View itemView){
            super(itemView);
            mRecipeName = itemView.findViewById(R.id.recipe_name_tv);
            mRecipeServings = itemView.findViewById(R.id.recipe_serving_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            mRecipeClickListener.onRecipeClick(clickPosition);
        }
    }
}
