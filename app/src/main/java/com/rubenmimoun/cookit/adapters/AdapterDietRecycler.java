package com.rubenmimoun.cookit.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rubenmimoun.cookit.R;
import com.rubenmimoun.cookit.utils.IO;
import com.rubenmimoun.cookit.utils.Preferences;


public class AdapterDietRecycler extends RecyclerView.Adapter<AdapterDietRecycler.ViewHolderDiet> {



    private Context context ;
    private RadioGroup lastCheckedRadioGroup = null;

    public AdapterDietRecycler(Context context ){
        this.context = context ;

    }

    @NonNull
    @Override
    public ViewHolderDiet onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v  =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diet,parent, false);
        return new ViewHolderDiet(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderDiet holder, final int position) {
        holder.setIsRecyclable(false);
        final String diet = IO.getInstance().diets_list.get(position);
        String diet_instruction = IO.getInstance().diets_explanation.get(position);

        holder.instructions.setText(diet_instruction);
        IO.getInstance().setPicked_diet("");

        RadioButton rb = new RadioButton(AdapterDietRecycler.this.context);
        rb.setFocusableInTouchMode(false);
        rb.setText(diet);
        holder.diet_box.addView(rb);

        Preferences preferences = Preferences.getInstance(context);


        holder.diet_box.setOnCheckedChangeListener((group, checkedId) -> {
            if (lastCheckedRadioGroup != null
                    && lastCheckedRadioGroup.getCheckedRadioButtonId()
                    != group.getCheckedRadioButtonId()
                    && lastCheckedRadioGroup.getCheckedRadioButtonId() != -1) {

                preferences.removeValue("diet");
                preferences.saveData("diet",rb.getText().toString());
                Log.i("Diet adapter", preferences.getData("diet"));

                Toast.makeText(AdapterDietRecycler.this.context,
                        preferences.getData("diet"),
                        Toast.LENGTH_SHORT).show();

                lastCheckedRadioGroup.clearCheck();
            }

            lastCheckedRadioGroup = group;



        });

    }

    @Override
    public int getItemCount() {
        return IO.getInstance().diets_list.size();
    }


    class ViewHolderDiet extends RecyclerView.ViewHolder {

        TextView instructions ;
        RadioGroup diet_box ;


        ViewHolderDiet(@NonNull View itemView) {
            super(itemView);


            instructions = itemView.findViewById(R.id.diet_instruction);
            diet_box = itemView.findViewById(R.id.checkbox_diet) ;


        }





    }

}
