package com.example.rfaldu.ilovezappos.interfaces;

/**
 * Created by rfaldu on 2/5/2017.
 */

public class ApiUtils {
    public static final String BASE_URL = "https://api.zappos.com/";

    public static ZapposAPI getZapposAPI() {
        return RetrofitClient.getClient(BASE_URL).create(ZapposAPI.class);
    }
}
