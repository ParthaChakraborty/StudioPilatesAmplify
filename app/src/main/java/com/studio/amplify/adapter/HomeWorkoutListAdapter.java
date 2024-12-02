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
import com.studio.amplify.model.HomeWorkoutListItem;
import com.studio.amplify.util.CommonApi;

import java.util.List;

public class HomeWorkoutListAdapter extends RecyclerView.Adapter<HomeWorkoutListAdapter.MyViewHolder> {

    private Typeface fontBold, font;
    private List<HomeWorkoutListItem> homeWorkoutListItemList;
    private Activity activity;
    private CommonApi commonApi;
    private String titleForHeaderToSet;

    public HomeWorkoutListAdapter(List<HomeWorkoutListItem> homeWorkoutListItemList, Activity activity, String titleForHeaderToSet) {
        this.activity = activity;
        this.homeWorkoutListItemList = homeWorkoutListItemList;
        this.titleForHeaderToSet = titleForHeaderToSet;
        commonApi = new CommonApi(activity);
        fontBold = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronBoldWebfront());
        font = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronRegularWebfront());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final HomeWorkoutListItem homeWorkoutListItem = homeWorkoutListItemList.get(position);

        holder.tvForTitle.setTypeface(fontBold);
        holder.tvForTitle.setText(homeWorkoutListItem.getTitle());

        Glide.with(activity).load(homeWorkoutListItem.getImage()).into(holder.imageForWorkout);


        holder.rlForViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b1 = new Bundle();
                b1.putString("webViewContentUrl", homeWorkoutListItem.getUrl());
                b1.putString("titleForHeader", titleForHeaderToSet);
                commonApi.openNewScreen(WebViewActivity.class, b1);
            }
        });

    }


    @Override
    public int getItemCount() {
        return homeWorkoutListItemList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_workout_list_items_row, parent, false);
        return new MyViewHolder(v);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvForTitle;
        ImageView imageForWorkout;
        RelativeLayout rlForViews;

        private MyViewHolder(View view) {
            super(view);
            tvForTitle = view.findViewById(R.id.tvForTitle);
            imageForWorkout = view.findViewById(R.id.imageForWorkout);
            rlForViews = view.findViewById(R.id.rlForViews);
        }
    }

}

