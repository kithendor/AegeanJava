package com.example.fire2test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText name_txt, mail_txt, password_txt, address_txt;
    Button reg_btn;
    TextView login_lbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        name_txt = findViewById(R.id.name_txt);
        mail_txt = findViewById(R.id.mail_txt);
        password_txt = findViewById(R.id.password_txt);
        address_txt = findViewById(R.id.address_txt);
        reg_btn = findViewById(R.id.reg_btn);
        login_lbl = findViewById(R.id.login_lbl);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_txt.getText().toString().trim();
                String mail = mail_txt.getText().toString().trim();
                String password = password_txt.getText().toString().trim();
                String address = address_txt.getText().toString().trim();

                if(name.isEmpty()){
                    name_txt.setError("Fill your Name");
                    name_txt.requestFocus();
                    return;
                }

                if(mail.isEmpty()){
                    mail_txt.setError("Fill your mail");
                    mail_txt.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    password_txt.setError("Fill your password");
                    password_txt.requestFocus();
                    return;
                }

                if(address.isEmpty()){
                    address_txt.setError("Fill your address");
                    address_txt.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    mail_txt.setError("Invalid mail");
                    mail_txt.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            User user = new User(name, address, mail);
                            Toast.makeText(MainActivity.this, user.mail, Toast.LENGTH_SHORT).show();


                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        login_lbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){
            Toast.makeText(this, "Loged in", Toast.LENGTH_SHORT).show();
        }
    }

    public void logOut(View v){
        mAuth.signOut();
    }
}

