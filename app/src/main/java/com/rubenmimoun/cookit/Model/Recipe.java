package com.rubenmimoun.cookit.Model;

public class Recipe {

    private double id ;
    private String title ;
    private String img_url ;
    private String instruction;

    public Recipe(double id, String title, String img_url) {
        this.id = id;
        this.title = title;
        this.img_url = img_url;
        this.instruction = "" ;
    }

    public Recipe(String title, String img_url) {
        this.title = title;
        this.img_url = img_url;

    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", img_url='" + img_url + '\'' +
                ", instruction='" + instruction + '\'' +
                '}';
    }
}
