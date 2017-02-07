package com.example.rfaldu.ilovezappos;

/**
 * Created by rfaldu on 2/7/2017.
 */

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;
import android.net.Uri;

import com.squareup.picasso.Picasso;

public class ImageBindingAdapter {

    @BindingAdapter({"imageURL"})
    public static void loadImage(ImageView imageView, String url) {
        if (url != null) {
            Picasso.with(imageView.getContext().getApplicationContext()).load(url).into(imageView);
        }
        else
        {
            imageView.setImageResource(R.drawable.images);
            Log.d("AAAAAAAAAAAAAAAAAAAAAAA","Url not found");
        }
    }
}