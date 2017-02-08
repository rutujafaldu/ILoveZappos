package com.example.rfaldu.ilovezappos;


import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.support.design.widget.FloatingActionButton;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rfaldu.ilovezappos.databinding.ActivityMainBinding;
import com.example.rfaldu.ilovezappos.interfaces.ApiUtils;
import com.example.rfaldu.ilovezappos.interfaces.ZapposAPI;
import com.example.rfaldu.ilovezappos.model.ItemDescription;
import com.example.rfaldu.ilovezappos.model.ItemName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ZapposAPI zapposAPI;
    String searchItem;
    TextView brandName_TextView, price_TextView;
    List<ItemDescription> itemDescriptionList;
    DisplayItem displayItem;
    ActivityMainBinding binding;

    public boolean fab2_status = false;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward, zoom_in, zoom_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        zoom_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                Toast.makeText(v.getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
                animateFab1();
                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab2:
                if(fab2_status == false) {
                    Toast.makeText(v.getContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();
                    fab2.setImageResource(R.drawable.ic_favorite_white_24dp);
                    fab2.startAnimation(zoom_in);
                    fab2.startAnimation(zoom_out);
                    //Snackbar.make(v, "Added to Favourites",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    fab2_status = true;
                }
                else{
                    Toast.makeText(v.getContext(), "Removed from Favourites", Toast.LENGTH_SHORT).show();
                    fab2.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    //Snackbar.make(v, "Removed from Favourites",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    fab2_status = false;
                }
                Log.d("Raj", "Fab 2");
                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            //fab2.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab1.setScaleX(0.8f);
            fab1.setScaleY(0.8f);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    public  void  animateFab1(){
        final int deltaY = 200;

        TranslateAnimation jump = new TranslateAnimation(0,0,0, -deltaY);
        jump.setFillAfter(true);
        jump.setDuration(400);
        jump.setInterpolator(new DecelerateInterpolator(2.0f));

        jump.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation)  {
                fab1.setScaleX(1.0f);
                fab1.setScaleY(1.0f);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                TranslateAnimation bounceBack = new TranslateAnimation(0,0,-deltaY, 0);
                bounceBack.setFillAfter(true);
                bounceBack.setDuration(500);
                bounceBack.setInterpolator(new BounceInterpolator());
                fab1.setScaleX(0.8f);
                fab1.setScaleY(0.8f);
                fab1.startAnimation(bounceBack);

            }
        });

        fab1.startAnimation(jump);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        brandName_TextView = (TextView)findViewById(R.id.brandName_TextView);
        price_TextView = (TextView)findViewById(R.id.price_textView);
        zapposAPI = ApiUtils.getZapposAPI();
        itemDescriptionList = new ArrayList<ItemDescription>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchItem = query;
                //t1.setText(searchItem);
                displayProduct(query);
                brandName_TextView.setVisibility(View.VISIBLE);
                price_TextView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                fab2.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                fab2_status = false;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //searchItem = newText;
                //t1.setText(searchItem);
                //displayProduct(newText);
                return false;

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void displayProduct(String query){
        zapposAPI.getProduct(query).enqueue(new Callback<ItemName>() {
            @Override
            public void onResponse(Call<ItemName> call, Response<ItemName> response) {
                if(response.isSuccessful()){
                    itemDescriptionList = response.body().getResults();
                    displayItem = new DisplayItem(itemDescriptionList.get(0).getProductName(), itemDescriptionList.get(0).getBrandName()+" ", itemDescriptionList.get(0).getThumbnailImageUrl(), itemDescriptionList.get(0).getPrice(), itemDescriptionList.get(0).getPercentOff());
                    binding.setDisplayItem(displayItem);

                    //ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
                }
                else{
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<ItemName> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }
}
