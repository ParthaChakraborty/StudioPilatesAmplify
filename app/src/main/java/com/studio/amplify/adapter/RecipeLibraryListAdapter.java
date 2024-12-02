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
import com.studio.amplify.model.RecipeLibraryListItem;
import com.studio.amplify.util.CommonApi;

import java.util.List;

public class RecipeLibraryListAdapter extends RecyclerView.Adapter<RecipeLibraryListAdapter.MyViewHolder> {

    private Typeface font;
    private List<RecipeLibraryListItem> recipeLibraryListItemList;
    private Activity activity;
    private CommonApi commonApi;

    public RecipeLibraryListAdapter(List<RecipeLibraryListItem> recipeLibraryListItemList, Activity activity) {
        this.activity = activity;
        this.recipeLibraryListItemList = recipeLibraryListItemList;
        commonApi = new CommonApi(activity);
        font = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronRegularWebfront());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final RecipeLibraryListItem recipeLibraryListItemObject = recipeLibraryListItemList.get(position);
        holder.tvRecipeInformation.setText(recipeLibraryListItemObject.getTitle());
        holder.tvRecipeInformation.setTypeface(font);
        holder.tvRecipeTimingTitle.setText(recipeLibraryListItemObject.getMeal_timing());
        holder.tvRecipeTimingTitle.setTypeface(font);
        Glide.with(activity).load(recipeLibraryListItemObject.getImage()).into(holder.imageForRecipeItem);

        holder.rlForRecipeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlKeyToSend = recipeLibraryListItemObject.getUrl();
                Bundle b = new Bundle();
                b.putString("urlKey", urlKeyToSend);
                b.putString("titleForHeader", "Recipe Library");
                commonApi.openNewScreen(WebViewActivity.class, b);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeLibraryListItemList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_library_list_items_row, parent, false);
        return new MyViewHolder(v);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecipeInformation;
        ImageView imageForRecipeItem;
        TextView tvRecipeTimingTitle;
        RelativeLayout rlForRecipeList;

        private MyViewHolder(View view) {
            super(view);
            tvRecipeInformation = view.findViewById(R.id.tvRecipeInformation);
            imageForRecipeItem = view.findViewById(R.id.imageForRecipeItem);
            tvRecipeTimingTitle = view.findViewById(R.id.tvRecipeTimingTitle);
            rlForRecipeList = view.findViewById(R.id.rlForRecipeList);
        }
    }

}

