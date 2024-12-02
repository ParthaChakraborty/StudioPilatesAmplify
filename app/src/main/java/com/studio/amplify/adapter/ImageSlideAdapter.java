package com.studio.amplify.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.studio.amplify.R;

public class ImageSlideAdapter extends SliderViewAdapter<ImageSlideAdapter.Holder> {
    int[] images;
    String [] sliderHeading;
    String [] sliderBody;

    public ImageSlideAdapter(int[] images, String [] sliderHeading, String [] sliderBody){

        this.images = images;
        this.sliderHeading = sliderHeading;
        this.sliderBody = sliderBody;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carousel_image_list,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.im_slider.setImageResource(images[position]);
        holder.tvCaroHeading.setText(sliderHeading[position]);
        holder.tvCaroBody.setText(sliderBody[position]);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class Holder extends SliderViewAdapter.ViewHolder {
        ImageView im_slider;
        TextView tvCaroHeading, tvCaroBody;

        public Holder(View view) {
            super(view);

             im_slider = view.findViewById(R.id.im_slider);
             tvCaroHeading = view.findViewById(R.id.tvCaroHeading);
             tvCaroBody = view.findViewById(R.id.tvCaroBody);
        }
    }
}
