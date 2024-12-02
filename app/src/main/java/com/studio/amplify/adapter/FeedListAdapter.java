package com.studio.amplify.adapter;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.activity.WebViewActivity;
import com.studio.amplify.pojo.feed_list.FeedList;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.LoginCredentials;

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.MyViewHolder> {
    private Typeface font;
    private Activity activity;
    private LoginCredentials loginCredentials;
    private CommonApi commonApi;
    private String flagToHideView;
    Integer flagno;
    FeedList feedList;
    private String flagToCheckWorkoutView, titleToSendForWorkoutView;

    public FeedListAdapter(FeedList feedList, Activity activity, int flagno) {
        this.activity = activity;
        this.flagno=flagno;
        this.feedList = feedList;
        loginCredentials = LoginCredentials.getInstance(activity);
        commonApi = new CommonApi(activity);
        font = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronRegularWebfront());
    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meals_list_items_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        try {
            if (flagno ==1) {
                Glide.with(activity).load(feedList.getCardioPlans().get(i).getImage()).into(holder.imageForMealItem);
                holder.tvMealInformation.setText(feedList.getCardioPlans().get(i).getTitle());
                holder.tvMealInformation.setTypeface(font);
            }
            else if(flagno==3) {
                Glide.with(activity).load(feedList.getHomeWorkouts().get(i).getImage()).into(holder.imageForMealItem);
                holder.tvMealInformation.setText(feedList.getHomeWorkouts().get(i).getTitle());
                holder.tvMealInformation.setTypeface(font);
            }
            else if(flagno==2) {
                Glide.with(activity).load(feedList.getHealthyTips().get(i).getImage()).into(holder.imageForMealItem);
                holder.tvMealInformation.setText(feedList.getHealthyTips().get(i).getTitle());
                holder.tvMealInformation.setTypeface(font);
            }
            else if (flagno ==4) {
                Glide.with(activity).load(feedList.getPartnerOffers().get(i).getImage()).into(holder.imageForMealItem);
                holder.tvMealInformation.setText(feedList.getPartnerOffers().get(i).getTitle());
                holder.tvMealInformation.setTypeface(font);
            }

       /* if (flagToHideView.equalsIgnoreCase("1")) {
            holder.tvMealTimingTitle.setTypeface(font);
            holder.tvMealTimingTitle.setVisibility(View.VISIBLE);

           // holder.tvMealTimingTitle.setText(mealListingItemObject.getMeal_timing());
        } else {
            holder.tvMealTimingTitle.setVisibility(View.GONE);
        }*/

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        holder.rlForMealsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //String urlKeyToSend = plans.get(i).getUrl();
                    Bundle b = new Bundle();
                    if (flagno == 1) {
                        b.putString("webViewContentUrl", feedList.getCardioPlans().get(i).getUrl());
                        b.putString("titleForHeader", "Cardio Plans");

                    } else if (flagno == 2) {
                        b.putString("webViewContentUrl", feedList.getHealthyTips().get(i).getUrl());
                        b.putString("titleForHeader", "Health and Wellness Blogs");

                    } else if (flagno == 3) {
                        b.putString("webViewContentUrl", feedList.getHomeWorkouts().get(i).getUrl());
                        b.putString("titleForHeader", "Home Workouts");

                    } else if (flagno == 4) {
                        b.putString("webViewContentUrl", feedList.getPartnerOffers().get(i).getUrl());
                        b.putString("titleForHeader", "Partner Offers");
                    }
                /*else {
                    b.putString("urlKey", urlKeyToSend);
                }*/

                    commonApi.openNewScreen(WebViewActivity.class, b);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int count=0;
        try {

            if (flagno == 1) {
               count= feedList.getCardioPlans().size();

            } else if (flagno == 2) {
                count= feedList.getHealthyTips().size();

            } else if (flagno == 3) {
                count= feedList.getHomeWorkouts().size();

            } else if (flagno == 4) {
                count= feedList.getPartnerOffers().size();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageForMealItem;
        TextView tvMealInformation;
        TextView tvMealTimingTitle;
        RelativeLayout rlForMealsList;

        public MyViewHolder( View view) {
            super(view);
            tvMealTimingTitle = view.findViewById(R.id.tvMealTimingTitle);
            imageForMealItem = view.findViewById(R.id.imageForMealItem);
            tvMealInformation = view.findViewById(R.id.tvMealInformation);
            rlForMealsList = view.findViewById(R.id.rlForMealsList);

        }
    }
}
