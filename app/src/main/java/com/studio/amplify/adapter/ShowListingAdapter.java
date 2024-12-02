package com.studio.amplify.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.model.ShowListItem;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Epshita Roy on 6/7/2019.
 */

public class ShowListingAdapter extends RecyclerView.Adapter<ShowListingAdapter.MyViewHolder> {
    private List<ShowListItem> showListItems;
    private Activity activity;

    public ShowListingAdapter(List<ShowListItem> showListItems, Activity activity) {
        this.activity = activity;
        this.showListItems = showListItems;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_listing_for_emoji_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ShowListItem showListListItemObject = showListItems.get(position);
        holder.tvShowChallengeTitle.setText(showListListItemObject.getOn_your_mind());
        holder.tvShowChallengeDate.setText(showListListItemObject.getEmoji_date());

        if (showListListItemObject.getEmoji_name().equalsIgnoreCase("Thrilled")) {
            holder.imgShowChallengeEmoji.setImageResource(R.drawable.smi_thril);
        } else if(showListListItemObject.getEmoji_name().equalsIgnoreCase("Frustrated")) {
            holder.imgShowChallengeEmoji.setImageResource(R.drawable.smi_frust);
        } else if(showListListItemObject.getEmoji_name().equalsIgnoreCase("Content")) {
            holder.imgShowChallengeEmoji.setImageResource(R.drawable.smi_cont);
        } else if(showListListItemObject.getEmoji_name().equalsIgnoreCase("Discouraged")) {
            holder.imgShowChallengeEmoji.setImageResource(R.drawable.smi_disc);
        }

    }


    @Override
    public int getItemCount() {
        return showListItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvShowChallengeTitle, tvShowChallengeDate;
        ImageView imgShowChallengeEmoji;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvShowChallengeTitle = (TextView) itemView.findViewById(R.id.tvShowChallengeTitle);
            tvShowChallengeDate = (TextView) itemView.findViewById(R.id.tvShowChallengeDate);
            imgShowChallengeEmoji = (ImageView) itemView.findViewById(R.id.imgShowChallengeEmoji);
        }
    }
}
