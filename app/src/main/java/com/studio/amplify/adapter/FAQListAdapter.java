package com.studio.amplify.adapter;

import android.app.Activity;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.amplify.R;
import com.studio.amplify.model.FAQListItem;
import com.studio.amplify.util.CommonApi;

import java.util.List;

public class FAQListAdapter extends RecyclerView.Adapter<FAQListAdapter.MyViewHolder> {

    Typeface fontBold, font;
    private List<FAQListItem> faqListItemList;
    private Activity activity;
    private CommonApi commonApi;

    public FAQListAdapter(List<FAQListItem> faqListItemList, Activity activity) {
        this.activity = activity;
        this.faqListItemList = faqListItemList;
        commonApi = new CommonApi(activity);
        fontBold = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronBoldWebfront());
        font = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronRegularWebfront());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final FAQListItem faqListItemObject = faqListItemList.get(position);
        holder.tvCategory.setText(faqListItemObject.getCategory());

        FAQContentListAdapter faqContentListAdapter = new FAQContentListAdapter(faqListItemObject.getFaqContentListItemArrayList(), activity);
        holder.rvForFAQSubList.setAdapter(faqContentListAdapter);
    }

    @Override
    public int getItemCount() {
        return faqListItemList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_list_items_row, parent, false);
        return new MyViewHolder(v);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;
        RecyclerView rvForFAQSubList;


        private MyViewHolder(View view) {
            super(view);
            tvCategory = view.findViewById(R.id.tvCategory);
            rvForFAQSubList = view.findViewById(R.id.rvForFAQSubList);

        }
    }

}
