package com.rubenmimoun.cookit.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rubenmimoun.cookit.R;
import com.rubenmimoun.cookit.utils.IO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdapterIntolerances extends RecyclerView.Adapter<AdapterIntolerances.ViewHolderIntolerances>{

    private Context mContext ;
    SparseBooleanArray itemStateArray= new SparseBooleanArray();

    public AdapterIntolerances(Context context){
        this.mContext = context ;
    }

    @NonNull
    @Override
    public ViewHolderIntolerances onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_intolerance,parent,false);
        return new ViewHolderIntolerances(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderIntolerances holder, int position) {

        final String intolerance_string = IO.getInstance().intolerances.get(position) ;
        holder.intolerance_box.setText(intolerance_string);
        IO.getInstance().setPicked_intolerances("nothing");

        holder.intolerance_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentText = IO.getInstance().getPicked_intolerances().equals("nothing") ? "" : IO.getInstance().getPicked_intolerances();
                    if(holder.intolerance_box.isChecked()){
                        IO.getInstance().setPicked_intolerances(currentText += intolerance_string +" ");
                    }else{

                        String [] intolerances = IO.getInstance().getPicked_intolerances().split(" ");
                        List <String>into_list =new ArrayList<>();

                        currentText = "" ;
                        for (int i = 0; i <intolerances.length ; i++) {
                            if(!intolerances[i].equals(intolerance_string)){
                                into_list.add(intolerances[i]);
                            }else{
                                continue;
                            }
                        }

                        for ( String into: into_list) {
                            currentText += into +" ";
                        }

                        IO.getInstance().setPicked_intolerances(currentText);

                    }




            }
        });
    }

    @Override
    public int getItemCount() {
        return IO.getInstance().intolerances.size();
    }


    class ViewHolderIntolerances extends RecyclerView.ViewHolder{

        private CheckBox intolerance_box ;

        public ViewHolderIntolerances(@NonNull View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
            intolerance_box = itemView.findViewById(R.id.checkbox_intolerances) ;
        }






    }


}
