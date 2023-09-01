package com.kmy.yallana3raf.NewsClasses.NewsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kmy.yallana3raf.NewsClasses.NewsModel.NewsRecyclerViewAdapter;
import com.kmy.yallana3raf.R;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class NewsHeadLinesActivity extends AppCompatActivity implements View.OnClickListener{

    boolean isLanguage ;

    RecyclerView recyclerView ;

    List<Article> articleList = new ArrayList<>();

    NewsRecyclerViewAdapter newsRecyclerViewAdapter ;

    LinearProgressIndicator linearProgressIndicator ;

    Button btn_1 ,btn_2 ,btn_3 ,btn_4 ,btn_5 ,btn_6 ,btn_7;

    ImageView btn_language ;

    SearchView searchView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_head_lines);

        recyclerView = findViewById(R.id.news_recycler_view);
        linearProgressIndicator = findViewById(R.id.progress_bar);

        btn_1 = findViewById(R.id.btn_general);
        btn_2 = findViewById(R.id.btn_business);
        btn_3 = findViewById(R.id.btn_sports);
        btn_4 = findViewById(R.id.btn_science);
        btn_5 = findViewById(R.id.btn_technology);
        btn_6 = findViewById(R.id.btn_health);
        btn_7 = findViewById(R.id.btn_entertainment);
        btn_language = findViewById(R.id.btn_language);

        searchView = findViewById(R.id.search_view);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                getNews("General",query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        setupRecyclerView();
        getNews("General",null);

    }

    public void setupRecyclerView()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerViewAdapter =new NewsRecyclerViewAdapter(articleList);
        recyclerView.setAdapter(newsRecyclerViewAdapter);
    }

    public void changeInProgress(boolean show)
    {
        if(show)
        {
            linearProgressIndicator.setVisibility(View.VISIBLE);
        }
        else
        {
            linearProgressIndicator.setVisibility(View.INVISIBLE);
        }
    }

    public void getNews(String category ,String query)
    {
        changeInProgress(true);

        NewsApiClient newsApiClient =new NewsApiClient("2d5ee7e9fece4736b0094b1060db36dd");

        newsApiClient.getTopHeadlines(

                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .category(category)
                        .q(query)
                        .build(),

                new NewsApiClient.ArticlesResponseCallback()
                {

                    @Override
                    public void onSuccess(ArticleResponse response)
                    {

                        runOnUiThread(() ->
                        {
                            changeInProgress(false);
                            articleList = response.getArticles();

                            newsRecyclerViewAdapter.updateData(articleList);
                            newsRecyclerViewAdapter.notifyDataSetChanged();
                        });

                    }

                    @Override
                    public void onFailure(Throwable throwable) {}
                }
        );

        btn_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLanguage)
                {
                    isLanguage = true ;

                    newsApiClient.getTopHeadlines(

                            new TopHeadlinesRequest.Builder()
                                    .language("ar")
                                    .category(category)
                                    .q(query)
                                    .build(),

                            new NewsApiClient.ArticlesResponseCallback()
                            {

                                @Override
                                public void onSuccess(ArticleResponse response)
                                {

                                    runOnUiThread(() ->
                                    {
                                        changeInProgress(false);
                                        articleList = response.getArticles();

                                        newsRecyclerViewAdapter.updateData(articleList);
                                        newsRecyclerViewAdapter.notifyDataSetChanged();
                                    });

                                }

                                @Override
                                public void onFailure(Throwable throwable) {}
                            }
                    );

                }
                else
                {
                    isLanguage = false ;

                    newsApiClient.getTopHeadlines(

                            new TopHeadlinesRequest.Builder()
                                    .language("en")
                                    .category(category)
                                    .q(query)
                                    .build(),

                            new NewsApiClient.ArticlesResponseCallback()
                            {

                                @Override
                                public void onSuccess(ArticleResponse response)
                                {

                                    runOnUiThread(() ->
                                    {
                                        changeInProgress(false);
                                        articleList = response.getArticles();
                                        newsRecyclerViewAdapter.updateData(articleList);
                                        newsRecyclerViewAdapter.notifyDataSetChanged();
                                    });

                                }

                                @Override
                                public void onFailure(Throwable throwable) {}
                            }
                    );

                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view ;
        String category = btn.getText().toString();
        getNews(category,null);
    }
}