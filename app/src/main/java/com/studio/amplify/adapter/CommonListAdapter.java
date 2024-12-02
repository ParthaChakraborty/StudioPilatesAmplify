package com.studio.amplify.adapter;

import android.app.Activity;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.studio.amplify.R;
import com.studio.amplify.model.CommonListItem;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.LoginCredentials;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommonListAdapter extends RecyclerView.Adapter<CommonListAdapter.MyViewHolder> {
    private ArrayList<CommonListItem> commonListItemsList;

    private LoginCredentials loginCredentials;
    private CommonApi commonApi;
    private Activity activity;
    private Typeface font;
    private String flagForWebService;

    public CommonListAdapter(ArrayList<CommonListItem> commonListItemsList, Activity activity, String flagForWebService) {
        this.commonListItemsList = commonListItemsList;
        this.activity = activity;
        this.flagForWebService = flagForWebService;
        commonApi = new CommonApi(activity);
        font = Typeface.createFromAsset(activity.getAssets(), commonApi.getAileronRegularWebfront());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.common_list_item_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return commonListItemsList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CommonListItem commonListItem = commonListItemsList.get(position);

        holder.tvTitle.setText(commonListItem.getName());
        holder.tvTitle.setTypeface(font);
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flagForWebService.equalsIgnoreCase("1")) {
                    updateData("country", commonListItem.getId());
                } else if (flagForWebService.equalsIgnoreCase("2")) {
                    updateData("studio", commonListItem.getId());
                } else if(flagForWebService.equalsIgnoreCase("3")){
                    updateData("timezone", commonListItem.getName());
                } else if(flagForWebService.equalsIgnoreCase("4")){
                    updateData("challange", commonListItem.getName());
                } /*else if(flagForWebService.equalsIgnoreCase("4")) {
                    updateData("group", commonListItem.getId());
                }*/
                commonApi.closeKeyBoard();
            }

            private void updateData(final String urlKey, final String s) {
                commonApi.showProgressDialog("Please wait..");
                JSONObject jsonObject = new JSONObject();
                try {
                    loginCredentials = LoginCredentials.getInstance(activity);
                    jsonObject.put("user_id", loginCredentials.getUserId());

                    if (flagForWebService.equalsIgnoreCase("1")) {
                        jsonObject.put("country_code", s);
                    } else if (flagForWebService.equalsIgnoreCase("2")) {
                        jsonObject.put("studio", s);
                    } else if(flagForWebService.equalsIgnoreCase("3")){
                        jsonObject.put("timezone_name", s);
                    } else if(flagForWebService.equalsIgnoreCase("4")){
                        jsonObject.put("challange", s);
                    } /*else if(flagForWebService.equalsIgnoreCase("4")) {
                        jsonObject.put("group", s);
                    }*/

                    System.out.println("jsonObject" + jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new GetServiceCall(Urls.EDIT_PROFILE_UPDATE + urlKey, GetServiceCall.TYPE_JSONOBJECT_POST, jsonObject) {
                    @Override
                    public void response(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                                Toast.makeText(activity, jsonObject.getString("sMessage"), Toast.LENGTH_SHORT).show();
                                if (flagForWebService.equalsIgnoreCase("1")) {
                                    loginCredentials.setUserCountryCode(s);
                                } else if (flagForWebService.equalsIgnoreCase("2")) {
                                    loginCredentials.setUserStudioName(commonListItem.getName());
                                } else if(flagForWebService.equalsIgnoreCase("3")){
                                    loginCredentials.setUserTimeZone(s);
                                } else if(flagForWebService.equalsIgnoreCase("4")){
                                    loginCredentials.setChallenge(s);
                                }
                                /*else if(flagForWebService.equalsIgnoreCase("4")) {
                                    loginCredentials.setUserGroupName(commonListItem.getName());
                                }*/
                            }
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
        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}


