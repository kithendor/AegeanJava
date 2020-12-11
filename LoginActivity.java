package com.example.fire2test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText  mailtxt, passwordtxt;
    Button login_btn;
    TextView register_lbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mailtxt = findViewById(R.id.mailtxt);
        passwordtxt = findViewById(R.id.passwordtxt);
        login_btn = findViewById(R.id.login_btn);
        register_lbl = findViewById(R.id.register_lbl);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mailtxt.getText().toString().trim();
                String password = passwordtxt.getText().toString().trim();

                if(mail.isEmpty()){
                    mailtxt.setError("Fill your Mail");
                    mailtxt.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    passwordtxt.setError("Fill your password");
                    passwordtxt.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Log in ok", Toast.LENGTH_SHORT).show();
                        }
                        else    {
                            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}

