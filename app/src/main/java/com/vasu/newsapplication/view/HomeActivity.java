package com.vasu.newsapplication.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vasu.newsapplication.CategoryOnClickInterface;
import com.vasu.newsapplication.R;
import com.vasu.newsapplication.adapter.NewsAdapter;
import com.vasu.newsapplication.adapter.ViewPager2Adapter;
import com.vasu.newsapplication.databinding.ActivityHomeBinding;
import com.vasu.newsapplication.model.NewsModel;
import com.vasu.newsapplication.viewModel.NewsViewModel;

import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getName();
    private ActivityHomeBinding binding;
    private NewsViewModel viewModel;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        viewModel = new ViewModelProvider(HomeActivity.this).get(NewsViewModel.class);

//        final Observer<NewsModel> newsModelObserver = newsModel -> showData(newsModel);
        final Observer<NewsModel> newsModelObserver = this::showData;  // same as above

        viewModel.getNewsObserver().observe(this,newsModelObserver);


        try {

//            ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this, new CategoryOnClickInterface() {
//                @Override
//                public void onCategoryClick(String category) {
//                    binding.progressBar.setVisibility(View.VISIBLE);
//                    viewModel.makeAPICall(category);
//                }
//            });

            ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this, category -> {
                binding.progressBar.setVisibility(View.VISIBLE);
                viewModel.makeAPICall(category);
            });


            binding.btnRecyclerView.setAdapter(viewPager2Adapter);
            binding.btnRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.HORIZONTAL, false));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
       searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.progressBar.setVisibility(View.VISIBLE);
                viewModel.makeAPICall(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            searchView.onActionViewCollapsed();
        } else {
            super.onBackPressed();
        }
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