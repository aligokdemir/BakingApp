package com.gokdemir.bakingapp.Helpers;

import android.widget.ImageView;

import com.gokdemir.bakingapp.R;
import com.squareup.picasso.Picasso;

public class LoadImageHelper {

    public static void loadRecipeImage(String imageUrl, ImageView imageView){
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .noFade()
                .into(imageView);
    }

}
