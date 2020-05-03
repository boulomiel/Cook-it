package com.rubenmimoun.cookit.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.rubenmimoun.cookit.Model.IngredientModel;
import com.rubenmimoun.cookit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PickedIngredientManager {

    private Context context ;
    private Activity activity ;
    private List<IngredientModel> ingredientModels ;
    private List<String> chosen_ingredients = new ArrayList<>();



    //TODO :  set listview in xml
    //set list view , and its adapter using context from the previous adapter

    public PickedIngredientManager(Activity activity ,Context context, List<IngredientModel> ingredientModels) {
        this.context = context;
        this.ingredientModels = ingredientModels;
        this.activity = activity ;

    }

    public void addToChosenList(String ingredient){
        TextView textView = activity.findViewById(R.id.chosen_ingredient) ;
        String ingredients =  textView.getText().toString() ;
        textView.setText("");
        ingredients += ingredient +"\n" ;
        textView.setText(ingredients);
        chosen_ingredients.add(ingredient);

    }

    public void removeFromChosenList(String ingredient){
        chosen_ingredients.remove(ingredient);
        TextView textView = activity.findViewById(R.id.chosen_ingredient) ;
      if(textView.getText() != null){
          String ingredients =  textView.getText().toString() ;
          String[] ingredient_list = ingredients.split("\n") ;
          List<String> ing_list = Arrays.asList(ingredient_list);
          System.out.println(ing_list.toString());

          ingredients = "" ;
        textView.setText(ingredients);
        try{

            for (int i = 0; i <ing_list.size() ; i++) {
                if(ing_list.get(i).equals(ingredient)){
                    ing_list.remove(ing_list.get(i));
                }
                if(ingredient_list.length > 0){
                    ingredients += ingredient_list[i]+"\n";
                }


                System.out.println(ing_list.toString());
                textView.setText(ingredients);

            }
        }catch (UnsupportedOperationException e){
            e.printStackTrace();
        }



      }



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void clearIngredientList(){
        TextView ingredient = activity.findViewById(R.id.chosen_ingredient) ;
        ingredient.setText("");
        RelativeLayout container =activity.findViewById(R.id.item_container);
        container.setBackgroundColor(context.getColor(R.color.colorBase));

    }

    public List<String> transferIngredientData(){

        TextView textView = activity.findViewById(R.id.chosen_ingredient) ;
            String ingredients =  textView.getText().toString() ;
            String[] ingredient_list = ingredients.split("\n") ;
            List<String> ing_list = Arrays.asList(ingredient_list);

            return ing_list ;

    }

    public List<String> getChosen_ingredients() {
        return chosen_ingredients;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<IngredientModel> getIngredientModels() {
        return ingredientModels;
    }

    public void setIngredientModels(List<IngredientModel> ingredientModels) {
        this.ingredientModels = ingredientModels;
    }


}
