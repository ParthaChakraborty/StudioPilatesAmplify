package com.studio.amplify.adapter;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.model.TrackingListItem;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.LoginCredentials;
import com.studio.amplify.util.OnItemClick;
import com.studio.amplify.util.OnItemClickForTracking;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrackingListAdapter extends RecyclerView.Adapter<TrackingListAdapter.MyViewHolder> {
    private String spinnerSelectedValue = "0";
    private List<TrackingListItem> trackingItemArrayList;
    private Activity activity;
    private LoginCredentials loginCredentials;
    private OnItemClick mCallback;
    private String dateToSend;
    private String isLayoutDisableCheck;
    private CommonApi commonApi;
    private boolean isTargetPoint;


    public TrackingListAdapter(List<TrackingListItem> trackingItemArrayList, Activity activity, OnItemClick listener, String dateToSend, String isLayoutDisableCheck, boolean isTargetPoint) {
        this.activity = activity;
        this.trackingItemArrayList = trackingItemArrayList;
        this.mCallback = listener;
        this.dateToSend = dateToSend;
        this.isTargetPoint = isTargetPoint;
        this.isLayoutDisableCheck = isLayoutDisableCheck;
        loginCredentials = LoginCredentials.getInstance(activity);
        commonApi = new CommonApi(activity);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final int adapterPosition = position;
        final TrackingListItem trackingItemObject = trackingItemArrayList.get(position);
        holder.tvItemName.setText(trackingItemObject.getItem());
        Glide.with(activity).load(Urls.BASE_URL + trackingItemObject.getIcon()).into(holder.imageViewItemIcon);

        final List<String> myOptionList = new ArrayList<>(Arrays.asList(trackingItemArrayList.get(position).getOptions().split(",")));
        //////changed by Payal Garg on 20/6/2019////////////////////////////////////
       /*if(trackingItemArrayList.get(position).getItem().equals("Water")) {
            myOptionList.add(0, "0L");
        }
        else if(trackingItemArrayList.get(position).getItem().equals("")) {
            myOptionList.add(0,"0Hrs");
        }*/

        if (!trackingItemObject.getOptions().equalsIgnoreCase("")) {
            ArrayAdapter<String> arrayAdapterForOptionList = new ArrayAdapter<>(activity, R.layout.spinner_item, myOptionList);
            holder.spinnerForWater.setAdapter(arrayAdapterForOptionList);
            holder.spinnerForWater.setSelected(false); // must

            holder.checkImageForTracking.setVisibility(View.GONE);
            holder.llForSpinner.setVisibility(View.VISIBLE);

            if (trackingItemObject.getIsChecked().equals("1")) {
                holder.tvItemPoints.setText(trackingItemArrayList.get(position).getChossen_option_point() + "/" + trackingItemArrayList.get(position).getItem_point() + " pts");
                holder.spinnerForWater.setSelection(myOptionList.indexOf(trackingItemArrayList.get(position).getChossen_option()));
            } else {
                holder.tvItemPoints.setText("0" + "/" + trackingItemArrayList.get(position).getItem_point() + " pts");
            }
        } else if (trackingItemObject.getOptions().equalsIgnoreCase("")&& trackingItemObject.getIsChecked().equalsIgnoreCase("1")) {
            holder.checkImageForTracking.setImageResource(R.drawable.b_check);
            holder.tvItemPoints.setText(trackingItemObject.getItem_point() + "/" + trackingItemObject.getItem_point() + " pts");
        } else {
            holder.checkImageForTracking.setImageResource(R.drawable.b_uncheck);
            holder.tvItemPoints.setText("0/" + trackingItemObject.getItem_point() + " pts");
        }

        holder.checkImageForTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trackingItemArrayList.get(adapterPosition).getIsChecked().equalsIgnoreCase("0")) {
                    holder.checkImageForTracking.setImageResource(R.drawable.b_check);
                    trackingItemObject.setIsChecked("1");
                    trackingItemObject.setChossen_option(trackingItemArrayList.get(adapterPosition).getItem());
                    trackingItemObject.setChossen_option_point(trackingItemArrayList.get(adapterPosition).getItem_point());
                    trackingItemArrayList.set(adapterPosition,trackingItemObject);

                    holder.tvItemPoints.setText(trackingItemArrayList.get(adapterPosition).getItem_point() + "/" + trackingItemArrayList.get(adapterPosition).getItem_point() + " pts");
                    saveTrackingDetails(trackingItemArrayList.get(adapterPosition).getItem(),
                            /*String.valueOf(trackingItemArrayList.get(adapterPosition).getCam_chl_id()),*/
                            trackingItemArrayList.get(adapterPosition).getItem_point(),
                            "1", "", "");
                    holder.llForSpinner.setVisibility(View.GONE);
                    Log.e("***total pts pre add",String.valueOf(Constant.totalPoints));

                } else {
                    holder.tvItemPoints.setText("0/" + trackingItemArrayList.get(adapterPosition).getItem_point() + " pts");
                    holder.checkImageForTracking.setImageResource(R.drawable.b_uncheck);
                    trackingItemObject.setIsChecked("0");
                    trackingItemObject.setChossen_option("");
                    trackingItemObject.setChossen_option_point(trackingItemArrayList.get(adapterPosition).getItem_point());
                    trackingItemArrayList.set(adapterPosition, trackingItemObject);

                    saveTrackingDetails(trackingItemArrayList.get(adapterPosition).getItem(),
                            /*String.valueOf(trackingItemArrayList.get(adapterPosition).getCam_chl_id()),*/
                            trackingItemArrayList.get(adapterPosition).getItem_point(),
                            "0", "", "");
                    holder.llForSpinner.setVisibility(View.GONE);
                    Log.e("***total pts pre subtr",String.valueOf(Constant.totalPoints));
                }
                mCallback.onClickForCalculation(trackingItemArrayList, trackingItemObject,String.valueOf(Constant.totalPoints), "m", spinnerSelectedValue, isTargetPoint);
            }
        });


        holder.spinnerForWater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final List<String> myOptionListPoints = new ArrayList<>(Arrays.asList(trackingItemArrayList.get(adapterPosition).getOption_points().split(",")));
                myOptionListPoints.add(0, "0");
                Log.d("***myOptionsList",myOptionList.toString());
                String waterPoints = myOptionListPoints.get(position);
                //myOptionListPoints.add(0, "0");
                trackingItemArrayList.get(adapterPosition).setIsChecked("1"); // for 1st time sppinner selection
                trackingItemArrayList.get(adapterPosition).setChossen_option_point(waterPoints);
                trackingItemArrayList.get(adapterPosition).setChossen_option(trackingItemArrayList.get(adapterPosition).getItem());
                String waterValue = holder.spinnerForWater.getSelectedItem().toString();

                holder.tvItemPoints.setText(waterPoints + "/" + trackingItemArrayList.get(adapterPosition).getItem_point());
                ((TextView) parent.getChildAt(0)).setTextColor(activity.getResources().getColor(R.color.white));

                if (position != 0) {
                    saveTrackingDetails(trackingItemArrayList.get(adapterPosition).getItem(),
                            /*String.valueOf(trackingItemArrayList.get(adapterPosition).getCam_chl_id()),*/
                            trackingItemArrayList.get(adapterPosition).getItem_point(),
                            "1", waterValue, waterPoints);
                } else {
                    saveTrackingDetails(trackingItemArrayList.get(adapterPosition).getItem(),
                           /* String.valueOf(trackingItemArrayList.get(adapterPosition).getCam_chl_id()),*/
                            trackingItemArrayList.get(adapterPosition).getItem_point(),
                            "0", waterValue, waterPoints);
                }
                spinnerSelectedValue = waterPoints;
                mCallback.onClickForCalculation(trackingItemArrayList, trackingItemObject,String.valueOf(Constant.totalPoints), "p", waterPoints, isTargetPoint);
                       // mCallback.onClickForTracking(String.valueOf(Constant.totalPoints),waterPoints);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return trackingItemArrayList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tracking_list_items_row, parent, false);
        return new MyViewHolder(v);
    }

    private void saveTrackingDetails(String getItemName, /*String getChallengeID*/ String itemPoint, String isChecked, String chosenOption, String chosenOptionPoint) {
        JSONObject jsonObject = new JSONObject();
        try {
            String words[] = dateToSend.split(" ");
            String monthToSend = words[1]; // first two words
            String yearToSend = words[5];

            jsonObject.put("item", getItemName);
            jsonObject.put("user_id", loginCredentials.getUserId());
            jsonObject.put("item_point", itemPoint);
          //jsonObject.put("challange_id", getChallengeID);
            jsonObject.put("track_date", loginCredentials.getCurrentDate());
            if (!chosenOptionPoint.equals("")) {
                jsonObject.put("options", chosenOption);
                jsonObject.put("option_point", chosenOptionPoint);
            } else {
                jsonObject.put("chossen_option", "");
                jsonObject.put("chossen_option_point", "");
            }

            jsonObject.put("isChecked", isChecked);
            jsonObject.put("track_month", monthToSend);
            jsonObject.put("track_year", yearToSend);
            jsonObject.put("camchall_id", loginCredentials.getCAMP_CHAL_ID());
            jsonObject.put("camchall_status", loginCredentials.getCAMP_CHAL_TYPE());
            Log.d("***params", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.SAVE_TRACKING_PAGE_DETAIL + loginCredentials.getUserId(), GetServiceCall.TYPE_JSONOBJECT_POST, jsonObject) {
            @Override
            public void response(String response) {
                try {
                    Log.d("***save response", response);
                    JSONObject jsonObjectResponse = new JSONObject(response);
                    if (jsonObjectResponse.getString("sStatus").equalsIgnoreCase("1")) {
                        isTargetPoint = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error, String errorMsg) {

            }
        }.call();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemPoints;
        ImageView imageViewItemIcon;
        ImageView checkImageForTracking;
        LinearLayout llForSpinner;
        Spinner spinnerForWater;
        RelativeLayout rootForTrackingItemView;

        private MyViewHolder(View view) {
            super(view);
            tvItemName = view.findViewById(R.id.tvItemName);
            tvItemPoints = view.findViewById(R.id.tvItemPoints);
            imageViewItemIcon = view.findViewById(R.id.imageViewItemIcon);
            checkImageForTracking = view.findViewById(R.id.checkImageForTracking);
            llForSpinner = view.findViewById(R.id.llForSpinner);
            spinnerForWater = view.findViewById(R.id.spinnerForWater);
            rootForTrackingItemView = view.findViewById(R.id.rootForTrackingItemView);

            //for layout disability
            if (isLayoutDisableCheck.equalsIgnoreCase("1")) {
                commonApi.enableViews(rootForTrackingItemView, false);
            } else {
                commonApi.enableViews(rootForTrackingItemView, true);
            }
            //for layout disability
        }
    }
}
