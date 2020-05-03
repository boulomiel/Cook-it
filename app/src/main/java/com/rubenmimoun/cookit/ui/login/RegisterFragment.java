package com.rubenmimoun.cookit.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rubenmimoun.cookit.MainActivity;
import com.rubenmimoun.cookit.R;

import java.util.Arrays;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private EditText firstname ;
    private EditText lastname ;
    private EditText dateOfBirth ;
    private EditText email ;
    private EditText password ;
    private EditText passwordRepeat ;
    private Button registerBtn ;

    private DatabaseReference mRef ;
    private FirebaseAuth mAuth ;


    public RegisterFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_register, container, false);

        firstname =v.findViewById(R.id.firstname);
        lastname =v.findViewById(R.id.lastname);
        dateOfBirth =v.findViewById(R.id.date_of_birth);
        email =v.findViewById(R.id.email_register);
        password =v.findViewById(R.id.password);
        passwordRepeat =v.findViewById(R.id.repeat_password);
        registerBtn = v.findViewById(R.id.register_btn) ;

        mRef = FirebaseDatabase.getInstance().getReference().child("Users") ;
        mAuth = FirebaseAuth.getInstance() ;



        registerBtn.setOnClickListener(v1 -> {

            if(nameAreOk() && checkPassWords() && dateIsOk() && isEmailOk()){
                String firstName = firstname.getText().toString() ;
                String lastName  = lastname.getText().toString() ;
                String emailString = email.getText().toString() ;
                String passWord =  password.getText().toString() ;
                String birthday = dateOfBirth.getText().toString() ;

                mAuth.createUserWithEmailAndPassword(emailString, passWord).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fuser = mAuth.getCurrentUser();
                        System.out.println(fuser.getUid());
                        System.out.println(fuser == null);
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("uid", fuser.getUid());
                        userMap.put("firstname", firstName);
                        userMap.put("lastname", lastName);
                        userMap.put("email", emailString);
                        userMap.put("password", passWord);
                        userMap.put("birthday", birthday);

                        mRef.child(fuser.getUid()).setValue(userMap).addOnSuccessListener(aVoid -> {
                            if (getView() != null)
                                Snackbar.make(getView(), "Registered !", Snackbar.LENGTH_SHORT).show();

                            startActivity(new Intent(getContext(), MainActivity.class).
                                    setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        });
                    }
                }).addOnFailureListener(e -> {
                    String error = cleanErrorLog(e.toString());
                    if (getView() != null)
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


    private boolean nameAreOk(){

        if( firstname.getText().length() > 2 && lastname.getText().length() > 2){
            return true ;
        }else if (firstname.getText().length() < 2 && lastname.getText().length() > 2){
            firstname.setError("First name musts contain more than one character");
            return false ;
        }else if(firstname.getText().length() > 2 && lastname.getText().length() < 2){
            lastname.setError("Last name name musts contain more than one character");
            return  false ;
        }else if(firstname.getText().length() < 2 && lastname.getText().length() < 2){
            firstname.setError("Last name name musts contain more than one character");
            lastname.setError("Last name name musts contain more than one character");


            return false ;
        }




        return false ;
    }

    private boolean dateIsOk(){
        String date =  dateOfBirth.getText().toString();

        String [] dateArray = date.split("/");
        if(dateArray.length < 3){
            dateOfBirth.setError("DD / MM / YYYY , please correct the input format");
                    return false ;
        }


        try {

                if(dateArray[0].length() < 2  || dateArray[1].length() <2){
                    dateOfBirth.setError("DD / MM / YYYY , please correct the input format");
                    return false ;
                }

                if(Integer.valueOf(dateArray[2])< 1950){
                    dateOfBirth.setError("Insert a current year please");
                    return false ;
                }

        }catch(NumberFormatException e){
            dateOfBirth.setError("DD / MM / YYYY , please correct the input format");
            return  false ;

        }



        return true ;
    }

    private boolean checkPassWords(){

        if(!password.getText().toString().equals(passwordRepeat.getText().toString())){
            passwordRepeat.setError("Inputs are different");
            password.setError("");
            return  false ;
        }

        return true;
    }

    private boolean isEmailOk(){

        if(email.getText().toString().equals("")){
            email.setError("Email is empty");
            return false;
        }

        return true ;
    }

}
