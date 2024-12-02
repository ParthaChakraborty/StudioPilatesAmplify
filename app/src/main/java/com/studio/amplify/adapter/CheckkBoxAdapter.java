package com.studio.amplify.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.amplify.R;
import com.studio.amplify.model.FAQListItem;
import com.studio.amplify.util.CommonApi;

import java.util.List;

public class CheckkBoxAdapter extends RecyclerView.Adapter<CheckkBoxAdapter.MyViewHolder> {
    Typeface fontBold, font;
    private Activity activity;
    private CommonApi commonApi;

    public CheckkBoxAdapter(Activity activity) {
        this.activity = activity;
        commonApi = new CommonApi(activity);
        fontBold = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronBoldWebfront());
        font = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronRegularWebfront());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkbox_list, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTrackTitle;
        CheckBox checkbox;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tvTrackTitle = view.findViewById(R.id.tvTrackTitle);
            checkbox = view.findViewById(R.id.checkbox);
        }
    }
}
