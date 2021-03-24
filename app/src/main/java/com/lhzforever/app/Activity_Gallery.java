package com.lhzforever.app;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class Activity_Gallery extends AppCompatActivity {

    private int[] imageId = new int[] {
            R.drawable.img01, R.drawable.img02, R.drawable.img03,
            R.drawable.img04, R.drawable.img05,
    };
    ViewPager2 mviewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__gallery);


        mviewPager2 = findViewById(R.id.viewpager2);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        mviewPager2.setAdapter(new ViewPagerAdapter(this, list, mviewPager2));
    }
}

