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
    Animation top, bottom_right,bottom_left;
    ImageView im;
    TextView t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        top = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottom_right = AnimationUtils.loadAnimation(this, R.anim.bottom_right_anim);
        bottom_left = AnimationUtils.loadAnimation(this, R.anim.bottom_left_anim);
        im = findViewById(R.id.logoimage);
        t1 = findViewById(R.id.text1);
        t2=findViewById(R.id.text2);

        im.setAnimation(top);
        t1.setAnimation(bottom_right);
        t2.setAnimation(bottom_left);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this, login.class);
                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(im, "Logo");
                pairs[1] = new Pair<View, String>(t1, "Logotext");
                pairs[2]=new Pair<View,String>(t2,"Logotext2");

                ActivityOptions activityoptions = ActivityOptions.makeSceneTransitionAnimation(splash.this, pairs);

                startActivity(intent, activityoptions.toBundle());
                finish();
            }
        }, 5000);
    }
}