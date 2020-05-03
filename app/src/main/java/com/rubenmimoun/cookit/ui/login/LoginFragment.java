package com.rubenmimoun.cookit.ui.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.rubenmimoun.cookit.MainActivity;
import com.rubenmimoun.cookit.R;


public class LoginFragment extends Fragment {


    private DatabaseReference mRef ;
    private FirebaseAuth mAuth ;



    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_login, container, false);
       // getActivity().setTitle("Login");

        final EditText emailEditText = v.findViewById(R.id.username_login);
        final EditText passwordEditText = v.findViewById(R.id.password_login);
        final Button loginButton = v.findViewById(R.id.login);
        final ProgressBar loadingProgressBar = v.findViewById(R.id.loading);

        mAuth = FirebaseAuth.getInstance();

//        if(!emailEditText.getText().toString().equals("") && !passwordEditText.getText().toString().equals("")){
        loginButton.setEnabled(true);
//        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString() ;
                Log.i("email",email);
                String password = passwordEditText.getText().toString() ;
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        if (getView() != null)
                            Snackbar.make(getView(), "Welcome back !", Snackbar.LENGTH_SHORT).show();

                        startActivity(new Intent(getContext(), MainActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

                    }
                }).addOnFailureListener(e -> {
                    String error = cleanErrorLog(e.toString());
                    if (getView() != null)
                        Log.i("error", error);
                        Snackbar.make(getView(), error, Snackbar.LENGTH_SHORT).show();
                });

            }
        });

        return v ;

    }

    private String cleanErrorLog(String e) {
        String [] errorArr =  e.split(":");
        return errorArr[1];
    }


}
