package com.studio.amplify.model;

public class MealListItem {
    private String meal_timing;
    private String title;
    private String url;
    private String image;

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
}
