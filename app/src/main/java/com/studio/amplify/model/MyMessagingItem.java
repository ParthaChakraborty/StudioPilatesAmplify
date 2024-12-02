package com.studio.amplify.model;

public class MyMessagingItem {
    private String user_message_id;
    private String message;
    private String message_date;


    public String getMessage() {
        return message;
    }

    public String getUser_message_id() {
        return user_message_id;
    }


    public void setUser_message_id(String user_message_id) {
        this.user_message_id = user_message_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_date() {
        return message_date;
    }

    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }
}
