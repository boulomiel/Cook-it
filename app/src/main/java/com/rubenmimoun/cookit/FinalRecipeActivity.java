package com.rubenmimoun.cookit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rubenmimoun.cookit.Model.CookingStep;
import com.rubenmimoun.cookit.adapters.AdapterRecipeActivity;
import com.rubenmimoun.cookit.utils.AsyncClassCookingStep;

import java.util.List;
import java.util.Objects;

public class FinalRecipeActivity extends AppCompatActivity {


    private TextView title_recipe ;
    private String image_url ;
    private String title ;
    private RecyclerView recyclerView ;
    public  String instructions_url ;

    private static boolean activityVisible ;



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityVisible = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityVisible = false ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityVisible = false ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_recipe);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        activityVisible = true ;

        Intent intent =  getIntent() ;
        if(intent != null){
            String id = intent.getStringExtra("id");
            title = intent.getStringExtra("title");
            toolbar.setTitle(title);
            image_url =  intent.getStringExtra("img_url");
            instructions_url =  "https://api.spoonacular.com/recipes/"+ id +"/analyzedInstructions?apiKey=06c1ae1fb987422283eaaa542f0ed32a" ;
        }

        ImageView image_recipe = findViewById(R.id.image_recipe_activity);
        title_recipe = findViewById(R.id.title_recipe) ;
        recyclerView = findViewById(R.id.recipe_actitivy_recycler) ;





        Glide.with(getApplicationContext()).load(image_url).into(image_recipe) ;

    }

    @Override
    protected void onResume() {
        super.onResume();

        activityVisible = true ;

        if(getApplicationContext() != null)


        title_recipe.setText(title);

        new AsyncClassCookingStep(this).execute(instructions_url);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        activityVisible = true ;
    }

    public void fillInstructions(List<CookingStep>instructions){

        Log.i("fill instruction", instructions.toString());

        AdapterRecipeActivity adapterRecipeActivity = new AdapterRecipeActivity(instructions);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapterRecipeActivity);

        adapterRecipeActivity.notifyDataSetChanged();


    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

}
