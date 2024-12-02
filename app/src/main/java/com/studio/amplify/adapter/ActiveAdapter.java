package com.studio.amplify.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.studio.amplify.R;
import com.studio.amplify.model.UpcomingChallengeAndCampaignsItem;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.LoadingInterface;
import com.studio.amplify.util.LoginCredentials;
import com.studio.amplify.util.OnClassItemClick;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;
import com.studio.amplify.volleyparser.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.MyViewHolder> implements OnClassItemClick {
    private List<UpcomingChallengeAndCampaignsItem> upcomingChallengeAndCampaignsItemList;
    private Activity activity;
    private CommonApi commonApi;
    String type, userId, selected_class="";
    private LoadingInterface loadingInterface;
    private LoginCredentials loginCredentials;


    public ActiveAdapter(String userId, ArrayList<UpcomingChallengeAndCampaignsItem> upcomingChallengeAndCampaignsItemList, Activity activity, String type, LoadingInterface loadingInterface) {
        this.activity = activity;
        this.type = type;
        this.userId = userId;
        this.upcomingChallengeAndCampaignsItemList = upcomingChallengeAndCampaignsItemList;
        commonApi = new CommonApi(activity);
        loginCredentials = LoginCredentials.getInstance(activity);
        this.loadingInterface = loadingInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,  int position) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.active_chl_cam_list_row, parent, false);
        return new ActiveAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {
             final UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem = upcomingChallengeAndCampaignsItemList.get(position);
            //Glide.with(activity).load(myChallengeAndCampaignsItem.getImage()).into(holder.imageForMealItem);
            holder.tv_chlng_cam_title.setText(upcomingChallengeAndCampaignsItem.getHeading());

            final SpannableStringBuilder sbFrom = new SpannableStringBuilder(activity.getString(R.string.from_text) + " " + upcomingChallengeAndCampaignsItem.getStartDate());
            final StyleSpan bssFrom = new StyleSpan(android.graphics.Typeface.BOLD);
            sbFrom.setSpan(bssFrom, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tvFromDate.setText(sbFrom);

            final SpannableStringBuilder sbTo = new SpannableStringBuilder(activity.getString(R.string.to_text) + " " + upcomingChallengeAndCampaignsItem.getEndDate());
            final StyleSpan bssTo = new StyleSpan(android.graphics.Typeface.BOLD);
            sbTo.setSpan(bssTo, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tvToDate.setText(sbTo);

            if (upcomingChallengeAndCampaignsItem.getStatus() == 0) {
                holder.btn_Chlng_cmpn_participate.setText(R.string.participate);
                holder.btn_Chlng_cmpn_participate.setVisibility(View.VISIBLE);
                holder.btn_Chlng_cmpn_join.setVisibility(View.GONE);
                holder.btn_data_update.setVisibility(View.GONE);
            } else {
                holder.btn_Chlng_cmpn_participate.setVisibility(View.GONE);
                holder.btn_Chlng_cmpn_join.setText(R.string.view_history);
                holder.btn_Chlng_cmpn_join.setVisibility(View.VISIBLE);
                holder.btn_data_update.setVisibility(View.VISIBLE);
                holder.btn_data_update.setText(R.string.update);
               // holder.btn_Chlng_cmpn_join.setText(R.string.participate);

              /*  Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedCurrentDate = df.format(c);

                if (isValidDate(upcomingChallengeAndCampaignsItem.getStartDate(), formattedCurrentDate, upcomingChallengeAndCampaignsItem.getEndDate())) {
                    holder.btn_Chlng_cmpn_join.setText(R.string.view_history);
                    holder.btn_data_update.setVisibility(View.VISIBLE);
                    holder.btn_data_update.setText(R.string.update);
                }
*/
            }


            holder.btn_Chlng_cmpn_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.btn_Chlng_cmpn_participate.setVisibility(View.GONE);
                  //  callClassList(type, upcomingChallengeAndCampaignsItemList.get(position).getId(), upcomingChallengeAndCampaignsItemList.get(position).getGroup_id(),upcomingChallengeAndCampaignsItem);
                    participateNow(type, upcomingChallengeAndCampaignsItemList.get(position).getId(), upcomingChallengeAndCampaignsItemList.get(position).getGroup_id(),upcomingChallengeAndCampaignsItem);
                  //  loadingInterface.onRedirect(upcomingChallengeAndCampaignsItem, Integer.parseInt(userId), type);
                    /*if (upcomingChallengeAndCampaignsItem.getStatus() == 0) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.myDialog);
                        alertDialogBuilder.setMessage(R.string.participation_text);
                        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                participateNow(type, upcomingChallengeAndCampaignsItemList.get(position).getId());
                                loadingInterface.onRedirect(upcomingChallengeAndCampaignsItem, Integer.parseInt(userId), type);
                            }
                        });

                        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    } else {
                        loadingInterface.onRedirect(upcomingChallengeAndCampaignsItem, Integer.parseInt(userId), type);*/

                    }

            });

            holder.btn_Chlng_cmpn_participate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callClassList(type, upcomingChallengeAndCampaignsItemList.get(position).getId(), upcomingChallengeAndCampaignsItemList.get(position).getGroup_id(),upcomingChallengeAndCampaignsItem);
                }
            });

        holder.btn_data_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btn_data_update.setVisibility(View.VISIBLE);
                loadingInterface.onRedirect(upcomingChallengeAndCampaignsItem, Integer.parseInt(userId), type);
            }
        });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return upcomingChallengeAndCampaignsItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_chlng_cam_title, tvFromDate, tvToDate, btn_Chlng_cmpn_join, btn_data_update, btn_Chlng_cmpn_participate;
         ;
        CardView cardViews;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tv_chlng_cam_title = view.findViewById(R.id.tv_chlng_cam_title);
            btn_Chlng_cmpn_join = view.findViewById(R.id.btn_Chlng_cmpn_join);
            cardViews = view.findViewById(R.id.cardViews);
            tvFromDate = view.findViewById(R.id.tvFromDate);
            tvToDate = view.findViewById(R.id.tvToDate);
            btn_data_update = view.findViewById(R.id.btn_data_update);
            btn_Chlng_cmpn_participate = view.findViewById(R.id.btn_Chlng_cmpn_participate);

        }
    }

    public void participateNow(String camp_chall_type, int camp_chall_id, final int id_group,UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem) {
        commonApi.showProgressDialog("Please Wait...");
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", userId);
            object.put("camchall_id", camp_chall_id);
            object.put("camchall_status", camp_chall_type);
            object.put("selected_class",selected_class);
            object.put("group_id", id_group);
           // Log.d("TAG", "parObject" + object);
            System.out.println(" participateUrl : " + Urls.PARTICIPATE_CAMPAIGNS_CHALLENGES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.PARTICIPATE_CAMPAIGNS_CHALLENGES, GetServiceCall.TYPE_JSONOBJECT_POST, object) {
            @Override
            public void response(String response) {
                Log.d("***participate resp", response);
                try {
                        JSONObject jsonObject = new JSONObject(response);
                        String group_name = jsonObject.getString("group_name");
                        loginCredentials.setUserGroup(String.valueOf(id_group));
                        loginCredentials.setUserGroupName(group_name);
                        commonApi.dismissProgressDialog();
                        loadingInterface.onRedirect(upcomingChallengeAndCampaignsItem, Integer.parseInt(userId), camp_chall_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                commonApi.dismissProgressDialog();
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
                commonApi.dismissProgressDialog();
            }
        }.call();
    }


    public boolean isValidDate(String startDate,String currentDate,String endDate){
        Boolean isValidDates = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);
            Date currDate = sdf.parse(currentDate);
            if (currDate.before(stDate) && currDate.after(eDate)) {
                isValidDates = false;
            } else {
                isValidDates = true;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return isValidDates;
    }

    public void callClassList(String ch_ca_type, int ch_ca_id, int group_id,UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem) {

        commonApi.showProgressDialog("Please Wait...");
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setContentView(R.layout.classlist_view);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        RecyclerView rvForClassList = bottomSheetDialog.findViewById(R.id.rvForClassList);
        ImageView img_close = bottomSheetDialog.findViewById(R.id.img_close);

        String s = Urls.GET_CLASS_LIST+ch_ca_id+"/"+ch_ca_type;
        List<String> classArrayList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            commonApi.dismissProgressDialog();
                            JSONObject jsonObject = new JSONObject(response);
                            String listValues = jsonObject.getString("classlists");
                            JSONArray listItems = new JSONArray(listValues);

                            for (int i = 0; i < listItems.length(); i++) {
                                String str = listItems.getString(i);
                                classArrayList.add(str);
                            }
                            if(classArrayList.size()>0 && !classArrayList.get(0).equals("")) {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
                                rvForClassList.setLayoutManager(layoutManager);
                                ClassListAdapter classListAdapter = new ClassListAdapter(classArrayList, activity, ActiveAdapter.this);
                                rvForClassList.setAdapter(classListAdapter);
                                bottomSheetDialog.show();
                            } else {
                                bottomSheetDialog.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        commonApi.dismissProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                commonApi.dismissProgressDialog();
            }
        });
        MyApplication.getInstance().addToRequestQueue(stringRequest);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                participateNow(type, ch_ca_id, group_id,upcomingChallengeAndCampaignsItem);
               // loadingInterface.onRedirect(upcomingChallengeAndCampaignsItem, Integer.parseInt(userId), type);
            }
        });
    }

    @Override
    public void onItemClick(String data) {
        selected_class = data;
        Log.d("Selected: ",selected_class);
    }

}
