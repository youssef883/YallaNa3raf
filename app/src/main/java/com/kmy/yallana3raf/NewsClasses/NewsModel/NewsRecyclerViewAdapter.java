package com.kmy.yallana3raf.NewsClasses.NewsModel;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmy.yallana3raf.NewsClasses.NewsActivity.NewsBodyActivity;
import com.kmy.yallana3raf.R;
import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder>
{
    List<Article> articleList;
    public NewsRecyclerViewAdapter(List<Article> articleList)
    {
        this.articleList = articleList ;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_news_model,parent,false);

        return new NewsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position)
    {
        Article article = articleList.get(position);

        holder.newsTitle.setText(article.getTitle());
        holder.newsDescription.setText(article.getDescription());
        Picasso.get().load(article.getUrlToImage())
                    .error(R.drawable.ic_hide_image)
                    .placeholder(R.drawable.ic_hide_image)
                    .into(holder.newsImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NewsBodyActivity.class);
            intent.putExtra("url",article.getUrl());
            v.getContext().startActivity(intent);
        });
    }

    public void updateData(List<Article>data)
    {
        articleList.clear();
        articleList.addAll(data);
    }

    @Override
    public int getItemCount()
    {
        return articleList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder
    {
        TextView newsTitle , newsDescription ;
        ImageView newsImage;

        public NewsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            newsTitle = itemView.findViewById(R.id.article_title_name);
            newsDescription = itemView.findViewById(R.id.article_description);
            newsImage = itemView.findViewById(R.id.article_image_view);

        }
    }

}
