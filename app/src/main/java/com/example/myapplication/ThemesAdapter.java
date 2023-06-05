package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ThemesAdapter extends ArrayAdapter<ThemeInfo> {

    public ThemesAdapter(@NonNull Context context) {
        super(context, R.layout.theme_list_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.theme_list_item, null);
        }
        TextView tv = convertView.findViewById(R.id.theme_name);
        ThemeInfo t = getItem(position);
        tv.setText(t.name);
        return  convertView;
    }
}
