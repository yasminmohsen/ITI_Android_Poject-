package com.example.android_project.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email;
    EditText pass;
    Button login;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        email=(EditText)findViewById(R.id.logEmail);
        pass=(EditText)findViewById(R.id.logPass);
        login=(Button)findViewById(R.id.loginBtn);
        register=(Button)findViewById(R.id.regCreat);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setLogin(email.getText().toString(),pass.getText().toString());

            }
        });




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent= new Intent(MainActivity.this,Register.class);
                startActivity(intent);


            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }








    public void setLogin(String e, String p) {


        mAuth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast toast_1 = Toast.makeText(MainActivity.this, "Sucess Login", Toast.LENGTH_SHORT);
                            toast_1.show();

                            //String myEmail= user.getEmail();
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            // intent.putExtra("email",myEmail);
                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
                             finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast toast_2 = Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG);
                            toast_2.show();


                        }

                        // ...
                    }
                });







    }


/// comment

    }
