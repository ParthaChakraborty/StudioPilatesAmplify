package com.studio.amplify.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.amplify.R;
import com.studio.amplify.fragment.FeedFragment;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.OnClassItemClick;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.MyViewHolder> {

    private List<String> classListItem;
    private Activity activity;
    private OnClassItemClick onclassitemclick;
    private int selectedPosition = -1;

    public ClassListAdapter(List<String> classListItem, Activity activity) {
        this.activity = activity;
        this.classListItem = classListItem;
        this.onclassitemclick = new FeedFragment();
    }
    public ClassListAdapter(List<String> classListItem, Activity activity, OnClassItemClick onClassItemClick) {
        this.activity = activity;
        this.classListItem = classListItem;
        this.onclassitemclick = onClassItemClick;
    }
    @Override
    public void onBindViewHolder(@NonNull final ClassListAdapter.MyViewHolder holder, final int position) {
        final String classListingItemObject = classListItem.get(position);

           holder.tvClassName.setText(classListingItemObject);

        if (selectedPosition == position) {
            holder.imgEnable.setVisibility(View.VISIBLE);
            Log.d("hhj",classListingItemObject);
            onclassitemclick.onItemClick(classListingItemObject);

        } else {
            holder.imgEnable.setVisibility(View.GONE);
        }


        holder.tvClassName.setOnClickListener(view -> {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition);
            holder.imgEnable.setVisibility(View.VISIBLE);
            onclassitemclick.onItemClick(classListingItemObject);
            selectedPosition = holder.getAdapterPosition();
           });


    }

    @Override
    public int getItemCount() {
        return classListItem.size();
    }

    @NonNull
    @Override
    public ClassListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.classlist_item_view, parent, false);
        return new ClassListAdapter.MyViewHolder(v);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassName;
        ImageView imgEnable;


        private MyViewHolder(View view) {
            super(view);
            tvClassName = view.findViewById(R.id.tvClassName);
            imgEnable   = view.findViewById(R.id.imgEnable);

        }
    }
}
