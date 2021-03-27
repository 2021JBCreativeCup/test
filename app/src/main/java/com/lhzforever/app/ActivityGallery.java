package com.lhzforever.app;

import android.content.Intent;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ActivityGallery extends AppCompatActivity {
    public static String IMAGE_URL = "http://121.36.36.134:5555/getImage";

    ViewPager2 mviewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__gallery);

        mviewPager2 = findViewById(R.id.viewpager2);

        int[] indices = getIntent().getIntArrayExtra("indices");
        System.out.println(Arrays.toString(indices));
        List<String> list = Arrays.stream(indices).mapToObj(i -> IMAGE_URL + "?picID=" + i).collect(Collectors.toList());
        mviewPager2.setAdapter(new ViewPagerAdapter(this, list, mviewPager2));
    }
}

