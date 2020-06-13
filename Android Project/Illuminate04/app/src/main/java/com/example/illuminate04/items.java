package com.example.illuminate04;

import android.graphics.drawable.Drawable;

public class items {
    private String image;
    private int id;
    private String appname;
    private String rating;
    private int reviews;
    private String size;

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public String getAppname() {
        return appname;
    }

    public String getRating() {
        return rating;
    }

    public int getReviews() {
        return reviews;
    }

    public String getSize() {
        return size;
    }

    public items(String image, int id, String appname, String rating, int reviews, String size) {
        this.image = image;
        this.id = id;
        this.appname = appname;
        this.rating = rating;
        this.reviews = reviews;
        this.size = size;
    }
}
