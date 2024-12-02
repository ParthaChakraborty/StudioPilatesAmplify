package com.studio.amplify.model;

public class ChallengeCampaignListItem {
    String heading;
    String startDate;
    String endDate;
    String classBuy;

    public String getClassOpted() {
        return classOpted;
    }

    public void setClassOpted(String classOpted) {
        this.classOpted = classOpted;
    }

    String classOpted;
    int id;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassBuy() {
        return classBuy;
    }

    public void setClassBuy(String classBuy) {
        this.classBuy = classBuy;
    }
}
