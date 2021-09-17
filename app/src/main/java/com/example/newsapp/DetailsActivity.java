package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    NewsHeadlines headlines;
    TextView txt_title,txt_author,txt_time,txt_detail,txt_content;
    ImageView img_news;
    String murl;
    Button btntoWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txt_title = findViewById(R.id.text_detail_title);
        txt_author = findViewById(R.id.text_detail_author);
        txt_time = findViewById(R.id.text_detail_time);
//        txt_detail = findViewById(R.id.text_detail_detail);
//        txt_content = findViewById(R.id.text_detail_content);
        img_news = findViewById(R.id.img_detail_news);

        btntoWeb = findViewById(R.id.btn_to_webview);


        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");

        assert headlines != null;

        txt_title.setText(headlines.getTitle());
        txt_author.setText(headlines.getAuthor());

        String time = getTime(headlines.getPublishedAt());

        txt_time.setText(time);
//        txt_detail.setText(headlines.getDescription());
//        txt_content.setText(headlines.getContent());

        murl = headlines.getUrl();


        Picasso.get().load(headlines.getUrlToImage()).into(img_news);

        btntoWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initViews(murl);
                btntoWeb.setVisibility(View.GONE);
            }
        });

    }

    private String getTime(String publishedAt) {
        String s = publishedAt;
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)>='a' && s.charAt(i)<='z')
            {
                s = s.replace(s.charAt(i), ' ');
            }
            if(s.charAt(i)>='A' && s.charAt(i)<='Z')
            {
                s = s.replace(s.charAt(i), ' ');
            }
        }
        return s;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initViews(String url)
    {
        WebView webView = findViewById(R.id.webView);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }

}