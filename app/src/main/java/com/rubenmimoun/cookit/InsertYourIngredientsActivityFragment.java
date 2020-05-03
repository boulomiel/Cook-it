package com.rubenmimoun.cookit;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rubenmimoun.cookit.utils.IngredientReader;

import java.util.HashMap;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class InsertYourIngredientsActivityFragment extends Fragment {

    private String ingredient_name = "" ;
    private  String INGREDIENT_IMG_LINK ="https://spoonacular.com/cdn/ingredients_100x100/"+ ingredient_name;
    private List<String> ingredient_list ;

    public InsertYourIngredientsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_insert_your_ingredients, container, false);



        return root ;
    }
}
