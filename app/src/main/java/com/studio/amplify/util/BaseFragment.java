package com.studio.amplify.util;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class BaseFragment extends Fragment {
    //public CommonApi commonApi;
    public LoginCredentials loginCredentials;

    //    static {
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // commonApi = CommonApi.getInstance(getActivity());
        loginCredentials = LoginCredentials.getInstance(getActivity());
    }
}

