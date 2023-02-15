package com.vasu.newsapplication.network;

import com.vasu.newsapplication.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPIService {

    @GET("everything/")
    Call<NewsModel> getEverything(@Query("domains") String domains, @Query("apiKey")String apiKey);

    @GET("everything/")
    Call<NewsModel> searchNews(@Query("q") String query,
                               @Query("from") String date,
                               @Query("sortBy") String sortBy,
                               @Query("apiKey")String apiKey);
}
