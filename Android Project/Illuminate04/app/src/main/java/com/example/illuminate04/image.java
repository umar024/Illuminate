package com.example.illuminate04;

public class image {
    private String image;
    private int id;

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }


    public image(int id, String image) {
        this.image = image;
        this.id = id;
    }
}
