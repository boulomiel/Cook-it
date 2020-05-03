package com.rubenmimoun.cookit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rubenmimoun.cookit.Model.Recipe;
import com.rubenmimoun.cookit.R;
import com.rubenmimoun.cookit.RecipeChoiceActivity;

import java.util.List;

public class AdapterRecipe extends RecyclerView.Adapter<AdapterRecipe.ViewHolderRecipe> {

        private Context context ;
        private List<Recipe> recipeList  ;
        private AppCompatActivity activity ;



        public AdapterRecipe(Context context,AppCompatActivity activity ,List<Recipe> reccipeList) {
            this.activity = activity ;
            this.context = context ;
            this.recipeList = reccipeList ;

        }

        @NonNull
        @Override
        public ViewHolderRecipe onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v  =LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item,parent,false);

            return new ViewHolderRecipe(v) ;
        }



        @Override
        public void onBindViewHolder(@NonNull ViewHolderRecipe holder, int position) {
            Recipe recipe = recipeList.get(position) ;

            holder.name_recipe.setText(recipe.getTitle());
            if(context != null)
            Glide.with(context).load(recipe.getImg_url()).into(holder.img_recipe);

            holder.cardView.setOnClickListener(v -> {
                int id = (int) recipe.getId();

                ((RecipeChoiceActivity)activity).showFragment
                        (String.valueOf(id),recipe.getTitle() , recipe.getImg_url());

            });
        }

        @Override
        public int getItemCount() {
            return recipeList.size();
        }

        class ViewHolderRecipe extends RecyclerView.ViewHolder {

            ImageView img_recipe ;
            TextView name_recipe ;
            RelativeLayout cardView ;

            ViewHolderRecipe(@NonNull View itemView) {
                super(itemView);

                img_recipe =  itemView.findViewById(R.id.img_recipe);
                name_recipe = itemView.findViewById(R.id.name_recipe);
                cardView = itemView.findViewById(R.id.recipe_view) ;
            }


        }


    }


