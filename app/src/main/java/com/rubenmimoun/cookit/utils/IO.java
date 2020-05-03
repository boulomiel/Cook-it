package com.rubenmimoun.cookit.utils;

import com.rubenmimoun.cookit.Model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IO {

    private IO(){}
    private static final IO ourInstance = new IO();


    public static IO getInstance(){
        return ourInstance ;
    }



    public  String API_SPOONACULAR_ID = "06c1ae1fb987422283eaaa542f0ed32a";

    public  final List<String> ingredient_list = new ArrayList<>();

    public  final List<String> diets_list =
            Arrays.asList("gluten free","ketogenic","vegetarian","lacto-vegetarian","ovo-vegetarian",
                    "vegan","pescetarian","paleo"
            );

    private   String picked_diet   ;


    public  final List<String> diets_explanation  =
            Arrays.asList(
                    "Eliminating gluten means avoiding wheat, barley, rye, and other gluten-containing grains and foods made from them (or that may have been cross contaminated).",
                    "The keto diet is based more on the ratio of fat, protein, and carbs in the diet rather than specific ingredients. Generally speaking, high fat, protein-rich foods are acceptable and high carbohydrate foods are not.",
                    "No ingredients may contain meat or meat by-products, such as bones or gelatin.",
                    "All ingredients must be vegetarian and none of the ingredients can be or contain egg.",
                    "All ingredients must be vegetarian and none of the ingredients can be or contain dairy.",
                    "No ingredients may contain meat or meat by-products, such as bones or gelatin, nor may they contain eggs, dairy, or honey.",
                    "Everything is allowed except meat and meat by-products - some pescetarians eat eggs and dairy, some do not.",
                    "Allowed ingredients include meat (especially grass fed), fish, eggs, vegetables, some oils (e.g. coconut and olive oil), and in smaller quantities, fruit, nuts, and sweet potatoes. We also allow honey and maple syrup (popular in Paleo desserts, but strict Paleo followers may disagree). Ingredients not allowed include legumes (e.g. beans and lentils), grains, dairy, refined sugar, and processed foods."
            );

    public  final List<String> intolerances =
            Arrays.asList("dairy","egg","gluten","grain",
                    "peanut","seafood","sesame","shellfish","soy",
                    "sulfite","tree%20nut","wheat","none");

    private  String picked_intolerances = ""  ;

    private  int time ;
    private  String maxReadyTime = "maxReadyTime=" +time;





    public  String getLinkRequest(String diet, String intolerance, int maxTime){

        StringBuilder API_DETAILS = new StringBuilder();

        for (String diet_s: diets_list) {
            if(diet_s.equals(diet)){
                API_DETAILS.append("diet=").append(diet_s);
            }
        }

        for ( String intolerance_s: intolerances) {
            if(intolerance_s.equals(intolerance)){
                API_DETAILS.append("&intolerances").append(intolerance_s);
            }
        }

        time  = maxTime ;

        API_DETAILS.append("&").append(maxReadyTime);

        return "https://api.spoonacular.com/recipes/complexSearch?"+API_DETAILS+"&apiKey=06c1ae1fb987422283eaaa542f0ed32a";


    }

    public  int getDietInfoNumber(String diet){
        for (int i = 0; i <diets_list.size() ; i++) {
            if(diets_list.get(i).equals(diet)){
                return i ;
            }
        }

        return 0 ;
    }

    public  String getDietDefinition(int pos){

        for (int i = 0; i <diets_explanation.size() ; i++) {
            if(i ==pos){
                return diets_explanation.get(i);
            }
        }

        return  "" ;
    }


    public  String getPicked_intolerances() {
        return picked_intolerances;
    }

    public  void setPicked_intolerances(String picked_intolerances) {
        this.picked_intolerances = picked_intolerances;
    }


    public String getPicked_diet() {
        return picked_diet;
    }

    public  void setPicked_diet(String diet) {
        this.picked_diet = picked_diet;
    }



}
