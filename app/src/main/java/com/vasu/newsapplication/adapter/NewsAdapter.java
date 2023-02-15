package com.vasu.newsapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vasu.newsapplication.R;
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
        View view = LayoutInflater.from(context).inflate(R.layout.news_item_layout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        try {
            holder.title.setText(articleList.get(position).getTitle());
            holder.desc.setText(articleList.get(position).getDescription());
            holder.author.setText(articleList.get(position).getAuthor());
            holder.content.setText(articleList.get(position).getContent());
            holder.publisher.setText("PublishedAt : "+articleList.get(position).getPublishedAt());


            Glide.with(context)
                    .load(articleList.get(position).getUrlToImage())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.newsImage);


            holder.cardView.setOnClickListener(v -> {
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

        TextView title, desc, author,publisher,content;
        ImageView newsImage;
        CardView cardView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            desc = itemView.findViewById(R.id.txtDesc);
            author = itemView.findViewById(R.id.txtAuthor);
            content = itemView.findViewById(R.id.txtContent);
            publisher = itemView.findViewById(R.id.txtPublisher);

            cardView = itemView.findViewById(R.id.cardView);


            newsImage = itemView.findViewById(R.id.newsImage);
        }
    }
}
