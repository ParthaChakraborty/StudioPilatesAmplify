package com.studio.amplify.model;

public class RecipeLibraryListItem {

    private String meal_timing;
    private String title;
    private String url;
    private String image;
    private String publish_date;

    public String getMeal_timing() {
        return meal_timing;
    }

    public void setMeal_timing(String meal_timing) {
        this.meal_timing = meal_timing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }
}
