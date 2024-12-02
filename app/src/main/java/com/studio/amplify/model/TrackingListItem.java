package com.studio.amplify.model;

public class TrackingListItem {

    private String item;
    private String item_id;
    private String item_point;
    private String options;
    private String option_points;
    private String icon;
    private String isChecked;
    private String chossen_option;
    private String chossen_option_point;
    private String challange_id;
    private int cam_chl_id;
    private String cam_chl_status;

    public int getCam_chl_id() {
        return cam_chl_id;
    }

    public void setCam_chl_id(int cam_chl_id) {
        this.cam_chl_id = cam_chl_id;
    }

    public String getCam_chl_status() {
        return cam_chl_status;
    }

    public void setCam_chl_status(String cam_chl_status) {
        this.cam_chl_status = cam_chl_status;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_point() {
        return item_point;
    }

    public void setItem_point(String item_point) {
        this.item_point = item_point;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOption_points() {
        return option_points;
    }

    public void setOption_points(String option_points) {
        this.option_points = option_points;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getChossen_option() {
        return chossen_option;
    }

    public void setChossen_option(String chossen_option) {
        this.chossen_option = chossen_option;
    }

    public String getChossen_option_point() {
        return chossen_option_point;
    }

    public void setChossen_option_point(String chossen_option_point) {
        this.chossen_option_point = chossen_option_point;
    }

    public String getChallange_id() {
        return challange_id;
    }

    public void setChallange_id(String challange_id) {
        this.challange_id = challange_id;
    }
}
