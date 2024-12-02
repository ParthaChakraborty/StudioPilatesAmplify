package com.studio.amplify.model;

public class GoalItem {
    private boolean isChecked;
    private String title_goal;
    private String goal_id;

    public String getGoalId() {
        return goal_id;
    }

    public void setGoalId(String goalid) {
        goal_id = goalid;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title_goal;
    }

    public void setTitle(String title) {
        title_goal = title;
    }


}
