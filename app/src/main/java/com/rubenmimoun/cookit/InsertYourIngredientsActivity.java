package com.rubenmimoun.cookit;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rubenmimoun.cookit.Model.IngredientModel;
import com.rubenmimoun.cookit.adapters.AdapterIngredients;
import com.rubenmimoun.cookit.utils.IngredientReader;
import com.rubenmimoun.cookit.utils.PickedIngredientManager;

import java.util.ArrayList;
import java.util.List;

public class InsertYourIngredientsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{


    private RecyclerView recyclerView ;
    private List<IngredientModel>ingredient_model_list ;
    private  AdapterIngredients adapterIngredients ;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_your_ingredients);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pick ingredients");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        ingredient_model_list = new IngredientReader(this).readFile() ;
        setIngredientModelList(ingredient_model_list);


        recyclerView = findViewById(R.id.ingredient_recycler) ;
        GridLayoutManager gridLayoutManager =  new GridLayoutManager(getApplicationContext(),2);
        adapterIngredients = new AdapterIngredients(this,getApplicationContext(),ingredient_model_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterIngredients);

        Button clear = findViewById(R.id.btn_clear);
        clear.setOnClickListener(btn_clear_clicked);

        String chosen_diet = this.getIntent().getStringExtra("diet") ;
        String intolerances =  this.getIntent().getStringExtra("intolerance");



        Button go_to_recipe = findViewById(R.id.get_recipe);
        go_to_recipe.setOnClickListener(v -> {

            List<String> ingredients_final_list =  adapterIngredients.getManager().getChosen_ingredients();
            Intent intent = new Intent(InsertYourIngredientsActivity.this,RecipeChoiceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("diet",chosen_diet) ;
            intent.putExtra("intolerance", intolerances);
            intent.putStringArrayListExtra("ingredients", (ArrayList<String>) ingredients_final_list);
            startActivity(intent);
            finish();


        });

    }





    private void setIngredientModelList(List<IngredientModel>list){
        ingredient_model_list = new ArrayList<>();
        for (IngredientModel model: list) {
            //+ ingredient_name;
            String INGREDIENT_IMG_LINK = "https://spoonacular.com/cdn/ingredients_100x100/";
            ingredient_model_list.add(new IngredientModel(model.getName(), INGREDIENT_IMG_LINK +model.getName()+".jpg",model.getId()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_insert_your_ingredients, menu);

        final MenuItem item = menu.findItem(R.id.action_search) ;
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        return true ;
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(InsertYourIngredientsActivity.this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        return super.onSupportNavigateUp();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {



        newText = newText.toLowerCase();

            List<IngredientModel> filteredModelList = new ArrayList<>();
            for (IngredientModel model : ingredient_model_list) {
                final String text = model.getName().toLowerCase();
                if (text.contains(newText)) {
                    filteredModelList.add(model);
                }
            }



            adapterIngredients.animateTo(filteredModelList);
            recyclerView.scrollToPosition(0);



        return true;
    }


    View.OnClickListener btn_clear_clicked =  new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            Toast.makeText(InsertYourIngredientsActivity.this, "clear", Toast.LENGTH_SHORT).show();
            new PickedIngredientManager(InsertYourIngredientsActivity.this,getApplicationContext(),ingredient_model_list).clearIngredientList();

        }
    };


}

