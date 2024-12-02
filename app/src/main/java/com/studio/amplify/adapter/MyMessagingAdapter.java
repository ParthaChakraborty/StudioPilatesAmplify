package com.studio.amplify.adapter;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.studio.amplify.R;
import com.studio.amplify.activity.WebViewActivity;
import com.studio.amplify.model.MyMessagingItem;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.Urls;

import java.util.List;

public class MyMessagingAdapter extends RecyclerView.Adapter<MyMessagingAdapter.MyViewHolder> {
    private List<MyMessagingItem> myMessagingItems;
    private Activity activity;
    private CommonApi commonApi;
    String userType;

    public MyMessagingAdapter(List<MyMessagingItem> myMessagingItems, Activity activity) {
        this.activity = activity;
        this.myMessagingItems = myMessagingItems;
        commonApi = new CommonApi(activity);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_messaging_for_notification, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final MyMessagingItem messagingItem = myMessagingItems.get(position);
        holder.tvNotificationTitle.setText(messagingItem.getMessage());
        holder.tvNotificationDate.setText(messagingItem.getMessage_date());

        holder.rl_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = myMessagingItems.get(holder.getAdapterPosition()).getUser_message_id();
                Constant.FROM_FRAGMENT = 0;
                Bundle bundle = new Bundle();
                bundle.putString("urlKey", Urls.ADMIN_NOTIFICATION_DETAILS + userType);
                bundle.putString("titleForHeader", "Message");
                commonApi.openNewScreen(WebViewActivity.class, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return myMessagingItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotificationTitle;
        TextView tvNotificationDate;
        RelativeLayout rl_notification;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNotificationTitle = (TextView) itemView.findViewById(R.id.tvNotificationTitle);
            tvNotificationDate = (TextView) itemView.findViewById(R.id.tvNotificationDate);
            rl_notification = (RelativeLayout) itemView.findViewById(R.id.rl_notification);
        }
    }
}
