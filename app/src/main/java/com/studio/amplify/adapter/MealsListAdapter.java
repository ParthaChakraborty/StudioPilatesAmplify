package com.studio.amplify.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.activity.WebViewActivity;
import com.studio.amplify.model.MealListItem;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.LoginCredentials;

import java.util.List;

public class MealsListAdapter extends RecyclerView.Adapter<MealsListAdapter.MyViewHolder> {

    private Typeface font;
    private List<MealListItem> mealListingItemList;
    private Activity activity;
    private LoginCredentials loginCredentials;
    private CommonApi commonApi;
    private String flagToHideView;
    private String flagToCheckWorkoutView, titleToSendForWorkoutView;

    public MealsListAdapter(List<MealListItem> mealListingItemList, Activity activity, String flagToHideView, String flagToCheckWorkoutView, String titleToSendForWorkoutView) {
        this.activity = activity;
        this.flagToHideView = flagToHideView;
        this.flagToCheckWorkoutView = flagToCheckWorkoutView;
        this.titleToSendForWorkoutView = titleToSendForWorkoutView;
        this.mealListingItemList = mealListingItemList;
        loginCredentials = LoginCredentials.getInstance(activity);
        commonApi = new CommonApi(activity);
        font = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronRegularWebfront());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final MealListItem mealListingItemObject = mealListingItemList.get(position);
        Glide.with(activity).load(mealListingItemObject.getImage()).into(holder.imageForMealItem);
        if(mealListingItemObject.getTitle().equals("")) {
            holder.rlForMealsList.setVisibility(View.GONE);
        }
        if (flagToHideView.equalsIgnoreCase("1")) {
            holder.tvMealTimingTitle.setTypeface(font);
            holder.tvMealTimingTitle.setVisibility(View.VISIBLE);

            holder.tvMealTimingTitle.setText(mealListingItemObject.getMeal_timing());
        } else {
            holder.tvMealTimingTitle.setVisibility(View.GONE);
        }
        holder.tvMealInformation.setText(mealListingItemObject.getTitle());
        holder.tvMealInformation.setTypeface(font);


        holder.rlForMealsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlKeyToSend = mealListingItemObject.getUrl();
                Bundle b = new Bundle();
                if (flagToCheckWorkoutView.equalsIgnoreCase("1")) {
                    b.putString("webViewContentUrl", urlKeyToSend);

                } else {
                    b.putString("urlKey", urlKeyToSend);
                }
                b.putString("titleForHeader", titleToSendForWorkoutView);
                commonApi.openNewScreen(WebViewActivity.class, b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealListingItemList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meals_list_items_row, parent, false);
        return new MyViewHolder(v);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageForMealItem;
        TextView tvMealInformation;
        TextView tvMealTimingTitle;
        RelativeLayout rlForMealsList;

        private MyViewHolder(View view) {
            super(view);
            tvMealTimingTitle = view.findViewById(R.id.tvMealTimingTitle);
            imageForMealItem = view.findViewById(R.id.imageForMealItem);
            tvMealInformation = view.findViewById(R.id.tvMealInformation);
            rlForMealsList = view.findViewById(R.id.rlForMealsList);

        }
    }

}
