package com.example.womensecurity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.viewHolder> {
    ArrayList<Article>articleList;
    Context context;

    newsAdapter(Context context,ArrayList<Article> articleList){
        this.context=context;
        this.articleList=articleList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.news_representing_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Article article=articleList.get(position);
        holder.newsTitle.setText(article.getTitle());
        holder.newspaperName.setText(article.getSource().getName());
        try{
            Picasso.get()
                    .load(article.getUrlToImage())
                    .error(R.drawable.icon_no_image)
                    .placeholder(R.drawable.news_icon)
                    .into(holder.newsImage);
        }catch(Exception e){}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, webActivity.class);
                intent.putExtra("URL",article.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        ImageView newsImage;
        TextView newsTitle,newspaperName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage=itemView.findViewById(R.id.newsImage);
            newsTitle=itemView.findViewById(R.id.newsTitle);
            newspaperName=itemView.findViewById(R.id.newspaperName);
        }
    }
    void updateData(ArrayList<Article> data){
        articleList.clear();
        articleList.addAll(data);
    }
}
