package com.rubenmimoun.cookit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.rubenmimoun.cookit.adapters.AdapterRecipe;
import com.rubenmimoun.cookit.utils.AsyncClassRecipe;
import com.rubenmimoun.cookit.utils.IO;
import com.rubenmimoun.cookit.utils.Preferences;

import java.util.ArrayList;
import java.util.Objects;

public class RecipeChoiceActivity extends AppCompatActivity {



    // change api key
    private  String GET_RECIPE_LINK;
    public static String RECIPE_ID = "" ;
    RecyclerView recyclerView ;
    ArrayList<String> ingredient_list ;
    String diet ;
    String intolerance ;



    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(RecipeChoiceActivity.this, InsertYourIngredientsActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        return super.onSupportNavigateUp();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_choice);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pick a recipe !");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        diet =  Preferences.getInstance(getApplicationContext()).getData("diet") ;
        ingredient_list = this.getIntent().getStringArrayListExtra("ingredients");
        intolerance = this.getIntent().getStringExtra("intolerance");
        recyclerView = findViewById(R.id.recycler_recipe) ;


        GET_RECIPE_LINK = "https://api.spoonacular.com/recipes/complexSearch?apiKey=06c1ae1fb987422283eaaa542f0ed32a" ;

    }

    @Override
    protected void onStart() {
        super.onStart();

        String url =createRecipeUrl() ;

        new AsyncClassRecipe(this,getApplicationContext()).execute(url);


    }



    public void showFragment( String id, String title, String image_url){
        Intent intent = new Intent(RecipeChoiceActivity.this, FinalRecipeActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("title", title);
        intent.putExtra("img_url", image_url);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
        startActivity(intent);
    }

    public void setAdapter(RecyclerView recyclerView, AdapterRecipe adapterRecipe){
        recyclerView.setAdapter(adapterRecipe);

        runOnUiThread(adapterRecipe::notifyDataSetChanged);
    }



    private String createRecipeUrl(){
        StringBuilder ingredients = new StringBuilder();
        String link = GET_RECIPE_LINK ;

        if(diet.equals("gluten free")){
            diet = "gluten%free" ;
        }

        link += "&diet=" + diet ;

        if( ingredient_list != null){


            for (int i = 0; i <ingredient_list.size() ; i++) {
                if(ingredient_list.get(i).contains(" ")){
                    char [] ing = ingredient_list.get(i).toCharArray();
                    StringBuilder ingString = new StringBuilder();
                    for (int j = 0; j <ing.length ; j++) {
                        if((ing[j] == 32 )){         // 32 = space ascii
                            ing[j] = 37;         // 37 = % ascii
                        }
                        ingString.append(ing[j]);
                    }

                    String newIng = ingredient_list.get(i).replace(ingredient_list.get(i),ingString.toString());
                    ingredients.append(newIng).append(",") ;
                }else{
                    ingredients.append(ingredient_list.get(i)).append(",") ;
                }


            }



            link += "&ingredients=" +ingredients.toString() ;
        }

        if(intolerance != null){
            StringBuilder builderIntolerances =  new StringBuilder();
            String [] intolerancesArr = intolerance.split(" ") ;
            for (String into :  intolerancesArr){
                if(into !=  intolerancesArr[intolerancesArr.length -1]){
                    builderIntolerances.append(into).append("+");
                }else{
                    builderIntolerances.append(into) ;
                }
            }
            link += "&intolerances=" + builderIntolerances.toString() ;
        }

        System.out.println(link);


        return link ;
    }


}


