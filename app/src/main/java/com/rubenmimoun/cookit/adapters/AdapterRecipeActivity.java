package com.rubenmimoun.cookit.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rubenmimoun.cookit.Model.CookingStep;
import com.rubenmimoun.cookit.Model.Recipe;
import com.rubenmimoun.cookit.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AdapterRecipeActivity extends  RecyclerView.Adapter<AdapterRecipeActivity.ViewHolder>{

    private List<CookingStep> cookingSteps ;

    public AdapterRecipeActivity(List<CookingStep>cookingSteps){
        this.cookingSteps = cookingSteps ;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int stepNumber= cookingSteps.get(position).getNumber();
       String instructionsString = cookingSteps.get(position).getInstruction() ;

        List<String>list =  new ArrayList<>(cookingSteps.get(position).getIngredientList());

        StringBuilder builder = new StringBuilder();
        builder.append("Ingredients : ");
        Set<String> set = new LinkedHashSet<>(list);
        for ( String i: set) {
            builder.append(i);
            if(!i.equals(list.get(list.size()-2))){
                builder.append(", ");
            }else{
                builder.append(".");
            }
        }

        if(position == 0  ){
            holder.ingredientList.setText(builder.toString());
        }

        if( position == 7){
            holder.ingredientList.setText("");
        }



        holder.stepNumber.setText(String.valueOf(stepNumber));
        holder.instruction.setText(instructionsString);

        Log.i("stepNumber",""+stepNumber);




    }

    @Override
    public int getItemCount() {
        return cookingSteps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView stepNumber ;
        TextView instruction;
        TextView ingredientList ;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            stepNumber = itemView.findViewById(R.id.step_number_recipe);
            instruction = itemView.findViewById(R.id.instruction_activity_recipe) ;
            ingredientList =itemView.findViewById(R.id.ingredient_item_recipe);

        }
    }
}
