package com.vasu.newsapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vasu.newsapplication.R;
import com.vasu.newsapplication.databinding.NewsItemLayoutBinding;
import com.vasu.newsapplication.model.NewsModel;
import com.vasu.newsapplication.view.NewsDetailsActivity;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    List<NewsModel.Article> articleList;

    public NewsAdapter(Context context, List<NewsModel.Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsItemLayoutBinding binding = NewsItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        try {
            holder.binding.txtTitle.setText(articleList.get(position).getTitle());
            holder.binding.txtDesc.setText(articleList.get(position).getDescription());
            holder.binding.txtAuthor.setText(articleList.get(position).getAuthor());
            holder.binding.txtContent.setText(articleList.get(position).getContent());
            holder.binding.txtPublisher.setText("PublishedAt : "+articleList.get(position).getPublishedAt());


            if(TextUtils.isEmpty(articleList.get(position).getUrlToImage())){
                holder.binding.imageShimmerEffect.setVisibility(View.GONE);
            }else {
                Glide.with(context)
                        .load(articleList.get(position).getUrlToImage())
                        .centerCrop()
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                holder.binding.imageShimmerEffect.setVisibility(View.GONE);
                                holder.binding.newsImage.setVisibility(View.VISIBLE);
                                return false;
                            }
                        })
//                    .placeholder(R.drawable.ic_launcher_background)
                        .into(holder.binding.newsImage);
            }


            holder.binding.cardView.setOnClickListener(v -> {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("URL",articleList.get(position).getUrl());
                context.startActivity(intent);
            });
        } catch (Exception e
        ) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        NewsItemLayoutBinding binding;
        public NewsViewHolder(@NonNull NewsItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
