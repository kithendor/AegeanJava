package com.example.savetest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class seeImages extends AppCompatActivity {

    DatabaseReference databaseReference;
    List<UploadImage> uploadImages;
    ListView images_list;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_images);

        images_list = findViewById(R.id.images_list);

        uploadImages = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Images");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    UploadImage uploadImage = ds.getValue(UploadImage.class);
                    uploadImages.add(uploadImage);

                }

                myAdapter = new MyAdapter();
                images_list.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(seeImages.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return uploadImages.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_image_layout,null);

            TextView cl_title = (TextView) convertView.findViewById(R.id.cl_title);
            TextView cl_info = (TextView) convertView.findViewById(R.id.cl_info);
            ImageView cl_img = (ImageView) convertView.findViewById(R.id.cl_img);

            cl_title.setText(uploadImages.get(position).getimagename());
            cl_info.setText(uploadImages.get(position).getimageinfo());
            Picasso.get().load(uploadImages.get(position).getimageurl()).into(cl_img);

            //System.out.println("ekr"+uploadImages.get(position).getName());
           // cl_img.setImageURI(uploadImages.get(position).getimageurl());

            return convertView;
        }
    }
}