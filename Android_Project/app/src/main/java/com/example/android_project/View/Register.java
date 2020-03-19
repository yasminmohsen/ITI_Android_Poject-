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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email;
    EditText pass;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.regEmail);
        pass=(EditText)findViewById(R.id.regPass);
        register=(Button)findViewById(R.id.regBtn);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                setRegister(email.getText().toString(),pass.getText().toString());
            }
        });



    }



    public void setRegister(String e, String p){


        mAuth.createUserWithEmailAndPassword(e,p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            //Toast.makeText(MainActivity.this, "Registeration Succsess",
                            // Toast.LENGTH_SHORT).show();
                            Toast toast_1 = Toast.makeText(Register.this, "Registeration Succsess", Toast.LENGTH_SHORT);
                            toast_1.show();

                            Intent intent= new Intent(Register.this,MainActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast toast_2 = Toast.makeText(Register.this, "Registeration Failed ", Toast.LENGTH_LONG);
                            toast_2.show();

                        }

                        // ...
                    }
                });



    }


}
