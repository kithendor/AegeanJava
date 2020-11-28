package com.example.museumapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PaintingsActivity extends AppCompatActivity {

    ListView paintlistview;

    String[] paint_titler = {"Pierre Soulages","Roy Lichtenstein","Jackson Pollock","Paul Gauguin",
            "Vincent van Gogh","Edgar Degas","Claude Monet","Joan Miró","Wassily Kandinsky"};

    String[] paint_subtitle = {"Peinture","Sunrise","Number 13","Nature morte aux pamplemousses",
            "La cueillette des olives","Cheval à l’abreuvoir","La cathédrale de Rouen le matin","La sauterelle","Beide gestreift"};

    int[] patin_images={R.drawable.doulages,R.drawable.lichtenstein,R.drawable.pollock,R.drawable.gauguin,
            R.drawable.gogh,R.drawable.degas,R.drawable.monet,R.drawable.miro,R.drawable.kandinsky};


    String[] paint_title_fromxml,paint_subtitle_fromxml;
    TypedArray paint_images_fromxml;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paintings);

        paintlistview = (ListView) findViewById(R.id.paintlistview);

        MyPaintAdapter myPaintAdapter = new MyPaintAdapter();

        Resources res = getResources();
        paint_title_fromxml =  res.getStringArray(R.array.paint_title);
        paint_subtitle_fromxml =  res.getStringArray(R.array.paint_subtitle);
        paint_images_fromxml =  res.obtainTypedArray(R.array.paint_images);




        paintlistview.setAdapter(myPaintAdapter);


        paintlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(PaintingsActivity.this,ShowPaint.class);

                Bundle b = new Bundle();
                b.putInt("key", i); //Your id
                intent.putExtras(b);

                startActivity(intent);

            }
        });
    }


    class MyPaintAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return patin_images.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_paint_layout, null);

            TextView titlepainttxt = (TextView) view.findViewById(R.id.paint_title_txt);
            TextView subtitlepaint = (TextView) view.findViewById(R.id.paint_subtitle);
            ImageView paintimage = (ImageView) view.findViewById(R.id.paint_img);

            titlepainttxt.setText(paint_title_fromxml[i]);
            subtitlepaint.setText(paint_subtitle_fromxml[i]);
            paintimage.setImageResource(paint_images_fromxml.getResourceId(i,0));
            return view;
        }
    }
}

