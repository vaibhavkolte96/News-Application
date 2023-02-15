package com.vasu.newsapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vasu.newsapplication.CategoryOnClickInterface;
import com.vasu.newsapplication.databinding.SearchByButtonBinding;

public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

    // Array of String
    private final String[] searchKeyWords = {"Technology", "Science", "Corona",
            "Layoff", "Stock Market","Business","IT Jobs","Farmer"};
    private Context ctx;
    CategoryOnClickInterface onClickInterface;

    // Constructor of our ViewPager2Adapter class
    public ViewPager2Adapter(Context ctx, CategoryOnClickInterface categoryOnClickInterface) {
        this.ctx = ctx;
        this.onClickInterface = categoryOnClickInterface;
    }

    // This method returns our layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchByButtonBinding binding = SearchByButtonBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
        holder.binding.btnSearchKeyword.setText(searchKeyWords[position]);

        holder.binding.btnSearchKeyword.setOnClickListener(v -> onClickInterface.onCategoryClick(searchKeyWords[position]));
    }

    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return searchKeyWords.length;
    }

    // The ViewHolder class holds the view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        SearchByButtonBinding binding;

        public ViewHolder(@NonNull SearchByButtonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

