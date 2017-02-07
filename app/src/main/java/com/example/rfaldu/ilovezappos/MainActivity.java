package com.example.rfaldu.ilovezappos;


import android.databinding.DataBindingUtil;
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
import android.widget.TextView;


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

public class MainActivity extends AppCompatActivity {

    ZapposAPI zapposAPI;
    String searchItem;
    TextView brandName_TextView, price_TextView;
    List<ItemDescription> itemDescriptionList;
    DisplayItem displayItem;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        brandName_TextView = (TextView)findViewById(R.id.brandName_TextView);
        price_TextView = (TextView)findViewById(R.id.price_textView);
        //t3 = (TextView)findViewById(R.id.textView3);
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //searchItem = newText;
                //t1.setText(searchItem);
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
                    //t2.setText(response.body().getCurrentResultCount());
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
