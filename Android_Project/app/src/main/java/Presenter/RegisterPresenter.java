package Presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import Contract.RegisterBase;
import view.MainActivity;
import view.Register;

public class RegisterPresenter {
    private FirebaseAuth mAuth;
    RegisterBase registerBase;
    Register reg;


    public RegisterPresenter(RegisterBase rB) {

        mAuth = FirebaseAuth.getInstance();
        registerBase = rB;
        reg = new Register();
    }


    public void setRegister(String e, String p) {


        mAuth.createUserWithEmailAndPassword(e, p)
                .addOnCompleteListener(reg, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            registerBase.showOnSucess();

                        } else {

                            registerBase.showOnFail();
                        }


                    }
                });
    }


}






