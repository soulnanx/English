package com.example.renan.english.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.renan.english.R;
import com.example.renan.english.pojo.MenuItemPojo;

import java.util.List;

public class MenuAdapter extends ArrayAdapter<MenuItemPojo> {

    private int resource;

    public MenuAdapter(Context context, int resource, List<MenuItemPojo> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        MenuItemPojo itemMenu = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
            itemHolder = new ItemHolder(convertView);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }

        if (itemMenu != null) {
            itemHolder.name.setText(itemMenu.getIdStringResource());
            itemHolder.image.setImageResource(itemMenu.getIdDrawableResource());
        }

        return convertView;
    }

    public class ItemHolder {
        TextView name;
        ImageView image;

        ItemHolder(View view) {
            this.name = (TextView) view.findViewById(R.id.item_txt_name);
            this.image = (ImageView) view.findViewById(R.id.item_img);
        }
    }
}
