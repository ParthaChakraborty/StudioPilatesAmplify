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
import com.studio.amplify.model.HealthTipsListItem;
import com.studio.amplify.util.CommonApi;

import java.util.List;

public class HealthtipsListAdapter extends RecyclerView.Adapter<HealthtipsListAdapter.MyViewHolder> {

    private Typeface fontBold, font;
    private List<HealthTipsListItem> healthTipsListItemList;
    private Activity activity;
    private CommonApi commonApi;


    public HealthtipsListAdapter(List<HealthTipsListItem> healthTipsListItemList, Activity activity) {
        this.activity = activity;
        this.healthTipsListItemList = healthTipsListItemList;
        commonApi = new CommonApi(activity);
        fontBold = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronBoldWebfront());
        font = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronRegularWebfront());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final HealthTipsListItem healthTipsListItemObject = healthTipsListItemList.get(position);
        Glide.with(activity).load(healthTipsListItemObject.getImage()).into(holder.imageForHealthTips);
        holder.tvForTitle.setText(healthTipsListItemObject.getTitle());
        holder.tvForTitle.setTypeface(font);
        holder.tvForDate.setText(healthTipsListItemObject.getPost_date());
        holder.tvForDate.setTypeface(font);

        holder.rlForViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlKeyToSend = healthTipsListItemObject.getUrl();
                Bundle b = new Bundle();
                b.putString("urlKey", urlKeyToSend);
                b.putString("titleForHeader", "Nutrition Tips");
                commonApi.openNewScreen(WebViewActivity.class, b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return healthTipsListItemList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.health_list_items_row, parent, false);
        return new MyViewHolder(v);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageForHealthTips;
        TextView tvForTitle;
        TextView tvForDate;
        RelativeLayout rlForViews;

        private MyViewHolder(View view) {
            super(view);
            tvForDate = view.findViewById(R.id.tvForDate);
            imageForHealthTips = view.findViewById(R.id.imageForHealthTips);
            tvForTitle = view.findViewById(R.id.tvForTitle);
            rlForViews = view.findViewById(R.id.rlForViews);
        }
    }

}
