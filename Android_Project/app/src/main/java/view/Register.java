package view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import Contract.RegisterBase;
import Presenter.Presenter;
import Presenter.RegisterPresenter;

public class Register extends AppCompatActivity implements RegisterBase {

    EditText email;
    EditText pass;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.regEmail);
        pass = (EditText) findViewById(R.id.regPass);
        register = (Button) findViewById(R.id.regBtn);
        final RegisterPresenter presenter = new RegisterPresenter(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailVal = email.getText().toString();
                String passVal = pass.getText().toString();

                if(!emailVal.isEmpty() && !passVal.isEmpty()) {
                    presenter.setRegister(email.getText().toString(), pass.getText().toString());
                } else {
                    Toast.makeText(Register.this, "Enter Valid Email and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void showOnSucess() {
        Toast toast_1 = Toast.makeText(Register.this, "Registeration Succsess", Toast.LENGTH_SHORT);
        toast_1.show();

        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showOnFail() {
        Toast toast_2 = Toast.makeText(Register.this, "Registeration Failed ", Toast.LENGTH_LONG);
        toast_2.show();

    }

}
