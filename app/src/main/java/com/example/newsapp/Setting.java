package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import static maes.tech.intentanim.CustomIntent.customType;

public class Setting extends AppCompatActivity {

    ImageView imgback;

    CardView c1,c2,c3,c4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        imgback = findViewById(R.id.back);

        c1 = findViewById(R.id.card_view1);
        c2 = findViewById(R.id.card_view2);
        c3 = findViewById(R.id.card_view3);
        c4 = findViewById(R.id.card_view4);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),About.class));
                customType(Setting.this,"fadein-to-fadeout");
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://contact-gourav.netlify.app/")));
                customType(Setting.this,"fadein-to-fadeout");
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://newsapi.org/")));
                customType(Setting.this,"fadein-to-fadeout");
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/vickyjsr/NewsApp")));
                customType(Setting.this,"fadein-to-fadeout");
            }
        });

    }
}