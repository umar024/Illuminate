package com.example.illuminate04;

public class installeditem {

    private String appname;
    private String size;
    private String version;
    private String installedOn;
    private android.graphics.drawable.Drawable image;

    public String getAppname() {
        return appname;
    }

    public String getVersion() {
        return version;
    }

    public String getInstalledOn() {
        return installedOn;
    }

    public String getSize() {
        return size;
    }

    public android.graphics.drawable.Drawable getImage() {
        return image;
    }

    public installeditem(android.graphics.drawable.Drawable image, String appname, String size, String version, String InstalledOn) {

        this.installedOn = InstalledOn;
        this.image = image;
        this.appname = appname;
        this.size = size;
        this.version = version;
    }
}
