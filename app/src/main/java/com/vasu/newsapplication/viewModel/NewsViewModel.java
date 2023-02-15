package com.vasu.newsapplication.viewModel;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.vasu.newsapplication.model.NewsModel;
import com.vasu.newsapplication.network.NewsAPIService;
import com.vasu.newsapplication.network.RetroInstance;

import java.time.LocalDate;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsViewModel extends ViewModel {

    private static final String TAG = NewsAPIService.class.getName();
    private MutableLiveData<NewsModel> newsModel;



    public MutableLiveData<NewsModel> getNewsObserver() {
        if (newsModel == null) {
            newsModel = new MutableLiveData<>();
            makeAPICall();
        }
        return newsModel;
    }

    public void makeAPICall() {
        NewsAPIService apiService = RetroInstance.getRetroClient().create(NewsAPIService.class);
        Call<NewsModel> call = apiService
                .getEverything("techcrunch.com,thenextweb.com",
                        "1c5c80bf1ee34f17a3df9fe95b5112b6");
        callNewsAPI(call);
    }



    public void makeAPICall(String text) {
        NewsAPIService apiService = RetroInstance.getRetroClient().create(NewsAPIService.class);
        Call<NewsModel> call = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            call = apiService
                    .searchNews(text, String.valueOf(LocalDate.now()), "publishedAt", "1c5c80bf1ee34f17a3df9fe95b5112b6");
        }
        callNewsAPI(call);
    }

    private void callNewsAPI(Call<NewsModel> call){
        try{
            if (call != null) {
                call.enqueue(new Callback<NewsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<NewsModel> call, @NonNull Response<NewsModel> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.e(TAG, "onResponse: " + Objects.requireNonNull(response.body()));
                                newsModel.postValue(response.body());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NewsModel> call, @NonNull Throwable t) {
                        try {
                            Log.e(TAG, "onFailure: Something went wrong : " + t.getMessage());
                            newsModel.postValue(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
