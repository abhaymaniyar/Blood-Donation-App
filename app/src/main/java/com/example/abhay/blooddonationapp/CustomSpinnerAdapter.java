package com.example.abhay.blooddonationapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhay on 23/7/17.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private List<String> groups;
    private int itemToHide;
    public CustomSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, List<String> groups) {
        super(context, resource);
        this.groups = groups;
    }

    public void setItemToHide(int itemToHide){
        this.itemToHide = itemToHide;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = null;
        if (position == itemToHide){
            TextView textView = new TextView(getContext());
            textView.setVisibility(View.GONE);
            textView.setHeight(0);
            v = textView;
            v.setVisibility(View.GONE);
        }else {
            v = super.getDropDownView(position, convertView, parent);
        }
        return v;
    }
}
