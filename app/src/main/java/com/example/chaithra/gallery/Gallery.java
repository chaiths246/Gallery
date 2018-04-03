package com.example.chaithra.gallery;

/**
 * Created by chaithra on 3/26/18.
 */

public class Gallery {
    private  String image;
    private  String titile;

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getTitile() {

        return titile;
    }

    public Gallery(String image, String title)
    {
        this.image=image;
        this.titile=title;

    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {

        return image;
    }
}
