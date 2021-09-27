package com.example.greenfield.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

public class ClienteListviewAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<Puntaje> items;
    private Context mContext;

    public ClienteListviewAdapter(Activity context, List<Puntaje> items) {
        super();
        this.mContext = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


}



