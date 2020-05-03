package com.rubenmimoun.cookit.Model;

import java.util.List;

public class CookingStep {

    private int number  ;
    private String instruction ;
    private List<String> ingredientList ;

    public CookingStep(int number, String instruction, List<String> ingredientList) {
        this.number = number;
        this.instruction = instruction;
        this.ingredientList = ingredientList;
    }

    public List<String> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<String> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toString() {
        return "CookingStep{" +
                "number=" + number +
                ", instruction='" + instruction + '\'' +
                ", ingredientList=" + ingredientList.toString() +
                '}';

    }
}
