package com.studio.amplify.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.activity.MainActivity;
import com.studio.amplify.activity.RecipeLibraryActivity;
import com.studio.amplify.activity.ShoppingListActivity;
import com.studio.amplify.fragment.MealsFragment;
import com.studio.amplify.model.MealPlanItem;
import com.studio.amplify.util.CommonApi;

import java.util.ArrayList;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.MyViewHolder> {
    private ArrayList<MealPlanItem> mealsPlanList;
    Context context;

    public MealPlanAdapter(Context context,ArrayList<MealPlanItem> mealsPlanList) {
        this.context = context;
        this.mealsPlanList = mealsPlanList;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_meal_plan, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {
            if(mealsPlanList.get(position).getLinkType().equals("internal")) {
                holder.tvForMeals.setText(mealsPlanList.get(position).getHeading());
                Glide.with(context).load(mealsPlanList.get(position).getImage()).into(holder.imgForMeals);
            } else if (mealsPlanList.get(position).getLinkType().equals("")){
                holder.tvForMeals.setText(mealsPlanList.get(position).getHeading());
                Glide.with(context).load(mealsPlanList.get(position).getImage()).into(holder.imgForMeals);
            }

            holder.imgForMeals.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mealsPlanList.get(position).getLinkType().equals("internal")) {
                        //Urls.GET_DATA_FOR_MEALS_LIST=Urls.GET_DATA_FOR_MEALS_LIST.replace("link",mealsPlanList.get(position).getLink());
                        ((FragmentActivity) v.getContext()).getFragmentManager().beginTransaction()
                                .replace(R.id.rootLayout, new MealsFragment()).commit();
                    } else if(mealsPlanList.get(position).getLinkType().equals("external")) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mealsPlanList.get(position).getLink()));
                        context.startActivity(browserIntent);
                    }
                }
            });

            if(holder.tvForMeals.getText().toString().equals("Shopping List")) {
                holder.tvForMeals.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent send = new Intent(context, ShoppingListActivity.class);
                        context.startActivity(send);
                    }
                });

            }

            if(holder.tvForMeals.getText().toString().equals("Recipe Library")) {
                holder.tvForMeals.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent send = new Intent(context, RecipeLibraryActivity.class);
                        context.startActivity(send);
                    }
                });

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
       return mealsPlanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgForMeals;
        TextView tvForMeals;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgForMeals = itemView.findViewById(R.id.imgForMeals);
            tvForMeals =  itemView.findViewById(R.id.tvForMeals);

        }
    }

}
