package com.studio.amplify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.studio.amplify.R;
import com.studio.amplify.model.ShoppingListItem;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<ShoppingListItem> mListData;

    public ViewPagerAdapter(Context context, List<ShoppingListItem> listDate) {
        mContext = context;
        mListData = listDate;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.view_pager_item_row, container, false);
        ShoppingListItem shoppingListItem = mListData.get(position);
        WebView webViewForViewPager = view.findViewById(R.id.webViewForViewPager);

        webViewForViewPager.loadData(shoppingListItem.getContent(), "text/html", "UTF-8");

        container.addView(view);
        return view;
    }
}