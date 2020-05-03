package com.rubenmimoun.cookit.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rubenmimoun.cookit.Model.Recipe;
import com.rubenmimoun.cookit.R;
import com.rubenmimoun.cookit.RecipeChoiceActivity;
import com.rubenmimoun.cookit.adapters.AdapterRecipe;

import java.util.List;


public class AsyncClassRecipe extends AsyncTask<String, Integer, List<Recipe>> {

    @SuppressLint("StaticFieldLeak")
    private Context context ;
    @SuppressLint("StaticFieldLeak")
    private RecipeChoiceActivity  recipeActivity ;

    public AsyncClassRecipe(RecipeChoiceActivity activity,Context context){
        this.recipeActivity = activity ;
       this.context =  context ;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected List<Recipe> doInBackground(String... strings) {

       String parsed =  JsonParser.downloadUrl(strings[0]);

        assert parsed != null;
        if(parsed.contains("results")){
           return JsonParser.recipesToJsonObject(parsed);

       }


       return null ;

    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
        super.onPostExecute(recipes);


        if(recipeActivity != null){
            RecyclerView recyclerView =  recipeActivity.findViewById(R.id.recycler_recipe) ;
            AdapterRecipe adapterRecipe = new AdapterRecipe(context,recipeActivity,recipes) ;
            LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(context) ;
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.hasFixedSize() ;
            recipeActivity.setAdapter(recyclerView,adapterRecipe);
        }


    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);



    }


}
