package com.example.rfaldu.ilovezappos.interfaces;

/**
 * Created by rfaldu on 2/5/2017.
 */

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.example.rfaldu.ilovezappos.model.*;
public interface ZapposAPI {

    @GET("/Search?key=b743e26728e16b81da139182bb2094357c31d331")
            Call<ItemName> getProduct(@Query("term") String productName);
}
