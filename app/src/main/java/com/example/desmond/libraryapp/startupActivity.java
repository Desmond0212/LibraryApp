package com.example.desmond.libraryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class startupActivity extends AppCompatActivity {

    private TextView tv, tv2, tv3;
    private ImageView iv, iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        tv = (TextView) findViewById(R.id.app_des);
        iv = (ImageView) findViewById(R.id.app_icon);
        tv2 = (TextView) findViewById(R.id.app_dev);
       // tv3 = (TextView) findViewById(R.id.app_dev2);
      //  iv2 = (ImageView) findViewById(R.id.app_logo);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);
        tv2.startAnimation(myanim);
    //   tv3.startAnimation(myanim);
      //  iv2.startAnimation(myanim);

        final Intent i = new Intent(this, MainActivity.class);

        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(10000);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
