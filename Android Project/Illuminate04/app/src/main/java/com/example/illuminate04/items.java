package com.example.illuminate04;


public class items {
    private String image;
    private int id;
    private String appname;
    private String installs;
    private String score;
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

    public String getInstalls() {
        return installs;
    }

    public String getScore() {
        return score;
    }

    public String getSize() {
        return size;
    }

    public items(String image, int id, String appname, String installs, String score, String size) {
        this.image = image;
        this.id = id;
        this.appname = appname;
        this.installs = installs;
        this.score = score;
        this.size = size;
    }
}
