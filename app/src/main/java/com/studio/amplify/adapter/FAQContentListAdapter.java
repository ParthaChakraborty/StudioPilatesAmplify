package com.studio.amplify.adapter;

import android.app.Activity;
import android.graphics.Typeface;
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
import com.studio.amplify.model.FAQContentListItem;
import com.studio.amplify.util.CommonApi;

import java.util.List;

public class FAQContentListAdapter extends RecyclerView.Adapter<FAQContentListAdapter.MyViewHolder> {

    private Typeface font;
    private List<FAQContentListItem> faqContentListItems;
    private Activity activity;
    private CommonApi commonApi;

    FAQContentListAdapter(List<FAQContentListItem> faqContentListItems, Activity activity) {
        this.activity = activity;
        this.faqContentListItems = faqContentListItems;
        commonApi = new CommonApi(activity);
        font = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronRegularWebfront());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final FAQContentListItem faqContentListItem = faqContentListItems.get(position);
        holder.tvFAQContentTitle.setText(faqContentListItem.getTitle());


        holder.rlForFAQTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("content", faqContentListItem.getContent());
                b.putString("titleForHeader", "FAQ");
                commonApi.openNewScreen(WebViewActivity.class, b);
            }
        });

    }

    @Override
    public int getItemCount() {
        return faqContentListItems.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_content_list_items_row, parent, false);
        return new MyViewHolder(v);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvFAQContentTitle;
        RelativeLayout rlForFAQTitle;
        View viewAfterRl;

        private MyViewHolder(View view) {
            super(view);
            tvFAQContentTitle = view.findViewById(R.id.tvFAQContentTitle);
            rlForFAQTitle = view.findViewById(R.id.rlForFAQTitle);
            viewAfterRl = view.findViewById(R.id.viewAfterRl);
        }
    }

}

