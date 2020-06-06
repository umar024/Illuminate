package com.example.illuminate00;

public class items {
    private int image;
    private int id;
    private String appname;
    private String rating;
    private int reviews;
    private String size;

    public int getImage() {
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

    public items(int image, int id, String appname, String rating, int reviews, String size) {
        this.image = image;
        this.id = id;
        this.appname = appname;
        this.rating = rating;
        this.reviews = reviews;
        this.size = size;
    }
}
