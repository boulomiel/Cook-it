package com.rubenmimoun.cookit.Model;

public class IngredientModel {

    String name ;
    String imageLink;
    double id ;

    public IngredientModel(String name) {
        this.name = name;
    }

    public IngredientModel(String name, String imageLink) {
        this.name = name;
        this.imageLink = imageLink;
    }

    public IngredientModel(String name,  double id) {
        this.name = name;
        this.id = id;
    }

    public IngredientModel(String name, String imageLink, double id) {
        this.name = name;
        this.imageLink = imageLink;
        this.id = id;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
