package com.studio.amplify.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.studio.amplify.R;
public class ImageAdapter extends PagerAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;

    public ImageAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
      //  return sliderImages.length;
        return sliderHeading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==  object;
    }
    private int[] sliderImages = {
            R.drawable.carousel_1, R.drawable.carousel_2, R.drawable.carousel_3,R.drawable.carousel_4,
            R.drawable.carousel_5, R.drawable.carousel_6
    };

    private String [] sliderHeading = {
            "", "MOTIVATION + INSPIRATION", "RECIPES + MEAL PLAN", "TRACKING",
            "COMMUNITY CONNECTION",  "HOME WORKOUTS"
    };

    private String [] sliderBody = {
            "Challenge Starts 2 August", "Health articles, videos and tips to keep you on track",
            "A complete 4-week meal featuring 85+ nutritionist designed recipes",
            "Plan and track your progress throughout the Challenge to stay focussed on your goals",
            "Connect with fellow Challengers and stay up to date with studio news",
            "Pilates mat, circuit and stretch workouts"
    };


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.carousel_image_list, container, false);
        ImageView im_slider = view.findViewById(R.id.im_slider);
        TextView tvCaroHeading = view.findViewById(R.id.tvCaroHeading);
        TextView tvCaroBody = view.findViewById(R.id.tvCaroBody);
        im_slider.setImageResource(sliderImages[position]);
        tvCaroHeading.setText(sliderHeading[position]);
        tvCaroBody.setText(sliderBody[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // container.removeView((ImageView) object);
        View view = (View) object;
        container.removeView(view);
    }
}