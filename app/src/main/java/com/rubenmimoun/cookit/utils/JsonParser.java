package com.rubenmimoun.cookit.utils;

import android.util.Log;

import com.rubenmimoun.cookit.Model.CookingStep;
import com.rubenmimoun.cookit.Model.Recipe;
import com.rubenmimoun.cookit.RecipeChoiceActivity;
import com.rubenmimoun.cookit.adapters.AdapterRecipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

   private static  Recipe r ;
   private static  CookingStep s ;
   private static List<Recipe> recipeList =  new ArrayList<>();
   private static List<CookingStep> cookingSteps =  new ArrayList<>();
   private static  List<String> ingredientList =  new ArrayList<>();


    static String downloadUrl(String link){
        try {

            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStreamReader is =  new InputStreamReader(connection.getInputStream());
            StringBuilder stringBuilder =  new StringBuilder() ;
            int i  ;

            while((i = is.read()) != -1){
                stringBuilder.append((char)i);
            }


            return stringBuilder.toString() ;



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Recipe>recipesToJsonObject(String json){

        recipeList.clear();
        if( !cookingSteps.isEmpty())
        cookingSteps.clear();

        try {

            JSONObject jsonObject =  new JSONObject(json) ;
            JSONArray jsonArray = (JSONArray) jsonObject.get("results");

            for (int i = 0; i <jsonArray.length() ; i++) {

                JSONObject object = (JSONObject) jsonArray.get(i);
                String id_string =  object.getString("id");
                double id = Double.valueOf(id_string);
                String title = object.getString("title");
                String img_url = object.getString("image");

                r = new Recipe(id,title,img_url) ;
                RecipeChoiceActivity.RECIPE_ID = String.valueOf(r.getId());
                recipeList.add(r) ;
            }

            Log.i("Recipe list is not null", recipeList.toString());
            return recipeList ;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Recipe list is null", recipeList.toString());
        return null ;

    }

    static List<CookingStep> stepsToObject(String json){


        try {

            JSONArray firstarray = new JSONArray(json);

            for (int j = 0; j <firstarray.length() ; j++) {
                JSONObject stepsObject = (JSONObject) firstarray.get(j);
                JSONArray stepsArray = stepsObject.getJSONArray("steps") ;

                for (int i = 0; i <stepsArray.length() ; i++) {

                    JSONObject object = (JSONObject) stepsArray.get(i);
                    String num_string =  object.getString("number");
                    int number = Integer.parseInt(num_string);
                    String step = object.getString("step");
                    JSONArray ingredient_array  = (JSONArray) object.get("ingredients");
                    if( ingredient_array.length() > -1){
                        for (int k = 0; k <ingredient_array.length() ; k++) {
                            JSONObject ingredient = (JSONObject) ingredient_array.get(k);
                            String ingredient_name =  ingredient.getString("name");
                            ingredientList.add(ingredient_name);

                        }
                    }

                    s = new CookingStep(number,step,ingredientList) ;
                    cookingSteps.add(s);
                }
            }




            r.setInstruction(s.toString());



            return cookingSteps ;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null ;

    }

}
