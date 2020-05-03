package com.rubenmimoun.cookit.utils;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.rubenmimoun.cookit.Model.IngredientModel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IngredientReader {


    private Context context ;
    final List<Integer> ingredient_id_list = new ArrayList<>();


   public  IngredientReader(Context context){
    this.context =context ;
   }

    public List<Integer> getIngredient_id_list() {
        return ingredient_id_list;
    }

    public List<IngredientModel> readFile(){
       final HashMap<String, String>map =  new HashMap<>() ;
       final String[] json = new String[1];
       final List<String> ingredient_list =  new ArrayList<>();
       final  List<IngredientModel> modelList = new ArrayList<>();



       ///  System.out.println(Arrays.toString(data));
       Thread t = new Thread(new Runnable() {
           @RequiresApi(api = Build.VERSION_CODES.KITKAT)
           @Override
           public void run() {
               try {
                   InputStream is = context.getAssets().open("ingredients.csv");
                   int size = is.available();
                   byte[] buffer = new byte[size];
                   is.read(buffer);
                   is.close();
                   json[0] = new String(buffer, StandardCharsets.UTF_8);
               } catch (IOException ex) {
                   ex.printStackTrace();

               }

               String[] data = json[0].split("\n");
               ///  System.out.println(Arrays.toString(data));


               for (int i = 0; i < data.length - 1; i++) {
                   String[] ingredient = data[i].split(";");
                   map.put(ingredient[1], ingredient[0]);
                   ingredient_list.add(ingredient[0]);
                   ingredient_id_list.add(Integer.parseInt(ingredient[1]));
                   modelList.add(new IngredientModel(ingredient[0],ingredient[1]));


               }

           }
       });
        t.run();

      return modelList ;
   }



}
