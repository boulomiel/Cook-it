package com.rubenmimoun.cookit.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rubenmimoun.cookit.Model.IngredientModel;
import com.rubenmimoun.cookit.R;
import com.rubenmimoun.cookit.utils.IO;
import com.rubenmimoun.cookit.utils.PickedIngredientManager;
import com.rubenmimoun.cookit.utils.SelectableAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterIngredients extends SelectableAdapter<AdapterIngredients.ViewHolderIngredients> {

    private Context context ;
    private List<IngredientModel>ingredient_model_list ;
    private int LEFT = 2 ;
    private int RIGHT = 1;

    private final ArrayList<Integer> selected = new ArrayList<>();

    private PickedIngredientManager manager ;



    public AdapterIngredients(Activity  activity , Context context , List<IngredientModel> list){

        this.ingredient_model_list =list ;
        this.context =  context ;

        manager = new PickedIngredientManager(activity,context,ingredient_model_list);
    }



    @NonNull
    @Override
    public ViewHolderIngredients onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v ;
        if(viewType == RIGHT){
             v  =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scroll_ingredient,parent, false);
        }else {
             v  =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scroll_ingredient_right,parent, false);
        }



        return new ViewHolderIngredients(v);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderIngredients holder, final int position) {
        holder.setIsRecyclable(false);
        final int[] count = {0};

            IngredientModel model = ingredient_model_list.get(position) ;
            holder.onBind(model);

        holder.titleLeft.setText(model.getName());

        Glide.with(context).load(model.getImageLink());
        Glide.with(context).load(model.getImageLink()).into(holder.imageLeft);


        holder.container.setOnClickListener(v -> {



            count[0]++ ;

            if(  count[0] % 2 == 1){
                holder.container.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPicked));
                holder.container.setTag(model.getId());
                IO.getInstance().ingredient_list.add(ingredient_model_list.get(position).getName());
                String ingredient =  ingredient_model_list.get(position).getName() ;
                manager.addToChosenList(ingredient);

            }else {
                holder.container.setBackgroundColor(ContextCompat.getColor(context,R.color.colorBase));
                IO.getInstance().ingredient_list.remove(ingredient_model_list.get(position).getName());
                String ingredient =  ingredient_model_list.get(position).getName() ;
                manager.removeFromChosenList(ingredient);



            }


        });

    }




    private IngredientModel removeItem(int position) {
        final IngredientModel model = ingredient_model_list.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    private void addItem(int position, IngredientModel model) {
        ingredient_model_list.add(position, model);
        notifyItemInserted(position);
    }

    private void moveItem(int fromPosition, int toPosition) {
        final IngredientModel model = ingredient_model_list.remove(fromPosition);
        ingredient_model_list.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<IngredientModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<IngredientModel> newModels) {
        for (int i = ingredient_model_list.size() - 1; i >= 0; i--) {
            final IngredientModel model = ingredient_model_list.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<IngredientModel> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final IngredientModel model = newModels.get(i);
            if (!ingredient_model_list.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<IngredientModel> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final IngredientModel model = newModels.get(toPosition);
            final int fromPosition = ingredient_model_list.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    @Override
    public int getItemCount() {
        return ingredient_model_list.size();
    }

    @Override
    public int getItemViewType(int position) {


        if(position % 2 == 0 ){
            return LEFT ;
        }else {
            return RIGHT ;
        }



        }

    public PickedIngredientManager getManager() {
        return manager;
    }

    public static class ViewHolderIngredients extends RecyclerView.ViewHolder  implements View.OnClickListener {

        ImageView imageLeft ;
        TextView titleLeft ;
        RelativeLayout container ;
        View selectedOverlay;



        ViewHolderIngredients(@NonNull View itemView) {
            super(itemView);

            imageLeft =itemView.findViewById(R.id.item_img_left) ;

            titleLeft =itemView.findViewById(R.id.item_img_left_title) ;

            container =itemView.findViewById(R.id.item_container);

            selectedOverlay = itemView.findViewById(R.id.selected_overlay);




        }

        void onBind(IngredientModel model){
            titleLeft.setText(model.getName());
        }

        @Override
        public void onClick(View v) {

        }


    }

}//class

