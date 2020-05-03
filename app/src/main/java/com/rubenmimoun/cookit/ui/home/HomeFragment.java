package com.rubenmimoun.cookit.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rubenmimoun.cookit.InsertYourIngredientsActivity;
import com.rubenmimoun.cookit.R;
import com.rubenmimoun.cookit.adapters.AdapterDietRecycler;
import com.rubenmimoun.cookit.adapters.AdapterIntolerances;
import com.rubenmimoun.cookit.utils.IO;
import com.rubenmimoun.cookit.utils.Preferences;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recycler_diet ;
    private RecyclerView recycler_intolerance ;
    private Button lets_cook  ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        lets_cook = root .findViewById(R.id.find_recipe) ;

        recycler_diet =  root.findViewById(R.id.recycler_diet) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) ;
        AdapterDietRecycler adapterDiet =  new AdapterDietRecycler(getContext());
        recycler_diet.setLayoutManager(layoutManager);
        recycler_diet.setAdapter(adapterDiet);

        recycler_intolerance =  root.findViewById(R.id.recycler_intolerances) ;
        LinearLayoutManager layoutManagerIntolerance = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) ;
        AdapterIntolerances adapterIntolerance =  new AdapterIntolerances(getActivity());
        recycler_intolerance.setLayoutManager(layoutManagerIntolerance);
        recycler_intolerance.setAdapter(adapterIntolerance);

        Preferences preferences = Preferences.getInstance(getContext());
        lets_cook.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                String chosen_diet = preferences.getInstance(getContext()).getData("diet");
                System.out.println(chosen_diet);

                if(chosen_diet == null){
                    Toast.makeText(getActivity(), "Pick a diet", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(IO.getInstance().getPicked_intolerances().equals("")){
                    IO.getInstance().setPicked_intolerances("nothing");
                }

                new AlertDialog.Builder(getContext())
                        .setTitle("Ready ?")
                        .setMessage("Your diet is " +chosen_diet+ " and you have intolerances to " + IO.getInstance().getPicked_intolerances())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateDb(IO.getInstance().getPicked_intolerances(), chosen_diet);
                                Toast.makeText(getContext(),"lets go", Toast.LENGTH_SHORT).show();
                                Intent intent =  new Intent(getActivity(), InsertYourIngredientsActivity.class ) ;
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
                                intent.putExtra("diet",chosen_diet);
                                intent.putExtra("intolerance", IO.getInstance().getPicked_intolerances());
                                startActivity(intent);


                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                return ;
                            }
                        })
                        .show()
                        .create();
            }
        });

        return root;
    }


    private void updateDb(String intolerances, String diet){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
        HashMap<String, Object> updateMap = new HashMap<>() ;
        if(user != null){
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(user.getUid());

            db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    db.child("diet").setValue(diet);
                    db.child("intolerances").setValue(intolerances);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    private void setRecapitulativeTextView(View v){

        TextView recapitulative = v.findViewById(R.id.recapitulation) ;
        String diets = "Diets : ";
        String intolerances = "Intolerances : " ;
        StringBuilder final_diet = new StringBuilder() ;
        StringBuilder final_into =  new StringBuilder() ;
        if(IO.getInstance().getPicked_diet() != null){
            final_diet.append(diets).append(IO.getInstance().getPicked_diet()).append(" \n");

        }
        if(IO.getInstance().getPicked_intolerances() != null){
             final_into.append(intolerances).append(IO.getInstance().getPicked_intolerances()) ;

        }

        String finalS = final_diet.toString() + final_into.toString();

        recapitulative.setText(finalS);
    }
}