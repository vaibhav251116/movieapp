package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash extends AppCompatActivity {
    Animation top, bottom;
    ImageView im;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        top = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        im = findViewById(R.id.logoimage);
        t1 = findViewById(R.id.text1);

        im.setAnimation(top);
        t1.setAnimation(bottom);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this, login.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(im, "Logo");
                pairs[1] = new Pair<View, String>(t1, "Logotext");

                ActivityOptions activityoptions = ActivityOptions.makeSceneTransitionAnimation(splash.this, pairs);

                startActivity(intent, activityoptions.toBundle());
                finish();
            }
        }, 5000);
    }
}