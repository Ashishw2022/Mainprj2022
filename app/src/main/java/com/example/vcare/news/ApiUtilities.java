package com.example.vcare.news;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {

    private static Retrofit retrofit = null;

    public static com.example.vcare.news.ApiInterface getApiInterface(){


        if (retrofit==null){

            retrofit = new Retrofit.Builder().baseUrl(com.example.vcare.news.ApiInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }

        return retrofit.create(com.example.vcare.news.ApiInterface.class);
    }

}
