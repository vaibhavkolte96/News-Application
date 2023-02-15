package com.vasu.newsapplication.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vasu.newsapplication.adapter.NewsAdapter;
import com.vasu.newsapplication.adapter.ViewPager2Adapter;
import com.vasu.newsapplication.databinding.ActivityHomeBinding;
import com.vasu.newsapplication.model.NewsModel;
import com.vasu.newsapplication.viewModel.NewsViewModel;

import java.util.List;
import java.util.Objects;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getName();
    private ActivityHomeBinding binding;
    NewsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(HomeActivity.this).get(NewsViewModel.class);

//        final Observer<NewsModel> newsModelObserver = newsModel -> showData(newsModel);
        final Observer<NewsModel> newsModelObserver = this::showData;  // same as above

        viewModel.getNewsObserver().observe(this,newsModelObserver);

        viewModel.makeAPICall();

        try {

//            ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this, new CategoryOnClickInterface() {
//                @Override
//                public void onCategoryClick(String category) {
//                    viewModel.makeAPICall(category);
//                }
//            });

            ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this,
                    category -> viewModel.makeAPICall(category)); // same as above comment 6 line
            binding.btnRecyclerView.setAdapter(viewPager2Adapter);
            binding.btnRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.HORIZONTAL, false));
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.btnSearch.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(Objects.requireNonNull(binding.etSearchText.getText()).toString())) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                viewModel.makeAPICall(binding.etSearchText.getText().toString());
            }
        });

    }

    private void showData(NewsModel news) {
        try {
            binding.progressBar.setVisibility(View.GONE);
            binding.newsRecyclerView.setVisibility(View.VISIBLE);
            List<NewsModel.Article> articleList = news.getArticles();
            NewsAdapter newsAdapter = new NewsAdapter(this, articleList);
            binding.newsRecyclerView.setAdapter(newsAdapter);
            binding.newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}