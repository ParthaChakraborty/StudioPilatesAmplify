package com.studio.amplify.model;

import java.util.ArrayList;

public class FAQListItem {

    ArrayList<FAQContentListItem> faqContentListItemArrayList;
    private String category;

    public ArrayList<FAQContentListItem> getFaqContentListItemArrayList() {
        return faqContentListItemArrayList;
    }

    public void setFaqContentListItemArrayList(ArrayList<FAQContentListItem> faqContentListItemArrayList) {
        this.faqContentListItemArrayList = faqContentListItemArrayList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
