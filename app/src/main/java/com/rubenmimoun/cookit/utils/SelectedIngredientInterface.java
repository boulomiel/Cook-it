package com.rubenmimoun.cookit.utils;



import java.util.ArrayList;
import java.util.List;

public interface SelectedIngredientInterface {


   default void setSelectedIngredient(List<String> selectedIngredient){
       getSelectedIngredient(selectedIngredient);

   }

    List<String> getSelectedIngredient(List<String> selectedIngredient);

}
