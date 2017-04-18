package com.example.vietd.cinema;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vietd on 04/18/2017.
 */

public class item_menu_adpter extends ArrayAdapter {
    public Activity atc;
    public int layout;
    public ArrayList<item_menu_info> data;

    public item_menu_adpter(Activity act, int layout, ArrayList<item_menu_info> data) {
        super(act ,layout,data);
        this.atc = act;
        this.layout = layout;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = atc.getLayoutInflater().inflate(layout,null);

        ImageView img_icon = (ImageView) v.findViewById(R.id.img_icon_menu);
        img_icon.setImageResource(data.get(position).getImage());

        TextView tv_icon = (TextView) v.findViewById(R.id.tv_item_menu);
        tv_icon.setText(data.get(position).getName());

        return v;
    }
}
