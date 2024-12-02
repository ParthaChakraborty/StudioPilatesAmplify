package com.studio.amplify.util;

import com.studio.amplify.model.TrackingListItem;

import java.util.ArrayList;
import java.util.List;

public interface OnItemClick {
    void onClickForCalculation (List<TrackingListItem> trackingListItemArrayList, TrackingListItem
            trackingListItem, String value, String operation, String spinnerSelection, boolean isDialogShown);
}