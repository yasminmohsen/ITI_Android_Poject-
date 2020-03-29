package view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Contract.FirebaseBase;
import Contract.LoginBase;
import Contract.TripDAO;
import Model.AppDataBase;
import Pojos.Trip;
import Presenter.LoginPresenter;
import Presenter.Presenter;

public class MainActivity extends AppCompatActivity implements LoginBase  {

    private FirebaseAuth mAuth;
    EditText email;
    EditText pass;
    Button login;
    Button register;
    SignInButton google;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginPresenter presenter;
    //
    private   List<Trip>tripList=new ArrayList<Trip>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.logEmail);
        pass = (EditText) findViewById(R.id.logPass);
        login = (Button) findViewById(R.id.loginBtn);
        register = (Button) findViewById(R.id.regCreat);
        google = (SignInButton) findViewById(R.id.googleLogIn);

        presenter = new LoginPresenter(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.setLoginEmail(email.getText().toString(), pass.getText().toString());


            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);


            }
        });


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.googleLogIn) {
                    signIn();
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();


    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.firebaseAuthWithGoogle(account);
                //firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void showOnSucessEmail() {

        Toast toast_1 = Toast.makeText(MainActivity.this, "Sucess Login", Toast.LENGTH_SHORT);
        toast_1.show();
        Intent intent = new Intent(MainActivity.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


         intent = new Intent(MainActivity.this, Home.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST", (Serializable) tripList);
        intent.putExtra("BUNDLE",args);
        startActivity(intent);
    }



    @Override
    public void showOnFailEmail() {
        Toast toast_2 = Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG);
        toast_2.show();


    }

    @Override
    public void showOnSucessGoogle() {


        Intent intent = new Intent(MainActivity.this, Home.class);

        startActivity(intent);



    }

    @Override
    public void showOnFailGoogle() {
        Log.w(TAG, "signInWithCredential:failure");
        Snackbar.make(findViewById(R.id.MainActivity), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
    }



}

