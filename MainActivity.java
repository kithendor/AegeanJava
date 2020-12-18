package com.example.savetest2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView upload_img;
    Button send_btn;
    TextView title_txt,info_txt;

    Uri uri;

    StorageReference storageReference;
    DatabaseReference databaseReference;

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       storageReference = FirebaseStorage.getInstance().getReference("Images");
       databaseReference = FirebaseDatabase.getInstance().getReference("Images");

        upload_img = findViewById(R.id.upload_img);
        send_btn = findViewById(R.id.send_btn);
        title_txt = findViewById(R.id.title_txt);
        info_txt = findViewById(R.id.info_txt);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uri ==null){
                    Toast.makeText(MainActivity.this, "No files", Toast.LENGTH_SHORT).show();
                    return;
                }
                StorageReference fileref = storageReference.child(System.currentTimeMillis()+"."+getExtention(uri));

                fileref.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        return fileref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri duri = task.getResult();

                            String imageUrl = duri.toString();
                            String imageName = title_txt.getText().toString().trim();
                            String imageInfo = info_txt.getText().toString().trim();

                            UploadImage uploadImage = new UploadImage(imageName,imageInfo,imageUrl);
                            databaseReference.child(databaseReference.push().getKey()).setValue(uploadImage);

                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });



        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uri = data.getData();

            Picasso.get().load(uri).into(upload_img);
            //upload_img.setImageURI(uri);
        }
    }

    private String getExtention(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    public void seeImages(View v){
           Intent intent = new Intent(MainActivity.this,seeImages.class);
           startActivity(intent);
    }
}