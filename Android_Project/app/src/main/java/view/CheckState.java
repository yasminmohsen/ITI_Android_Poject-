package view;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import view.alarm.NotificationService;

public class CheckState extends NotificationService {


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(CheckState.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            startActivity(intent);

        }
    }

}
