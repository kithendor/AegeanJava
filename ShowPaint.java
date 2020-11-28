package com.example.museumapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowPaint extends AppCompatActivity {

    Bundle b;
    int id;

    TextView title,subtitle,desc;
    ImageView img;

    Resources res;
    String[] paint_title_fromxml;
    String[] paint_subtitle_fromxml;
    String[] paint_desc_fromxml;
     TypedArray paint_images_fromxml;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_paint);

        b = getIntent().getExtras();
        id = -1; // or other values
        if(b != null)
            id = b.getInt("key");

        title = findViewById(R.id.titletxt);
        subtitle = findViewById(R.id.subtitletxt);
        desc = findViewById(R.id.desctxt);
        img= findViewById(R.id.paintimage);

        res = getResources();
        paint_title_fromxml =  res.getStringArray(R.array.paint_title);
        paint_subtitle_fromxml =  res.getStringArray(R.array.paint_subtitle);
        paint_desc_fromxml =  res.getStringArray(R.array.paint_description);
        paint_images_fromxml =  res.obtainTypedArray(R.array.paint_images);

        title.setText(paint_title_fromxml[id]);
        subtitle.setText(paint_subtitle_fromxml[id]);
        desc.setText(paint_desc_fromxml[id]);
        img.setImageResource(paint_images_fromxml.getResourceId(id,0));

    }
}


