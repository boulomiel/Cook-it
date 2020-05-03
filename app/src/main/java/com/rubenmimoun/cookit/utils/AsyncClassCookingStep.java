package com.rubenmimoun.cookit.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.rubenmimoun.cookit.FinalRecipeActivity;
import com.rubenmimoun.cookit.Model.CookingStep;
import com.rubenmimoun.cookit.Model.Recipe;
import com.rubenmimoun.cookit.R;
import com.rubenmimoun.cookit.RecipeChoiceActivity;

import java.util.List;

public class AsyncClassCookingStep extends AsyncTask<String, Integer, List<CookingStep>> {

    @SuppressLint("StaticFieldLeak")
    private Context context ;
    private ProgressDialog dialog ;
    @SuppressLint("StaticFieldLeak")
    private Activity activity ;

    public AsyncClassCookingStep(Activity activity){
        this.activity =activity ;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }

    @Override
    protected List<CookingStep> doInBackground(String... strings) {

        String parsed =  JsonParser.downloadUrl(strings[0]);

        assert parsed != null;

    if(parsed.contains("number")){
           return JsonParser.stepsToObject(parsed) ;
       }

        return null ;

    }

    @Override
    protected void onPostExecute(List<CookingStep> cookingSteps) {
        super.onPostExecute(cookingSteps);
        if(cookingSteps != null)
        ((FinalRecipeActivity)activity).fillInstructions(cookingSteps);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);


    }
}
