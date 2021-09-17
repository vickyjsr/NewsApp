package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.newsapp.Models.NewsApiResponse;
import com.example.newsapp.Models.NewsHeadlines;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,SelectListener, View.OnClickListener {

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog progressDialog;
    ShimmerFrameLayout container;
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;
    String cate = "general";
    RelativeLayout net_gone;

    Button prev;

    ImageView settings;

    Button b1,b2,b3,b4,b5,b6,b7;
    int btn_press;

    List<Button> btn = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = findViewById(R.id.settings);

        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this);

        btn.add(b1);btn.add(b2);btn.add(b3);btn.add(b4);btn.add(b5);btn.add(b6);btn.add(b7);

        for(int i=0;i<btn.size();i++)
        {
            btn.get(i).setBackgroundColor(getColor(R.color.btn));
        }


        prev = b1;
        prev.setBackgroundColor(Color.parseColor("#ffffff"));

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this::shimmer);
        recyclerView = findViewById(R.id.recyclerView_main);

        searchView = findViewById(R.id.search_view);

        net_gone = findViewById(R.id.iv_net_gone);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = (new Intent(getApplicationContext(),Setting.class));
                startActivity(intent);
                customType(MainActivity.this,"fadein-to-fadeout");
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(),"Fetching new articles!!",Toast.LENGTH_SHORT).show();
                shimmerMore();
                RequestManager manager = new RequestManager(getApplicationContext());
                manager.getNewsHeadlines(listener,"general",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        container = findViewById(R.id.shimmer_view_container);


        Toast.makeText(this,"Fetching new articles!!",Toast.LENGTH_SHORT).show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,"general",null);

    }

    private void shimmerMore() {

        container.showShimmer(true);
        container.startShimmer();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                container.stopShimmer();
                container.hideShimmer();
            }
        }, 3000);
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"No data found!!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                showNews(list);
                shimmer();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(getApplicationContext(),"Check your Internet!!",Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.INVISIBLE);
            net_gone.setVisibility(View.VISIBLE);
            container.stopShimmer();container.hideShimmer();
        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new CustomAdapter(this,list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        Intent intent = (new Intent(getApplicationContext(),DetailsActivity.class));
        intent.putExtra("data",headlines);
        startActivity(intent);
        customType(MainActivity.this,"fadein-to-fadeout");
    }

    @Override
    public void onClick(View v) {

        Button button = (Button)v;

        v.setBackgroundColor(Color.parseColor("#ffffff"));
        for(int i=0;i<btn.size();i++)
        {
            if(button!=btn.get(i))
            {
                btn.get(i).setBackgroundColor(getColor(R.color.btn));
            }
        }
        String category = button.getText().toString();
        net_gone.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        cate = category;
        shimmerMore();
        Toast.makeText(this,"Fetching news article of "+category,Toast.LENGTH_SHORT).show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,category,null);
    }

    private void shimmer() {

        container.showShimmer(true);
        container.startShimmer();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                container.stopShimmer();
                container.hideShimmer();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);

    }

    @Override
    public void onRefresh() {
        net_gone.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        String category = cate;
        shimmerMore();
        Toast.makeText(this,"Fetching news article of "+category,Toast.LENGTH_SHORT).show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,category,null);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}