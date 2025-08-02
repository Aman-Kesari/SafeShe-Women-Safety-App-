package com.example.womensecurity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;

public class news_fragment extends Fragment {
    Context context;
    RecyclerView recyclerView;
    newsAdapter newsAdapter;
    ArrayList<Article>articleList=new ArrayList<>();

    public news_fragment(Context context) {
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.news_fragment, container, false);

        recyclerView=view.findViewById(R.id.newsRecycleView);
        newsAdapter=new newsAdapter(context,articleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(newsAdapter);
        getnews();
        return view;
    }
    public void getnews(){
        NewsApiClient newsApiClient = new NewsApiClient("9df82a24e2a7489c9e8a638ab3e891bb");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                            getActivity().runOnUiThread(()-> {
                            articleList = (ArrayList<Article>) response.getArticles();
                            newsAdapter.updateData(articleList);
                            newsAdapter.notifyDataSetChanged();
                        });
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                       Log.i("GOT FAILURE",throwable.getMessage());
                    }
                }
        );
    }
}