package com.studio.amplify.model;

public class ShoppingListItem {
    private String week_title;
    private String content;
    private String is_selected;
    private String sub_cat;

    public String getWeek_title() {
        return week_title;
    }

    public void setWeek_title(String week_title) {
        this.week_title = week_title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_selected() {
        return is_selected;
    }

    public String getSub_cat() {
        return sub_cat;
    }

    public void setSub_cat(String sub_cat) {
        this.sub_cat = sub_cat;
    }

    public void setIs_selected(String is_selected) {
        this.is_selected = is_selected;
    }

}
