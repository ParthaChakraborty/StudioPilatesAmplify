package com.studio.amplify.util;

import com.studio.amplify.model.ChallengeCampaignListItem;
import com.studio.amplify.model.TrackingListItem;
import com.studio.amplify.model.UpcomingChallengeAndCampaignsItem;

import java.util.List;

public interface LoadingInterface {
    void onRedirect(UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem, int userId, String type);

    void onRedirectFromMyCampaignsAndChallenges(ChallengeCampaignListItem challengeCampaignListItem, int userId, String type);

    void onRediredectToMainActivity();

}
